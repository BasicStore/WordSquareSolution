package com.naimuri.client;
import com.naimuri.engine.IWordSquareProducer;
import com.naimuri.exceptions.*;
import com.naimuri.model.WordSquare;
import com.naimuri.model.WordSquareResults;
import com.naimuri.model.WordSquareSearch;
import com.naimuri.utils.SysProperties;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import org.apache.log4j.Logger;


/**
 * Class to manage the view of the wordSquareSolution app from the command line
 */
public class CommandLineView implements IView {

	private Logger LOG = Logger.getLogger(CommandLineView.class.getName());
	
	protected IWordSquareProducer model;
	
	private BufferedReader reader;
	
	private static List<WordSquareResults> previousSingleSearches = new ArrayList<WordSquareResults>();
	
	/**
	 * Command line view constructor 
	 * @param model - the model from which to query word square requests
	 */
	public CommandLineView(IWordSquareProducer model) {
		this.model = model;
		reader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	
	/**
	 * Method to manage the retrieval of word square instructions from the user and pass back the results 
	 * @return void 
	 */
	public void manageWordSquareRequests() {
		System.out.println("Welcome to the Word Square Calculator\n");
		try {
			boolean makeWordSqReq = true;
			while (makeWordSqReq) {
				try {
					makeWordSquareRequest();
					System.out.println("\nWould you like to make a word square request? Y/N");
					makeWordSqReq = reader.readLine().toUpperCase().equals("Y") ? true : false;
					LOG.debug("Make word square request: " + makeWordSqReq);
				} catch(InvalidInputException e) {
					makeWordSqReq = continueAfterMinorIssue(e.getMessage());
				} catch(WordSquareCalculationException e) {
					makeWordSqReq = continueAfterMinorIssue(e.getMessage());
				} 
			}
		}
		catch(IOException e) {
			System.out.println("\nSorry, there is a problem reading your input - " + e.getMessage());
		}
		
		System.out.println("\nThank you and goodbye!");
	}
	
	
	private boolean continueAfterMinorIssue(String msg) throws IOException {
		System.out.println(msg + ". Would you like to try again? Y/N");
		LOG.debug("An error occurred: " + msg);
		return reader.readLine().toUpperCase().equals("Y") ? true : false;
	}
	
	
	/**
	 * Method to gather data from the user to make a word square request, and pass back the results
	 * @return void 
	 * @throws IOException - the system encountered a problem reading the dictionary or user input
	 * @throws InvalidInputException - the word square criteria supplied by the user is somehow invalid
	 * @throws WordSquareCalculationException - an exception occurred during the word square calculation process
	 */
	public void makeWordSquareRequest() throws IOException, InvalidInputException, WordSquareCalculationException {
		int sqWords = getNumSquareWords();
		String approvedLetters = getApprovedLetters(sqWords*sqWords);
		boolean isSingleSearch = !isFullSearch();
		LOG.debug("Making a word square request of " + sqWords + " and approved letters " + 
				approvedLetters + ". Single search: " + isSingleSearch);
		System.out.println("Please wait while we calculating the word square options. This may take a short while!\n");
		WordSquareResults lastWordSqResults = getWordSquares(sqWords, approvedLetters, isSingleSearch);
		presentWordSquares(lastWordSqResults);
	}
	
	
	private int getNumSquareWords() throws IOException, InvalidInputException {
		System.out.println("Please enter the number of square words:");
		String numWdSquStr = reader.readLine();
		try {
			int maxWordSquares = getProperty("max_word_squares");
			int numWordSquares = Integer.parseInt(numWdSquStr);
			if (numWordSquares < 1 || numWordSquares > maxWordSquares) {
				throw new InvalidInputException("The number of square words must be between 1 and " + maxWordSquares);
			}
			return numWordSquares;
		} catch(NumberFormatException e) {
			throw new InvalidInputException("Could not read properties from config file");
		} 
	}
	
	
	private String getApprovedLetters(int expectedLetters) throws IOException, InvalidInputException {
		System.out.println("Now enter the " + expectedLetters + " letters available to words in the word square:");
		String letters = reader.readLine();
		
		// check there are the right number of input letters
		if (letters.length() != expectedLetters) {
			throw new InvalidInputException("The number of word square letters supplied does not match the specified number "
					+ "of words squared, which is " + expectedLetters);
		}
		
		// check input letters for word squares are valid
		for (char c : letters.toCharArray()) {
			if(!Character.isLetter(c)) {
				throw new InvalidInputException("Not all letters entered are proper characters");
	        }
		}
		
		return letters;
	}
	
	
	private boolean isFullSearch() throws IOException {
		System.out.println("Press key 'Y' to search for all possible word squares, or another key to find just 1 appropriate word square");
		String ans = reader.readLine();
		return ans.toLowerCase().equals("y") ? true : false;
	}
	
	
	private static int getProperty(String key) throws IOException, InvalidInputException {
		try {
			String prop = SysProperties.getProperty(key);
			return Integer.parseInt(SysProperties.getProperty(key));
		} catch(NumberFormatException e) {
			throw new IOException("Could not read properties from config file");
		}
	}
	
	
	/**
	 * Generates the word squares given the given search criteria
	 * @param specifiedSquareWords - the number of word squares
	 * @param specifiedLetters - the letters available for use
	 * @param isSingleSearch - identifies if this search is to return 1 word square rather than all possible word squares
	 * @return WordSquareResults - the word square results information
	 * @throws WordSquareCalculationException
	 */
	public WordSquareResults getWordSquares(int specifiedSquareWords, String specifiedLetters, boolean isSingleSearch) throws WordSquareCalculationException {
		WordSquareSearch srch = new WordSquareSearch(specifiedSquareWords, specifiedLetters, isSingleSearch);
		setNextSearchIndex(srch, isSingleSearch);
		WordSquareResults results = model.generateSquareWords(srch);
		LOG.debug("Search results received: " + results.getNumWdSqDetected());
		
		// record all previous single searches
		if (isSingleSearch) {
			previousSingleSearches.add(results);
		}
		
		return results;
	}
	

	// indicates which index to return from the result set of the next single search
	// if this is a full search, set this index to -1
	private void setNextSearchIndex(WordSquareSearch currSrch, boolean isSingleSearch) {
		if (!isSingleSearch) {
			// this is a full search, so indicate this with index 1
			currSrch.setNextWordSqSearchIndex(-1);
			LOG.debug("The next search will return all possible word squares");
		} else {
			boolean prevSearchExists = false;
			
			// if a previous single search of this type has been requested before, then use the next index 
			for (WordSquareResults prevSrch : previousSingleSearches) {
				if (prevSrch.getSpecifiedSquareWords() == currSrch.getSpecifiedSquareWords()
						&& prevSrch.getSpecifiedLetters().equals(currSrch.getSpecifiedLetters())) {
					prevSearchExists = true;
					LOG.debug("This is a single search which has been performed before");
					
					// start again at index 0 if running out of elements
					currSrch.setNextWordSqSearchIndex(prevSrch.getNextWordSqSearchIndex());
					LOG.debug("The next search will return one word square, but if possible a different word square than last time");
				}
			}
			
			// if a previous single search of this type has not been done before, start from index 0
			if (!prevSearchExists) {
				currSrch.setNextWordSqSearchIndex(0);
				LOG.debug("The next search will return one word square, the first in the result list");
			}
		}
	}
	
	
	
	private void presentWordSquares(WordSquareResults wordSqResults) throws IOException, InvalidInputException {
		List<WordSquare> wordSquares = wordSqResults.getWordSquares();
		int wSqPerPage = getProperty("page_size");
		int count = 0;
		for (WordSquare wordSq : wordSquares) {
			System.out.println(wordSq.toString());
			count++;
			if (count >= wSqPerPage) {
				System.out.println("Press key 'S' to skip to the summary data, or press any other button to view the next " + wSqPerPage + " results");
				String ans = reader.readLine();
				if (ans.toLowerCase().equals("s")) {
					break;
				} 
				count = 0;
			}
		}
		
		printSearchSummary(wordSqResults);
	}

	
	private void printSearchSummary(WordSquareResults wordSqResults) {
		System.out.println("\nSEARCH SUMMARY: ");
		System.out.println("Specified letters permitted: " + wordSqResults.getSpecifiedLetters());
		System.out.println("Specified number of letters per word / number of word squares: " + wordSqResults.getSpecifiedSquareWords());
		System.out.println("Number of square words detected for this search: " + wordSqResults.getNumWdSqDetected());
		System.out.println("\n-------------------------------------------------------------------\n");
	}

	
	
}

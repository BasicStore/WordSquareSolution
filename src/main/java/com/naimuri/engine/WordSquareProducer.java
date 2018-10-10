package com.naimuri.engine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import com.naimuri.exceptions.WordSquareCalculationException;
import com.naimuri.model.*;

/**
 * Implementation defining the functionality to perform a word square search
 */
public class WordSquareProducer implements IWordSquareProducer {

	private String dictinaryUrl;
	
	private boolean eagerSingleSrchFound;
	
	/**
	 * Constructor to setup a WordSquareProducer with reference to a specified dictionary
	 * @param dictinaryUrl - the path to a remote text file of words representing the dictionary for word square searches
	 */
	public WordSquareProducer(String dictinaryUrl) {
		this.dictinaryUrl = dictinaryUrl;
	}
		
	
	/**
	 * Method to generate the results of a word square search
	 * @param srch - contains the search criteria
	 * @throws WordSquareCalculationException - an exception occurred during the word square calculation process
	 */
	public WordSquareResults generateSquareWords(WordSquareSearch srch) throws WordSquareCalculationException {
		int numLetters = srch.getSpecifiedSquareWords();
		String letterBank = srch.getSpecifiedLetters();
		int tgtIndex = srch.getNextWordSqSearchIndex();
		boolean singleSrch = srch.isNxtSingleSearch();
		eagerSingleSrchFound = false;
		
		WordConstructor wordMaker = load(numLetters, letterBank);
		LettersAvailable lettersAvail = new LettersAvailable(numLetters, letterBank);
		List<List<String>> resultList = new ArrayList<List<String>>(); 
		List<String> subresults = new ArrayList<String>();
		
		for (String word : wordMaker.getFilteredWordList()) {
			subresults.add(word); 
			generateSquareWords(resultList, numLetters, tgtIndex, subresults, wordMaker, lettersAvail, srch.isNxtSingleSearch());	
			if (eagerSingleSrchFound) {
				break;	
			}
			subresults.remove(subresults.size() - 1);
		}
		
		return createWordSquareResults(resultList, numLetters, letterBank, tgtIndex, singleSrch); 
	}
	
	
	// returns word square results with either the full word square results, or if a single search then just the item at the target index
	private WordSquareResults createWordSquareResults(List<List<String>> resultList, int numLetters, String letterBank, int tgtIndex, 
																boolean wasSingleSearch) {
		if (wasSingleSearch) {
			List<List<String>> freshList = new ArrayList<List<String>>();
			int maxIndex = resultList.size() - 1;
			freshList.add(resultList.get(maxIndex));
			resultList = freshList;
		} 
		
		return new WordSquareResults(resultList, numLetters, letterBank, tgtIndex, wasSingleSearch, eagerSingleSrchFound);
	}
		

	private void generateSquareWords(List<List<String>> resultList, int squareWords, int tgtIndex,
			List<String> subresults, WordConstructor wordMaker, LettersAvailable lettersAvail, boolean singleSrch) {
		if (eagerSingleSrchFound) {
			return;
		}
		
		if (subresults.size() == squareWords) {
			if (lettersAvail.usesValidLetters(subresults)) { 
				List<String> copy = new ArrayList<String>();
				copy.addAll(subresults);  
				resultList.add(copy);
				if (tgtIndex == resultList.size() - 1) {
					eagerSingleSrchFound = true;
				}
			}
			return;
		}
		
		// create the prefix based on the contents of subresults
		int i = subresults.size();
		StringBuilder prefixer = new StringBuilder();
		for (String s : subresults) {
			prefixer.append(s.charAt(i)); 
		}
		
		// fetches a set of words from the dictionary which start with the prefix  
		Set<String> possWords = wordMaker.searchPossibleNextWords(prefixer.toString(), lettersAvail);
		for (String poss : possWords) {
			subresults.add(poss); 
			generateSquareWords(resultList, squareWords, tgtIndex, subresults, wordMaker, lettersAvail, singleSrch);   
			subresults.remove(subresults.size() - 1);  
		}
	}
	
	
	// stores words from the dictionary which in isolation agree with the given valid letters criteria
	private WordConstructor load(int numLetters, String acceptedLetters) throws WordSquareCalculationException {
		LettersAvailable lettersForWord = new LettersAvailable(numLetters, acceptedLetters);
		WordConstructor library = new WordConstructor();
		try {
			URL url = new URL(dictinaryUrl);
		    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		    String newWord;
		    while ((newWord = in.readLine()) != null) {
		    	newWord = newWord.toLowerCase();
		    	if (newWord.length() == numLetters && lettersForWord.usesValidLetters(newWord)) {
					library.store(newWord);
				}
		    }
		    in.close();
		} catch(IOException ex) {
			throw new WordSquareCalculationException("An error occurred attempting to read dictionary");
		}
		
		return library;
	}
	
	
}

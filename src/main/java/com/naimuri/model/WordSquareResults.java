package com.naimuri.model;
import java.util.ArrayList;
import java.util.List;


/**
 * The template for word square results 
 */
public class WordSquareResults extends WordSquareSearch {
	
	private List<WordSquare> wordSquares;
	
	private int numWdSqDetected;	

	/**
	 * Constructor to create an WrodSquareResults instance initially with search data only
	 * @param specifiedSquareWords
	 * @param specifiedLetters
	 */
	public WordSquareResults(int specifiedSquareWords, String specifiedLetters) {
		super(specifiedSquareWords, specifiedLetters);
	}
	
	
	/**
	 * Constructor to create a new WrodSquareResults instance with search data and word square search results
	 * @param specifiedSquareWords
	 * @param specifiedLetters
	 */
	public WordSquareResults(int specifiedSquareWords, String specifiedLetters, List<WordSquare> wordSquares) {
		super(specifiedSquareWords, specifiedLetters);
		this.wordSquares = wordSquares;
		this.numWdSqDetected = wordSquares == null || wordSquares.size() == 0 ? 0 : wordSquares.size(); 
	}

	
	/**
	 * Constructor to create a new WrodSquareResults instance recording single search information
	 * @param rawResults
	 * @param specifiedSquareWords
	 * @param specifiedLetters
	 * @param specifiedWordSqIndex
	 * @param wasSingleSearch
	 * @param eagerSingleSrchFound
	 */
	public WordSquareResults(List<List<String>> rawResults, int specifiedSquareWords, String specifiedLetters, int specifiedWordSqIndex, 
														boolean wasSingleSearch, boolean eagerSingleSrchFound) {
		this(specifiedSquareWords, specifiedLetters);
		if (wasSingleSearch) {
			if (!eagerSingleSrchFound && specifiedWordSqIndex >= rawResults.size() - 1) {
				this.nextWordSqSearchIndex = 0;
			} else {
				this.nextWordSqSearchIndex = specifiedWordSqIndex + 1;
			}
		} else {
			this.nextWordSqSearchIndex = -1;
		}
		
		setWordSquares(rawResults);
	}
	
	
	/**
	 * Fetches a list oif word squares from a search
	 * @return List<WordSquare> - the list of word square from a search
	 */
	public List<WordSquare> getWordSquares() {
		return wordSquares;
	}
	
	/**
	 * Sets the word square from raw input of a list of word squares each in string list form
	 * @param rawResults
	 */
	public void setWordSquares(List<List<String>> rawResults) {
		this.wordSquares = new ArrayList<WordSquare>();
		for (List<String> wSquareWords : rawResults ) {
			this.wordSquares.add(new WordSquare(wSquareWords));
		}
		this.numWdSqDetected = wordSquares == null || wordSquares.size() == 0 ? 0 : wordSquares.size();
	}

	
	/**
	 * Counts the number of word squares retrieved as part of this search
	 * @return int - the number of word squares retrieved as part of this search
	 */
	public int getNumWdSqDetected() {
		return numWdSqDetected;
	}
	
	
}

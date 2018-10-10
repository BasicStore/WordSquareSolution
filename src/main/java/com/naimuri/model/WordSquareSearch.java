package com.naimuri.model;

/**
 * The template for a word square search, containing instructions specified by the user
 */
public class WordSquareSearch {
	
	// the number of square words / letters per word which for which to search
	protected int specifiedSquareWords; 
	
	// the exclusive list of letters to be included in the search results (size: 'specifiedSquareWords' squared)
	protected String specifiedLetters;

	// defines if this search is for a single specific word square, or else seeking all possible word squares
	protected boolean nxtSingleSearch;
	
	// if this is a single word square, specify which index to attempt to find
	// the value is -1 if it is not a single word square search
	// the value is always 0 if this is the first of a particular search type in memory
	protected int nextWordSqSearchIndex;
	
	/**
	 * Constructor to create a word square search instance
	 * @param specifiedSquareWords
	 * @param specifiedLetters
	 */
	public WordSquareSearch(int specifiedSquareWords, String specifiedLetters) {
		this.specifiedSquareWords = specifiedSquareWords;
		this.specifiedLetters = specifiedLetters;
	}
	
	
	/**
	 * Constructor to create a word square search instance with single search information
	 * @param specifiedSquareWords
	 * @param specifiedLetters
	 */
	public WordSquareSearch(int specifiedSquareWords, String specifiedLetters, boolean nxtSingleSearch) {
		this.specifiedSquareWords = specifiedSquareWords;
		this.specifiedLetters = specifiedLetters;
		this.nxtSingleSearch = nxtSingleSearch;
	}
	
	
	/**
	 * Constructor to create a word square search instance specifying the next index with single search information
	 * @param specifiedSquareWords
	 * @param specifiedLetters
	 */
	public WordSquareSearch(int specifiedSquareWords, String specifiedLetters, int nextWordSqSearchIndex) {
		this.specifiedSquareWords = specifiedSquareWords;
		this.specifiedLetters = specifiedLetters;
		this.nextWordSqSearchIndex = nextWordSqSearchIndex;
	}
	
	
	public int getSpecifiedSquareWords() {
		return specifiedSquareWords;
	}

	public void setSpecifiedSquareWords(int specifiedSquareWords) {
		this.specifiedSquareWords = specifiedSquareWords;
	}


	public String getSpecifiedLetters() {
		return specifiedLetters;
	}


	public void setSpecifiedLetters(String specifiedLetters) {
		this.specifiedLetters = specifiedLetters;
	}
	
	public int getNextWordSqSearchIndex() {
		return nextWordSqSearchIndex;
	}

	public void setNextWordSqSearchIndex(int nextWordSqSearchIndex) {
		this.nextWordSqSearchIndex = nextWordSqSearchIndex;
	}

	public boolean isNxtSingleSearch() {
		return nxtSingleSearch;
	}

	public void setNxtSingleSearch(boolean nxtSingleSearch) {
		this.nxtSingleSearch = nxtSingleSearch;
	}
	
}

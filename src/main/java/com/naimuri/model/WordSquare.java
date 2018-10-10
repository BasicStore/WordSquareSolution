package com.naimuri.model;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * The template for a word square 
 */
public class WordSquare {
	
	private List<String> words;
	
	/**
	 * Constructor to create a new word square
	 * @param words
	 */
	public WordSquare(List<String> words) {
		this.words = words;
	}

	public List<String> getWords() {
		return words;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}
	

	public boolean equals(WordSquare ws) {
		
		if (ws == null || ws.getWords().size() != words.size()) {
			return false;
		}
		
		for (int i = 0; i < words.size(); i++) {
			String word = words.get(i);
			if (!word.equals(ws.getWords().get(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	
	public String toString() {
		if (words == null || words.size() == 0) {
			return StringUtils.EMPTY;
		}
		
		StringBuilder sb = new StringBuilder();
		for (String word : words) {
			sb.append(word);
			sb.append("\n");
		}
		
		sb.append("\n");
		return sb.toString();
	}

	
}

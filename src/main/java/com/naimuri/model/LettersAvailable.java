package com.naimuri.model;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for keeping track of which letters are available for a given word square search.
 * It can be used to verify that each word from the dictionary contains the accepted characters
 * before storage. 
 * It is also used against multiple words to ensure that any potential word square is valid.
 */
public final class LettersAvailable {
	
	// map containing accepted characters against the number of each 
	private Map<Character, Integer> charAvailMap = new HashMap<Character, Integer>(); 		
	
	
	/**
	 * Constructor to create a new LettersAvailable class
	 * @param numLetters
	 * @param acceptedLetters
	 */
	public LettersAvailable(int numLetters, String acceptedLetters) {
		for (char c : acceptedLetters.toLowerCase().toCharArray()) {
			if (charAvailMap.get(c) == null) {
				charAvailMap.put(c, 0);
			}
			
			charAvailMap.put(c, charAvailMap.get(c) + 1);
		}
	}

	
	/**
	 * Method to determine whether a particular word in isolation uses only accepted characters.
	 * Use this method to filter the dictionary with valid words 
	 * @param word
	 * @return boolean - whether the word is valid
	 */
	public boolean usesValidLetters(String word) {
		for (char c : word.toCharArray()) {
			Integer existingVal = charAvailMap.get(Character.toLowerCase(c));
			if (existingVal == null || existingVal - 1 < 0) {
				return false;
			} 
		}
		
		return true;
	}
	
	
	/**
	 * Method to determine whether a list of words collectively use only accepted characters.
	 * @param wordList - he word list
	 * @return boolean - whether the word list is valid
	 */
	public boolean usesValidLetters(List<String> wordList) {
		Map<Character, Integer> tmpAvailMap = copyAvailability(); 
		for (String word : wordList) {
			for (char c : word.toCharArray()) {
				Integer existingVal = tmpAvailMap.get(c);
				if (existingVal == null || existingVal - 1 < 0) {
					return false;
				} else {
					existingVal -= existingVal; 
				}
			}
		}
		
		return true;
	}
	
	
	// makes a clone of the character availability map
	private Map<Character, Integer> copyAvailability() {
		Map<Character, Integer> tmpAvailMap = new HashMap<Character, Integer>();
		for (Map.Entry<Character, Integer> entry : charAvailMap.entrySet()) {
		    Character key = (Character)entry.getKey();
		    int value = (Integer)entry.getValue();
		    tmpAvailMap.put(key, value);
		}

		return tmpAvailMap;
	}
	
	
	
	/**
	 * Method to prevent cloning of this method
	 */
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	
}
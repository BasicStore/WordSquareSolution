package com.naimuri.engine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.naimuri.model.LettersAvailable;


/**
 * Implementation for storing words as letters within a trie structure.
 * Used to retrieve full words stored given a matching prefix.
 */
public class WordConstructor {

	private Node root;
	private List<String> filteredWordList;
	
	/**
	 * Node constructor referencing the next letters of this particular node.
	 * Used in the context of identifying stored words in a data structure
	 * by linking letters in a trie structure.
	 */
	public class Node {
		Map<Character, Node> children;
		
		boolean isFullWord;
		
		public Node(boolean isFullWord) {
			children = new HashMap<Character, Node>();
			this.isFullWord = isFullWord;
		}
	}
	
	
	/**
	 * WordConstructor constructor to record words built from letter nodes in a trie structure
	 */
	public WordConstructor() {
		root = new Node(false);
		filteredWordList = new ArrayList<String>();
	}
	
	
	/**
	 * Stores a word in a letter trie structure 
	 * @return void
	 */
	public void store(String word) {
		filteredWordList.add(word);
		Node curr = root; 
		
		char[] letters = word.toCharArray();
		
		for (int i = 0; i < letters.length; i++) {
			char c = letters[i];
			if (curr.children.get(c) == null ) {
				curr.children.put(c, new Node(i == letters.length - 1 ? true : false));
			}
			
			curr = curr.children.get(c);
		}
	}
	
	
	/**
	 * Retrieves a set of possible words stored in this class starting with the given prefix
	 * @param prefix - the given word prefix
	 * @param avail - the record of letters available for use in the word square clalculation process
	 * @return void
	 */
	public Set<String> searchPossibleNextWords(String prefix, LettersAvailable avail) {
		Set<String> possNxtWords = new HashSet<String>();
		if (root == null) {
			return possNxtWords;
		}
		
		Node currNode = root;
		for (char c : prefix.toCharArray()) {
			if (currNode == null) {
				return possNxtWords;
			}
			
			currNode = currNode.children.get(c);
		}

		if (currNode != null) {
			searchPossibleNextWords(prefix, currNode, possNxtWords, avail);
		}
		
		return possNxtWords;
	}
	
	
	private void searchPossibleNextWords(String prefix, Node node, Set<String> possNxtWords, LettersAvailable avail) {
		if (node.isFullWord) {
			boolean isCapacity = true;  
			if (isCapacity) {
				possNxtWords.add(prefix);
			}
		}
		
		for (char key : node.children.keySet()) {
			String nxtPrefix = prefix + Character.valueOf(key);
			Node nxtNode = node.children.get(key);
			searchPossibleNextWords(nxtPrefix, nxtNode, possNxtWords, avail);
		}
	}

	
	/**
	 * Fetches a list of words found stored in this word constructor object
	 * @return List<String> - the list of words stored in this object
	 */
	public List<String> getFilteredWordList() {
		return filteredWordList;
	}
	
}

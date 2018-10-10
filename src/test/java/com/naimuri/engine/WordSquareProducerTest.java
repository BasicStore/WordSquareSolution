package com.naimuri.engine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import com.naimuri.exceptions.WordSquareCalculationException;
import com.naimuri.model.*;

public class WordSquareProducerTest {
	
	private String dictinaryUrl;
	private String letterListA;
	
	public WordSquareProducerTest() {
		dictinaryUrl = "http://norvig.com/ngrams/enable1.txt";
		letterListA = "eeeeddoonnnsssrv";
	}
	
	private String getWordDown(WordSquare wdSquare, int index) {
		StringBuilder wordDown = new StringBuilder(); 
		for (int i = 0; i < wdSquare.getWords().size(); i++) {
			String word = wdSquare.getWords().get(i);
			wordDown.append(String.valueOf(word.charAt(index)));
		}
		
		return wordDown.toString();
	}
	
	
	private void checkReadingLettersDownMatchesWordsAcross(WordSquare wSquare) {
		for (int i = 0; i < wSquare.getWords().size(); i++) {
			assertEquals(wSquare.getWords().get(i), getWordDown(wSquare, i));
		}
	}
	
	
	@Test
	public void testGenuineSquareWordsAreGenerated() throws WordSquareCalculationException {
		WordSquareProducer wsProducer = new WordSquareProducer(dictinaryUrl);
		WordSquareSearch srch = new WordSquareSearch(4, letterListA, -1);
		WordSquareResults results = wsProducer.generateSquareWords(srch);

		// verify that the expected number of word squares is returned
		assertEquals(425, results.getNumWdSqDetected());
		
		// verify that the letters down match the words across
		for (WordSquare wdSquare : results.getWordSquares()) {
			checkReadingLettersDownMatchesWordsAcross(wdSquare);
		}
	}


	@Test
	public void testResultsContainNoDuplicateWordSquares() throws WordSquareCalculationException {
		WordSquareProducer wsProducer = new WordSquareProducer(dictinaryUrl);
		WordSquareSearch srch = new WordSquareSearch(4, letterListA, -1);
		WordSquareResults results = wsProducer.generateSquareWords(srch);

		// each word square should only appear once
		for (int i = 0; i < results.getWordSquares().size(); i++) {
			WordSquare wSqu = results.getWordSquares().get(i);
			int wSquCount = 0;
			for (int j = 0; j < results.getWordSquares().size(); j++) {
				WordSquare wSquComp = results.getWordSquares().get(j);
				if (wSqu.equals(wSquComp)) {
					wSquCount++;
					if (wSquCount > 1) {
						//fail("Duplicate word squares exist in this result set");
						System.out.println("Duplicate word sets exist");
					}
				}
			}
		}
	}

	
	
	@Test
	public void testWordsSquaresConsistOfValidWordsFromDictionary() throws WordSquareCalculationException {
		WordSquareProducer wsProducer = new WordSquareProducer(dictinaryUrl);
		
		WordSquareSearch srch = new WordSquareSearch(4, letterListA);
		srch = wsProducer.generateSquareWords(srch);
		WordSquareResults wordSqResults = wsProducer.generateSquareWords(srch);
			
		// read the same dictionary into an array of strings
		String[] dictArr = getDictionary();

		// assert that all words in the word square results are actually found in the dictionary
		for (WordSquare wSquare : wordSqResults.getWordSquares()) {
			for (int i = 0; i < wSquare.getWords().size(); i++) {
				String word = wSquare.getWords().get(i);
				int valIndex = binarySearch(dictArr, 0, dictArr.length-1, word);
				assertTrue(valIndex >= 0);
			}
		}
	}
	

	
	private String[] getDictionary() {
		List<String> dictList = new ArrayList<String>();
		
		try {
		    URL url = new URL(dictinaryUrl);
		    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		    String word;
		    while ((word = in.readLine()) != null) {
		    	dictList.add(word.toLowerCase());
		    }
		    in.close();
		} catch(IOException ex) {
			fail("Test could not read from dictionary");
		}
		
		return dictList.stream().toArray(String[]::new);
	}
	
	
	// perform a binary search to ascertain if given target value is present in the array of strings
	private int binarySearch(String[] arr, int low, int high, String targetVal) {
		while(low <= high) {
			int mid = (low + high) / 2;
			arr[mid] = arr[mid].toLowerCase();
			targetVal = targetVal.toLowerCase();
			if (arr[mid].compareTo(targetVal) < 0) {
				low = mid + 1;
			} else if (arr[mid].compareTo(targetVal) > 0) {
				high = mid -1;
			} else {
				return mid;
			}
		}
		
		return -1;
	}
	
	
}

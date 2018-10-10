package com.naimuri.engine;
import com.naimuri.exceptions.WordSquareCalculationException;
import com.naimuri.model.WordSquareResults;
import com.naimuri.model.WordSquareSearch;

/**
 * Defines the functionality to perform a word square search
 */
public interface IWordSquareProducer {

	/**
	 * Method to generate the results of a word square search
	 * @param srch - contains the search criteria
	 * @throws WordSquareCalculationException - an exception occurred during the word square calculation process
	 */
	WordSquareResults generateSquareWords(WordSquareSearch srch) throws WordSquareCalculationException;
	 
}

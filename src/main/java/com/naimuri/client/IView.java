package com.naimuri.client;
import java.io.IOException;

import com.naimuri.exceptions.InvalidInputException;
import com.naimuri.exceptions.WordSquareCalculationException;
import com.naimuri.model.WordSquareResults;

/**
 * Defines the view of the WordSquareSolution app from the command line
 */
public interface IView {

	/**
	 * Method to manage the retrieval of word square instructions from the user and pass back the results 
	 * @return void 
	 */
	void manageWordSquareRequests();
	
	/**
	 * Method to gather data from the user to make a word square request, and pass back the results
	 * @return void 
	 * @throws IOException - the system encountered a problem reading the dictionary or user input
	 * @throws InvalidInputException - the word square criteria supplied by the user is somehow invalid
	 * @throws WordSquareCalculationException - an exception occurred during the word square calculation process
	 */
	void makeWordSquareRequest() throws IOException, InvalidInputException, WordSquareCalculationException;
	
	
	/**
	 * Generates the word squares given the given search criteria
	 * @param specifiedSquareWords - the number of word squares
	 * @param specifiedLetters - the letters available for use
	 * @param isSingleSearch - identifies if this search is to return 1 word square rather than all possible word squares
	 * @return WordSquareResults - the word square results information
	 * @throws WordSquareCalculationException
	 */
	WordSquareResults getWordSquares(int specifiedSquareWords, String specifiedLetters, boolean isSingleSearch) throws WordSquareCalculationException;
}

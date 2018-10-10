package com.naimuri.exceptions;

/**
 * Class to indicate that something has gone wrong during the word square calculation process 
 */
public class WordSquareCalculationException extends Exception {
	public WordSquareCalculationException(String msg) {
		super(msg);
	}
}

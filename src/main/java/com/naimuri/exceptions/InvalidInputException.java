package com.naimuri.exceptions;

/**
 * Class to indicate that the user has supplied badly formed or missing data 
 */
public class InvalidInputException extends Exception {
	public InvalidInputException(String msg) {
		super(msg);
	}
	
}

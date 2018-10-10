package com.naimuri.controller;
import com.naimuri.client.*;
import com.naimuri.engine.*;
import com.naimuri.utils.SysProperties;
import org.apache.log4j.Logger;

/**
 * The main method and controller for the WordSquareSolution app
 */
public class WordSquareMain {

	private Logger LOG = Logger.getLogger(WordSquareMain.class.getName());
	
	/**
	 * Controller method for the WordSquareSolution app 
	 */
	public WordSquareMain() {
		LOG.info("Starting application.......");
		IWordSquareProducer model = new WordSquareProducer(SysProperties.getProperty("dictionary_path"));
		LOG.info("model initialized");
		IView view = new CommandLineView(model);
		LOG.info("view initialized");
		view.manageWordSquareRequests();
		LOG.info("Application started");
	}
	
	
	/**
	 * Main method of the WordSquareSolution app
	 * @param args - [none]
	 * @return void
	 */
	public static void main(String[] args) {
		new WordSquareMain();
	}
	
}

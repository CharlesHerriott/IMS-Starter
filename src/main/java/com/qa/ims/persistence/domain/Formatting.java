package com.qa.ims.persistence.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public enum Formatting {
	BR("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	private String description;
	
	private Formatting(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public static StringBuilder fixedLengthString(String message, String message2) {
		StringBuilder test = new StringBuilder(message + " " + message2);
		test.setLength(20);
		return test;
	}
	
	public static StringBuilder fixedLengthString(String message){
		StringBuilder test = new StringBuilder(message);
		test.setLength(15);
		return test;
	}

}

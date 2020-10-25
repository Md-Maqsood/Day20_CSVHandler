package com.bridgeLabs.csvHandler;

public class CsvException extends Exception {	
	public CsvExceptionType exceptionType;
	public CsvException(String message, CsvExceptionType exceptionType) {
		super(message);
		this.exceptionType=exceptionType;
	}
}


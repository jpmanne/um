package com.sciits.devops.poc.exception;

public class UMException extends Exception {
	private static final long serialVersionUID = 6057638889793569346L;

	public UMException() {
		super();
	}

	public UMException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UMException(String message, Throwable cause) {
		super(message, cause);
	}

	public UMException(String message) {
		super(message);
	}

	public UMException(Throwable cause) {
		super(cause);
	}
	
}

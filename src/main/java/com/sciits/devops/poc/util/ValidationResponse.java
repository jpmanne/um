
package com.sciits.devops.poc.util;

import lombok.Data;

public @Data class ValidationResponse {
	
	public ValidationResponse(boolean success, String errorMessage) {
		super();
		this.success = success;
		this.errorMessage = errorMessage;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	private boolean success;
	private String errorMessage;

}


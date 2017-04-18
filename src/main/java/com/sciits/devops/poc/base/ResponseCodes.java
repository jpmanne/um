
package com.sciits.devops.poc.base;

public enum ResponseCodes {
	
	OK("200"),
	BAD_REQUEST("400"),
	SERVER_ERROR("500"),
	UNAUTHORIZED("401");
	
	private String responseCode;
	
	ResponseCodes(String code){
		this.responseCode = code;
	}

	public String getCode() {
		return responseCode;
	}
	

}

package com.sciits.devops.poc.base;

public  class Response {
	
	Boolean success;
	String message;
	String responseCode;
	Object responsePayload;
	String [] header;
	
	
	
	public Response() {
		super();
	}

	public Response(Boolean success, String message, String responseCode, Object responsePayload) {
		super();
		this.success = success;
		this.message = message;
		this.responseCode = responseCode;
		this.responsePayload = responsePayload;
	}
	
	
	
	
	/**
	 * @param success
	 * @param message
	 * @param responseCode
	 * @param responsePayload
	 * @param headder
	 */
	public Response(Boolean success, String message, String responseCode, String[] header, Object responsePayload) {
		this.success = success;
		this.message = message;
		this.responseCode = responseCode;
		this.header = header;
		this.responsePayload = responsePayload;
		
	}

	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public Object getResponsePayload() {
		return responsePayload;
	}
	public void setResponsePayload(Object responsePayload) {
		this.responsePayload = responsePayload;
	}

	/**
	 * @return the header
	 */
	public String[] getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(String[] header) {
		this.header = header;
	}

}
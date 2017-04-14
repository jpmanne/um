package com.sciits.devops.poc.controller;

import com.sciits.devops.poc.base.Response;
import com.sciits.devops.poc.base.ResponseCodes;
import com.sciits.devops.poc.base.SessionBase;

public abstract class BaseController extends SessionBase {

	public Response getInvalidAuthCodeResponse() {
		Response invalidAuthCodeResponse = new Response();
		invalidAuthCodeResponse.setMessage("Invalid AuthCode");
		invalidAuthCodeResponse.setResponseCode(ResponseCodes.OK.getCode());
		return invalidAuthCodeResponse;
	}

	public Response getInvalidAccessResponse() {
		Response invalidAuthCodeResponse = new Response();
		invalidAuthCodeResponse.setMessage("Unauthorized Access");
		invalidAuthCodeResponse.setResponseCode(ResponseCodes.OK.getCode());
		return invalidAuthCodeResponse;
	}

}

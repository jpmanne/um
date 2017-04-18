package com.sciits.devops.poc.model;

import java.util.List;

public class SecondaryAuthDetailsGet {
	
	private List<SecondaryAuthorizationDetails> secondaryAuthDetailsFirstQuesitionsGet;
	private List<SecondaryAuthorizationDetails> secondaryAuthDetailsSecondQuesitionsGet;
	public List<SecondaryAuthorizationDetails> getSecondaryAuthDetailsFirstQuesitionsGet() {
		return secondaryAuthDetailsFirstQuesitionsGet;
	}
	public void setSecondaryAuthDetailsFirstQuesitionsGet(
			List<SecondaryAuthorizationDetails> secondaryAuthDetailsFirstQuesitionsGet) {
		this.secondaryAuthDetailsFirstQuesitionsGet = secondaryAuthDetailsFirstQuesitionsGet;
	}
	public List<SecondaryAuthorizationDetails> getSecondaryAuthDetailsSecondQuesitionsGet() {
		return secondaryAuthDetailsSecondQuesitionsGet;
	}
	public void setSecondaryAuthDetailsSecondQuesitionsGet(
			List<SecondaryAuthorizationDetails> secondaryAuthDetailsSecondQuesitionsGet) {
		this.secondaryAuthDetailsSecondQuesitionsGet = secondaryAuthDetailsSecondQuesitionsGet;
	}
	
}

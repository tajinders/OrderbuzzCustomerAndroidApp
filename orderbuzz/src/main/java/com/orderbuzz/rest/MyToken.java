package com.orderbuzz.rest;

public interface MyToken {

	public void setStripeToken(String token);
	public void error(Boolean status , String error);
	public Boolean getErrorStatus();
	public String getStripeToken();
}

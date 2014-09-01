package com.orderbuzz.rest;

public class PaymentToken implements MyToken {

	private String stripeToken ;
	private Boolean errorStatus;
	
	public PaymentToken(){
		errorStatus = false ; 
		stripeToken = null;
		System.out.println("Stripe ctor" );
		
	}
	
	public String getStripeToken() {
		System.out.println("newly get stripe token " + this.stripeToken );
		return stripeToken;
	}

	public void setStripeToken(String stripeToken) {
		this.stripeToken = stripeToken;
		System.out.println("newly set stripe token " + this.stripeToken );
	}

	
	public void error(Boolean status , String error) {
		System.out.println("Stripe error " );
		errorStatus = status ;
	}
	
	public Boolean getErrorStatus()
	{
		System.out.println("get error status " );
		return errorStatus;
		
	}
	
}

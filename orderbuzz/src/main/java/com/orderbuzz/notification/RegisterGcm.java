package com.orderbuzz.notification;
public class RegisterGcm {
	private String registerationid=null;
	private static RegisterGcm instance;
	private RegisterGcm(){

	}

	public static RegisterGcm getInstance(){
		if (instance == null){
			instance = new RegisterGcm();
		}
		return instance;
	}

	public String getRegisterationid() {
		return registerationid;
	}

	public void setRegisterationid(String registerationid) {
		this.registerationid = registerationid;
	}

}

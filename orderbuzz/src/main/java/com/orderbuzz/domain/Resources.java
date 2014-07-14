package com.orderbuzz.domain;

import java.math.BigInteger;

/**
 * 
 * @author Tajinder Singh
 * This class will store the restaurant ID and the restaurant photo's 
 * 
 */

public class Resources {
	private String  rest_id_pk;
	private String rest_photo;
	public String getRest_id_pk() {
		return rest_id_pk;
	}
	public void setRest_id_pk(String rest_id_pk) {
		this.rest_id_pk = rest_id_pk;
	}
	public String getRest_photo() {
		return rest_photo;
	}
	public void setRest_photo(String rest_photo) {
		this.rest_photo = rest_photo;
	}

}

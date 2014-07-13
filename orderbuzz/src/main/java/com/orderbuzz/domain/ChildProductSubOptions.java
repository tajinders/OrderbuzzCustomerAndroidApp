package com.orderbuzz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * 
 * @author Tajinder Singh 
 * This class will store details of All the SUB OPTIONS , a product can have, or 
 * more precisely a MAIN OPTION can have.
 * 
 *  For Example: 
 *             SIZE -> [SMALL, MEDIUM, LARGE]
 *             TOPINGS -> [ CAPCICUM, ONIONS, TOMATO, MASHROOMS] 
 *             BAKED-> [YES or NO] 
 * Have used Hibernate Annotations to create Table in Database
 */


public class ChildProductSubOptions {
	@JsonIgnore
	private long childProdSubOptionId;
	private String childProdSubOptionName;
	private float childProdSubOptionPrice;
	private boolean checked;

	@JsonIgnore
	public long getChildProdSubOptionId() {
		return childProdSubOptionId;
	}
	public void setChildProdSubOptiond(long childProdSubOptionId) {
		this.childProdSubOptionId = childProdSubOptionId;
	}
	public String getChildProdSubOptionName() {
		return childProdSubOptionName;
	}
	public void setChildProdSubOptionName(String childProdSubOptionName) {
		this.childProdSubOptionName = childProdSubOptionName;
	}
	public float getChildProdSubOptionPrice() {
		return childProdSubOptionPrice;
	}
	public void setChildProdSubOptionPrice(float childProdSubOptionPrice) {
		this.childProdSubOptionPrice = childProdSubOptionPrice;
	}
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean isChecked) {
		this.checked = isChecked;
	}


}

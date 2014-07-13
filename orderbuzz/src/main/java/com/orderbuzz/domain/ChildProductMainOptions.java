package com.orderbuzz.domain;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @author Tajinder Singh
 * This class will store details of MAIN options which a Sub Product can have
 * Example  CHICKENBURGER -> [SIZE , TOPINGS, BAKED]
 *          VEGBURGER -> [SIZE , TOPINGS, BAKED]
 *			FRENCH VANILA -> [SIZE , CREAM, etc ]
 *
 * Each Main options will store a List of SubOptions
 *  For Example We can have Multi Selection   SIZE -> [SMALL, MEDIUM, LARGE]
 *                        TOPINGS -> [ CAPCICUM, ONIONS, TOMATO, MASHROOMS] 
 *  We can have single selection Suboptins as well  BAKED-> [YES or NO] 
 *                   
 */

public class ChildProductMainOptions {
	
	@JsonIgnore
	private long chileProdMainOptionId;
	private String childProdMainOptionName;
	private boolean singleSelection;
	private List<ChildProductSubOptions> childProductSubOptionsList ;
	
	@JsonIgnore
	public long getChileProdMainOptionId() {
		return chileProdMainOptionId;
	}

	public void setChileProdMainOptionId(long chileProdMainOptionId) {
		this.chileProdMainOptionId = chileProdMainOptionId;
	}
	public String getChildProdMainOptionName() {
		return childProdMainOptionName;
	}
	public void setChildProdMainOptionName(String childProdMainOptionName) {
		this.childProdMainOptionName = childProdMainOptionName;
	}

	public List<ChildProductSubOptions> getChildProductSubOptionsList() {
		return childProductSubOptionsList;
	}
	public void setChildProductSubOptionsList(
			List<ChildProductSubOptions> childProductSubOptionsList) {
		this.childProductSubOptionsList = childProductSubOptionsList;
	}
	public boolean isSingleSelection() {
		return singleSelection;
	}
	public void setSingleSelection(boolean singleSelection) {
		this.singleSelection = singleSelection;
	}


}

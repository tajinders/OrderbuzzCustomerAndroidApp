package com.orderbuzz.domain;

import java.util.List;
import java.util.Set;


/**
 *  @author Tajinder Singh
 *  This class will store details of child/sub products. Our main product are BURGER, DONUT , JUICES etc
 *  Our child products will be BURGER -> CHICEKEN BURGER, or VEG BURGER etc 
 *                             DONUT -> MINT DONUT or CHOC DONUT etc
 *                             JUICES -> ORANGE or APPPLE or GRAPES etc. 
 *                             
 *  Each child product will store List of MAIN OPTIONS as well. 
 *                            BURGER-> CHICKEN BURGER -> [SIZE or TOPINGS or TOASTED  etc]
 *                            
 *
 */


public class ChildProduct {

	private long childProdId;
	private String childProdName;
	private float childProdBasePrice;
	private List<ChildProductMainOptions> childProductMainOptionsList;

	public long getChildProdId() {
		return childProdId;
	}
	public void setChildProdId(long childProdId) {
		this.childProdId = childProdId;
	}
	public String getChildProdName() {
		return childProdName;
	}
	public void setChildProdName(String childProdName) {
		this.childProdName = childProdName;
	}

	public float getChildProdBasePrice() {
		return childProdBasePrice;
	}
	public void setChildProdBasePrice(float childProdBasePrice) {
		this.childProdBasePrice = childProdBasePrice;
	}
	public List<ChildProductMainOptions> getChildProductMainOptionsList() {
		return childProductMainOptionsList;
	}
	public void setChildProductMainOptionsList(
			List<ChildProductMainOptions> childProductMainOptionsList) {
		this.childProductMainOptionsList = childProductMainOptionsList;
	}

}

package com.orderbuzz.domain;
import java.util.List;
import java.util.Set;

/**
 * @author Tajinder Singh
 * Product Class will store Details of Product (example of products are DONUT, JUICE, DONUT etc)
 * Each product has a List of Child Products 
 *                         For Example BURGER ->[ CHICEKEN BURGER, or VEG BURGER etc ]
 * 
 * 
 */

public class Product {

	private long prodId;
	private String prodname;
	private String prodPhoto;
	private List<ChildProduct> childProdList;

	//Start of Member Functions 

	public List<ChildProduct> getChildProdList() {
		return childProdList;
	}
	public void setChildProdList(List<ChildProduct> childProdList) {
		this.childProdList = childProdList;
	}
	public String getProdname() {
		return prodname;
	}
	public long getProdId() {
		return prodId;
	}
	public void setProdId(long prodId) {
		this.prodId = prodId;
	}
	public void setProdname(String prodname) {
		this.prodname = prodname;
	}
	public String getProdPhoto() {
		return prodPhoto;
	}
	public void setProdPhoto(String prodPhoto) {
		this.prodPhoto = prodPhoto;
	}


}

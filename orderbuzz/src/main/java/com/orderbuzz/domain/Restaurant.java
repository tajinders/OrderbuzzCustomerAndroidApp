package com.orderbuzz.domain;

/**
 * @author Tajinder singh
 * 
 * Resturant class will store details for a each Resturant/FoodOutlet/CofeeCafee
 * Each Resturant Will have List of Products
 *                 Example - TimHORTONS -> [BURGER, DONUT, COFEE etc ]
 *                 
 */

public class Restaurant {
	private String  rest_id_pk;
	private String rest_Name;
	private String rest_Photo;
	private String rest_QueueNo;
	private String add_desc;
	private Double distance_in_km;
	
	public String getRest_id_pk() {
		return rest_id_pk;
	}
	public void setRest_id_pk(String rest_id_pk) {
		this.rest_id_pk = rest_id_pk;
	}
	public String getRest_Name() {
		return rest_Name;
	}
	public void setRest_Name(String rest_Name) {
		this.rest_Name = rest_Name;
	}
	public String getRest_Photo() {
		return rest_Photo;
	}
	public void setRest_Photo(String rest_Photo) {
		this.rest_Photo = rest_Photo;
	}
	public String getRest_QueueNo() {
		return rest_QueueNo;
	}
	public void setRest_QueueNo(String rest_QueueNo) {
		this.rest_QueueNo = rest_QueueNo;
	}
	public String getAdd_desc() {
		return add_desc;
	}
	public void setAdd_desc(String add_desc) {
		this.add_desc = add_desc;
	}
	public Double getDistance_in_km() {
		return distance_in_km;
	}
	public void setDistance_in_km(Double distance_in_km) {
		this.distance_in_km = distance_in_km;
	}

}

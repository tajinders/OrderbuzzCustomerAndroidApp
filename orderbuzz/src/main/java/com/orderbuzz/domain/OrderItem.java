package com.orderbuzz.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrderItem {

	private String restid; 
	private String prodid;
	private String prodname;
	private String produrl;
	private String subprodid;
	public List<ChildProductMainOptions> getCprodMainList() {
		return cprodMainList;
	}

	public void setCprodMainList(List<ChildProductMainOptions> cprodMainList) {
		this.cprodMainList = cprodMainList;
	}

	private String subprodname;
	private float  price;
	List<ChildProductMainOptions> cprodMainList; 

	public String getRestid() {
		return restid;
	}

	public void setRestid(String restid) {
		this.restid = restid;
	}

	public OrderItem(String restid, String prodId, String subProdId, 
			String prodName, String subprodName, String produrl , float Currentprice,  List<ChildProductMainOptions> cprodMainList )
	{
		this.restid = restid;
		this.prodname = prodName;
		this.subprodname = subprodName;
		this.prodid = prodId;
		this.subprodid= subProdId;
		this.produrl = produrl;
		this.price = Currentprice;
		this.cprodMainList = cprodMainList;

	}
	
	public String getProdid() {
		return prodid;
	}

	public void setProdid(String prodid) {
		this.prodid = prodid;
	}

	public String getSubprodid() {
		return subprodid;
	}

	public void setSubprodid(String subprodid) {
		this.subprodid = subprodid;
	}

	public String getSubprodname() {
		return subprodname;
	}

	public void setSubprodname(String subprodname) {
		this.subprodname = subprodname;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getProdname() {
		return prodname;
	}

	public void setProdname(String prodname) {
		this.prodname = prodname;
	}

	public String getProdurl() {
		return produrl;
	}

	public void setProdurl(String produrl) {
		this.produrl = produrl;
	}

	@JsonIgnore
	public String getOrderSummary()
	{

		String ordersummary="";


		for(int i=0;i<cprodMainList.size();i++)
		{
			int flag = 0 ;
			String mainoptionname = cprodMainList.get(i).getChildProdMainOptionName();
			List<ChildProductSubOptions> suboptions = cprodMainList.get(i).getChildProductSubOptionsList();

			for(int j=0;j<suboptions.size();j++)
			{
				if( suboptions.get(j).isChecked()==true)
				{
					if(flag == 0)
					{
						ordersummary = ordersummary + mainoptionname + ": ";
						flag = 1;
					}

					String SubName = suboptions.get(j).getChildProdSubOptionName();
					ordersummary = ordersummary + SubName+", ";
				}
			}

			ordersummary = ordersummary+"\n";
		}

		return ordersummary; 
	}

}

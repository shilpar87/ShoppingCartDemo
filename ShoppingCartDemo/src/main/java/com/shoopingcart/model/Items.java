package com.shoopingcart.model;


public class Items {
	private String productName;
	private int quantity;
	private double price;
	
	public Items() {
		productName = "";
		price = 0.0;
		quantity = 0;	
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}

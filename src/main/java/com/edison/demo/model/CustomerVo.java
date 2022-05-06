package com.edison.demo.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private Customer customer;
	
	@JsonProperty("CUSTOMER_ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@JsonProperty("CUSTOMER_DETAIL")
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}

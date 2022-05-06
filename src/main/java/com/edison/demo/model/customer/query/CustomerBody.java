package com.edison.demo.model.customer.query;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerBody implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("CUSTOMER_ID") 
	private List<String> customerId;

	public List<String> getCustomerId() {
		return customerId;
	}

	public void setCustomerId(List<String> customerId) {
		this.customerId = customerId;
	}
}

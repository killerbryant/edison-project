package com.edison.demo.model.customer.query;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestCustomerBody implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("REQ_BODY") 
    private CustomerBody customerBody;

	public CustomerBody getCustomerBody() {
		return customerBody;
	}

	public void setCustomerBody(CustomerBody customerBody) {
		this.customerBody = customerBody;
	}
}

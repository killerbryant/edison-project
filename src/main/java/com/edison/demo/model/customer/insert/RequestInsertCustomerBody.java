package com.edison.demo.model.customer.insert;

import java.io.Serializable;

import com.edison.demo.model.CustomerDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestInsertCustomerBody implements Serializable {

	private static final long serialVersionUID = 1L;

	private CustomerDto customer;

	@JsonProperty("REQ_BODY")
	public CustomerDto getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}
}

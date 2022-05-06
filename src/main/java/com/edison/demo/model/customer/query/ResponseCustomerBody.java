package com.edison.demo.model.customer.query;

import java.io.Serializable;
import java.util.List;

import com.edison.demo.model.CustomerVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseCustomerBody implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<CustomerVo> customers;

	@JsonProperty("CUSTOMER_DATA")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public List<CustomerVo> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerVo> customers) {
		this.customers = customers;
	}
	
	public void clear() {
		if(this.customers != null && this.customers.size() > 0) {
			this.customers.clear();
		}
	}
}

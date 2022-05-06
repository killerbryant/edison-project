package com.edison.demo.model.customer.query;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseCustomerHeader implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String code;
	
	private String message;
	
	@JsonProperty("RETURN_CODE")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@JsonProperty("RETURN_DESC")
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void clear() {
		this.code = "";
		this.message = "";
	}
}

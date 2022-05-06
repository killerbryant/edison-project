package com.edison.demo.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String gender;
	private String city;
	private Timestamp signTime;
	
	@JsonProperty("CUSTOMER_ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@JsonProperty("CUSTOMER_NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonProperty("CUSTOMER_GENDER")
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@JsonProperty("CUSTOMER_CITY")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@JsonIgnore
	public Timestamp getSignTime() {
		return signTime;
	}
	public void setSignTime(Timestamp signTime) {
		this.signTime = signTime;
	}
}

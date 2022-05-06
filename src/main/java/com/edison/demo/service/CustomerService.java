package com.edison.demo.service;

import java.util.List;

import com.edison.demo.model.Customer;
import com.edison.demo.model.CustomerDto;
import com.edison.demo.model.CustomerVo;

public interface CustomerService {

	List<Customer> getCustomerById(List<String> ids);
	
	int insertCustomer(CustomerDto customer);

	String CheckCustomerData(CustomerDto customer);
	
	void cleanCustomerCache();
	
	List<CustomerVo> convertCustomersToCustomerVos(List<Customer> customers);
}

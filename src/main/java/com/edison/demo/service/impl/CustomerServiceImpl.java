package com.edison.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.edison.demo.dao.CustomerMapper;
import com.edison.demo.model.Customer;
import com.edison.demo.model.CustomerDto;
import com.edison.demo.model.CustomerVo;
import com.edison.demo.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	CustomerMapper customerMapper;
	
	private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Override
	@Cacheable(cacheNames = "customer_cache")
    public List<Customer> getCustomerById(List<String> ids) {
        return customerMapper.findByCustomerIds(ids);
    }
	
	@Override
	@CacheEvict(cacheNames = "customer_cache", allEntries = true) // 清除customer_cache快取中的所有快取
    public void cleanCustomerCache() {
		log.info("clean all data in customer_cache");
    }

	@Override
//	 @CacheEvict(cacheNames = "customer_cache", allEntries = true) // 清除customer_cache快取中的所有快取
	public int insertCustomer(CustomerDto customer) {
		return customerMapper.insertCustomer(customer);
	}

	@Override
	public String CheckCustomerData(CustomerDto customer) {

		List<String> errorMsg = new ArrayList<String>();
		
		if(customer.getId() == null || customer.getId().isEmpty()) {
			errorMsg.add("未傳入CUSTOMER_ID");
		}
		
		if(customer.getName() == null || customer.getName().isEmpty()) {
			errorMsg.add("未傳入CUSTOMER_NAME");
		}
		
		if(customer.getGender() == null || customer.getGender().isEmpty()) {
			errorMsg.add("未傳入CUSTOMER_GENDER");
		}
		
		if(customer.getCity() == null || customer.getCity().isEmpty()) {
			errorMsg.add("未傳入CUSTOMER_CITY");
		}
		return String.join(", ", errorMsg);
	}

	@Override
	public List<CustomerVo> convertCustomersToCustomerVos(List<Customer> customers) {
		CustomerVo customerVo;
        List<CustomerVo> customerVos = new ArrayList<CustomerVo>();
        for(Customer customer : customers) {
        	customerVo = new CustomerVo();
        	customerVo.setId(customer.getId());
        	customerVo.setCustomer(customer);
        	customerVos.add(customerVo);
        }
		return customerVos;
	}
}

package com.edison.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
	
	/**
     * 連接池自動管理，提供了一個高度封裝的“RedisTemplate”類
     * 由spring-data-redis針對jedis提供
     */
    @Autowired
    private RedisTemplate redisTemplate;

	
	private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Override
//	@Cacheable(cacheNames = "customer_cache")
    public List<Customer> getCustomerById(List<String> ids) {
		List<Customer> customoers = null;
        
        String key = "Customers";
        // 取出key值所對應的值（String類型）
        ValueOperations<String,List<Customer>> operations = redisTemplate.opsForValue();
        // 判斷是否有key所對應的值，有則返回true，沒有則返回false
        Boolean hasKey = redisTemplate.hasKey(key);

        //如果緩存中存在就從緩存中獲取資料
        if (hasKey){
        	customoers = operations.get(key);
            log.debug("==========從緩存中獲得資料=========");
            log.debug("ID = " + customoers.get(0).getId() + " Name = " + customoers.get(0).getName());
            log.debug("==============================");
        } else{
        	customoers = customerMapper.findByCustomerIds(ids);
            log.debug("==========從資料表中獲得資料=========");
            log.debug("ID = " + customoers.get(0).getId() + " Name = " + customoers.get(0).getName());
            log.debug("==============================");
            //插入緩存將user放入redis中有效時間為5分鐘
            operations.set(key, customoers, 5, TimeUnit.MINUTES);
        }

        return customoers;
    }
	
	@Override
//	@CacheEvict(cacheNames = "customer_cache", allEntries = true) // 清除customer_cache快取中的所有快取
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

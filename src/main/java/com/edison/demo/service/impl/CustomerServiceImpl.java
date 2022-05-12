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
	
//	/**
//     * 連接池自動管理，提供了一個高度封裝的"RedisTemplate"類
//     * 由spring-data-redis針對jedis提供
//     */
//    @Autowired
//    private RedisTemplate redisTemplate;

	
	private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	/*
	@Cacheable：作用是主要針對方法配置，能夠根據方法的請求引數對其結果進行快取 
	主要引數說明： 
	  1) value ： 
	  快取的名稱，在 spring 配置檔案中定義，必須指定至少一個，
	  例如：@Cacheable(value="mycache") 或者 @Cacheable(value={"cache1","cache2"}。
	  2) key ：快取的 key，可以為空，
	  如果指定要按照 SpEL 表示式編寫，如果不指定，則預設按照方法的所有引數進行組合，
	  例如：@Cacheable(value="testcache",key="#userName')。 
	  3) condition ：快取的條件，可以為空
	  
	  	屬性/方法名			解釋
		value			快取的名稱，指定了快取放在那塊空間上
		cacheNames		與value差不多，二選一
		key				快取key,可以用SPEL標籤自定義
		keyGenerator	key生成器
		cacheManager	快取管理器
		cacheResolver	快取解析器
		condition		條件符合則快取
		unless			條件符合不快取
		sync			是否使用非同步模式，預設false

	@CachePut：作用是主要針對方法配置，能夠根據方法的請求引數對其結果進行快取，
	和 @Cacheable 不同的是，它每次都會觸發真實查詢
	  方法的呼叫 
	  主要引數說明：
	  引數配置和@Cacheable一樣。

	@CacheEvict：作用是主要針對方法配置，能夠根據一定的條件對快取進行清空 
	屬性/方法名	         	解釋
	allEntries			是否清空所有快取，預設false,如果指定為true,則方法呼叫後立即清空所有的快取
	beforeInvocation	是否在執行方法之前就清空快取，預設為false,如果指定為true，剛方法執行前會清空所有快取
	*/
	
	@Override
	@Cacheable(cacheNames = "customer_cache")
    public List<Customer> getCustomerById(List<String> ids) {
//		List<Customer> customoers = null;
//        
//        String key = "Customers";
//        // 取出key值所對應的值（String類型）
//        ValueOperations<String,List<Customer>> operations = redisTemplate.opsForValue();
//        // 判斷是否有key所對應的值，有則返回true，沒有則返回false
//        Boolean hasKey = redisTemplate.hasKey(key);
//
//        //如果緩存中存在就從緩存中獲取資料
//        if (hasKey){
//        	customoers = operations.get(key);
//            log.debug("==========從緩存中獲得資料=========");
//            log.debug("ID = " + customoers.get(0).getId() + " Name = " + customoers.get(0).getName());
//            log.debug("==============================");
//        } else{
//        	customoers = customerMapper.findByCustomerIds(ids);
//            log.debug("==========從資料表中獲得資料=========");
//            log.debug("ID = " + customoers.get(0).getId() + " Name = " + customoers.get(0).getName());
//            log.debug("==============================");
//            //插入緩存將user放入redis中有效時間為5分鐘
//            operations.set(key, customoers, 5, TimeUnit.MINUTES);
//        }

        return customerMapper.findByCustomerIds(ids);
    }
	
	@Override
	@CacheEvict(cacheNames = "customer_cache", allEntries = true) // 清除customer_cache快取中的所有快取
    public void cleanCustomerCache() {
		log.info("clean all data in customer_cache");
    }

	@Override
	 @CacheEvict(cacheNames = "customer_cache", allEntries = true) // 清除customer_cache快取中的所有快取
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

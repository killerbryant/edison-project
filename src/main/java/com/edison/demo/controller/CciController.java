package com.edison.demo.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.edison.demo.model.Customer;
import com.edison.demo.model.CustomerDto;
import com.edison.demo.model.CustomerVo;
import com.edison.demo.model.customer.insert.RequestInsertCustomerBody;
import com.edison.demo.model.customer.query.RequestCustomerBody;
import com.edison.demo.model.customer.query.ResponseCustomerBody;
import com.edison.demo.model.customer.query.ResponseCustomerHeader;
import com.edison.demo.service.CustomerService;
import com.edison.demo.utils.ConstantUtils;
import com.edison.demo.utils.DateUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/cciapi/v1")
@Api(tags = "客戶資訊相關")
public class CciController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(CciController.class);
	
	@Autowired
    private CustomerService customerService;
	
	private ResponseCustomerHeader header = new ResponseCustomerHeader();
	private ResponseCustomerBody body = new ResponseCustomerBody();
	private Map<String, Object> map = new LinkedHashMap<>();
	
	private void clear() {
		header.clear();
		body.clear();
		map.clear();
	}
	
	@RequestMapping(value = "/customer/detail/query", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ApiOperation(value = "查詢", notes = "查詢客戶資料")
	public Map<String, Object> QueryCustomer(@RequestBody RequestCustomerBody requestCustomerBody) {
		clear();
		try {
			if(requestCustomerBody != null) {
				List<String> ids = requestCustomerBody.getCustomerBody().getCustomerId();
				if(ids != null && ids.size() > 0) {
					List<Customer> customers = customerService.getCustomerById(ids);
					header.setCode(ConstantUtils.Status.SUCCESS.getCode());
					header.setMessage(ConstantUtils.TRADE_SUCCESS);
					if(customers.size() > 0) {
				        List<CustomerVo> customerVos = customerService.convertCustomersToCustomerVos(customers);
				        body.setCustomers(customerVos);
					}
					
					map.put("RESP_HEADER", header);
					
					map.put("RESP_BODY", body);
				}
				else {
					header.setCode(ConstantUtils.Status.FAIL.getCode());
					header.setMessage(ConstantUtils.WITHOUT_CUSTOMER_ID);
					map.put("RETURN_HEADER", header);
					
					map.put("RETURN_BODY", body);
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			header.setCode(ConstantUtils.Status.ERROR.getCode());
			header.setMessage(ConstantUtils.ERROR_MSG);
			map.put("RETURN_HEADER", header);
			
			map.put("RETURN_BODY", body);
		}
		return map;
	}

	@RequestMapping(value = "/customer/detail/insert", method= {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ApiOperation(value = "新增", notes = "新增客戶資料")
	public Map<String, Object> InsertCustomer(@RequestBody RequestInsertCustomerBody insertBody) {
		clear();
		int count = 0;
		try {
			if(insertBody != null) {
				CustomerDto customer = insertBody.getCustomer();
				String errorMsg = customerService.CheckCustomerData(insertBody.getCustomer());
				if(errorMsg.length() > 0) {
					header.setCode(ConstantUtils.Status.FAIL.getCode());
					header.setMessage(errorMsg);
					
					map.put("RETURN_HEADER", header);
					
					map.put("RETURN_BODY", body);
				}
				else {
					customer.setSignTime(DateUtils.getNow());
					count = customerService.insertCustomer(customer);
					if(count > 0) {
						header.setCode(ConstantUtils.Status.SUCCESS.getCode());
						header.setMessage(ConstantUtils.TRADE_SUCCESS);
						
						map.put("RESP_HEADER", header);
						
						map.put("RESP_BODY", body);
					}
				}
				
			}
		}catch(DataAccessException dae) {
			dae.printStackTrace();
			log.error(dae.getMessage());
			header.setCode(ConstantUtils.Status.INSSERT_FAIL.getCode());
			header.setMessage(ConstantUtils.INSERT_CUSTOMER_FAIL_MSG);
			map.put("RETURN_HEADER", header);
			
			map.put("RETURN_BODY", body);
		}catch(Exception ex) {
			ex.printStackTrace();
			header.setCode(ConstantUtils.Status.ERROR.getCode());
			header.setMessage(ConstantUtils.ERROR_MSG);
			map.put("RETURN_HEADER", header);
			
			map.put("RETURN_BODY", body);
		}
		return map;
	}
	
	@RequestMapping(value = "/cache/clean", method= {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=UTF-8")
	@ResponseBody
	@ApiOperation(value = "清除", notes = "清除Cache客戶資料")
	public String CleanCustomer() {
		customerService.cleanCustomerCache();
		return ConstantUtils.SUCCESS;
	}
}

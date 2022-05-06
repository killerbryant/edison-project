package com.edison.demo.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edison.demo.model.customer.query.ResponseCustomerBody;
import com.edison.demo.model.customer.query.ResponseCustomerHeader;
import com.edison.demo.utils.ConstantUtils;

public class BaseController {

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public Map<String, Object> messageNotReadable(HttpMessageNotReadableException exception, HttpServletResponse response){
		ResponseCustomerHeader header = new ResponseCustomerHeader();
		ResponseCustomerBody body = new ResponseCustomerBody();
		Map<String, Object> map = new LinkedHashMap<>();
	    header.setCode("9999");
		header.setMessage(ConstantUtils.ERROR_MSG);
		map.put("RETURN_HEADER", header);
		
		map.put("RETURN_BODY", body);
	    return map;
	}
}

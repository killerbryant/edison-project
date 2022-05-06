package com.edison.demo.utils;

public class ConstantUtils {
	
	public static final String SUCCESS = "SUCCESS";
	public static final String FAIL = "FAIL";
	public static final String ERROR = "ERROR";
	
	public static final String TRADE_SUCCESS = "交易成功";
	public static final String TRADE_FAIL = "交易失敗";
	
	
	public static final String WITHOUT_CUSTOMER_ID = "未傳入CUSTOMER_ID";
	public static final String INSERT_CUSTOMER_FAIL_MSG = "客戶資料寫入失敗";
	public static final String ERROR_MSG = "未預期的錯誤";
	
	public static enum Status {

	    SUCCESS("0000"), FAIL("9991"), INSSERT_FAIL("9992"), ERROR("9999");

	    private final String code;

	    Status(String code) {
	        this.code = code;
	    }

	    public String getCode() {
	        return code;
	    }
	}
}

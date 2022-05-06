package com.edison.demo;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect // 告知Spring這是一個Aspect
@Component // 告知Spring要管理這個class
public class LogAspect {
	
	@Autowired
    private ObjectMapper mapper;
	
	private static final Logger log = LoggerFactory.getLogger(LogAspect.class);
	
	@Pointcut("execution(public * com.edison.demo.controller.*.*(..))")
	public void LogPointcut() {
		
	}

	@Before("LogPointcut()")
	public void doBefore(JoinPoint joinPoint) {
		log.debug("doBefore");
	}

	@After("LogPointcut()")
	public void doAfter(JoinPoint joinPoint) {
		log.debug("doAfter");
	}

	@AfterReturning("LogPointcut()")
	public void doAfterReturning(JoinPoint joinPoint) {
		log.debug("doAfterReturning");
	}

	@AfterThrowing("LogPointcut()")
	public void deAfterThrowing(JoinPoint joinPoint) {
		log.debug("deAfterThrowing");
	}

	@Around("LogPointcut()")
	public Object deAround(ProceedingJoinPoint joinPoint) throws Throwable {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
        
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        
        //重點 這裡就是獲取@RequestBody參數的關鍵  調試的情況下 可以看到o變數已經獲取到了請求的參數
        Object[] objArray = joinPoint.getArgs();
        
        if(objArray.length > 0) {
        	String jsonRequestData = mapper.writeValueAsString(objArray[0]);
            
            String logBefore = String.format("[Log] Receive Request -> %s | Request %s | RequestBody %s", timeStamp, url, jsonRequestData);
            log.info(logBefore);
        }
        
        
        // result的值就是被攔截方法的返回值
        Object result = joinPoint.proceed();
        if(result != null) {
        	String jsonResponseData = mapper.writeValueAsString(result);
            
            String logAfter = String.format("[Log] Return Response -> %s | Request %s | ResponseBody %s", timeStamp, url, jsonResponseData);
            log.info(logAfter);
        }
        
		return result;
	}
}
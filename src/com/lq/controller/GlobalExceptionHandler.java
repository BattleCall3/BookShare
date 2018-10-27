package com.lq.controller;


import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 处理全局异常
 * 记录错误日志
 * @author bc
 * @date 2018年10月8日
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static Logger logger = Logger.getLogger(GlobalExceptionHandler.class);
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e) {
//		System.out.println("异常进来了。");
//		e.printStackTrace();
//		logger.debug(e.getMessage());
//		logger.error(e.getMessage());
//		logger.debug(e, e.fillInStackTrace());
		logger.error(e, e.fillInStackTrace());
		return "redirect:/index.jsp";
	}
	
}

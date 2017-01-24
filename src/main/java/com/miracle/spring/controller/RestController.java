
// This is for rest controller

// Edited Code
package com.miracle.spring.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miracle.spring.model.RestVO;
import com.miracle.spring.util.RestURIConstants;

@Controller
public class RestController {

	private static final Logger logger = LoggerFactory.getLogger(RestController.class);

	private RestVO restVo;

	@Autowired
	private void setRestVO(RestVO restVo){
		this.restVo = restVo;
	}
	//Map to store employees, ideally we should use database
	Map<Integer, RestVO> empData = new HashMap<Integer, RestVO>();
	@RequestMapping(value = RestURIConstants.TIME_STAMP, method = RequestMethod.GET)
	public @ResponseBody RestVO getTimeStamp() {
		logger.info("Start getTimeStamp");
		restVo.setCreatedDate(new Date());
		return restVo;
	}

}

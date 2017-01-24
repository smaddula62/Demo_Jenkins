package com.miracle.spring;

import org.springframework.web.client.RestTemplate;

import com.miracle.spring.util.RestURIConstants;
import com.miracle.spring.model.RestVO;

public class TestSpringRestExample {

	public static final String SERVER_URI = "http://localhost:8080/SpringRestExample";
	
	public static void main(String args[]){
		
		System.out.println("Exist Employee");
		testGetTimeStamp();
	}

	private static void testGetTimeStamp() {
		RestTemplate restTemplate = new RestTemplate();
		RestVO restVO = restTemplate.getForObject(SERVER_URI+RestURIConstants.TIME_STAMP, RestVO.class);
		printEmpData(restVO);
	}
	
	public static void printEmpData(RestVO restVO){
		System.out.println("CreatedDate = "+restVO.getCreatedDate());
	}
}

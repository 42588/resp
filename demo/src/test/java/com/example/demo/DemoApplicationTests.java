package com.example.demo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.example.demo.bean.Person;
import com.example.demo.dao.ServiceDao;

@SpringBootTest
class DemoApplicationTests {
	@Autowired
	Person person;
	@Autowired
	ApplicationContext ioc;
	@Test
	void contextLoads() {
		System.out.println(person);
	}
	@Test
	void testConfig(){
		System.out.println("--------------");
		ServiceDao bean = ioc.getBean(ServiceDao.class);
		bean.sayHello();
	}
}

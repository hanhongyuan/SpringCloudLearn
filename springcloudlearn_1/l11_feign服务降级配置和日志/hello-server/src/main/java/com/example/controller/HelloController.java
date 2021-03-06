package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;

@RestController
public class HelloController {

	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DiscoveryClient client;
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/hello",method=RequestMethod.GET)
	public String index() throws InterruptedException{
		ServiceInstance instance=client.getLocalServiceInstance();
		Long start=System.currentTimeMillis();
		long sleepTime=6000;
		Thread.sleep(sleepTime);
		logger.info("/hello,host:[{}],service_id:[{}]",instance.getHost(),instance.getServiceId());
		Long end=System.currentTimeMillis();
		logger.info("COST TIME : [{}]ms",(end-start));
		return "Hello World";
	}
	
	
	@RequestMapping(value="/hello1",method=RequestMethod.GET)
	public String hello(@RequestParam String name){
		return "Hello,"+name;
	}
	
	@RequestMapping(value="/hello2",method=RequestMethod.GET)
	public User hello(@RequestHeader String name,@RequestHeader Integer age){
		return new User(name,age);
	}
	
	@RequestMapping(value="/hello3",method=RequestMethod.POST)
	public String hello(@RequestBody User user){
		return "Hello,"+user.getName()+", "+user.getAge();
	}
}

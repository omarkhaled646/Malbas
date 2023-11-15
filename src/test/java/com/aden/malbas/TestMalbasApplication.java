package com.aden.malbas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestMalbasApplication {


	public static void main(String[] args) {
		SpringApplication.from(MalbasApplication::main).with(TestMalbasApplication.class).run(args);
	}

}

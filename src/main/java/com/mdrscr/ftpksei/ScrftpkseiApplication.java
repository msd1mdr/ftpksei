package com.mdrscr.ftpksei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScrftpkseiApplication { // extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ScrftpkseiApplication.class, args);
	}

//	untuk digunakan jika menggunakan jndi
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		// TODO Auto-generated method stub
//		return builder.sources(ScrftpkseiApplication.class);
//	}

	
}

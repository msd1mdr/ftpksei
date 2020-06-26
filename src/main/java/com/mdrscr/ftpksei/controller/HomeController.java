package com.mdrscr.ftpksei.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdrscr.ftpksei.service.StatementService;

@Controller	
public class HomeController {

	@Autowired 
	private StatementService statementService;
	
	@GetMapping(value="/sendstmt")
	public @ResponseBody String stmt () {
		String msg = "x";
		try {
			msg = statementService.sendToKsei();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String response = "<h2>STATEMENT</h2>";
		response += "<p>" + msg + "</p>";
		
		return response;
	}
	
	
}

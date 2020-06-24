package com.mdrscr.ftpksei.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdrscr.ftpksei.service.StatementService;

@Controller	
public class HomeController {

	@Autowired 
	private StatementService statementService;
	
	@GetMapping(value="/runftp")
	public @ResponseBody String call () {
		List<String> msg = new ArrayList<>();
		try {
			msg = statementService.getStatement();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String response = "<h2>STATEMENT</h2>";
		for (String m : msg) {
			response += "<p>" + m + "</p>";
		}
		
		return response;
	}
}

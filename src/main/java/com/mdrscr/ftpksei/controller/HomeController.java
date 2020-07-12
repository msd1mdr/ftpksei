package com.mdrscr.ftpksei.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.mdrscr.ftpksei.persist.model.AnggotaBursa;
import com.mdrscr.ftpksei.persist.repo.AnggotaBursaRepo;
import com.mdrscr.ftpksei.service.FtpService;
import com.mdrscr.ftpksei.service.StatementService;

@Controller
public class HomeController {

	@Autowired
	private StatementService statementService;
	@Autowired
	private AnggotaBursaRepo anggotaBursaRepo;
	@Autowired
	private FtpService ftpService;

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

	@GetMapping(value="/download")
	public @ResponseBody String download() throws JSchException, SftpException {
		String response = "<h2>Download Files</h2>";
		
		ftpService.download("/home/tomcat/Source_DCV/inbnd/", "D:\\Temp\\KSEI\\");
		
		return response;
	}

	@GetMapping(value="/ab")
	public ModelAndView anggotaBursa () {
		ModelAndView mv = new ModelAndView();
		List<AnggotaBursa> abList = anggotaBursaRepo.findAll();
		String author = "Frans";
		mv.addObject("author", author);
		mv.addObject("abList", abList);
		mv.setViewName("anggotabursa");
		return mv;
	}

	@GetMapping(value="/abform")
	public ModelAndView anggotaBursaForm (@RequestParam(name="id") String abId) {
		AnggotaBursa ab = anggotaBursaRepo.findById(abId).orElse(new AnggotaBursa());
		ModelAndView mv = new ModelAndView();
		mv.addObject("ab", ab);
		mv.setViewName("anggotabursa_form");
		return mv;
	}

	@PostMapping(value="/abform")
	public ModelAndView anggotaBursaSubmit (@ModelAttribute AnggotaBursa ab) {
		System.out.println("Disini 1");
		anggotaBursaRepo.save(ab);
		// call method this.anggotaBursa
		ModelAndView mv = new ModelAndView();
		List<AnggotaBursa> abList = anggotaBursaRepo.findAll();
		String author = "Frans";
		mv.addObject("author", author);
		mv.addObject("abList", abList);
		mv.setViewName("anggotabursa");
		return mv;

	}

}

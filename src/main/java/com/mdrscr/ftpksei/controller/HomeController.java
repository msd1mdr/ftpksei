package com.mdrscr.ftpksei.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.mdrscr.ftpksei.persist.model.AnggotaBursa;
import com.mdrscr.ftpksei.persist.model.BalanceKsei;
import com.mdrscr.ftpksei.persist.model.FileTransmision;
import com.mdrscr.ftpksei.persist.model.StatementKsei;
import com.mdrscr.ftpksei.persist.model.StaticKsei;
import com.mdrscr.ftpksei.persist.repo.AnggotaBursaRepo;
import com.mdrscr.ftpksei.persist.repo.BalanceKseiRepo;
import com.mdrscr.ftpksei.persist.repo.FileTransmisionRepo;
import com.mdrscr.ftpksei.persist.repo.StaticKseiRepo;
import com.mdrscr.ftpksei.service.BalanceService;
import com.mdrscr.ftpksei.service.KseiResponseService;
import com.mdrscr.ftpksei.service.StatementService;
import com.mdrscr.ftpksei.service.ZipService;

@Controller
public class HomeController {

	@Autowired
	private StatementService statementService;
	@Autowired
	private AnggotaBursaRepo anggotaBursaRepo;
	@Autowired
	private FileTransmisionRepo fileTransmitRepo;
	@Autowired
	private KseiResponseService kseiResponseService;
	@Autowired
	private StaticKseiRepo staticKseiRepo;
	@Autowired
	private BalanceKseiRepo balanceRepo;
	@Autowired
	private ZipService zipService;
	@Autowired
	private BalanceService balanceService;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

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
	
//	@GetMapping(value="/unzip")
//	public @ResponseBody String unzip() {
//		String response = "<h2>Unzip Files</h2>";
//		List<String> files = new ArrayList<>();
//		try {
//			files = zipService.unzipFile("D:\\Temp\\KSEI\\KSEIResponse\\responses.zip");
//			for (String file : files) {
//				response = response + "<h3>" + file + "</h3>";
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return response;
//	}

	@GetMapping(value="/download")
	public @ResponseBody String download() { 
		String response = "<h2>Download Files</h2>";
		
		try {
			kseiResponseService.getResponse("*.zip");
		} catch (JSchException e) {
			logger.error("Gagal connect FTP");
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		ftpService.download("/home/tomcat/Source_DCV/inbnd/", "D:\\Temp\\KSEI\\");
		
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

	@GetMapping(value="/sendbal")
	public @ResponseBody String sendbal() {
		String response = "<h2>Sending Balance</h2>";
		try {
			String fileName1 = balanceService.sendToKsei();
			response = response + "<h3>" + fileName1 + "</h3>";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "gagal";
		}
		return response;

	}

		
		
	@GetMapping(value="/fileftp")
	public ModelAndView fileftp () {
		ModelAndView mv = new ModelAndView();
		List<FileTransmision> fileList = fileTransmitRepo.findAll();
		String author = "Frans";
		mv.addObject("author", author);
		mv.addObject("fileList", fileList);
		mv.setViewName("fileksei");
		return mv;
	}

	@GetMapping(value="/stmtlist")
	public ModelAndView statementList (@RequestParam(name="filename") String filename) {
		ModelAndView mv = new ModelAndView();
		List<StatementKsei> stmts = statementService.getAllStatementKsei(filename);
		mv.addObject("stmts", stmts);
		mv.addObject("filename", filename);
		mv.setViewName("statementlist");
		return mv;
	}

	@GetMapping(value="/allstmtlist")
	public ModelAndView allStatement() {
		ModelAndView mv = new ModelAndView();
		List<StatementKsei> stmts = statementService.getAllStatementKsei();
		mv.addObject("stmts", stmts);
		mv.addObject("filename", "*");
		mv.setViewName("statementlist");
		return mv;
	}

	@GetMapping(value="/allstatlist")
	public ModelAndView allStatic() {
		ModelAndView mv = new ModelAndView();
		List<StaticKsei> stats = staticKseiRepo.findAll();
		mv.addObject("stats", stats);
		mv.addObject("filename", "*");
		mv.setViewName("staticlist");
		return mv;
	}

	@GetMapping(value="/allbalance")
	public ModelAndView allBalance() {
		ModelAndView mv = new ModelAndView();
		List<BalanceKsei> bals = balanceRepo.findAll();
		mv.addObject("bals", bals);
		mv.addObject("filename", "*");
		mv.setViewName("balancelist");
		return mv;
	}

	@GetMapping(value="/sendftp")
	public ModelAndView getsendftp (@RequestParam(name="id") String abId) {
		AnggotaBursa ab = anggotaBursaRepo.findById(abId).orElse(new AnggotaBursa());
		ModelAndView mv = new ModelAndView();
		mv.addObject("ab", ab);
		mv.setViewName("anggotabursa_form");
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

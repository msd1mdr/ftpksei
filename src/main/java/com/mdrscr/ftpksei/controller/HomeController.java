package com.mdrscr.ftpksei.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.mdrscr.ftpksei.service.BalanceService;
import com.mdrscr.ftpksei.service.KseiResponseService;
import com.mdrscr.ftpksei.service.StatementService;
import com.mdrscr.ftpksei.service.StaticService;

@Controller
public class HomeController {

	@Autowired
	private StatementService statementService;
	@Autowired
	private StaticService staticService;
	@Autowired
	private BalanceService balanceService;
    @Autowired
    private KseiResponseService kseiResponse;
    

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd"); 
    private static final String strYesterday = dtf.format(LocalDate.now().minusDays(1));

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
//			kseiResponse.getResponse("*_BMAN2_"+strYesterday+ "*.zip");
			kseiResponse.getResponseFile();
		} catch (JSchException e) {
			logger.error("Gagal connect FTP");
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}


	@GetMapping(value="/sendbal")
	public @ResponseBody String sendbal() {
		String response = "<h2>Sending Balance</h2>";
    	logger.debug("Send balance started");
		try {
			balanceService.sendToKsei();
			response = "<h3>Berhasil</h3>";
		} catch (IOException e) {
			logger.error("IO Error proses send Balance");
			return "gagal";
		}
		return response;
	}

	@GetMapping(value="/sendstmt")
	public @ResponseBody String sendstmt() {
		String response = "<h2>Sending Statement</h2>";
    	logger.debug("Send Statement");
		try {
			statementService.sendToKsei();
			response = "<h3>Berhasil</h3>";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("IO Error proses send Statement");
			return "gagal";
		}
		return response;
	}
		
	@GetMapping(value="/sendstat")
	public @ResponseBody String sendstat() {
		String response = "<h2>Sending Static</h2>";
    	logger.debug("Send Static started");
		try {
			staticService.ftpToKsei();
			response = "<h3>Berhasil</h3>";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("IO Error proses send Static");
			return "gagal";
		}
		return response;
	}

//	@GetMapping(value="/stmtlist")
//	public ModelAndView statementList (@RequestParam(name="filename") String filename) {
//		ModelAndView mv = new ModelAndView();
//		List<StatementKsei> stmts = statementService.getAllStatementKsei(filename);
//		mv.addObject("stmts", stmts);
//		mv.addObject("filename", filename);
//		mv.setViewName("statementlist");
//		return mv;
//	}

//	@GetMapping(value="/allstmtlist")
//	public ModelAndView allStatement() {
//		ModelAndView mv = new ModelAndView();
//		List<StatementKsei> stmts = statementService.getAllStatementKsei();
//		mv.addObject("stmts", stmts);
//		mv.addObject("filename", "*");
//		mv.setViewName("statementlist");
//		return mv;
//	}

//	@GetMapping(value="/allstatlist")
//	public ModelAndView allStatic() {
//		ModelAndView mv = new ModelAndView();
//		List<StaticKsei> stats = staticKseiRepo.findAll();
//		mv.addObject("stats", stats);
//		mv.addObject("filename", "*");
//		mv.setViewName("staticlist");
//		return mv;
//	}


//	@GetMapping(value="/abform")
//	public ModelAndView anggotaBursaForm (@RequestParam(name="id") String abId) {
//		AnggotaBursa ab = anggotaBursaRepo.findById(abId).orElse(new AnggotaBursa());
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("ab", ab);
//		mv.setViewName("anggotabursa_form");
//		return mv;
//	}

//	@PostMapping(value="/abform")
//	public ModelAndView anggotaBursaSubmit (@ModelAttribute AnggotaBursa ab) {
//		System.out.println("Disini 1");
//		anggotaBursaRepo.save(ab);
//		// call method this.anggotaBursa
//		ModelAndView mv = new ModelAndView();
//		List<AnggotaBursa> abList = anggotaBursaRepo.findAll();
//		String author = "Frans";
//		mv.addObject("author", author);
//		mv.addObject("abList", abList);
//		mv.setViewName("anggotabursa");
//		return mv;
//	}

	@GetMapping(value="/test")
	public @ResponseBody String test () {
		String msg = "x";
		String response = "<h2>Test sukses</h2>";

		return response;
	}

}

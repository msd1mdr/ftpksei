package com.mdrscr.ftpksei.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.mdrscr.ftpksei.persist.model.BalanceKsei;
import com.mdrscr.ftpksei.persist.model.Bejacn;
import com.mdrscr.ftpksei.persist.model.FileTransmision;
import com.mdrscr.ftpksei.persist.repo.BalanceKseiRepo;
import com.mdrscr.ftpksei.persist.repo.BejacnRepo;
import com.mdrscr.ftpksei.properties.KseiConfig;

@Service
public class BalanceService {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYYMMdd"); 
    private static final String strYesterday = dtf.format(LocalDate.now().minusDays(1));

	@Autowired
	private BejacnRepo bejacnRepo;
	@Autowired
	private BalanceKseiRepo balanceKseiRepo;
	@Autowired
	private FtpService ftpService;
	@Autowired
	private FileTransmisionService fileTransmisionService;
	@Autowired
	private KseiConfig kseiConfig;

    DateFormat df = new SimpleDateFormat("yyyyMMdd");

	public BalanceKsei mapFrom (Bejacn bejacn) {
		BalanceKsei newbal = new BalanceKsei();
		newbal.setExtref(df.format(new Date()) + bejacn.getAcctno());
		newbal.setBankcode("BMAN2");
		newbal.setAccount(bejacn.getAcctno());
		newbal.setCurcod("IDR");
		newbal.setValdate(bejacn.getBjvald());
		newbal.setBalance(bejacn.getBjcbal());
		newbal.setNotes("");
		return newbal;
	}

	@Transactional
	public String sendToKsei () throws IOException {

		Integer fileCounter = fileTransmisionService.getLastFileNumber("BALANCE") + 1;
		String fileName = "RecBalance_BMAN2_" + strYesterday + "_" + String.format("%02d", fileCounter) + ".fsp";
		System.out.println("namanya " + kseiConfig.getLocalOutbDir() + "\\" + fileName);
		FileWriter fileWriter = new FileWriter(kseiConfig.getLocalOutbDir() + "\\"+ fileName);
		BufferedWriter bw = new BufferedWriter(fileWriter);

//		PrintWriter printWriter = new PrintWriter(fileWriter);
		
	    List<Bejacn> balance = bejacnRepo.findAll();

		for (Bejacn bal : balance) {
			
			BalanceKsei balKsei = mapFrom(bal);
			balKsei.setFileName(fileName);
			
			String strBal = balKsei.getExtref() + "|" +
							 balKsei.getBankcode() + "|" +
							 balKsei.getAccount() + "|" +
							 balKsei.getCurcod() + "|" +
							 balKsei.getValdate() + "|" +
							 balKsei.getBalance() + "|" ;

//	    	printWriter.println(newLine);
	    	bw.write(strBal); bw.newLine();
	    	balanceKseiRepo.save(balKsei);

		}

//    	printWriter.close();
	    bw.close();
	    fileWriter.close();

    	FileTransmision fileQ = new FileTransmision(fileName, fileCounter, "BALANCE");
    	System.out.println("Akan kirim file " + fileName);
    	// kirim pake ftp
    	try {
			ftpService.upload(fileName, kseiConfig.getLocalOutbDir());
			fileQ.setSendStatus("SUCCESS");
		} catch (JSchException | SftpException e) {
			// TODO Auto-generated catch block
		fileQ.setSendStatus("ERROR");
			fileQ.setErrorMsg("Gagal kirim FTP");
			System.out.println("Tidak bisa ftp");
		}
    	
    	fileTransmisionService.save(fileQ);
		
		return fileName;
	}
	
}

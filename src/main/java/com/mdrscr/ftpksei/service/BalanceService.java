package com.mdrscr.ftpksei.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.mdrscr.ftpksei.persist.model.Bejacn;
import com.mdrscr.ftpksei.persist.model.FileTransmision;
import com.mdrscr.ftpksei.persist.repo.BejacnRepo;
import com.mdrscr.ftpksei.properties.KseiConfig;

@Service
public class BalanceService {

    private static final Logger logger = LoggerFactory.getLogger(BalanceService.class);
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd"); 

	@Autowired
	private BejacnRepo bejacnRepo;
	@Autowired
	private FtpService ftpService;
	@Autowired
	private FileTransmisionService fileTransmisionService;
	@Autowired
	private KseiConfig kseiConfig;

    DateFormat df = new SimpleDateFormat("yyyyMMdd");

    public String rmSpChar (String inStr) {
    	return StringUtils.replaceChars(inStr, "|/\'", null);
    }

//	public BalanceKsei mapFrom (Bejacn bejacn) {
//		BalanceKsei newbal = new BalanceKsei();
//		newbal.setExtref(df.format(new Date()) + bejacn.getAcctno());
//		newbal.setBankcode("BMAN2");
//		newbal.setAccount(bejacn.getAcctno());
//		newbal.setCurcod("IDR");
//		newbal.setValdate(bejacn.getBjvald());
//		newbal.setBalance(bejacn.getBjcbal());
//		newbal.setNotes("");
//		return newbal;
//	}

	@Transactional
	public String sendToKsei () throws IOException {
	    String strYesterday = dtf.format(LocalDate.now().minusDays(1));
	    String strToday = dtf.format(LocalDate.now());

		List<File> balanceFiles = new ArrayList<File>();
		Integer fileCounter = fileTransmisionService.getLastFileNumber("BALANCE") ;
		logger.debug("Cek file number balance: " + fileCounter);

	    List<Bejacn> balance = bejacnRepo.findAll();

		File f1 = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		Integer recordCounter = 0;

		for (Bejacn bal : balance) {
			if (null==bal) continue;
			if (bal.getBjvald().trim().equals("0")) continue;
			if (bal.getBjstat().equals("C")) continue;   //BEJSTAT udh closed tdk perlu dikirim. 20-01-2022
			
			if (recordCounter++ == 0) {
				++fileCounter;
				f1 = new File(kseiConfig.getLocalOutbDir() + 
						"RecBalance_BMAN2_" + strToday + "_" + String.format("%02d", fileCounter) + ".fsp");
				f1.setExecutable(false);
				f1.setReadable(true, true);
				f1.setWritable(true, true);
				logger.debug("Akan buat file " + f1.getName());
				fw = new FileWriter(f1);
				bw = new BufferedWriter(fw);
				balanceFiles.add(f1);
			}

			String strBal = df.format(new Date()) + 
							rmSpChar(bal.getAcctno()).trim() + "|BMAN2|" +
							rmSpChar(bal.getAcctno()).trim() + "|IDR|" +
							rmSpChar(bal.getBjvald()).trim() + "|" + 
							rmSpChar(bal.getBjcbal()).trim();
					
	    	bw.write(strBal); bw.newLine();

	    	if (recordCounter == 50000) {
	    		bw.close();
	    		fw.close();
	    		logger.debug("Akan insert filetransmision " + f1.getName() + "_" + fileCounter);
	    		FileTransmision fileQ = new FileTransmision(f1.getName(), fileCounter, "BALANCE");
		    	fileQ.setRecordNumber(recordCounter);
		    	fileQ.setValDate(strYesterday);
		    	fileQ = fileTransmisionService.save(fileQ);
		    	logger.debug("Sudah save filetransmision " + fileQ.getId());
	    		recordCounter = 0;
	    	}
	    		
		}

		if (recordCounter > 0) {
		    bw.close();
		    fw.close();
		    logger.debug("Akan insert file_transmission " + f1.getName() + fileCounter);
    		FileTransmision fileQ = new FileTransmision(f1.getName(), fileCounter, "BALANCE");
	    	fileQ.setRecordNumber(recordCounter);
	    	fileQ.setValDate(strYesterday);
	    	fileQ = fileTransmisionService.save(fileQ);
	    	logger.debug("Sudah save filetransmision " + fileQ.getId());
		}
	    
    	// kirim pake ftp
		for (File uploadFile : balanceFiles ) {
			logger.debug("Akan query filetrans "  + uploadFile.getName()); 
			FileTransmision ft = fileTransmisionService.getByFileName(uploadFile.getName());
			try {
				ftpService.upload(uploadFile.getName(), kseiConfig.getLocalOutbDir(), kseiConfig.getBalRmtoutdir());
				ft.setSendStatus("SUCCESS");
			} catch (JSchException | SftpException e) {
				ft.setSendStatus("ERROR");
				ft.setErrorMsg("Gagal kirim FTP");
				logger.error("Gagal kirim FTP");
			}
			fileTransmisionService.save(ft);
		}

		return "Sukses balance";
	}
	
}

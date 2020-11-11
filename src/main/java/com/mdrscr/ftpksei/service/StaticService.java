package com.mdrscr.ftpksei.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.mdrscr.ftpksei.persist.model.BejStaticStaging;
import com.mdrscr.ftpksei.persist.model.FileTransmision;
import com.mdrscr.ftpksei.persist.model.StaticKsei;
import com.mdrscr.ftpksei.persist.repo.BejStaticStagingRepo;
import com.mdrscr.ftpksei.persist.repo.StaticKseiRepo;
import com.mdrscr.ftpksei.properties.KseiConfig;

@Service
public class StaticService {

    private static final Logger logger = LoggerFactory.getLogger(StaticService.class);
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYYMMdd"); 

	@Autowired
	private BejStaticStagingRepo bejStaticStgRepo;
	@Autowired
	private StaticKseiRepo staticKseiRepo;
	@Autowired
	private FileTransmisionService fileTransmisionService;
	@Autowired
	private KseiConfig kseiConfig;
	@Autowired
	private FtpService ftpService;

    DateFormat df = new SimpleDateFormat("yyyyMMdd");

    public String rmSpChar (String inStr) {
    	return StringUtils.replaceChars(inStr, "|/\'", null);
    }

    public StaticKsei mapping (BejStaticStaging bejstat) {

    	StaticKsei stat = new StaticKsei();
    	stat.setExtref(rmSpChar(bejstat.getExtref()).trim());
    	stat.setParticipantid(rmSpChar(bejstat.getPartid()).trim());
    	stat.setParticipantname(rmSpChar(bejstat.getPartname()).trim());
    	stat.setInvestorname(rmSpChar(bejstat.getIvstname()).trim());
    	stat.setSidnumber(rmSpChar(bejstat.getSivstid()).trim());
    	stat.setAccountnumber(bejstat.getSacctno());
    	stat.setBankaccnumber(rmSpChar(bejstat.getAcctno()).trim());
    	stat.setBankcode(bejstat.getBnkcod());
    	stat.setActivity(bejstat.getActivity().trim());
    	stat.setActivitydate(bejstat.getActdate().trim());
    	return stat;
    }
       
    @Transactional
	public String ftpToKsei () throws IOException {
        String strYesterday = dtf.format(LocalDate.now().minusDays(1));
        String strToday = dtf.format(LocalDate.now());

		List<File> staticFiles = new ArrayList<File>();
    	Integer fileCounter = fileTransmisionService.getLastFileNumber("STATIC");
//		String fileName = "DataStaticInv_BMAN2_" + strToday + "_" + String.format("%02d", fileCounter) + ".fsp";

	    List<BejStaticStaging> stats = bejStaticStgRepo.findByActdate(strYesterday);

//		FileWriter fileWriter = new FileWriter(kseiConfig.getLocalOutbDir() + fileName);
//		BufferedWriter bw = new BufferedWriter(fileWriter);
		File f1 = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		Integer recordCounter = new Integer(0);

	    for (BejStaticStaging stat : stats) {
			if (recordCounter++ == 0) {
				++fileCounter;
				f1 = new File(kseiConfig.getLocalOutbDir() + 
						"DataStaticInv_BMAN2_" + strToday + "_" + String.format("%02d", fileCounter) + ".fsp");
				logger.debug("Akan buat file " + f1.getName());
				fw = new FileWriter(f1);
				bw = new BufferedWriter(fw);
				staticFiles.add(f1);
			}
		
	    	StaticKsei statKsei = mapping (stat);
	    	statKsei.setFileName(f1.getName());
	    	statKsei.setCreateTime(LocalDateTime.now());

			String statRow = statKsei.getExtref() + "|" + 
							statKsei.getParticipantid() + "|" + 
							statKsei.getParticipantname() + "|" + 
							statKsei.getInvestorname() + "|" + 
							statKsei.getSidnumber() + "|" + 
							statKsei.getAccountnumber() + "|" + 
							statKsei.getBankaccnumber() + "|" + 
							statKsei.getBankcode() + "|" + 
							statKsei.getActivitydate() + "|" + 
							statKsei.getActivity() ;
	    	bw.write(statRow); bw.newLine();

	    	staticKseiRepo.save(statKsei);
	    	
	    	if (recordCounter == 50000) {
	    		bw.close();
	    		fw.close();
	    		FileTransmision fileQ = new FileTransmision(f1.getName(), fileCounter, "STATIC");
		    	fileQ.setValDate(strYesterday);
		    	fileQ.setRecordNumber(recordCounter);
		    	fileTransmisionService.save(fileQ);
	    		recordCounter = 0;
	    	}

	    }
	    
		if (recordCounter > 0) {
		    bw.close();
		    fw.close();
		    logger.debug("Akan insert file_transmission " + f1.getName() + fileCounter);
    		FileTransmision fileQ = new FileTransmision(f1.getName(), fileCounter, "STATIC");
	    	fileQ.setRecordNumber(recordCounter);
	    	fileQ.setValDate(strYesterday);
	    	fileTransmisionService.save(fileQ);
		}

    	// kirim pake ftp
		for (File uploadFile : staticFiles ) {
			logger.debug("Akan query filetrans "  + uploadFile.getName()); 
			FileTransmision ft = fileTransmisionService.getByFileName(uploadFile.getName());
			try {
				ftpService.upload(uploadFile.getName(), kseiConfig.getLocalOutbDir(), kseiConfig.getStatRmtoutdir());
				ft.setSendStatus("SUCCESS");
			} catch (JSchException | SftpException e) {
				ft.setSendStatus("ERROR");
				ft.setErrorMsg("Gagal kirim FTP");
				logger.error("Gagal kirim FTP");
			}
			fileTransmisionService.save(ft);
		}

		
		return f1.getName();
	}
}

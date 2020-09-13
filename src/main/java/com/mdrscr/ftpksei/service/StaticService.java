package com.mdrscr.ftpksei.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    public StaticKsei mapping (BejStaticStaging bejstat) {

    	StaticKsei stat = new StaticKsei();
    	stat.setExtref(bejstat.getExtref());
    	stat.setParticipantid(bejstat.getPartid());
    	stat.setParticipantname(bejstat.getPartname());
    	stat.setInvestorname(bejstat.getIvstname());
    	stat.setSidnumber(bejstat.getSivstid());
    	stat.setAccountnumber(bejstat.getSacctno());
    	stat.setBankaccnumber(bejstat.getAcctno());
    	stat.setBankcode(bejstat.getBnkcod());
    	stat.setActivity(bejstat.getActivity());
    	stat.setActivitydate(bejstat.getActdate());
    	return stat;
    }
       
    @Transactional
	public String ftpToKsei () throws IOException {
    	Integer recordCounter = new Integer(0);
        String strYesterday = dtf.format(LocalDate.now().minusDays(1));

    	Integer fileCounter = fileTransmisionService.getLastFileNumber("STATIC") + 1;
		String fileName = "DataStaticInv_BMAN2_" + strYesterday + "_" + String.format("%02d", fileCounter) + ".fsp";

		FileWriter fileWriter = new FileWriter(kseiConfig.getLocalOutbDir() + fileName);
		BufferedWriter bw = new BufferedWriter(fileWriter);

	    List<BejStaticStaging> stats = bejStaticStgRepo.findByActdate(strYesterday);

	    for (BejStaticStaging stat : stats) {
			recordCounter++;
		
	    	StaticKsei statKsei = mapping (stat);
	    	statKsei.setFileName(fileName);
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
	    }
	    
	    bw.close();
    	fileWriter.close();
    	
    	FileTransmision fileQ = new FileTransmision(fileName, fileCounter, "STATIC");
    	fileQ.setValDate(strYesterday);
    	fileQ.setRecordNumber(recordCounter);
    	// kirim pake ftp
    	try {
			ftpService.upload(fileName, kseiConfig.getLocalOutbDir());
			fileQ.setSendStatus("SUCCESS");
		} catch (JSchException | SftpException e) {
			fileQ.setSendStatus("ERROR");
			fileQ.setErrorMsg("Gagal kirim FTP");
			logger.error("Tidak bisa ftp");
		}
    	
    	fileTransmisionService.save(fileQ);
		
		return fileName;
	}
}

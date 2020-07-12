package com.mdrscr.ftpksei.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
		
    	Integer fileCounter = fileTransmisionService.getLastFileNumber("STATIC") + 1;
		String fileName = "DataStaticInv_BMAN2_" + df.format(new Date()) + "_" + fileCounter + ".fsp";

//		FileWriter fileWriter = new FileWriter(kseiConfig.getLocalDir() + fileName);
		FileWriter fileWriter = new FileWriter("D:\\Temp\\Ksei\\file1.txt");
	    PrintWriter printWriter = new PrintWriter(fileWriter);

	    List<BejStaticStaging> stats = bejStaticStgRepo.findByFlag("T");
System.out.println("Ketemu: " + stats.size());
	    for (BejStaticStaging stat : stats) {
			
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
	    	printWriter.println(statRow);
	    	
	    	staticKseiRepo.save(statKsei);
	    	stat.setFlag("Y");
	    	bejStaticStgRepo.save(stat);

	    }
	    
    	printWriter.close();
    	
//    	FileTransmision fileQ = new FileTransmision(fileName, fileCounter, "STATIC");

    	// kirim pake ftp
//    	try {
//			ftpService.upload(fileName);
//			fileQ.setSendStatus("SUCCESS");
//		} catch (JSchException | SftpException e) {
//			// TODO Auto-generated catch block
//			fileQ.setSendStatus("ERROR");
//			fileQ.setErrorMsg("Gagal kirim FTP");
//			System.out.println("Tidak bisa ftp");
//			//			e.printStackTrace();
//		}
    	
//    	fileTransmisionService.save(fileQ);
		
		return fileName;
	}
}

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.mdrscr.ftpksei.mapper.StatementMapper;
import com.mdrscr.ftpksei.persist.model.BejStatementStaging;
import com.mdrscr.ftpksei.persist.model.FileTransmision;
import com.mdrscr.ftpksei.persist.model.StatementKsei;
import com.mdrscr.ftpksei.persist.repo.BejStatementStagingRepo;
import com.mdrscr.ftpksei.persist.repo.StatementKseiRepo;
import com.mdrscr.ftpksei.properties.AppConfig;

@Service
public class StatementService {
	
	@Autowired
	private BejStatementStagingRepo bejStmtStgRepo;
	@Autowired
	private StatementKseiRepo statementMsgRepo;
	@Autowired
	private FtpService ftpService;
	@Autowired
	private FileTransmisionService fileTransmisionService;
	@Autowired
	private AppConfig appConfig;
	
    DateFormat df = new SimpleDateFormat("yyyyMMdd");
  
    @Transactional
	public String sendToKsei () throws IOException {

    	Integer fileCounter = fileTransmisionService.getLastFileNumber() + 1;
		String fileName = "ReactStmt_BMAN2_" + df.format(new Date()) + "_" + fileCounter + ".fsp";

		FileWriter fileWriter = new FileWriter(appConfig.getKsei().getFtpLocalDir() + fileName);
	    PrintWriter printWriter = new PrintWriter(fileWriter);
		
	    List<BejStatementStaging> stmts = bejStmtStgRepo.findByFlag("T");

		for (BejStatementStaging stmt : stmts) {
			
			StatementKsei stmtMsg = StatementMapper.INSTANCE.stmtStgToStatementMsg(stmt);
			stmtMsg.setFileName(fileName);
			stmtMsg.setCreateTime(LocalDateTime.now());
			
			String newLine = stmt.getExtref() + "|" + 
						stmt.getSeqnum() + "|" + 
						stmt.getAcctno() + "|" +
						stmt.getCurcod() + "|" +
						stmt.getValdat() + "|" +
						stmt.getOpnbal() + "|" +
						stmt.getTrnref() + "|" +
						stmt.getTrntyp() + "|" +
						stmt.getDrorcr() + "|" +
						stmt.getTrnamt() + "|" +
						stmt.getTrndsc() + "|" +
						stmt.getClsbal() + "|" +
						(stmt.getAcnote()==null?"":stmt.getAcnote());
	    	printWriter.println(newLine);
	    	
	    	statementMsgRepo.save(stmtMsg);
	    	stmt.setFlag("Y");
	    	bejStmtStgRepo.save(stmt);
		}
		
    	printWriter.close();
    	
    	FileTransmision fileQ = new FileTransmision(fileName, fileCounter);

    	// kirim pake ftp
    	try {
			ftpService.upload(fileName);
			fileQ.setSendStatus("SUCCESS");
		} catch (JSchException | SftpException e) {
			// TODO Auto-generated catch block
			fileQ.setSendStatus("ERROR");
			fileQ.setErrorMsg("Gagal kirim FTP");
			System.out.println("Tidak bisa ftp");
			//			e.printStackTrace();
		}
    	
    	fileTransmisionService.save(fileQ);
		
		return fileName;
	}
	
}

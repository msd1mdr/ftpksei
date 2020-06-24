package com.mdrscr.ftpksei.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.mdrscr.ftpksei.mapper.StatementMapper;
import com.mdrscr.ftpksei.persist.model.BejStatementStaging;
import com.mdrscr.ftpksei.persist.model.FileTransmision;
import com.mdrscr.ftpksei.persist.model.StatementKsei;
import com.mdrscr.ftpksei.persist.repo.BejStatementStagingRepo;
import com.mdrscr.ftpksei.persist.repo.StatementKseiRepo;

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
	
	@Value("${scrp.ksei.ftp.localdir}")
    private String localDir;

    DateFormat df = new SimpleDateFormat("yyyyMMdd");

	public List<String> getStatement () throws IOException {
		System.out.println("Mulai.");
		List<BejStatementStaging> stmts = new ArrayList<>();
		List<String> msg = new ArrayList<>();

    	Integer fileCounter = fileTransmisionService.getLastFileNumber() + 1;
		String fileName = "ReactStmt_BMAN2_" + df.format(new Date()) + "_" + fileCounter + ".fsp";
		String newLine = "";

		FileWriter fileWriter = new FileWriter(localDir + fileName);
	    PrintWriter printWriter = new PrintWriter(fileWriter);
		
		stmts = bejStmtStgRepo.findByFlag("T");
		System.out.println("Jumlah: " + stmts.size());

		for (BejStatementStaging stmt : stmts) {
			
			StatementKsei stmtMsg = StatementMapper.INSTANCE.stmtStgToStatementMsg(stmt);
			stmtMsg.setFileName(fileName);
			stmtMsg.setCreateTime(LocalDateTime.now());
			
			newLine = stmt.getExtref() + "|" + 
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
			msg.add(newLine);
	    	printWriter.println(newLine);
	    	
	    	statementMsgRepo.save(stmtMsg);

		}
		
    	printWriter.close();
    	
    	FileTransmision fileQ = new FileTransmision();
    	fileQ.setFileName(fileName);
    	fileQ.setRecipient("KSEI");
    	fileQ.setSendMethod("FTP");
    	fileQ.setSubModul("STATEMENT");
    	fileQ.setSendTime(LocalDateTime.now());
    	fileQ.setValDate(df.format(new Date()));
    	fileQ.setDailyCounter(fileCounter);
    	
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
		
		return msg;
	}
	
}

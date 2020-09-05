package com.mdrscr.ftpksei.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.mdrscr.ftpksei.persist.model.BejStatementStaging;
import com.mdrscr.ftpksei.persist.model.FileTransmision;
import com.mdrscr.ftpksei.persist.model.StatementKsei;
import com.mdrscr.ftpksei.persist.repo.BejStatementStagingRepo;
import com.mdrscr.ftpksei.persist.repo.StatementKseiRepo;
import com.mdrscr.ftpksei.properties.KseiConfig;

@Service
public class StatementService {
	
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYYMMdd"); 
    private static final String strYesterday = dtf.format(LocalDate.now().minusDays(1));
			
	@Autowired
	private BejStatementStagingRepo bejStmtStgRepo;
	@Autowired
	private StatementKseiRepo statementKseiRepo;
	@Autowired
	private FtpService ftpService;
	@Autowired
	private FileTransmisionService fileTransmisionService;
	@Autowired
	private KseiConfig kseiConfig;
	
    DateFormat df = new SimpleDateFormat("yyyyMMdd");
  
    public StatementKsei mapping (BejStatementStaging bejstmt) {
    	StatementKsei stmt = new StatementKsei();
    	stmt.setExtref(bejstmt.getExtref());
    	stmt.setSeqnum(bejstmt.getSeqnum());
    	stmt.setAc(bejstmt.getAcctno());
    	stmt.setCurcod(bejstmt.getCurcod());
    	stmt.setValdate(bejstmt.getValdat());
    	stmt.setOpenbal(bejstmt.getOpnbal());
    	stmt.setStatLineExtRef(bejstmt.getTrnref());
    	stmt.setTrxtype(bejstmt.getTrntyp());
    	stmt.setDc(bejstmt.getDrorcr());
    	stmt.setCashVal(bejstmt.getTrnamt());
    	stmt.setDescription(bejstmt.getTrndsc());
    	stmt.setCloseBal(bejstmt.getClsbal());
    	stmt.setNotes(bejstmt.getAcnote());
    	return stmt;
    }
    
    @Transactional
	public String sendToKsei () throws IOException {

    	Integer recordCounter = new Integer(0);
    	Integer fileCounter = fileTransmisionService.getLastFileNumber("STATEMENT") + 1; 
		String fileName = "ReactStmt_BMAN2_" + strYesterday + "_" + String.format("%02d", fileCounter) + ".fsp";

		FileWriter fileWriter = new FileWriter(kseiConfig.getLocalOutbDir()+"\\" + fileName);

	    PrintWriter printWriter = new PrintWriter(fileWriter);
    	printWriter.println("Mulai");
	    List<BejStatementStaging> stmts = bejStmtStgRepo.findByValdat(strYesterday);

		for (BejStatementStaging stmt : stmts) {
			recordCounter++;
			StatementKsei stmtKsei = mapping(stmt);
			stmtKsei.setFileName(fileName);
			stmtKsei.setCreateTime(LocalDateTime.now());
			
			String newLine = stmtKsei.getExtref() + "|" + 
						stmtKsei.getSeqnum() + "|" + 
						stmtKsei.getAc() + "|" +
						stmtKsei.getCurcod() + "|" +
						stmtKsei.getValdate() + "|" +
						stmtKsei.getOpenbal() + "|" +
						stmtKsei.getStatLineExtRef() + "|" +
						stmtKsei.getTrxtype() + "|" +
						stmtKsei.getDc() + "|" +
						stmtKsei.getCashVal() + "|" +
						stmtKsei.getDescription() + "|" +
						stmtKsei.getCloseBal() + "|" +
						stmtKsei.getNotes()==null?"":stmtKsei.getNotes();
	    	printWriter.println(newLine);
	    	
	    	statementKseiRepo.save(stmtKsei);
		}
		
    	printWriter.close();
    	
    	FileTransmision fileQ = new FileTransmision(fileName, fileCounter, "STATEMENT");
    	fileQ.setRecordNumber(recordCounter);
    	
    	// kirim pake ftp
    	try {
			ftpService.upload(fileName, kseiConfig.getLocalOutbDir());
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
	
    public List<StatementKsei> getAllStatementKsei (String filename) {
    	 List<StatementKsei> stmts = statementKseiRepo.findByFileName(filename);
    	
    	return stmts;
    }

    public List<StatementKsei> getAllStatementKsei () {
   	 List<StatementKsei> stmts = statementKseiRepo.findAll();
   	
   	return stmts;
   }
}

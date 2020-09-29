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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
    private static final Logger logger = LoggerFactory.getLogger(StatementService.class);
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYYMMdd"); 
			
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
        String strYesterday = dtf.format(LocalDate.now().minusDays(1));

//    	Integer recordCounter = new Integer(0);
		List<File> statementFiles = new ArrayList<File>();
    	Integer fileCounter = fileTransmisionService.getLastFileNumber("STATEMENT"); 
		String fileName = "RecActStmt_BMAN2_" + strYesterday + "_" + String.format("%02d", fileCounter) + ".fsp";

	    List<BejStatementStaging> stmts = bejStmtStgRepo.findByValdat(strYesterday);

		File f1 = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		Integer recordCounter = new Integer(0);

		for (BejStatementStaging stmt : stmts) {
//			recordCounter++;
			if (recordCounter++ == 0) {
				++fileCounter;
				f1 = new File(kseiConfig.getLocalOutbDir() + 
						"RecActStmt_BMAN2_" + strYesterday + "_" + String.format("%02d", fileCounter) + ".fsp");
				logger.debug("Akan buat file " + f1.getName());
				fw = new FileWriter(f1);
				bw = new BufferedWriter(fw);
				statementFiles.add(f1);
			}

			StatementKsei stmtKsei = mapping(stmt);
			stmtKsei.setFileName(fileName);
			stmtKsei.setCreateTime(LocalDateTime.now());
			
			String notes = "";
			if (!(null==stmtKsei.getNotes()))  notes = stmtKsei.getNotes();
			String strLine = stmtKsei.getExtref() + "|" + 
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
						stmtKsei.getCloseBal()  + "|" +
						notes;

//	    	System.out.println("Record: " + strLine);
	    	if (!(null==strLine)) {
	    		bw.write(strLine); bw.newLine();
	    	}
	    	statementKseiRepo.save(stmtKsei);
	    	
	    	if (recordCounter == 50000) {
	    		bw.close();
	    		fw.close();
	    		FileTransmision fileQ = new FileTransmision(f1.getName(), fileCounter, "STATEMENT");
		    	fileQ.setValDate(strYesterday);
		    	fileQ.setRecordNumber(recordCounter);
		    	fileQ.setValDate(strYesterday);
		    	fileTransmisionService.save(fileQ);
	    		recordCounter = 0;
	    	}

		}
		
		if (recordCounter > 0) {
		    bw.close();
		    fw.close();
		    logger.debug("Akan insert file_transmission " + f1.getName() + fileCounter);
    		FileTransmision fileQ = new FileTransmision(f1.getName(), fileCounter, "STATEMENT");
	    	fileQ.setValDate(strYesterday);
	    	fileQ.setRecordNumber(recordCounter);
	    	fileQ.setValDate(strYesterday);
	    	fileTransmisionService.save(fileQ);
		}
    	
	    System.out.println("Akan insert filetransmision: " + recordCounter);
    	FileTransmision fileQ = new FileTransmision(fileName, fileCounter, "STATEMENT");
    	fileQ.setRecordNumber(recordCounter);
    	
    	// kirim pake ftp
		for (File uploadFile : statementFiles ) {
			logger.debug("Akan query filetrans "  + uploadFile.getName()); 
			FileTransmision ft = fileTransmisionService.getByFileName(uploadFile.getName());
			try {
				ftpService.upload(uploadFile.getName(), kseiConfig.getLocalOutbDir());
				ft.setSendStatus("SUCCESS");
			} catch (JSchException | SftpException e) {
				ft.setSendStatus("ERROR");
				ft.setErrorMsg("Gagal kirim FTP");
				logger.error("Gagal kirim FTP");
			}
			fileTransmisionService.save(ft);
		}
		
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

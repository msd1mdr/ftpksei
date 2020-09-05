package com.mdrscr.ftpksei.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.mdrscr.ftpksei.persist.model.BalanceKsei;
import com.mdrscr.ftpksei.persist.model.FileTransmision;
import com.mdrscr.ftpksei.persist.model.StatementKsei;
import com.mdrscr.ftpksei.persist.model.StaticKsei;
import com.mdrscr.ftpksei.persist.repo.BalanceKseiRepo;
import com.mdrscr.ftpksei.persist.repo.FileTransmisionRepo;
import com.mdrscr.ftpksei.persist.repo.StatementKseiRepo;
import com.mdrscr.ftpksei.persist.repo.StaticKseiRepo;

@Service
public class KseiResponseService {

	@Autowired
	private FtpService ftpService;
	@Autowired
	private FileTransmisionRepo fileTransmisionRepo;
	@Autowired
	private StaticKseiRepo staticKseiRepo;
	@Autowired
	private StatementKseiRepo stmtKseiRepo;
	@Autowired
	private BalanceKseiRepo balanceKseiRepo;
	@Autowired
	private ZipService zipService;
	
    private static final Logger logger = LoggerFactory.getLogger(KseiResponseService.class);

	private int totalSuccess;
	private int totalError;
	private String inputFileName;
	
	public void setStaticErrorResponse (String filename, String extref, String errorCode) {
		StaticKsei statKsei = staticKseiRepo.findByExtrefAndFileName(extref, filename);
		statKsei.setAckStatus("Error");
		statKsei.setAckNotes(errorCode);
		statKsei.setAckTime(LocalDateTime.now());
		staticKseiRepo.save(statKsei);		
	}

	public void setStatementErrorResponse (String filename, String extref, String errorCode) {
		StatementKsei statementKsei = stmtKseiRepo.findByExtrefAndFileName(extref, filename);
		statementKsei.setAckStatus("Error");
		statementKsei.setAckNotes(errorCode);
		statementKsei.setAckTime(LocalDateTime.now());
		stmtKseiRepo.save(statementKsei);		
	}

	public void setBalanceErrorResponse (String filename, String extref, String errorCode) {
		BalanceKsei balanceKsei = balanceKseiRepo.findByExtrefAndFileName(extref, filename);
		balanceKsei.setAckStatus("Error");
		balanceKsei.setAckNotes(errorCode);
		balanceKsei.setAckTime(LocalDateTime.now());
		balanceKseiRepo.save(balanceKsei);		
	}

	public File[] extractZip (File[] dwFiles) throws IOException {
		
		File[] files = new File[0];
		for (File file : dwFiles) {
			File[] extrcFiles = zipService.unzipFile(file);
			files = ArrayUtils.addAll(extrcFiles, files); 
		}
		return files;
	}
	
	public void getResponse (String fileNameExpr) throws JSchException, SftpException, IOException {
		File[] dwFiles = ftpService.downloadFiles(fileNameExpr);
		File[] files = extractZip(dwFiles);
		
		for (File file : files) {
	        Scanner myReader;
	        
			try {
				myReader = new Scanner(file);
		        while (myReader.hasNextLine()) {
		        	String data = myReader.nextLine();
			        String parseData = "";
			        if (data.startsWith("Input File Name:")) {
			        	  inputFileName = data.substring(17);
			        	  System.out.println("InputFileName: " + inputFileName);
			        } else if (data.startsWith("Total Success:")) {
			        	  parseData = data.substring(15,data.indexOf("Row")-1);
			        	  totalSuccess = Integer.parseInt(parseData);
			        } else if (data.startsWith("Total Failed:")) {
			        	  parseData = data.substring(14,data.indexOf("Row")-1);
			        	  totalError = Integer.parseInt(parseData);
			        } else if (data.startsWith("Error Detail:")) {
			        	  break;
			        }
		        }

		        FileTransmision ft = fileTransmisionRepo.findByFileName(inputFileName);
		    	ft.setResponseFile(file.getName());
		    	ft.setResponseSuccess(totalSuccess);
		    	ft.setResponseError(totalError);
		        fileTransmisionRepo.save(ft);
       
		    	while (myReader.hasNextLine()) {
		        	String data = myReader.nextLine();
		        	System.out.println("Data: " + data);
		        	if (data.trim().length() > 0) {
			        	String[] arrStr = data.split("\\|");
			        	System.out.println("Extref: " + arrStr[0]);
			        	System.out.println("Error: " + arrStr[1]);
		        	
						if (file.getName().contains("OutDataStaticInv_"))  {
							setStaticErrorResponse(inputFileName, arrStr[0], arrStr[1]);

						} else if (file.getName().contains("OutRecActStmt_"))  {
							setStatementErrorResponse(inputFileName, arrStr[0], arrStr[1]);
	
						} else if (file.getName().contains("OutRecBalance_")) {
							setBalanceErrorResponse(inputFileName, arrStr[0], arrStr[1]);
							
						}
		        	}
		        }

		        myReader.close();

			} catch (FileNotFoundException e) {
				logger.error("File tidak ketemu");
			}
		}
	
	}
		
}

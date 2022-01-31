package com.mdrscr.ftpksei.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.mdrscr.ftpksei.persist.model.FileTransmision;
import com.mdrscr.ftpksei.persist.model.StatementKsei;
import com.mdrscr.ftpksei.persist.model.StaticKsei;
//import com.mdrscr.ftpksei.persist.repo.FileTransmisionRepo;
import com.mdrscr.ftpksei.persist.repo.StatementKseiRepo;
import com.mdrscr.ftpksei.persist.repo.StaticKseiRepo;

@Service
public class KseiResponseService {

	@Autowired
	private FtpService ftpService;
	@Autowired
	private FileTransmisionService fileTransmisionService;
	@Autowired
	private StaticKseiRepo staticKseiRepo;
	@Autowired
	private StatementKseiRepo stmtKseiRepo;
	@Autowired
	private ZipService zipService;
	
    private static final Logger logger = LoggerFactory.getLogger(KseiResponseService.class);
//    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYYMMdd"); 

	private int totalSuccess;
	private int totalError;
	private String inputFileName;
	
	public void setStaticErrorResponse (String filename, String extref, String errorCode) {
		List<StaticKsei> statKseiLst = staticKseiRepo.findByExtrefAndFileName(extref, filename);
		for (StaticKsei statKsei : statKseiLst) {
			statKsei.setAckStatus("Error");
			statKsei.setAckNotes(errorCode);
			statKsei.setAckTime(LocalDateTime.now());
			staticKseiRepo.save(statKsei);		
		}
	}

	public void setStatementErrorResponse (String filename, String extref, String errorCode) {
		StatementKsei statementKsei = stmtKseiRepo.findByExtrefAndFileName(extref, filename);
		statementKsei.setAckStatus("Error");
		statementKsei.setAckNotes(errorCode);
		statementKsei.setAckTime(LocalDateTime.now());
		stmtKseiRepo.save(statementKsei);		
	}

//	public void setBalanceErrorResponse (String filename, String extref, String errorCode) {
//		BalanceKsei balanceKsei = balanceKseiRepo.findByExtrefAndFileName(extref, filename);
//		balanceKsei.setAckStatus("Error");
//		balanceKsei.setAckNotes(errorCode);
//		balanceKsei.setAckTime(LocalDateTime.now());
//		balanceKseiRepo.save(balanceKsei);		
//	}


	public File[] extractZip (File[] dwFiles) throws IOException {
		
		File[] files = new File[0];
		for (File file : dwFiles) {
			File[] extrcFiles = zipService.unzipFile(file);
			files = ArrayUtils.addAll(extrcFiles, files); 
		}
		return files;
	}
	
	
	public void getResponseFile () throws JSchException, SftpException, IOException {
//	    String strYesterday = dtf.format(LocalDate.now().minusDays(1));
	    
		List<FileTransmision> fts = fileTransmisionService.getResponseFileIsNull();
		for (FileTransmision ft : fts) {
			String filename = StringUtils.replace(ft.getFileName(), "fsp", "zip");
			logger.debug("Cari output untuk " + ft.getFileName());
			
			Optional<File> dwFile = ftpService.downloadFile("Out".concat(filename), ft.getSubModul());
			File[] extractFiles = new File[0];
	        if (dwFile.isPresent()) {
				System.out.println("Dapat file " + dwFile.get());
				extractFiles = zipService.unzipFile(dwFile.get());
	        }
	        
			for (File outFile : extractFiles) {
		        Scanner myReader;
				try {
					myReader = new Scanner(outFile);
			        while (myReader.hasNextLine()) {
			        	String data = myReader.nextLine();

				        String parseData = "";
				        if (data.startsWith("Input File Name:")) {
				        	  inputFileName = data.substring(17);
				        } else if (data.startsWith("Total Success:")) {
				        	  parseData = data.substring(15,data.indexOf("Row")-1);
				        	  totalSuccess = Integer.parseInt(parseData);
				        } else if (data.startsWith("Total Failed:")) {
				        	  parseData = data.substring(14,data.indexOf("Row")-1);
				        	  totalError = Integer.parseInt(parseData);
				        } else if (data.startsWith("Error Detail :")) {
				        	  break;
				        }
			        }
	
			        logger.debug("InputFile: " + inputFileName + ", success: " + totalSuccess + ", error: " + totalError);
			        ft.setResponseFile(outFile.getName());
			    	ft.setResponseSuccess(totalSuccess);
			    	ft.setResponseError(totalError);
			        fileTransmisionService.save(ft);
			        logger.debug("Sudah update filetrans "+ft.getFileName());	
			        
			    	while (myReader.hasNextLine()) {

			        	String data = myReader.nextLine();
			        	if (data.trim().length() > 0) {
			        		
				        	String[] arrStr = data.split("\\|");
			        	
							if (outFile.getName().contains("OutDataStaticInv_"))  {
								setStaticErrorResponse(inputFileName, arrStr[0], arrStr[1]);
	
							} else if (outFile.getName().contains("OutRecActStmt_"))  {
								setStatementErrorResponse(inputFileName, arrStr[0], arrStr[1]);
		
							} 
//							else if (file.getName().contains("OutRecBalance_")) {
//								setBalanceErrorResponse(inputFileName, arrStr[0], arrStr[1]);
//							}
			        	}
			        }
	
			        myReader.close();
	
				} catch (FileNotFoundException e) {
					logger.error("File tidak ketemu");
				}

			}
		}
		
	}
		
}

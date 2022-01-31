package com.mdrscr.ftpksei.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdrscr.ftpksei.persist.model.FileTransmision;
import com.mdrscr.ftpksei.persist.repo.FileTransmisionRepo;

@Service
public class FileTransmisionService {
    private static final Logger logger = LoggerFactory.getLogger(FileTransmisionService.class);

	@Autowired
	private FileTransmisionRepo fileTransmisionRepo;
	
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd"); 

	public Integer getLastFileNumber (String subModul) {
	    String strYesterday = dtf.format(LocalDate.now().minusDays(1));
		List<FileTransmision> files = fileTransmisionRepo.findBySubModulAndValDateOrderByDailyCounterDesc(subModul, strYesterday);
		Integer lastNumber = 0;
		if (files.size() > 0) lastNumber = files.get(0).getDailyCounter();
		return lastNumber;
	}
	
	public List<FileTransmision> findAllError () {
		return fileTransmisionRepo.findBySendStatusOrderBySendTime("ERROR");
	}
	
	public FileTransmision getByFileName (String filename) {
		logger.debug("Akan query FileTransmisionByFileName: "+ filename);
		List<FileTransmision> ftList = new ArrayList<>();
		ftList = fileTransmisionRepo.findByFileName(filename);
		logger.debug("Dapat sebanyak " + ftList.size());

		if (ftList.size()==0) return new FileTransmision();
		return ftList.get(0);
	}
	
	public FileTransmision save (FileTransmision fileRec) {
		return (fileTransmisionRepo.save(fileRec));
	}
	
	public List<FileTransmision> getResponseFileIsNull() {
		List<FileTransmision> fts = new ArrayList<>();
		
		for (int i=0; i<=3; i++) {
			String strDate = dtf.format(LocalDate.now().minusDays(i));
			List<FileTransmision> ft = fileTransmisionRepo.findByValDateAndResponseFileIsNull(strDate);
			fts.addAll(ft);
		}
				
		return fts;
	}
}

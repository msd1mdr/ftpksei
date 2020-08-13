package com.mdrscr.ftpksei.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdrscr.ftpksei.persist.model.FileTransmision;
import com.mdrscr.ftpksei.persist.repo.FileTransmisionRepo;

@Service
public class FileTransmisionService {

	@Autowired
	private FileTransmisionRepo fileTransmisionRepo;
	
    DateFormat df = new SimpleDateFormat("yyyyMMdd");

	public Integer getLastFileNumber (String subModul) {
		List<FileTransmision> files = fileTransmisionRepo.findBySubModulAndValDateOrderByDailyCounterDesc(subModul, df.format(new Date()));
		Integer lastNumber = new Integer(0);
		if (files.size() > 0) {
			lastNumber = files.get(0).getDailyCounter();
		}
		return lastNumber;
	}
	
	public List<FileTransmision> findAllError () {
		return fileTransmisionRepo.findBySendStatusOrderBySendTime("ERROR");
	}
	
	public void save (FileTransmision fileRec) {
		fileTransmisionRepo.save(fileRec);
	}
	
	public List<FileTransmision> getResponseFileIsNull(String valdate) {
		return fileTransmisionRepo.findByValDateAndResponseFileIsNull(valdate);
	}
}

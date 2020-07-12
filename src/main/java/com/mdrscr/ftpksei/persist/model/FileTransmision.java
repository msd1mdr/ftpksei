package com.mdrscr.ftpksei.persist.model;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name="FILE_TRANSMISION")
@SequenceGenerator(name="SEQ2", allocationSize=1)
@Data @NoArgsConstructor
public class FileTransmision {

	@Id	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ2")
	private Long Id;
	@Column(name="FILENAME")
	private String fileName;
	private Integer dailyCounter;
	private String sendMethod;
	private String recipient;
	@Column(name="SUBMODUL")
	private String subModul;
	@Column(name="VALDATE")
	private String valDate;
	private String sendStatus;
	private Integer retry;
	@Column(name="ERRORMSG")
	private String errorMsg;
	private LocalDateTime sendTime;
	
	public FileTransmision (String fname, Integer counter, String subModul) {
	    DateFormat df = new SimpleDateFormat("yyyyMMdd");
    	this.fileName = fname;
    	this.recipient = "KSEI";
    	this.sendMethod = "FTP";
    	this.subModul = subModul;
    	this.sendTime = LocalDateTime.now();
    	this.valDate = df.format(new Date());
    	this.dailyCounter = counter;
	}




}

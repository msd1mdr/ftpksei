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

@Entity @Table(name="FILE_TRANSMISION")
@SequenceGenerator(name="SEQ1", allocationSize=1)
public class FileTransmision {

	@Id	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ1")
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
	private Integer recordNumber;
	private String sendStatus;
	private Integer retry;
	@Column(name="ERRORMSG")
	private String errorMsg;
	private LocalDateTime sendTime;
	private String responseFile;
	private Integer responseSuccess;
	private Integer responseError;
	
	
	public FileTransmision() {
		super();
	}

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

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getDailyCounter() {
		return dailyCounter;
	}

	public void setDailyCounter(Integer dailyCounter) {
		this.dailyCounter = dailyCounter;
	}

	public String getSendMethod() {
		return sendMethod;
	}

	public void setSendMethod(String sendMethod) {
		this.sendMethod = sendMethod;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSubModul() {
		return subModul;
	}

	public void setSubModul(String subModul) {
		this.subModul = subModul;
	}

	public String getValDate() {
		return valDate;
	}

	public void setValDate(String valDate) {
		this.valDate = valDate;
	}

	public Integer getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(Integer recordNumber) {
		this.recordNumber = recordNumber;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Integer getRetry() {
		return retry;
	}

	public void setRetry(Integer retry) {
		this.retry = retry;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public LocalDateTime getSendTime() {
		return sendTime;
	}

	public void setSendTime(LocalDateTime sendTime) {
		this.sendTime = sendTime;
	}

	public String getResponseFile() {
		return responseFile;
	}

	public void setResponseFile(String responseFile) {
		this.responseFile = responseFile;
	}

	public Integer getResponseSuccess() {
		return responseSuccess;
	}

	public void setResponseSuccess(Integer responseSuccess) {
		this.responseSuccess = responseSuccess;
	}

	public Integer getResponseError() {
		return responseError;
	}

	public void setResponseError(Integer responseError) {
		this.responseError = responseError;
	}




}

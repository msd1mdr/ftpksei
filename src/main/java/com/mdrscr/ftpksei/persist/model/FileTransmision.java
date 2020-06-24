package com.mdrscr.ftpksei.persist.model;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity @Table(name="FILE_TRANSMISION")
@SequenceGenerator(name="SEQ2", allocationSize=1)
public class FileTransmision {

	@Id	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ2")
	private Long Id;
	@Column(name="FILENAME")
	private String fileName;
	private Integer dailyCounter;
//	@Column(name="SEND_METHOD")
	private String sendMethod;
	private String recipient;
	@Column(name="SUBMODUL")
	private String subModul;
	@Column(name="VALDATE")
	private String valDate;
//	@Column(name="SEND_STATUS")
	private String sendStatus;
	private String retry;
	@Column(name="ERRORMSG")
	private String errorMsg;
//	@Column(name="SEND_TIME")
	private LocalDateTime sendTime;
	
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
	public String getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}
	public String getRetry() {
		return retry;
	}
	public void setRetry(String retry) {
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


}

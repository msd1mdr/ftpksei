package com.mdrscr.ftpksei.persist.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name="BALANCE_KSEI")
public class BalanceKsei {

	@Id
	private String extref;
	private String bankcode;
	private String account;
	private String curcod;
	private String valdate;
	private String balance;
	private String notes;
	private String fileName;
	private String ackStatus;
	private LocalDateTime ackTime;
	private String ackNotes;
	
	public String getExtref() {
		return extref;
	}
	public void setExtref(String extref) {
		this.extref = extref;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCurcod() {
		return curcod;
	}
	public void setCurcod(String curcod) {
		this.curcod = curcod;
	}
	public String getValdate() {
		return valdate;
	}
	public void setValdate(String valdate) {
		this.valdate = valdate;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getAckStatus() {
		return ackStatus;
	}
	public void setAckStatus(String ackStatus) {
		this.ackStatus = ackStatus;
	}
	public LocalDateTime getAckTime() {
		return ackTime;
	}
	public void setAckTime(LocalDateTime ackTime) {
		this.ackTime = ackTime;
	}
	public String getAckNotes() {
		return ackNotes;
	}
	public void setAckNotes(String ackNotes) {
		this.ackNotes = ackNotes;
	}
	
	
}

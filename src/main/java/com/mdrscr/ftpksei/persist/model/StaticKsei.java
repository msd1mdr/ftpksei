package com.mdrscr.ftpksei.persist.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity @Table(name="STATIC_KSEI")
@SequenceGenerator(name="SEQ1", allocationSize=1)
public class StaticKsei {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ1")
	private Long Id;
	private String extref;
	private String participantid;
	private String participantname;
	private String investorname;
	private String sidnumber;
	private String accountnumber;
	private String bankaccnumber;
	private String bankcode;
	private String activitydate;
	private String activity;
	private LocalDateTime createTime;
	private String ackStatus;
	private String ackNotes;
	private LocalDateTime ackTime;
	private String fileName;
	
	public String getExtref() {
		return extref;
	}
	public void setExtref(String extref) {
		this.extref = extref;
	}
	public String getParticipantid() {
		return participantid;
	}
	public void setParticipantid(String participantid) {
		this.participantid = participantid;
	}
	public String getParticipantname() {
		return participantname;
	}
	public void setParticipantname(String participantname) {
		this.participantname = participantname;
	}
	public String getInvestorname() {
		return investorname;
	}
	public void setInvestorname(String investorname) {
		this.investorname = investorname;
	}
	public String getSidnumber() {
		return sidnumber;
	}
	public void setSidnumber(String sidnumber) {
		this.sidnumber = sidnumber;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	public String getBankaccnumber() {
		return bankaccnumber;
	}
	public void setBankaccnumber(String bankaccnumber) {
		this.bankaccnumber = bankaccnumber;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getActivitydate() {
		return activitydate;
	}
	public void setActivitydate(String activitydate) {
		this.activitydate = activitydate;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public LocalDateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
	public String getAckStatus() {
		return ackStatus;
	}
	public void setAckStatus(String ackStatus) {
		this.ackStatus = ackStatus;
	}
	public String getAckNotes() {
		return ackNotes;
	}
	public void setAckNotes(String ackNotes) {
		this.ackNotes = ackNotes;
	}
	public LocalDateTime getAckTime() {
		return ackTime;
	}
	public void setAckTime(LocalDateTime ackTime) {
		this.ackTime = ackTime;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}

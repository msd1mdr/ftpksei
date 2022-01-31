package com.mdrscr.ftpksei.persist.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name="STATIC_MSG")
public class StaticMsg {

	@Id
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
	private LocalDateTime ackTime;
	private String ackParticipantid;
	private String ackSidnumber;
	private String ackAccountnumber;
	private String ackActivity;
	private String flag;
	
	public StaticMsg() {
		this.extref = "";
		this.participantid = "";
		this.participantname = "";
		this.investorname = "";
		this.sidnumber = "";
		this.accountnumber = "";
		this.bankaccnumber = "";
		this.bankcode = "";
		this.activitydate = "";
		this.activity = "";
	}

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

	public LocalDateTime getAckTime() {
		return ackTime;
	}

	public void setAckTime(LocalDateTime ackTime) {
		this.ackTime = ackTime;
	}

	public String getAckParticipantid() {
		return ackParticipantid;
	}

	public void setAckParticipantid(String ackParticipantid) {
		this.ackParticipantid = ackParticipantid;
	}

	public String getAckSidnumber() {
		return ackSidnumber;
	}

	public void setAckSidnumber(String ackSidnumber) {
		this.ackSidnumber = ackSidnumber;
	}

	public String getAckAccountnumber() {
		return ackAccountnumber;
	}

	public void setAckAccountnumber(String ackAccountnumber) {
		this.ackAccountnumber = ackAccountnumber;
	}

	public String getAckActivity() {
		return ackActivity;
	}

	public void setAckActivity(String ackActivity) {
		this.ackActivity = ackActivity;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	

}

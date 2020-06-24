package com.mdrscr.ftpksei.persist.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity @Table(name="STATEMENT_KSEI")
@SequenceGenerator(name="SEQ2", allocationSize=1)
public class StatementKsei {

	@Id 
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ2")
	private Long Id;
	private String extref;
	private String seqnum;
	@Column(name="AC")
	private String ac;
	private String curcod;
	private String valdate;
	private String openbal;
	@Column(name="ACCREF")
	private String statLineExtRef;
	@Column(name="TRXTYPE")
	private String trxtype;
	@Column(name="DC")
	private String dc;
	@Column(name="CASHVAL")
	private String cashVal;
	@Column(name="DESCRIPTION")
	private String description;
	@Column(name="CLOSEBAL")
	private String closeBal;
	@Column(name="NOTES")
	private String notes;
	
	@Column(name="CREATE_TIME")
	private LocalDateTime createTime;
	@Column(name="FILE_NAME")
	private String fileName;
	@Column(name="ACK_STATUS")
	private String ackStatus;
	@Column(name="ACK_NOTES")
	private String ackNotes;
	@Column(name="ACK_TIME")
	private LocalDateTime ackTime;
	
	public StatementKsei () {}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getExtref() {
		return extref;
	}

	public void setExtref(String extref) {
		this.extref = extref;
	}

	public String getSeqnum() {
		return seqnum;
	}

	public void setSeqnum(String seqnum) {
		this.seqnum = seqnum;
	}

	public String getAc() {
		return ac;
	}

	public void setAc(String ac) {
		this.ac = ac;
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

	public String getOpenbal() {
		return openbal;
	}

	public void setOpenbal(String openbal) {
		this.openbal = openbal;
	}

	public String getStatLineExtRef() {
		return statLineExtRef;
	}

	public void setStatLineExtRef(String statLineExtRef) {
		this.statLineExtRef = statLineExtRef;
	}

	public String getTrxtype() {
		return trxtype;
	}

	public void setTrxtype(String trxtype) {
		this.trxtype = trxtype;
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public String getCashVal() {
		return cashVal;
	}

	public void setCashVal(String cashVal) {
		this.cashVal = cashVal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCloseBal() {
		return closeBal;
	}

	public void setCloseBal(String closeBal) {
		this.closeBal = closeBal;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
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

	
}

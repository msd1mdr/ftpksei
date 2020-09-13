package com.mdrscr.ftpksei.persist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name="BEJSTMT_STG")
public class BejStatementStaging {

	@Id
	@Column(name="EXTREF")
	private String extref;
	
	@Column(name="BANKID")
	private String bankId;
	@Column(name="ACCTNO")
	private String acctno;
	@Column(name="CURCOD")
	private String curcod;
	@Column(name="VALDAT")
	private String valdat;
	@Column(name="OPNBAL")
	private String opnbal;
	@Column(name="CLSBAL")
	private String clsbal;
	@Column(name="ACNOTE")
	private String acnote;
	@Column(name="SEQNUM")
	private String seqnum;
	@Column(name="DRORCR")
	private String drorcr;
	@Column(name="TRNTYP")
	private String trntyp;
	@Column(name="TRNAMT")
	private String trnamt;
	@Column(name="TRNDSC")
	private String trndsc;
	@Column(name="TRNREF")
	private String trnref;
	@Column(name="FLAG")
	private String flag;
	
	public String getExtref() {
		return extref;
	}
	public void setExtref(String extref) {
		this.extref = extref;
	}
	public String getDrorcr() {
		return drorcr;
	}
	public void setDrorcr(String drorcr) {
		this.drorcr = drorcr;
	}
	public String getTrntyp() {
		return trntyp;
	}
	public void setTrntyp(String trntyp) {
		this.trntyp = trntyp;
	}
	public String getTrnamt() {
		return trnamt;
	}
	public void setTrnamt(String trnamt) {
		this.trnamt = trnamt;
	}
	public String getTrndsc() {
		return trndsc;
	}
	public void setTrndsc(String trndsc) {
		this.trndsc = trndsc;
	}
	public String getTrnref() {
		return trnref;
	}
	public void setTrnref(String trnref) {
		this.trnref = trnref;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getAcctno() {
		return acctno;
	}
	public void setAcctno(String acctno) {
		this.acctno = acctno;
	}
	public String getCurcod() {
		return curcod;
	}
	public void setCurcod(String curcod) {
		this.curcod = curcod;
	}
	public String getValdat() {
		return valdat;
	}
	public void setValdat(String valdat) {
		this.valdat = valdat;
	}
	public String getOpnbal() {
		return opnbal;
	}
	public void setOpnbal(String opnbal) {
		this.opnbal = opnbal;
	}
	public String getClsbal() {
		return clsbal;
	}
	public void setClsbal(String clsbal) {
		this.clsbal = clsbal;
	}
	public String getAcnote() {
		return acnote;
	}
	public void setAcnote(String acnote) {
		this.acnote = acnote;
	}
	public String getSeqnum() {
		return seqnum;
	}
	public void setSeqnum(String seqnum) {
		this.seqnum = seqnum;
	}
	
	
}

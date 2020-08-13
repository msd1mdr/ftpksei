package com.mdrscr.ftpksei.persist.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name="BEJACN")
public class Bejacn {

	@Id
	private String acctno;
	private String actype;
	private String partid;
	private String partnm;
	private String ivstnm;
	private String ivstid;
	private String subacn;
	private String bjcrt8;
	private String bjstat;
	private String bjvusr;
	private String bjvdt8;
	private String bjvtme;
	private String bjvald;
	private String bjseqn;
	private String bjcbal;
	private String bejsc1;
	private String bejsc2;
	private String bejsc3;
	private String bejsc4;
	private String bejfl1;
	private String bejfl2;
	private String flag;
	
	public String getAcctno() {
		return acctno;
	}
	public void setAcctno(String acctno) {
		this.acctno = acctno;
	}
	public String getActype() {
		return actype;
	}
	public void setActype(String actype) {
		this.actype = actype;
	}
	public String getPartid() {
		return partid;
	}
	public void setPartid(String partid) {
		this.partid = partid;
	}
	public String getPartnm() {
		return partnm;
	}
	public void setPartnm(String partnm) {
		this.partnm = partnm;
	}
	public String getIvstnm() {
		return ivstnm;
	}
	public void setIvstnm(String ivstnm) {
		this.ivstnm = ivstnm;
	}
	public String getIvstid() {
		return ivstid;
	}
	public void setIvstid(String ivstid) {
		this.ivstid = ivstid;
	}
	public String getSubacn() {
		return subacn;
	}
	public void setSubacn(String subacn) {
		this.subacn = subacn;
	}
	public String getBjcrt8() {
		return bjcrt8;
	}
	public void setBjcrt8(String bjcrt8) {
		this.bjcrt8 = bjcrt8;
	}
	public String getBjstat() {
		return bjstat;
	}
	public void setBjstat(String bjstat) {
		this.bjstat = bjstat;
	}
	public String getBjvusr() {
		return bjvusr;
	}
	public void setBjvusr(String bjvusr) {
		this.bjvusr = bjvusr;
	}
	public String getBjvdt8() {
		return bjvdt8;
	}
	public void setBjvdt8(String bjvdt8) {
		this.bjvdt8 = bjvdt8;
	}
	public String getBjvtme() {
		return bjvtme;
	}
	public void setBjvtme(String bjvtme) {
		this.bjvtme = bjvtme;
	}
	public String getBjvald() {
		return bjvald;
	}
	public void setBjvald(String bjvald) {
		this.bjvald = bjvald;
	}
	public String getBjseqn() {
		return bjseqn;
	}
	public void setBjseqn(String bjseqn) {
		this.bjseqn = bjseqn;
	}
	public String getBjcbal() {
		return bjcbal;
	}
	public void setBjcbal(String bjcbal) {
		this.bjcbal = bjcbal;
	}
	public String getBejsc1() {
		return bejsc1;
	}
	public void setBejsc1(String bejsc1) {
		this.bejsc1 = bejsc1;
	}
	public String getBejsc2() {
		return bejsc2;
	}
	public void setBejsc2(String bejsc2) {
		this.bejsc2 = bejsc2;
	}
	public String getBejsc3() {
		return bejsc3;
	}
	public void setBejsc3(String bejsc3) {
		this.bejsc3 = bejsc3;
	}
	public String getBejsc4() {
		return bejsc4;
	}
	public void setBejsc4(String bejsc4) {
		this.bejsc4 = bejsc4;
	}
	public String getBejfl1() {
		return bejfl1;
	}
	public void setBejfl1(String bejfl1) {
		this.bejfl1 = bejfl1;
	}
	public String getBejfl2() {
		return bejfl2;
	}
	public void setBejfl2(String bejfl2) {
		this.bejfl2 = bejfl2;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	
}

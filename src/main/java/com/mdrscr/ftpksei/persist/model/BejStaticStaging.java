package com.mdrscr.ftpksei.persist.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name="BEJSTAT_STG")
public class BejStaticStaging {

	@Id
	private String extref;
	private String partid;
	private String partname;
	private String ivstname;
	private String sivstid;
	private String sacctno;
	private String acctno;
	private String bnkcod;
	private String actdate;
	private String activity;
	private String flag;
	
	public String getExtref() {
		return extref;
	}
	public void setExtref(String extref) {
		this.extref = extref;
	}
	public String getPartid() {
		return partid;
	}
	public void setPartid(String partid) {
		this.partid = partid;
	}
	public String getPartname() {
		return partname;
	}
	public void setPartname(String partname) {
		this.partname = partname;
	}
	public String getIvstname() {
		return ivstname;
	}
	public void setIvstname(String ivstname) {
		this.ivstname = ivstname;
	}
	public String getSivstid() {
		return sivstid;
	}
	public void setSivstid(String sivstid) {
		this.sivstid = sivstid;
	}
	public String getSacctno() {
		return sacctno;
	}
	public void setSacctno(String sacctno) {
		this.sacctno = sacctno;
	}
	public String getAcctno() {
		return acctno;
	}
	public void setAcctno(String acctno) {
		this.acctno = acctno;
	}
	public String getBnkcod() {
		return bnkcod;
	}
	public void setBnkcod(String bnkcod) {
		this.bnkcod = bnkcod;
	}
	public String getActdate() {
		return actdate;
	}
	public void setActdate(String actdate) {
		this.actdate = actdate;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
		
}

package com.mdrscr.ftpksei.persist.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name="PARAMETER")
public class Parameter {

	@Id
	private String kode;
	private String name;
	private String value1;
	private String value2;
	private String modifyBy;
	private LocalDate modifyDt;
	
	public String getKode() {
		return kode;
	}
	public void setKode(String kode) {
		this.kode = kode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public LocalDate getModifyDt() {
		return modifyDt;
	}
	public void setModifyDt(LocalDate modifyDt) {
		this.modifyDt = modifyDt;
	}
	
	
}

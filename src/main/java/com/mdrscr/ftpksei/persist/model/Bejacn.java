package com.mdrscr.ftpksei.persist.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
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
	
}

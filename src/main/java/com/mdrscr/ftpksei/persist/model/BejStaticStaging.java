package com.mdrscr.ftpksei.persist.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
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
		
}

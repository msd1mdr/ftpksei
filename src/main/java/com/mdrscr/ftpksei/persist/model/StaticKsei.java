package com.mdrscr.ftpksei.persist.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@Entity @Table(name="STATIC_KSEI")
public class StaticKsei {

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
	private String ackNotes;
	private LocalDateTime ackTime;
	private String fileName;
	
}

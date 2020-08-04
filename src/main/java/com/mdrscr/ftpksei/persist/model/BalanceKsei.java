package com.mdrscr.ftpksei.persist.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@Entity @Table(name="BALANCE_KSEI")
public class BalanceKsei {

	@Id
	private String extref;
	private String bankcode;
	private String account;
	private String curcod;
	private String valdate;
	private String balance;
	private String notes;
	private String fileName;
	private String ackStatus;
	private LocalDateTime ackTime;
	private String ackNotes;
	
}

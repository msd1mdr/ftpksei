package com.mdrscr.ftpksei.persist.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name="ANGGOTA_BURSA")
@Data @NoArgsConstructor
public class AnggotaBursa {
	
	@Id @Column(name="KODE_AB")
	private String kodeAb;

	private String namaAb;
	@Column(name="NO_REKENING")
	private String noRekening;

	@Column(name="SEND_METHOD")
	private String sendMethod;
	@Column(name="FILE_FORMAT")
	private String fileFormat;
	@Column(name="EMAIL_ADDRESS")
	private String emailAddress;
	
	@Column(name="IP_ADDRESS")
	private String ipAddress;
	@Column(name="PORT")
	private Integer port;
	@Column(name="FTP_USER")
	private String ftpUser;
	@Column(name="FTP_PASSWD")
	private String ftpPasswd;
	@Column(name="FILE_SIZE")
	private Integer fileSize;
	@Column(name="INTERVAL")
	private Integer interval;
	@Column(name="NEXT_SCHD")
	private Date nextSchedule;
	@Column(name="EXCP1")
	private String excp1;
	@Column(name="EXCP2")
	private String excp2;
	@Column(name="DIREKTORY")
	private String directory;
	@Column(name="WS_USER")
	private String wsUser;
	@Column(name="WS_PASSWD")
	private String wsPasswd;
	@Column(name="SERIAL_NO")
	private String serialNO;
	

	
}

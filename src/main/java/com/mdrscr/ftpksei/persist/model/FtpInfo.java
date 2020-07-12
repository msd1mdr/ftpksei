package com.mdrscr.ftpksei.persist.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name="FTP_INFO")
@Data @NoArgsConstructor
public class FtpInfo {
	
	@Id @Column(name="KODE_AB")
	private String kodeAb;
	private String protocol;

	@Column(name="IP_ADDR")
	private String ipAddress;

	private Integer port;

	@Column(name="USERNAME")
	private String ftpUser;
	@Column(name="PASSWD")
	private String ftpPasswd;

	@Column(name="OUTBND_DIR")
	private String outboundDir;
	
	@Column(name="INBND_DIR")
	private String inboundDir;
	private String localDir;

	
}

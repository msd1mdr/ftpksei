package com.mdrscr.ftpksei.persist.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="ANGGOTA_BURSA")
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
	
	public String getKodeAb() {
		return kodeAb;
	}
	public void setKodeAb(String kodeAb) {
		this.kodeAb = kodeAb;
	}
	public String getNamaAb() {
		return namaAb;
	}
	public void setNamaAb(String namaAb) {
		this.namaAb = namaAb;
	}
	public String getNoRekening() {
		return noRekening;
	}
	public void setNoRekening(String noRekening) {
		this.noRekening = noRekening;
	}
	public String getSendMethod() {
		return sendMethod;
	}
	public void setSendMethod(String sendMethod) {
		this.sendMethod = sendMethod;
	}
	public String getFileFormat() {
		return fileFormat;
	}
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getFtpUser() {
		return ftpUser;
	}
	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}
	public String getFtpPasswd() {
		return ftpPasswd;
	}
	public void setFtpPasswd(String ftpPasswd) {
		this.ftpPasswd = ftpPasswd;
	}
	public Integer getFileSize() {
		return fileSize;
	}
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}
	public Integer getInterval() {
		return interval;
	}
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	public Date getNextSchedule() {
		return nextSchedule;
	}
	public void setNextSchedule(Date nextSchedule) {
		this.nextSchedule = nextSchedule;
	}
	public String getExcp1() {
		return excp1;
	}
	public void setExcp1(String excp1) {
		this.excp1 = excp1;
	}
	public String getExcp2() {
		return excp2;
	}
	public void setExcp2(String excp2) {
		this.excp2 = excp2;
	}
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public String getWsUser() {
		return wsUser;
	}
	public void setWsUser(String wsUser) {
		this.wsUser = wsUser;
	}
	public String getWsPasswd() {
		return wsPasswd;
	}
	public void setWsPasswd(String wsPasswd) {
		this.wsPasswd = wsPasswd;
	}
	public String getSerialNO() {
		return serialNO;
	}
	public void setSerialNO(String serialNO) {
		this.serialNO = serialNO;
	}
	

	
}

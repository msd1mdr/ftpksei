package com.mdrscr.ftpksei.properties;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.mdrscr.ftpksei.persist.repo.ParameterRepo;



@Configuration
@ConfigurationProperties(prefix = "scrp")
public class KseiConfig {

	@Autowired 
	private ParameterRepo paramRepo;

	private String ftpIpAddr;
	private int ftpPort;
	private String ftpPasswd;
	private String ftpUser;
	private String ftpOutboundDir;
	private String ftpInboundDir;
	private String localOutbDir;
	private String localInbDir;

	@PostConstruct
	public void init() {
//		FtpInfo kseiInfo = ftpInfoRepo.findById("KSEI").orElse(new FtpInfo());
		this.ftpIpAddr = paramRepo.findByName("ksei.sftp.server").getValue1(); 
//		this.ftpPort = kseiInfo.getPort();
		this.ftpUser = paramRepo.findByName("ksei.sftp.username").getValue1();
		this.ftpPasswd = paramRepo.findByName("ksei.sftp.password").getValue1();
		this.ftpInboundDir = paramRepo.findByName("ksei.sftp.remoteinbdir").getValue1();
		this.ftpOutboundDir = paramRepo.findByName("ksei.sftp.remoteoutbdir").getValue1();
		this.localOutbDir = paramRepo.findByName("ksei.sftp.localoutbdir").getValue1();
		this.localInbDir = paramRepo.findByName("ksei.sftp.localinbdir").getValue1();
	}

	
	public KseiConfig() {
		super();
	}


	public ParameterRepo getParamRepo() {
		return paramRepo;
	}

	public void setParamRepo(ParameterRepo paramRepo) {
		this.paramRepo = paramRepo;
	}

	public String getFtpIpAddr() {
		return ftpIpAddr;
	}

	public void setFtpIpAddr(String ftpIpAddr) {
		this.ftpIpAddr = ftpIpAddr;
	}

	public int getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpPasswd() {
		return ftpPasswd;
	}

	public void setFtpPasswd(String ftpPasswd) {
		this.ftpPasswd = ftpPasswd;
	}

	public String getFtpUser() {
		return ftpUser;
	}

	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}

	public String getFtpOutboundDir() {
		return ftpOutboundDir;
	}

	public void setFtpOutboundDir(String ftpOutboundDir) {
		this.ftpOutboundDir = ftpOutboundDir;
	}

	public String getFtpInboundDir() {
		return ftpInboundDir;
	}

	public void setFtpInboundDir(String ftpInboundDir) {
		this.ftpInboundDir = ftpInboundDir;
	}

	public String getLocalOutbDir() {
		return localOutbDir;
	}

	public void setLocalOutbDir(String localOutbDir) {
		this.localOutbDir = localOutbDir;
	}

	public String getLocalInbDir() {
		return localInbDir;
	}

	public void setLocalInbDir(String localInbDir) {
		this.localInbDir = localInbDir;
	}

	
}

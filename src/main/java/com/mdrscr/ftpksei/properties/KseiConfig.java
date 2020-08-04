package com.mdrscr.ftpksei.properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.mdrscr.ftpksei.persist.model.FtpInfo;
import com.mdrscr.ftpksei.persist.repo.FtpInfoRepo;

import lombok.Data;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Configuration
@ConfigurationProperties(prefix = "scrp")
@Data @NoArgsConstructor
public class KseiConfig {

	@Getter(AccessLevel.PROTECTED)
	@Autowired 
	private FtpInfoRepo ftpInfoRepo;

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
		FtpInfo kseiInfo = ftpInfoRepo.findById("KSEI").orElse(new FtpInfo());
		this.ftpIpAddr = kseiInfo.getIpAddress();
		this.ftpPort = kseiInfo.getPort();
		this.ftpUser = kseiInfo.getFtpUser();
		this.ftpPasswd = kseiInfo.getFtpPasswd();
		this.ftpInboundDir = kseiInfo.getRemoteInboundDir();
		this.ftpOutboundDir = kseiInfo.getRemoteOutboundDir();
		this.localOutbDir = kseiInfo.getLocalOutboundDir();
		this.localInbDir = kseiInfo.getLocalInboundDir();
	}

}

package com.mdrscr.ftpksei.properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.mdrscr.ftpksei.persist.model.AnggotaBursa;
import com.mdrscr.ftpksei.persist.repo.AnggotaBursaRepo;

import lombok.Data;
import lombok.AccessLevel;
import lombok.Getter;

@Configuration
@ConfigurationProperties(prefix = "scrp")
@Data
public class AppConfig {

	@Getter(AccessLevel.PROTECTED)
	@Autowired 
	private AnggotaBursaRepo anggotaBursaRepo;
	
	private KseiProperties ksei;
	
	@PostConstruct
	public void init() {
		AnggotaBursa ab = anggotaBursaRepo.findById("KSEI").orElse(new AnggotaBursa());
		this.ksei.setFtpIpAddr(ab.getIpAddress());
		this.ksei.setFtpUser(ab.getFtpUser());
		this.ksei.setFtpPasswd(ab.getFtpUser());
	}

}

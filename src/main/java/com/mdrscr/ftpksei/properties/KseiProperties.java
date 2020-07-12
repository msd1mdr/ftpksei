package com.mdrscr.ftpksei.properties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class KseiProperties {
	
	private String ftpLocalDir;
	private String ftpIpAddr;
	private String ftpPasswd;
	private String ftpUser;

}

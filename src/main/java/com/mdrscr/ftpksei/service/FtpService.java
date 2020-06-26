package com.mdrscr.ftpksei.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.mdrscr.ftpksei.properties.AppConfig;

@Service
public class FtpService {

	@Autowired
	private AppConfig appConfig;
//    private String localFile = "src/main/resources/input.txt";
//    private String remoteFile = "welcome.txt";

    private String remoteDir = "home/tomcat/";
//    private String knownHostsFileLoc = "/Users/USERNAME/known_hosts_sample";


        public ChannelSftp setupJsch() throws JSchException  {
	    	JSch jsch = new JSch();
	    	jsch.setKnownHosts("C:\\Users\\fransma\\.ssh\\known_hosts");
	    	Session jschSession = jsch.getSession(appConfig.getKsei().getFtpUser(), 
	    										  appConfig.getKsei().getFtpIpAddr());
	    	jschSession.setPassword(appConfig.getKsei().getFtpPasswd());
	    	jschSession.connect();
	    	return (ChannelSftp) jschSession.openChannel("sftp");
        }

        public void download() throws JSchException, SftpException {
        	ChannelSftp channelSftp = setupJsch();
        	channelSftp.connect();

        	String remoteFile = "logcustmobil.log";
        	
        	channelSftp.get(remoteFile, appConfig.getKsei().getFtpLocalDir() + "jschFile.txt");
        	
        	channelSftp.exit();

        }
        
        public void upload(String fileName) throws JSchException, SftpException {
        	ChannelSftp channelSftp = setupJsch();
        	channelSftp.connect();
        	String localFile = appConfig.getKsei().getFtpLocalDir() + fileName;
        	String remoteDir = "./";

        	channelSftp.put(localFile, remoteDir + fileName);

        	channelSftp.exit();

        }
}

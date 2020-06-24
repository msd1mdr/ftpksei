package com.mdrscr.ftpksei.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

@Service
public class FtpService {

    private String remoteHost = "192.168.201.38";
    private String username = "tomcat";
    private String password = "Metrodata123";
//    private String localFile = "src/main/resources/input.txt";
//    private String remoteFile = "welcome.txt";
	@Value("${scrp.ksei.ftp.localdir}")
    private String localDir;

    private String remoteDir = "home/tomcat/";
//    private String knownHostsFileLoc = "/Users/USERNAME/known_hosts_sample";


        public ChannelSftp setupJsch() throws JSchException  {
	    	JSch jsch = new JSch();
	    	jsch.setKnownHosts("C:\\Users\\fransma\\.ssh\\known_hosts");
	    	Session jschSession = jsch.getSession(username, remoteHost);
	    	jschSession.setPassword(password);
	    	jschSession.connect();
	    	return (ChannelSftp) jschSession.openChannel("sftp");
        }

        public void download() throws JSchException, SftpException {
        	ChannelSftp channelSftp = setupJsch();
        	channelSftp.connect();

        	String remoteFile = "logcustmobil.log";
        	
        	channelSftp.get(remoteFile, localDir + "jschFile.txt");
        	
        	channelSftp.exit();

        }
        
        public void upload(String fileName) throws JSchException, SftpException {
        	ChannelSftp channelSftp = setupJsch();
        	channelSftp.connect();
        	String localFile = localDir + fileName;
        	String remoteDir = "./";

        	channelSftp.put(localFile, remoteDir + "chat.txt");

        	channelSftp.exit();

        }
}

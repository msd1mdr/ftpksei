package com.mdrscr.ftpksei.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.mdrscr.ftpksei.properties.KseiConfig;

@Service
public class FtpService {

	@Autowired
	private KseiConfig kseiConfig;
  
    @Value("${scrp.sftp.knownhosts}")
    private String knownHosts;

    public ChannelSftp setupJsch() throws JSchException  {
    	JSch jsch = new JSch();
    	jsch.setKnownHosts(knownHosts);
    	Session jschSession = jsch.getSession(kseiConfig.getFtpUser(), 
    										  kseiConfig.getFtpIpAddr());
    	jschSession.setPassword(kseiConfig.getFtpPasswd());
    	jschSession.connect();
    	return (ChannelSftp) jschSession.openChannel("sftp");
    }

    public void download(String remoteDir, String localDir) 
    			throws JSchException, SftpException {
    	ChannelSftp channelSftp = setupJsch();
    	channelSftp.connect();

    	channelSftp.lcd(localDir);
    	channelSftp.cd(remoteDir); 
    	
    	@SuppressWarnings("unchecked")
		Vector <LsEntry> rfiles = channelSftp.ls("*.txt");
    	for (LsEntry lsEntry : rfiles) {
    		String fileName = lsEntry.getFilename();
    		System.out.println("Akan ambil file " + fileName);
        	channelSftp.get(fileName, localDir + fileName);
        	channelSftp.rm(fileName);
    	}

    	channelSftp.disconnect();
    	channelSftp.exit();

    }
        
    public void upload(String fileName, String localDir, String remoteDir) 
    				throws JSchException, SftpException {
    	ChannelSftp channelSftp = setupJsch();
    	channelSftp.connect();

    	channelSftp.put(localDir + fileName, remoteDir + fileName);

    	channelSftp.disconnect();
    	channelSftp.exit();

    }
}

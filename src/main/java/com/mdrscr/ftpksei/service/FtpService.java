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

    
    public List<String> downloadFiles(String fileNameExpr) 
    			throws JSchException, SftpException {
    	ChannelSftp channelSftp = setupJsch();
    	channelSftp.connect();

    	channelSftp.lcd(kseiConfig.getLocalInbDir());
    	channelSftp.cd(kseiConfig.getFtpInboundDir()); 
    	
    	List<String> files = new ArrayList<String>();
    	@SuppressWarnings("unchecked")
		Vector <LsEntry> rfiles = channelSftp.ls(fileNameExpr);
    	for (LsEntry lsEntry : rfiles) {
    		String fileName = lsEntry.getFilename();
    		System.out.println("Akan ambil file " + fileName);
        	channelSftp.get(fileName, kseiConfig.getLocalInbDir() + fileName);
//        	channelSftp.rm(fileName);
        	files.add(fileName);
    	}

    	channelSftp.disconnect();
    	channelSftp.exit();
    	return files;
    }
        
    public void upload(String fileName, String localDir) 
    				throws JSchException, SftpException {
    	ChannelSftp channelSftp = setupJsch();
    	channelSftp.connect();

    	channelSftp.put(localDir + fileName, kseiConfig.getFtpOutboundDir() + fileName);

    	channelSftp.disconnect();
    	channelSftp.exit();

    }
    
}

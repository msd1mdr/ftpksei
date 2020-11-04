package com.mdrscr.ftpksei.service;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;
import java.util.Vector;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
    private static final Logger logger = LoggerFactory.getLogger(FtpService.class);

	@Autowired
	private KseiConfig kseiConfig;
  
//    @Value("${scrp.sftp.knownhosts}")
//    private String knownHosts;

    public ChannelSftp setupJsch() throws JSchException  {
    	JSch jsch = new JSch();
//    	jsch.setKnownHosts(knownHosts);
    	Session jschSession = jsch.getSession(kseiConfig.getFtpUser(), 
    										  kseiConfig.getFtpIpAddr());
    	jschSession.setConfig("StrictHostKeyChecking", "no");
    	jschSession.setPassword(kseiConfig.getFtpPasswd());
    	jschSession.connect();
    	return (ChannelSftp) jschSession.openChannel("sftp");
    }

    
    public File[] downloadFiles(String fileNameExpr) 
    			throws JSchException, SftpException {
    	ChannelSftp channelSftp = setupJsch();
    	channelSftp.connect();

    	channelSftp.lcd(kseiConfig.getLocalInbDir());
    	channelSftp.cd(kseiConfig.getFtpInboundDir()); 

    	System.out.print("Siap download dari " + channelSftp.pwd());
		System.out.println("Siap download ke " + channelSftp.lpwd());

    	File[] dwfiles = new File[0];

    	logger.debug("fileNameExpr: " + fileNameExpr);
    	@SuppressWarnings("unchecked")
		Vector <LsEntry> rfiles = channelSftp.ls(fileNameExpr);
    	logger.info("ls files " + rfiles.size());
    	for (LsEntry lsEntry : rfiles) {
    		String fileName = lsEntry.getFilename();
    		System.out.println("Akan taruh file " + fileName);
        	channelSftp.get(fileName, fileName);

//        	channelSftp.rm(fileName);
//        	files.add(fileName);
        	dwfiles = ArrayUtils.add(dwfiles, new File(kseiConfig.getLocalInbDir()+fileName));
    	}

    	channelSftp.disconnect();
    	channelSftp.exit();
    	return dwfiles;
    }

    @SuppressWarnings("unchecked")
	public Optional<File> downloadFile (String fileName, String msgType) throws SftpException, JSchException {
//		ChannelSftp channelSftp = new ChannelSftp();

		ChannelSftp channelSftp = setupJsch();
		channelSftp.connect();
	
		channelSftp.lcd(kseiConfig.getLocalInbDir());
		if (msgType.equals("STATEMENT")) channelSftp.cd(kseiConfig.getStmtRmtoutdir());
		else if (msgType.equals("STATIC")) channelSftp.cd(kseiConfig.getStatRmtoutdir());
		else if (msgType.equals("BALANCE")) channelSftp.cd(kseiConfig.getBalRmtoutdir());
		
		@SuppressWarnings("unchecked")
		Vector<LsEntry> rfiles = new Vector<LsEntry>(Arrays.asList());
		try {
			rfiles = channelSftp.ls(fileName);
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			System.out.println("Error ls");
			logger.warn("ls tidak dapat " + fileName);
			//	e.printStackTrace();
		}

		if (rfiles.size() > 0) {
			System.out.println("Akan download file " + fileName);
			channelSftp.get(fileName, fileName);
		}

		channelSftp.disconnect();
		channelSftp.exit();
		
		if (rfiles.size() > 0) 
			return Optional.of(new File(kseiConfig.getLocalInbDir()+fileName));
		else return Optional.empty();
	}

    public void upload(String fileName, String localDir, String remoteDir) 
    				throws JSchException, SftpException {
    	System.out.println("Akan upload " + localDir + fileName);
    	ChannelSftp channelSftp = setupJsch();
    	channelSftp.connect();
    	channelSftp.lcd(localDir);
//    	channelSftp.cd(kseiConfig.getFtpOutboundDir()); 
    	channelSftp.cd(remoteDir); 
    	
    	System.out.println("sudah connect ftp");
    	channelSftp.put(fileName, fileName);
    	System.out.println("sudah upload ftp");

    	channelSftp.disconnect();
    	channelSftp.exit();

    }
    
}

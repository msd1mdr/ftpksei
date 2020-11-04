package com.mdrscr.ftpksei.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.mdrscr.ftpksei.persist.model.FileTransmision;
import com.mdrscr.ftpksei.properties.KseiConfig;

@Service
public class RetryService {

	@Autowired
	private FileTransmisionService fileTransmisionService;
	@Autowired
	private FtpService ftpService;
	@Autowired
	private KseiConfig kseiConfig;
	
	public void resendFile () {
		
		List<FileTransmision> files = fileTransmisionService.findAllError();
		
		for (FileTransmision file1 : files) {
			file1.setSendTime(LocalDateTime.now());
			String outDir = "";
			if (file1.getSubModul().equals("STATEMENT")) outDir = kseiConfig.getStmtRmtoutdir();
			else if (file1.getSendMethod().equals("STATIC")) outDir = kseiConfig.getStatRmtoutdir();
			else if (file1.getSendMethod().equals("BALANCE")) outDir = kseiConfig.getBalRmtoutdir();

			try {
				ftpService.upload(file1.getFileName(), kseiConfig.getLocalOutbDir(), outDir);
				file1.setSendStatus("SUCCESS");
			} catch (JSchException | SftpException e) {
				// TODO Auto-generated catch block
				Integer counter = (file1.getRetry()==null)?1:file1.getRetry()+1;
				file1.setRetry(counter);
//				e.printStackTrace();
			}
			fileTransmisionService.save(file1);

		}
	}
	
}

package com.mdrscr.ftpksei.service;

import java.io.IOException;
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
	private KseiResponseService responseService;
	@Autowired
	private KseiConfig kseiConfig;
	
	public void resendFile () {
		
		List<FileTransmision> files = fileTransmisionService.findAllError();
		
		for (FileTransmision file1 : files) {
			file1.setSendTime(LocalDateTime.now());
			try {
				ftpService.upload(file1.getFileName(), kseiConfig.getLocalOutbDir());
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
	
	public void reTakeFile (String strYesterday) {
		List<FileTransmision> files = fileTransmisionService.getResponseFileIsNull(strYesterday);
		for (FileTransmision file : files) {
			try {
				responseService.getResponse("*" + file.getFileName().replace("fsp", "zip"));
			} catch (JSchException | SftpException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

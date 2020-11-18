package com.mdrscr.ftpksei.scheduler;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.mdrscr.ftpksei.service.BalanceService;
import com.mdrscr.ftpksei.service.KseiResponseService;
import com.mdrscr.ftpksei.service.RetryService;
import com.mdrscr.ftpksei.service.StatementService;
import com.mdrscr.ftpksei.service.StaticService;


@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private RetryService retryService;
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private StatementService statementService;
    @Autowired
    private StaticService staticService;
    @Autowired
    private KseiResponseService kseiResponse;
    
    
    @Scheduled(cron="${scrp.cronsched.sendftp}")
    public void ftpOutKsei () {
    	logger.info("FtpOutKsei started");
    	try {
			balanceService.sendToKsei();
			statementService.sendToKsei();
			staticService.ftpToKsei();
		} catch (IOException e) {
			logger.error("IO Error proses ftpOutKsei");
		}
    }
  
//    @Scheduled(cron="${scrp.cronsched.getresponse}")
    @Scheduled(fixedDelay = 300000) //tiap 5 menit
    public void getResponseFile() {
    	logger.debug("getResponseFile started");
    	try {
//			kseiResponse.getResponse("*_BMAN2_"+strYesterday+ "*.zip");
			kseiResponse.getResponseFile();
		} catch (JSchException | SftpException e) {
			logger.error("FTP Error proses getResponse");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("IO Zip Error proses ftpInKsei");
		}
    }
    
    @Scheduled(fixedDelay = 600000)  //tiap 10 menit coba retry send file jika ada
    public void scheduleTaskWithFixedRate() {
        logger.debug("Scheduler with fixedDelay=600000");
        System.out.println("Coba resend Error File");
        retryService.resendFile();
    }



}

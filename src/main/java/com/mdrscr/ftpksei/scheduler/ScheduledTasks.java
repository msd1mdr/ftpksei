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
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYYMMdd"); 

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
  
    @Scheduled(cron="${scrp.cronsched.getresponse}")
    public void getResponseFile() {
        String strYesterday = dtf.format(LocalDate.now().minusDays(1));

    	logger.info("getResponseFile started");
    	try {
			kseiResponse.getResponse("*_BMAN2_"+strYesterday+ "*.zip");
		} catch (JSchException | SftpException e) {
			logger.error("FTP Error proses ftpInKsei");
		} catch (IOException e) {
			logger.error("IO Zip Error proses ftpInKsei");
		}
    }
    
//    @Scheduled(fixedDelay = 120000)  //tiap 2 menit coba retry send file jika ada
    public void scheduleTaskWithFixedRate() {
        logger.info("Scheduler with fixedDelay=6000");
//        retryService.resendFile();
    }

//    @Scheduled(cron = "0 0 * * * ?")
    public void retryGetResponseFile() {
        logger.info("Test cron 0 0 * * * ?" );
//    	retryService.reTakeFile(strYesterday);
    }


}

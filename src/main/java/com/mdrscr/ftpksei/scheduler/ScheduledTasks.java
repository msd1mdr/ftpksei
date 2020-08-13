package com.mdrscr.ftpksei.scheduler;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY"); 
    private static final String strYesterday = dtf.format(LocalDate.now().minusDays(1));

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
    
    
//    @Scheduled(cron="0 50 0 * * ?") // tiap pk 00:50 tiap hari
    @Scheduled(cron="${scrp.cronsched.sendftp}")
    public void ftpOutKsei () {
    	System.out.println("ftpOutKsei started");
    	try {
			String fileName1 = balanceService.sendToKsei();
			String fileName2 = statementService.sendToKsei();
			String fileName3 = staticService.ftpToKsei();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("IO Error proses ftpOutKsei");
			e.printStackTrace();
		}
    }
  
//    @Scheduled(cron="0 0 5 * * ?") // tiap pk 05:00 tiap hari
    @Scheduled(cron="${scrp.cronsched.getresponse}")
    public void getResponseFile() {
    	try {
			kseiResponse.getResponse("*_BMAN2_"+strYesterday+ "*.zip");
		} catch (JSchException | SftpException e) {
			// TODO Auto-generated catch block
			logger.error("FTP Error proses ftpInKsei");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("IO Zip Error proses ftpInKsei");
			e.printStackTrace();
		}
    }
    
    @Scheduled(fixedDelay = 600000)  //tiap 10 menit coba retry send file jika ada
    public void scheduleTaskWithFixedRate() {
//        logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
        retryService.resendFile();
    }

    @Scheduled(cron="0 0 8-20 * * ?")  
    public void retryGetResponseFile() {
//        logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
    	retryService.reTakeFile(strYesterday);
    }


}

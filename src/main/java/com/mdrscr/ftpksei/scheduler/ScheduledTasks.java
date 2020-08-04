package com.mdrscr.ftpksei.scheduler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mdrscr.ftpksei.service.BalanceService;
import com.mdrscr.ftpksei.service.KseiResponseService;
import com.mdrscr.ftpksei.service.RetryService;


@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    private RetryService retryService;
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private BalanceService statementService;
    @Autowired
    private BalanceService staticService;
    @Autowired
    private KseiResponseService kseiResponse;
    
//    @Scheduled(fixedDelay = 30000)
    public void scheduleTaskWithFixedRate() {
        logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
//        retryService.resendFile();
    }
    
    public void ftpOutKsei () {
    	try {
			String fileName1 = balanceService.sendToKsei();
			String fileName2 = statementService.sendToKsei();
			String fileName3 = staticService.sendToKsei();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
  
    public void ftpInKsei() {
//    	kseiResponse.getStatementResponse();
//    	kseiResponse.getStaticResponse();
//    	kseiResponse.getBalanceResponse();
    }
    
}

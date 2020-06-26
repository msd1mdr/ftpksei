package com.mdrscr.ftpksei.scheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mdrscr.ftpksei.properties.AppConfig;
import com.mdrscr.ftpksei.service.RetryService;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    private RetryService retryService;
    
    @Scheduled(fixedDelay = 5000)
    public void scheduleTaskWithFixedRate() {
        logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
        retryService.resendFile();
    }
  
}

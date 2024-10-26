package com.sunghyun.football.config.schduler;

import com.sunghyun.football.config.batch.FreeSubNotiRegBatchConfig;
import com.sunghyun.football.global.utils.MatchDateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Date;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class SchedulerConfig {
    private final JobLauncher jobLauncher;
    private final PlatformTransactionManager manager;
    private final JobRepository jobRepository;
    private final FreeSubNotiRegBatchConfig freeSubNotiRegBatchConfig;

    @Scheduled(cron = "0/30 * * * * *" , zone = "Asia/Seoul") //30초마다
    public void freesubNotiRegScheduler() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("연체 대상 등록 스케줄러 start");
        JobParameters jobParameters= new JobParametersBuilder()
                .addString("nowDt", MatchDateUtils.getNowDtStr())
                .addLong("time",new Date().getTime()) //여러번 돌수 있게 세팅
                .toJobParameters();

        jobLauncher.run(freeSubNotiRegBatchConfig.freeSubNotiRegJob(jobRepository, manager),jobParameters);

        log.info("연체 대상 등록 스케줄러 end");
    }
}
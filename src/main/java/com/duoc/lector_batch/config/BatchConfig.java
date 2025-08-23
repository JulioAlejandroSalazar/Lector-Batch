package com.duoc.lector_batch.config;

import com.duoc.lector_batch.model.CuentaAnual;
import com.duoc.lector_batch.model.Interes;
import com.duoc.lector_batch.model.Transaccion;
import com.duoc.lector_batch.processor.CuentaAnualItemProcessor;
import com.duoc.lector_batch.processor.InteresItemProcessor;
import com.duoc.lector_batch.processor.TransaccionItemProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    private static final Logger logger = LoggerFactory.getLogger(BatchConfig.class);

    // job para interes
    @Bean
    public Job interesJob(JobRepository jobRepository, Step interesStep) {
        return new JobBuilder("interesJob", jobRepository)
                .listener(jobExecutionListener())
                .start(interesStep)
                .build();
    }

    // step para interes
    @Bean
    public Step interesStep(JobRepository jobRepository,
                               PlatformTransactionManager transactionManager,
                               ItemReader<Interes> itemReader,
                               InteresItemProcessor itemProcessor,
                               ItemWriter<Interes> itemWriter) {
        return new StepBuilder("interesStep", jobRepository)
                .<Interes, Interes>chunk(5, transactionManager) // procesa 5
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .throttleLimit(3) // 3 hilos max
                .listener(stepExecutionListener())
                .faultTolerant()
                .skipLimit(5)
                .skip(Exception.class)
                .build();
    }

    // job para cuenta anual
    @Bean
    public Job cuentaAnualJob(JobRepository jobRepository, Step cuentaAnualStep) {
        return new JobBuilder("cuentaAnualJob", jobRepository)
                .listener(jobExecutionListener())
                .start(cuentaAnualStep)
                .build();
    }

    // step para cuenta anual
    @Bean
    public Step cuentaAnualStep(JobRepository jobRepository,
                                PlatformTransactionManager transactionManager,
                                ItemReader<CuentaAnual> itemReader,
                                CuentaAnualItemProcessor itemProcessor,
                                ItemWriter<CuentaAnual> itemWriter) {
        return new StepBuilder("cuentaAnualStep", jobRepository)
                .<CuentaAnual, CuentaAnual>chunk(5, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .throttleLimit(3)
                .listener(stepExecutionListener())
                .faultTolerant()
                .skipLimit(5)
                .skip(Exception.class)
                .build();
    }

    // job para transaccion
    @Bean
    public Job transaccionJob(JobRepository jobRepository, Step transaccionStep) {
        return new JobBuilder("transaccionJob", jobRepository)
                .listener(jobExecutionListener())
                .start(transaccionStep)
                .build();
    }

    // step para transaccion
    @Bean
    public Step transaccionStep(JobRepository jobRepository,
                                PlatformTransactionManager transactionManager,
                                ItemReader<Transaccion> itemReader,
                                TransaccionItemProcessor itemProcessor,
                                ItemWriter<Transaccion> itemWriter) {
        return new StepBuilder("transaccionStep", jobRepository)
                .<Transaccion, Transaccion>chunk(5, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .throttleLimit(3)
                .listener(stepExecutionListener())
                .faultTolerant()
                .skipLimit(5)
                .skip(Exception.class)
                .build();
    }


    // listener para job
    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new JobExecutionListenerSupport() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                logger.info("Iniciando job: {}", jobExecution.getJobInstance().getJobName());
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                logger.info("Finalizó job con estado: {}", jobExecution.getStatus());
            }
        };
    }

    // listener para step
    @Bean
    public StepExecutionListener stepExecutionListener() {
        return new StepExecutionListenerSupport() {
            @Override
            public void beforeStep(StepExecution stepExecution) {
                logger.info("Iniciando step: {}", stepExecution.getStepName());
            }

            @Override
            public ExitStatus afterStep(StepExecution stepExecution) {
                logger.info("Finalizó step con {} items procesados", stepExecution.getWriteCount());
                return stepExecution.getExitStatus();
            }
        };
    }
}
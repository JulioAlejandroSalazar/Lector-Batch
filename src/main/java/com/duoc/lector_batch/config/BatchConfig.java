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
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private static final Logger logger = LoggerFactory.getLogger(BatchConfig.class);

    // --------------------------
    // transaction manager en memoria
    // --------------------------
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    // --------------------------
    // job repository en memoria
    // --------------------------
    @Bean
    public JobRepository jobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setTransactionManager(transactionManager());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(jobRepository);
        launcher.afterPropertiesSet();
        return launcher;
    }

    // --------------------------
    // listener para job
    // --------------------------
    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new JobExecutionListener() {
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

    // --------------------------
    // listener para step
    // --------------------------
    @Bean
    public StepExecutionListener stepExecutionListener() {
        return new StepExecutionListener() {
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

    // --------------------------
    // JOBS Y STEPS
    // --------------------------

    // --- INTERES ---
    @Bean
    public Job interesJob(JobRepository jobRepository, Step interesStep) {
        return new JobBuilder("interesJob", jobRepository)
                .listener(jobExecutionListener())
                .start(interesStep)
                .build();
    }

    @Bean
    public Step interesStep(JobRepository jobRepository,
                            ItemReader<Interes> itemReader,
                            InteresItemProcessor itemProcessor,
                            ItemWriter<Interes> itemWriter,
                            PlatformTransactionManager transactionManager) {
        return new StepBuilder("interesStep", jobRepository)
                .<Interes, Interes>chunk(5, transactionManager)
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

    // --- CUENTA ANUAL ---
    @Bean
    public Job cuentaAnualJob(JobRepository jobRepository, Step cuentaAnualStep) {
        return new JobBuilder("cuentaAnualJob", jobRepository)
                .listener(jobExecutionListener())
                .start(cuentaAnualStep)
                .build();
    }

    @Bean
    public Step cuentaAnualStep(JobRepository jobRepository,
                                ItemReader<CuentaAnual> itemReader,
                                CuentaAnualItemProcessor itemProcessor,
                                ItemWriter<CuentaAnual> itemWriter,
                                PlatformTransactionManager transactionManager) {
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

    // --- TRANSACCION ---
    @Bean
    public Job transaccionJob(JobRepository jobRepository, Step transaccionStep) {
        return new JobBuilder("transaccionJob", jobRepository)
                .listener(jobExecutionListener())
                .start(transaccionStep)
                .build();
    }

    @Bean
    public Step transaccionStep(JobRepository jobRepository,
                                ItemReader<Transaccion> itemReader,
                                TransaccionItemProcessor itemProcessor,
                                ItemWriter<Transaccion> itemWriter,
                                PlatformTransactionManager transactionManager) {
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
}

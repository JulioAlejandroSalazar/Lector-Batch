package com.duoc.lector_batch.config;

import com.duoc.lector_batch.model.CuentaAnual;
import com.duoc.lector_batch.model.Interes;
import com.duoc.lector_batch.model.Transaccion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private static final Logger logger = LoggerFactory.getLogger(BatchConfig.class);

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

    // --- TRANSACCION ---
    @Bean
    public Step transaccionStep(ItemReader<Transaccion> transaccionReader,
                                ItemWriter<Transaccion> transaccionWriter,
                                ItemProcessor<Transaccion, Transaccion> transaccionProcessor) {
        return new StepBuilder("transaccionStep")
                .<Transaccion, Transaccion>chunk(5)
                .reader(transaccionReader)
                .processor(transaccionProcessor)
                .writer(transaccionWriter)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .throttleLimit(3)
                .listener(stepExecutionListener())
                .faultTolerant()
                .skipLimit(5)
                .skip(Exception.class)
                .build();
    }

    @Bean
    public Job transaccionJob(Step transaccionStep) {
        return new JobBuilder("transaccionJob")
                .listener(jobExecutionListener())
                .start(transaccionStep)
                .build();
    }

    // --- INTERES ---
    @Bean
    public Step interesStep(ItemReader<Interes> interesReader,
                            ItemWriter<Interes> interesWriter,
                            ItemProcessor<Interes, Interes> interesProcessor) {
        return new StepBuilder("interesStep")
                .<Interes, Interes>chunk(5)
                .reader(interesReader)
                .processor(interesProcessor)
                .writer(interesWriter)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .throttleLimit(3)
                .listener(stepExecutionListener())
                .faultTolerant()
                .skipLimit(5)
                .skip(Exception.class)
                .build();
    }

    @Bean
    public Job interesJob(Step interesStep) {
        return new JobBuilder("interesJob")
                .listener(jobExecutionListener())
                .start(interesStep)
                .build();
    }

    // --- CUENTA ANUAL ---
    @Bean
    public Step cuentaAnualStep(ItemReader<CuentaAnual> cuentaAnualReader,
                                ItemWriter<CuentaAnual> cuentaAnualWriter,
                                ItemProcessor<CuentaAnual, CuentaAnual> cuentaAnualProcessor) {
        return new StepBuilder("cuentaAnualStep")
                .<CuentaAnual, CuentaAnual>chunk(5)
                .reader(cuentaAnualReader)
                .processor(cuentaAnualProcessor)
                .writer(cuentaAnualWriter)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .throttleLimit(3)
                .listener(stepExecutionListener())
                .faultTolerant()
                .skipLimit(5)
                .skip(Exception.class)
                .build();
    }

    @Bean
    public Job cuentaAnualJob(Step cuentaAnualStep) {
        return new JobBuilder("cuentaAnualJob")
                .listener(jobExecutionListener())
                .start(cuentaAnualStep)
                .build();
    }
}

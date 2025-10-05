package com.duoc.lector_batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.duoc.lector_batch.model.Transaccion;
import com.duoc.lector_batch.model.Interes;
import com.duoc.lector_batch.model.CuentaAnual;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;

@SpringBootApplication
@EnableBatchProcessing
@EnableFeignClients
public class BatchDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchDemoApplication.class, args);
    }

    // --- STEP: transacciones ---
    @Bean
    public Step stepTransacciones(JobRepository repo,
                                  PlatformTransactionManager tx,
                                  ItemReader<Transaccion> reader,
                                  ItemProcessor<Transaccion, Transaccion> processor,
                                  ItemWriter<Transaccion> writer) {
        return new StepBuilder("stepTransacciones", repo)
                .<Transaccion, Transaccion>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .transactionManager(tx)
                .build();
    }

    // --- STEP: intereses ---
    @Bean
    public Step stepIntereses(JobRepository repo,
                              PlatformTransactionManager tx,
                              ItemReader<Interes> reader,
                              ItemProcessor<Interes, Interes> processor,
                              ItemWriter<Interes> writer) {
        return new StepBuilder("stepIntereses", repo)
                .<Interes, Interes>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .transactionManager(tx)
                .build();
    }

    // --- STEP: cuentas anuales ---
    @Bean
    public Step stepCuentasAnuales(JobRepository repo,
                                   PlatformTransactionManager tx,
                                   ItemReader<CuentaAnual> reader,
                                   ItemProcessor<CuentaAnual, CuentaAnual> processor,
                                   ItemWriter<CuentaAnual> writer) {
        return new StepBuilder("stepCuentasAnuales", repo)
                .<CuentaAnual, CuentaAnual>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .transactionManager(tx)
                .build();
    }

    // --- JOB que une los steps ---
    @Bean
    public Job job(JobRepository repo,
                   Step stepTransacciones,
                   Step stepIntereses,
                   Step stepCuentasAnuales) {
        return new JobBuilder("jobBanco", repo)
                .start(stepTransacciones)
                .next(stepIntereses)
                .next(stepCuentasAnuales)
                .build();
    }

    // --- REST CONTROLLER para ejecutar el job ---
    @RestController
    @RequestMapping("/batch")
    public static class BatchController {
        private final JobLauncher jobLauncher;
        private final Job job;

        public BatchController(JobLauncher jobLauncher, Job job) {
            this.jobLauncher = jobLauncher;
            this.job = job;
        }

        @GetMapping("/run")
        public String runJob() throws Exception {
            JobExecution execution = jobLauncher.run(job, new JobParameters());
            return "Job finalizado con estado: " + execution.getStatus();
        }
    }
}

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
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
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
    

    // // STEP: transacciones
    // @Bean
    // public Step stepTransacciones(JobRepository repo,
    //                               PlatformTransactionManager tx,
    //                               ItemReader<Transaccion> transaccionReader,
    //                               ItemProcessor<Transaccion, Transaccion> transaccionProcessor,
    //                               ItemWriter<Transaccion> transaccionWriter) {
    //     return new StepBuilder("stepTransacciones", repo)
    //             .<Transaccion, Transaccion>chunk(10)
    //             .reader(transaccionReader)
    //             .processor(transaccionProcessor)
    //             .writer(transaccionWriter)
    //             .transactionManager(tx)
    //             .build();
    // }

    // // STEP: intereses
    // @Bean
    // public Step stepIntereses(JobRepository repo,
    //                           PlatformTransactionManager tx,
    //                           ItemReader<Interes> interesReader,
    //                           ItemProcessor<Interes, Interes> interesProcessor,
    //                           ItemWriter<Interes> interesWriter) {
    //     return new StepBuilder("stepIntereses", repo)
    //             .<Interes, Interes>chunk(10)
    //             .reader(interesReader)
    //             .processor(interesProcessor)
    //             .writer(interesWriter)
    //             .transactionManager(tx)
    //             .build();
    // }

    // // STEP: cuentas anuales
    // @Bean
    // public Step stepCuentasAnuales(JobRepository repo,
    //                                PlatformTransactionManager tx,
    //                                ItemReader<CuentaAnual> cuentaReader,
    //                                ItemProcessor<CuentaAnual, CuentaAnual> cuentaProcessor,
    //                                ItemWriter<CuentaAnual> cuentaWriter) {
    //     return new StepBuilder("stepCuentasAnuales", repo)
    //             .<CuentaAnual, CuentaAnual>chunk(10)
    //             .reader(cuentaReader)
    //             .processor(cuentaProcessor)
    //             .writer(cuentaWriter)
    //             .transactionManager(tx)
    //             .build();
    // }

    // // JOB que une los steps
    // @Bean
    // public Job job(JobRepository repo,
    //                Step stepTransacciones,
    //                Step stepIntereses,
    //                Step stepCuentasAnuales) {
    //     return new JobBuilder("jobBanco", repo)
    //             .start(stepTransacciones)
    //             .next(stepIntereses)
    //             .next(stepCuentasAnuales)
    //             .build();
    // }

    // // Lanza el job automáticamente al iniciar la app
    // @Bean
    // public CommandLineRunner runJob(JobLauncher launcher, Job job) {
    //     return args -> {
    //         JobExecution execution = launcher.run(job, new JobParameters());
    //         System.out.println("Job finalizado con estado: " + execution.getStatus());
    //     };
    // }
}

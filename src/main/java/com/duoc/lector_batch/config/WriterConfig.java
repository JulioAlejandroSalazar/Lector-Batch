package com.duoc.lector_batch.config;

import javax.sql.DataSource;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.duoc.lector_batch.model.Transaccion;
import com.duoc.lector_batch.model.Interes;
import com.duoc.lector_batch.model.CuentaAnual;

@Configuration
public class WriterConfig {

    @Bean
    public ItemWriter<Transaccion> transaccionWriter() {
        return items -> items.forEach(t -> 
            System.out.println("Transaccion procesada: " + t)
        );
    }

    @Bean
    public ItemWriter<Interes> interesWriter() {
        return items -> items.forEach(i -> 
            System.out.println("Interes procesado: " + i)
        );
    }

    @Bean
    public ItemWriter<CuentaAnual> cuentaAnualWriter() {
        return items -> items.forEach(c -> 
            System.out.println("CuentaAnual procesada: " + c)
        );
    }
}


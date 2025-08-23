package com.duoc.lector_batch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.item.ItemProcessor;
import com.duoc.lector_batch.model.Transaccion;
import com.duoc.lector_batch.model.Interes;
import com.duoc.lector_batch.model.CuentaAnual;
import com.duoc.lector_batch.processor.TransaccionItemProcessor;
import com.duoc.lector_batch.processor.InteresItemProcessor;
import com.duoc.lector_batch.processor.CuentaAnualItemProcessor;

@Configuration
public class ProcessorConfig {

    public ItemProcessor<Transaccion, Transaccion> transaccionProcessor() {
        return new TransaccionItemProcessor();
    }

    public ItemProcessor<Interes, Interes> interesProcessor() {
        return new InteresItemProcessor();
    }

    public ItemProcessor<CuentaAnual, CuentaAnual> cuentaAnualProcessor() {
        return new CuentaAnualItemProcessor();
    }
}

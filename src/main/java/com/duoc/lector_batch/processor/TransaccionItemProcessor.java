package com.duoc.lector_batch.processor;

import java.math.BigDecimal;
import com.duoc.lector_batch.model.Transaccion;
import org.springframework.batch.item.ItemProcessor;

public class TransaccionItemProcessor implements ItemProcessor<Transaccion, Transaccion> {
    @Override
    public Transaccion process(Transaccion t) {
        if (t.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            t.setObservacion("Monto inválido");
        }
        return t;
    }
}

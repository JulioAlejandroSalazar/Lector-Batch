package com.duoc.lector_batch.processor;

import com.duoc.lector_batch.model.CuentaAnual;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CuentaAnualItemProcessor implements ItemProcessor<CuentaAnual, CuentaAnual> {

    @Override
    public CuentaAnual process(CuentaAnual c) {
        c.setResumen("Cuenta " + c.getCuentaId() + " - Año " + c.getAnio());
        return c;
    }

}

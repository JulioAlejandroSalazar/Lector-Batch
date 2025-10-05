package com.duoc.lector_batch.processor;

import java.math.BigDecimal;
import com.duoc.lector_batch.model.Interes;
import org.springframework.batch.item.ItemProcessor;

public class InteresItemProcessor implements ItemProcessor<Interes, Interes> {

    @Override
    public Interes process(Interes item) throws Exception {
        // loguear los valores leídos
        System.out.println("DEBUG -> cuentaId: " + item.getCuentaId() 
            + ", saldo: " + item.getSaldo() 
            + ", tipo: " + item.getTipo());

        // calcular interés
        BigDecimal tasa = "ahorro".equalsIgnoreCase(item.getTipo()) ? new BigDecimal("0.02") : new BigDecimal("0.05");
        BigDecimal saldoInicial = item.getSaldo() != null ? item.getSaldo() : BigDecimal.ZERO;
        BigDecimal interes = saldoInicial.multiply(tasa);
        item.setInteresAplicado(interes);
        item.setSaldoFinal(saldoInicial.add(interes));
        return item;
    }

}


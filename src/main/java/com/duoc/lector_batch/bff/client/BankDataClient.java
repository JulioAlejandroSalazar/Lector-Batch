package com.duoc.lector_batch.bff.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.duoc.lector_batch.dto.CuentaAnualDto;
import com.duoc.lector_batch.dto.InteresDto;
import com.duoc.lector_batch.dto.TransaccionDto;

import java.util.List;

@FeignClient(name = "bankDataClient", url = "http://localhost:8080/api")
public interface BankDataClient {

    @GetMapping("/transacciones")
    List<TransaccionDto> getTransacciones();

    @GetMapping("/intereses")
    List<InteresDto> getIntereses();

    @GetMapping("/cuentas_anuales")
    List<CuentaAnualDto> getCuentaAnuales();
}


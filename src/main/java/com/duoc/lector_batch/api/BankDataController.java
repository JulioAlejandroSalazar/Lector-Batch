package com.duoc.lector_batch.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.duoc.lector_batch.dto.CuentaAnualDto;
import com.duoc.lector_batch.dto.InteresDto;
import com.duoc.lector_batch.dto.TransaccionDto;
import com.duoc.lector_batch.legacy.service.CsvService;

@RestController
@RequestMapping("/api")
public class BankDataController {

    private final CsvService csvService;

    public BankDataController(CsvService csvService) {
        this.csvService = csvService;
    }

    @GetMapping("/transacciones")
    public List<TransaccionDto> getTransaccionesApi() {
        return csvService.leerTransacciones();
    }

    @GetMapping("/intereses")
    public List<InteresDto> getInteresApi() {
        return csvService.leerIntereses();
    }

    @GetMapping("/cuentas_anuales")
    public List<CuentaAnualDto> getCuentasAnualesApi() {
        return csvService.leerCuentas();
    }
}


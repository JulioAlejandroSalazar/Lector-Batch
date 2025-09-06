package com.duoc.lector_batch.bff.web;

import com.duoc.lector_batch.bff.client.BankDataClient;
import com.duoc.lector_batch.dto.CuentaAnualDto;
import com.duoc.lector_batch.dto.InteresDto;
import com.duoc.lector_batch.dto.TransaccionDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/bff/web")
public class WebController {

    private final BankDataClient bankDataClient;

    public WebController(BankDataClient bankDataClient) {
        this.bankDataClient = bankDataClient;
    }

    @GetMapping("/transacciones")
    public List<TransaccionDto> getTransaccionesWeb() {
        return bankDataClient.getTransacciones();
    }

    @GetMapping("/intereses")
    public List<InteresDto> getInteresesWeb() {
        return bankDataClient.getIntereses();
    }

    @GetMapping("/cuentas_anuales")
    public List<CuentaAnualDto> getCuentasAnualesWeb() {
        return bankDataClient.getCuentaAnuales();
    }
}

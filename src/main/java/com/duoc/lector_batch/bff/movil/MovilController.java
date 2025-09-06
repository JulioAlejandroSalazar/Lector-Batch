package com.duoc.lector_batch.bff.movil;

import com.duoc.lector_batch.bff.client.BankDataClient;
import com.duoc.lector_batch.dto.CuentaAnualDto;
import com.duoc.lector_batch.dto.InteresDto;
import com.duoc.lector_batch.dto.TransaccionDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bff/movil")
public class MovilController {

    private final BankDataClient bankDataClient;

    public MovilController(BankDataClient bankDataClient) {
        this.bankDataClient = bankDataClient;
    }

    @GetMapping("/transacciones")
    public List<TransaccionDto> getTransaccionesMovil() {
        // solo devolver id y monto
        return bankDataClient.getTransacciones()
                .stream()
                .map(t -> {
                    TransaccionDto dto = new TransaccionDto();
                    dto.setId(t.getId());
                    dto.setMonto(t.getMonto());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/intereses")
    public List<InteresDto> getInteresesMovil() {
        // solo devolver cuentaId y saldoFinal
        return bankDataClient.getIntereses()
                .stream()
                .map(i -> {
                    InteresDto dto = new InteresDto();
                    dto.setCuentaId(i.getCuentaId());
                    dto.setSaldoFinal(i.getSaldoFinal());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/cuentas_anuales")
    public List<CuentaAnualDto> getCuentasAnualesMovil() {
        // solo devolver cuentaId y monto
        return bankDataClient.getCuentaAnuales()
                .stream()
                .map(c -> {
                    CuentaAnualDto dto = new CuentaAnualDto();
                    dto.setCuentaId(c.getCuentaId());
                    dto.setMonto(c.getMonto());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

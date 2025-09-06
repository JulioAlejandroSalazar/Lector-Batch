package com.duoc.lector_batch.legacy.service;

import com.duoc.lector_batch.dto.CuentaAnualDto;
import com.duoc.lector_batch.dto.InteresDto;
import com.duoc.lector_batch.dto.TransaccionDto;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {

    public List<CuentaAnualDto> leerCuentas() {
        List<CuentaAnualDto> cuentas = new ArrayList<>();
        DateTimeFormatter f1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter f3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new ClassPathResource("cuentas_anuales.csv").getInputStream()))) {
            String line;
            br.readLine(); // saltar encabezado
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",");
                CuentaAnualDto dto = new CuentaAnualDto();
                dto.setCuentaId(Long.parseLong(cols[0].trim()));
                try {
                    dto.setFecha(LocalDate.parse(cols[1].trim(), f1));
                } catch(Exception e1) {
                    try {
                        dto.setFecha(LocalDate.parse(cols[1].trim(), f2));
                    } catch(Exception e2) {
                        dto.setFecha(LocalDate.parse(cols[1].trim(), f3));
                    }
                }
                dto.setTransaccion(cols[2].trim());
                dto.setMonto(cols[3].isEmpty() ? 0 : Double.parseDouble(cols[3].trim()));
                dto.setDescripcion(cols.length > 4 ? cols[4].trim() : "");
                cuentas.add(dto);
            }
        } catch(Exception e){ e.printStackTrace(); }
        return cuentas;
    }
    

    public List<InteresDto> leerIntereses() {
        List<InteresDto> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new ClassPathResource("intereses.csv").getInputStream()))) {
            String line;
            br.readLine(); // saltar encabezado
            while ((line = br.readLine()) != null) {
                String[] c = line.split(",");
                InteresDto dto = new InteresDto();
                dto.setCuentaId(Long.parseLong(c[0].trim()));
                dto.setNombre(c[1].trim());
                dto.setSaldo(new BigDecimal(c[2].trim()));
                dto.setEdad(Integer.parseInt(c[3].trim()));
                dto.setInteresAplicado(BigDecimal.ZERO);
                dto.setSaldoFinal(dto.getSaldo());
                lista.add(dto);
            }
        } catch(Exception e){ e.printStackTrace(); }
        return lista;
    }

    public List<TransaccionDto> leerTransacciones() {
        List<TransaccionDto> lista = new ArrayList<>();
        DateTimeFormatter f1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter f3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new ClassPathResource("transacciones.csv").getInputStream()))) {
            String line;
            br.readLine(); // saltar encabezado
            while ((line = br.readLine()) != null) {
                String[] c = line.split(",");
                TransaccionDto dto = new TransaccionDto();
                dto.setId(Long.parseLong(c[0].trim()));
                try { 
                    dto.setFecha(LocalDate.parse(c[1].trim(), f1)); 
                } catch(Exception e1) {
                    try { 
                        dto.setFecha(LocalDate.parse(c[1].trim(), f2)); 
                    } catch(Exception e2) {
                        dto.setFecha(LocalDate.parse(c[1].trim(), f3));
                    }
                }
                dto.setMonto(new BigDecimal(c[2].trim()));
                dto.setTipo(c[3].trim());
                dto.setObservacion(c.length>4?c[4].trim():"");
                lista.add(dto);
            }
        } catch(Exception e){ e.printStackTrace(); }
        return lista;
    }
    
}

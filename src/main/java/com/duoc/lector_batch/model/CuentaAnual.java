package com.duoc.lector_batch.model;

import java.math.BigDecimal;

public class CuentaAnual {
    private Long cuentaId;
    private int anio;
    private BigDecimal saldoFinal;
    private String resumen;

    // getters y setters
    public Long getCuentaId() { return cuentaId; }
    public void setCuentaId(Long cuentaId) { this.cuentaId = cuentaId; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public BigDecimal getSaldoFinal() { return saldoFinal; }
    public void setSaldoFinal(BigDecimal saldoFinal) { this.saldoFinal = saldoFinal; }

    public String getResumen() { return resumen; }
    public void setResumen(String resumen) { this.resumen = resumen; }
}

# Proyecto Lector Batch - Banco

## Objetivo
Automatizar el procesamiento de datos bancarios con Spring Batch:  
- Procesar transacciones diarias y detectar anomalías.  
- Aplicar intereses mensuales a cuentas de ahorro, préstamos e hipotecas.  
- Generar estados de cuenta anuales por cada cuenta.


## Base de Datos
Tablas principales:  
- `transacciones`  
- `intereses`  
- `cuentas_anuales`  

## Ejecución
```bash
git clone <repo-url>
cd lector_batch
mvn clean install
mvn spring-boot:run

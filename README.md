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


## Cómo funciona el procesamiento

- **Procesamiento por bloques**  
  Los datos se leen y procesan en grupos pequeños de 5 registros a la vez. Esto permite manejar la información de forma más ordenada y eficiente.  

- **Trabajo en paralelo**  
  El sistema está configurado para usar hasta 3 tareas al mismo tiempo. Esto ayuda a que el procesamiento de grandes volúmenes de información sea más rápido.  

- **Manejo de errores**  
  Si algún registro viene con datos incorrectos, el sistema lo puede saltar hasta 5 veces sin detener todo el proceso. De esta forma se asegura que el resto de la información siga siendo procesada.  

- **Validaciones previas**  
  Antes de guardar la información en la base de datos, se aplican reglas para limpiar y verificar los datos, asegurando que sean consistentes.  

- **Seguimiento del proceso**  
  Se incluyen registros (logs) para saber cuándo empieza y termina cada etapa, y si ocurrió algún problema.  

## Ejecución
```bash
git clone <repo-url>
cd lector_batch
mvn clean install
mvn spring-boot:run

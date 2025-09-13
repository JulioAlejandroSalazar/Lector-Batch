# Proyecto Lector Batch - Banco

## 📌 Descripción
Este proyecto implementa un **BFF (Backend for Frontend)** que actúa como intermediario entre los microservicios del banco y los clientes finales (móvil, web y cajero).  
Cada cliente tiene su propio microservicio BFF, con endpoints adaptados a sus necesidades y seguridad mediante **keystore SSL (PKCS12)**.

## 🛠️ Tecnologías
- Java 17  
- Spring Boot 3.5  
- Spring Batch  
- Spring Cloud OpenFeign (BFF)  
- CSV como fuente de datos  
- Maven  

## 📂 Estructura principal
- `model` → entidades: Transaccion, Interes, CuentaAnual  
- `reader` → lectura de CSV  
- `processor` → transformación y reglas de negocio  
- `writer` → escritura o exposición de datos  
- `controller` → endpoints REST del BFF (móvil, web y cajero)  
- `BatchDemoApplication` → configuración de Spring Boot y Batch  

> ⚠️ Los jobs y steps están comentados para que el BFF no los ejecute al iniciar la app.

## 🔐 Seguridad
Cada microservicio BFF utiliza **TLS/SSL** configurado con un keystore en formato `.p12`.  
Esto asegura que todas las llamadas se realicen por **HTTPS**.  

- `bff-movil` → puerto **7443**  
- `bff-web` → puerto **8443**  
- `bff-cajero` → puerto **9443**  

## 🌐 Endpoints disponibles

### 📱 Móvil (`https://localhost:7443/bff/movil`)
- `/transacciones` → devuelve datos simplificados de transacciones  
- `/intereses` → devuelve saldo final simplificado  
- `/cuentas-anuales` → devuelve resumen reducido de cuentas anuales  

### 💻 Web (`https://localhost:8443/bff/web`)
- `/transacciones` → devuelve **todos los campos** de transacciones  
- `/intereses` → devuelve **todos los campos** de intereses  
- `/cuentas-anuales` → devuelve **todos los campos** de cuentas anuales  

### 🏧 Cajero (`https://localhost:9443/bff/cajero`)
- `/transacciones` → devuelve subset con `id`, `monto` y `fecha`  
- `/intereses` → devuelve subset con `cuentaId`
- `/cuentas-anuales` → devuelve subset con `cuentaId` 

## 🚀 Ejecución rápida
1. Clonar el repositorio:
```bash
git clone <repo-url>
``` 
2. Abrir el proyecto en tu IDE y ejecutar BatchDemoApplication.

3. Probar los endpoints vía Postman o navegador:

Móvil
- https://localhost:7443/bff/movil/transacciones
- https://localhost:7443/bff/movil/intereses
- https://localhost:7443/bff/movil/cuentas_anuales

Web
- https://localhost:8443/bff/web/transacciones
- https://localhost:8443/bff/web/intereses
- https://localhost:8443/bff/web/cuentas_anuales

Cajero
- https://localhost:9443/bff/cajero/transacciones
- https://localhost:9443/bff/cajero/intereses
- https://localhost:9443/bff/cajero/cuentas_anuales
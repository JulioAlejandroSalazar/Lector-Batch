# 🏦 Proyecto Lector Batch - Banco

## 📘 Descripción  
Este proyecto implementa una arquitectura basada en **microservicios BFF (Backend for Frontend)** que actúan como capa intermedia entre los servicios internos del banco y los distintos clientes: **móvil**, **web** y **cajero**.  

Cada BFF expone endpoints personalizados según el tipo de cliente y se comunica de forma segura mediante **keystores SSL (PKCS12)**, garantizando la transmisión de datos cifrada por **HTTPS**.  

Además, el ecosistema se integra con:  
- **Config Server**, para la gestión centralizada de configuraciones.  
- **Eureka Server**, para el registro y descubrimiento de microservicios.  

> Debido a limitaciones de espacio en el entorno de despliegue, se decidió omitir ciertas tecnologías inicialmente consideradas, priorizando la estabilidad y claridad del flujo BFF–Batch.

---

## ⚙️ Tecnologías principales
- ☕ **Java 17**  
- 🚀 **Spring Boot 3.5**  
- 🧩 **Spring Cloud OpenFeign** (comunicación entre BFFs)  
- 🗄️ **Spring Cloud Config Server** (configuración centralizada)  
- 🧭 **Spring Cloud Netflix Eureka** (service discovery)  
- 📊 **Spring Batch** (lectura, transformación y procesamiento de datos CSV)  
- 🗂️ **Maven** (gestión de dependencias)  
- 🔒 **TLS/SSL con keystores PKCS12**

---


> ✅ Los **Jobs** y **Steps** del batch se ejecutan normalmente al iniciar la aplicación, procesando los CSV y actualizando los datos correspondientes.

---

## 🔐 Seguridad
Cada microservicio BFF dispone de su propio certificado digital en formato `.p12`, lo que permite una comunicación cifrada mediante **HTTPS**:

| Microservicio | Puerto | Certificado |
|----------------|---------|--------------|
| BFF Móvil | `7443` | `keystore-movil.p12` |
| BFF Web | `8443` | `keystore-web.p12` |
| BFF Cajero | `9443` | `keystore-cajero.p12` |

---

## 🧩 Integración con Config Server y Eureka
- **Config Server** proporciona los archivos de configuración externos para cada BFF, permitiendo modificar propiedades sin necesidad de recompilar ni desplegar nuevamente.  
- **Eureka Server** permite que los BFFs se registren dinámicamente, habilitando el descubrimiento automático entre servicios.  

Ambos servicios son fundamentales para mantener la **consistencia, escalabilidad y resiliencia** del sistema distribuido.

---

## 🌐 Endpoints disponibles

### 📱 Móvil
| Endpoint | Descripción |
|-----------|--------------|
| `https://35.168.205.99:7443/bff/movil/transacciones` | Devuelve datos simplificados de transacciones |
| `https://35.168.205.99:7443/bff/movil/intereses` | Devuelve resumen de saldos finales |
| `https://35.168.205.99:7443/bff/movil/cuentas-anuales` | Devuelve resumen reducido de cuentas anuales |

### 💻 Web
| Endpoint | Descripción |
|-----------|--------------|
| `https://35.168.205.99:8443/bff/web/transacciones` | Devuelve todos los campos de transacciones |
| `https://35.168.205.99:8443/bff/web/intereses` | Devuelve todos los campos de intereses |
| `https://35.168.205.99:8443/bff/web/cuentas-anuales` | Devuelve todos los campos de cuentas anuales |

### 🏧 Cajero
| Endpoint | Descripción |
|-----------|--------------|
| `https://35.168.205.99:9443/bff/cajero/transacciones` | Devuelve `id`, `monto` y `fecha` |
| `https://35.168.205.99:9443/bff/cajero/intereses` | Devuelve solo `cuentaId` |
| `https://35.168.205.99:9443/bff/cajero/cuentas-anuales` | Devuelve solo `cuentaId` |

---

## 🚀 Ejecución rápida

1. **Clonar el repositorio:**
   ```bash
   git clone <repo-url>
    ```
2. Iniciar los servicios base:

Levantar Config Server

Levantar Eureka Server

3. Abrir y ejecutar los microservicios BFF en tu IDE preferido

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
- `controller` → endpoints REST del BFF  
- `BatchDemoApplication` → configuración de Spring Boot y Batch  

> ⚠️ Los jobs y steps están comentados para que el BFF no los ejecute al iniciar la app.

## 🌐 Endpoints disponibles
| Endpoint | Descripción |
|----------|------------|
| `/transacciones` | Transacciones procesadas |
| `/intereses` | Intereses calculados |
| `/cuentas-anuales` | Datos de cuentas anuales |

## 🚀 Ejecución rápida
1. Clonar el repositorio:
```bash
git clone <repo-url>
``` 

2. Abrir el proyecto en tu IDE y ejecutar BatchDemoApplication.

3. Probar los endpoints vía Postman o navegador:

- http://localhost:8080/bff/web/transacciones
- http://localhost:8080/bff/web/intereses
- http://localhost:8080/bff/web/cuentas-anuales

- http://localhost:8080/bff/movil/transacciones
- http://localhost:8080/bff/movil/intereses
- http://localhost:8080/bff/movil/cuentas-anuales

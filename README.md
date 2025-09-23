# Stock Market Data API

**Proyecto de Spring Boot para consulta de datos financieros utilizando la API de Alpha Vantage**

## Descripción del Proyecto

Este proyecto es una aplicación REST desarrollada en **Spring Boot 3.5.6** que actúa como un intermediario (proxy) para la API de Alpha Vantage, proporcionando endpoints para consultar datos de acciones en diferentes intervalos de tiempo: intraday, daily, weekly y monthly.

### Características Principales

- **Cache en memoria**: Implementación de cache con `ConcurrentHashMap` para optimizar consultas repetidas
- **Manejo de errores robusto**: Validación de parámetros y manejo centralizado de excepciones
- **Arquitectura REST**: Endpoints bien estructurados siguiendo principios RESTful
- **Configuración externa**: API key y URL configurables mediante `application.properties`
- **Cliente de pruebas**: Herramienta integrada para testing de carga y evaluación de performance

## Arquitectura del Proyecto

```
src/main/java/com/sparkweb/core/
├── CoreApplication.java          
├── Client/
│   └── Client.java              
├── Config/
│   └── RestClientConfig.java     
├── controller/
│   └── ApiController.java       
├── model/
│   ├── Info.java                
│   ├── Metadata.java            
│   ├── Response.java             
│   └── Times.java                
└── services/
    └── ApiService.java           
```

## Modelos de Datos

### `Info` Record
Representa la información de precios de una acción en un momento específico:
- **open**: Precio de apertura
- **high**: Precio máximo
- **low**: Precio mínimo
- **close**: Precio de cierre
- **volume**: Volumen de transacciones

```json
{
  "1. open": "266.4600",
  "2. high": "266.7000",
  "3. low": "266.1100",
  "4. close": "266.6000",
  "5. volume": "662"
}
```

### `Metadata` Record
Contiene la metainformación de la consulta realizada:
- **information**: Descripción del tipo de datos
- **symbol**: Símbolo de la acción (ej: IBM, AAPL)
- **lastRefreshed**: Última actualización de los datos
- **interval**: Intervalo de tiempo de los datos
- **outputSize**: Tamaño de la respuesta
- **timeZone**: Zona horaria de los datos

### `Response` Record
Combina la metadata con las series temporales usando `@JsonAlias` para mapear múltiples formatos de respuesta de la API.

### `Times` Enum
Define los tipos de series temporales disponibles:
- `TIME_SERIES_INTRADAY`
- `TIME_SERIES_DAILY`
- `TIME_SERIES_WEEKLY`
- `TIME_SERIES_MONTHLY`

## 🚀 Endpoints Disponibles

### Base URL: `/api/Data`

| Endpoint | Método | Parámetros | Descripción |
|----------|--------|------------|-------------|
| `/intraday` | GET | `symbol`, `interval` | Datos intraday (minutos) |
| `/daily` | GET | `symbol`, `interval` | Datos diarios |
| `/weekly` | GET | `symbol`, `interval` | Datos semanales |
| `/Monthly` | GET | `symbol`, `interval` | Datos mensuales |

#### Ejemplos de Uso:
```bash
# Datos intraday de IBM con intervalo de 5 minutos
GET /api/Data/intraday?symbol=IBM&interval=5min

# Datos diarios de Apple
GET /api/Data/daily?symbol=AAPL&interval=1day

# Datos semanales de Microsoft
GET /api/Data/weekly?symbol=MSFT&interval=1week
```

## Servicios y Funcionalidades

### `ApiService` - Funcionalidades Clave

#### ⚡ Sistema de Cache
- **Implementación**: `ConcurrentHashMap<String, Response>` thread-safe
- **Estrategia de clave**: `"{tipo}-{symbol}-{interval}"` (ej: "intraday-IBM-5min")
- **Beneficios**: Reduce llamadas a la API externa y mejora rendimiento

#### Validación de Parámetros
```java
private void checkParameters(String symbol, String interval) {
    if(symbol == null || interval == null){
        throw new IllegalArgumentException("The arguments cannot be null or could not be found");
    }
}
```

#### Manejo de Respuestas
```java
private Response getResponse(String url) {
    Response response = restTemplate.getForObject(url, Response.class);
    
    if (response.metadata() == null || response.timeSeries() == null){
        throw new RuntimeException("The metadata response or time serie is null");
    }
    return response;
}
```

### `ApiController` - Gestión de Endpoints

#### Características del Controlador:
- **Inyección de dependencias**: Constructor-based injection del `ApiService`
- **Manejo de errores**: Try-catch con respuestas HTTP apropiadas
- **Códigos de respuesta**:
  - `200 OK`: Datos recuperados exitosamente
  - `500 Internal Server Error`: Error en la consulta o validación

## Cliente de Pruebas de Carga

### `Client` - Herramienta de Testing Concurrente

El proyecto incluye un cliente independiente (`Client.java`) diseñado para realizar pruebas de carga y evaluar el rendimiento del sistema de cache implementado.

#### Características del Cliente:

**Configuración de Pruebas:**
- **Número de peticiones**: 20 requests concurrentes por defecto
- **Pool de hilos**: ExecutorService con 10 hilos simultáneos
- **Endpoint objetivo**: `/api/Data/intraday?symbol=IBM&interval=5min`
- **Cliente HTTP**: Java 11+ HttpClient nativo

**Funcionalidades Clave:**
```java
// Configuración del pool de hilos
ExecutorService executor = Executors.newFixedThreadPool(10);

// Cliente HTTP moderno
HttpClient client = HttpClient.newHttpClient();

// Request concurrente
HttpRequest request = HttpRequest.newBuilder()
    .uri(URI.create(BASE_URL))
    .GET()
    .build();
```

#### 📊 Propósito del Cliente:

1. **Validación de Cache**: Demuestra que las primeras requests hacen llamadas a la API externa, mientras que las subsecuentes utilizan el cache
2. **Testing de Concurrencia**: Verifica que el `ConcurrentHashMap` maneja correctamente múltiples requests simultáneas


#### 🎯 Uso del Cliente:

**Ejecutar desde línea de comandos:**
```bash
# Compilar el proyecto
./mvnw clean compile

# Ejecutar el servidor (en terminal separado)
./mvnw spring-boot:run

# Ejecutar el cliente de pruebas (en otro terminal)
java -cp target/classes com.sparkweb.core.Client.Client
```

**Salida esperada:**
```
Request #1 | Status: 200
Response: {"Meta Data":{"1. Information":"Intraday (5min)...

Request #2 | Status: 200  
Response: {"Meta Data":{"1. Information":"Intraday (5min)...
```

## Configuración

### `application.properties`
```properties
spring.application.name=core
alphavantage.url=https://www.alphavantage.co/query
```

### `RestClientConfig`
Configuración del bean `RestTemplate` para realizar llamadas HTTP a la API externa.

## Tecnologías Utilizadas

- **Spring Boot 3.5.6**
- **Java 17**
- **Spring Web** (spring-boot-starter-web)
- **Jackson** (para serialización JSON)
- **Maven** (gestión de dependencias)
- **Spring DevTools** (desarrollo)

## Instalación y Ejecución

### Prerrequisitos
- Java 17 o superior
- Maven 3.6 o superior

### Pasos para ejecutar:

1. **Clonar el repositorio**
```bash
git clone <repository-url>
cd ParcialARSW
```

2. **Compilar el proyecto**
```bash
./mvnw clean compile
```

3. **Ejecutar la aplicación**
```bash
./mvnw spring-boot:run
```

4. **Acceder a la aplicación**
- URL base: `http://localhost:8080`
- Endpoints: `http://localhost:8080/api/Data/{endpoint}`

## Testing

Ejecutar las pruebas unitarias:
```bash
./mvnw test
```

## Consideraciones de Rendimiento

1. **Cache**: Evita llamadas redundantes a la API externa
2. **Validación temprana**: Falla rápido con parámetros inválidos
3. **Thread-safety**: Uso de `ConcurrentHashMap` para acceso concurrente


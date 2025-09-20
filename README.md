## Modelos

Creé los siguientes modelos en base a las respuestas de la API dada en el enunciado

Info
Es la información (sin la fecha con tiempo) de la acción vendida, ejemplo
{
            "1. open": "266.4600",
            "2. high": "266.7000",
            "3. low": "266.1100",
            "4. close": "266.6000",
            "5. volume": "662"
        },
TimeSerie
Es la información (con la fecha con tiempo) de la acción vendida, ejemplo
 "Time Series (5min)": {
        "2025-09-19 19:55:00": {
            "1. open": "266.4600",
            "2. high": "266.7000",
            "3. low": "266.1100",
            "4. close": "266.6000",
            "5. volume": "662"
        },
MetaData
Es la metainformación de la acción vendida, por ejemplo

{
    "Meta Data": {
        "1. Information": "Intraday (5min) open, high, low, close prices and volume",
        "2. Symbol": "IBM",
        "3. Last Refreshed": "2025-09-19 19:55:00",
        "4. Interval": "5min",
        "5. Output Size": "Compact",
        "6. Time Zone": "US/Eastern"
    },

  Response
  Es la combinación de la MetaData con TimeSerie

  Servicios

  Las funciones que ofrece la api, en este caso es exponer los datos de la api del enunciado

  Controlador
  El que maneja los servicios, en este caso si hay datos para exponer retorna un codigo 200 sino 500

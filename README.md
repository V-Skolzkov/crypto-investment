# Crypto Investment

1. [Description](#description)
2. [How to run](#how-to-run)
3. [Service API](#service-api)

## Description

## How to run

Service can be run next:

To run from IDE need to add file src/main/resources/prices/cryptos.txt into classpath and run file CryptoInvestment.java

To run from command line need build project, modify file src/main/resources/prices/cryptos.txt - set full path for files CRYPTO_NAME_values.csv.

Service can be run using next command:

java -jar <full_path>/crypto-investment-0.1.0.jar <full_path>cryptos.txt --server.port=8080



## Service API

http://localhost:8080/crypto-investment/swagger-ui/index.html

Sample API call:

http://localhost:8080/v1/cryptoinvestment/currency/btc/metric/max

http://localhost:8080/v1/cryptoinvestment/normalized/range/2022-01-01/max

http://localhost:8080/v1/cryptoinvestment/normalized/range

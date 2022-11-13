package com.demo.cryptoinvestment.api.dto;

public final class CryptoInvestmentControllerConstants {

    public static final String CURRENCY_RESPONSE = "{\"timestamp\": 1641024000000,\"symbol\": \"ETH\",\"price\": 3715.32}";
    public static final String CURRENCY_RESPONSE_NORMALIZED = "{\"symbol\": \"ETH\",\"price\": 3715.32}";
    public static final String CURRENCY_RESPONSE_NORMALIZED_LIST = "[{\"symbol\": \"ETH\",\"price\": 0.63838101},{\"symbol\": \"XRP\",\"price\": 0.50605413}]";
    public static final String BAD_REQUEST = "{\"error_message\": \"Currency eth1 nor supported!\"}";
    public static final String NOT_FOUND = "{\"error_message\": \"No data found for date 2023-01-03. Try another date.\"}";
    public static final String INTERNAL_SERVER_ERROR = "{\"error_message\": \"Service not ready to serve request. Please try later.\"}";
}

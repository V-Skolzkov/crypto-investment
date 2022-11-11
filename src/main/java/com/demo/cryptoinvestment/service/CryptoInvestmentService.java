package com.demo.cryptoinvestment.service;

import com.demo.cryptoinvestment.api.dto.Metric;
import com.demo.cryptoinvestment.domain.entity.CryptoCurrencyEntity;

public interface CryptoInvestmentService {

    CryptoCurrencyEntity getCryptoCurrencyByMetric(String symbol, Metric metric);

    CryptoCurrencyEntity getCryptoCurrencyWithHighNormalizedRange(String date);
}

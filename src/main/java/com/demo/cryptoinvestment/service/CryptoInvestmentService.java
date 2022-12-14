package com.demo.cryptoinvestment.service;

import com.demo.cryptoinvestment.common.Metric;
import com.demo.cryptoinvestment.domain.entity.CryptoCurrencyEntity;

import java.util.TreeSet;

public interface CryptoInvestmentService {

    CryptoCurrencyEntity getCryptoCurrencyByMetric(String symbol, Metric metric);

    CryptoCurrencyEntity getCryptoCurrencyWithHighNormalizedRange(String date);

    TreeSet<CryptoCurrencyEntity> getSortedCryptoCurrencyByNormalizedRange();
}

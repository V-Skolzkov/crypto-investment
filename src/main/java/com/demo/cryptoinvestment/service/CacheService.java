package com.demo.cryptoinvestment.service;

import com.demo.cryptoinvestment.common.Metric;
import com.demo.cryptoinvestment.domain.entity.CryptoCurrencyEntity;

import java.util.TreeSet;

public interface CacheService {

    void precalculate();

    CryptoCurrencyEntity getCryptoCurrencyByMetric(String symbol, Metric metric);

    TreeSet<CryptoCurrencyEntity> getSortedCryptoCurrencyByNormalizedRange();
}

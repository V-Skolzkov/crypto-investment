package com.demo.cryptoinvestment.service.impl;

import com.demo.cryptoinvestment.common.Metric;
import com.demo.cryptoinvestment.domain.entity.CryptoCurrencyEntity;
import com.demo.cryptoinvestment.repository.CryptoCurrencyRepository;
import com.demo.cryptoinvestment.service.CacheService;
import com.demo.cryptoinvestment.service.CryptoInvestmentStateHolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.MathContext;
import java.util.*;

import static com.demo.cryptoinvestment.common.Metric.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheServiceImp implements CacheService {

    private final CryptoCurrencyRepository repository;
    private final CryptoInvestmentStateHolderService stateHolderService;

    private Map<String, Map<Metric, CryptoCurrencyEntity>> currencyMap = new HashMap<>();

    private TreeSet<CryptoCurrencyEntity> sortedCurrency = new TreeSet<>(Comparator.comparing(CryptoCurrencyEntity::getPrice,
            (e1, e2) -> e1.compareTo(e2) == 0 ? 0 : e1.compareTo(e2) > 0 ? -1 : 1));

    @Override
    public void precalculate() {

        for (String currency : stateHolderService.getCurrencies()) {
            Map<Metric, CryptoCurrencyEntity> metricMap = new HashMap<>();
            metricMap.put(min, getCurrency(repository.getMin(currency)));
            metricMap.put(max, getCurrency(repository.getMax(currency)));

            if (Objects.nonNull(metricMap.get(max)) && Objects.nonNull(metricMap.get(min))) {
                CryptoCurrencyEntity entity = CryptoCurrencyEntity.builder()
                        .symbol(currency)
                        .price(
                                (metricMap.get(max).getPrice().subtract(metricMap.get(min).getPrice()))
                                        .divide(metricMap.get(min).getPrice(), new MathContext(8)))
                        .build();
                sortedCurrency.add(entity);
            }

            metricMap.put(oldest, getCurrency(repository.getOldest(currency)));
            metricMap.put(newest, getCurrency(repository.getNewest(currency)));
            currencyMap.put(currency, metricMap);
        }
    }

    public CryptoCurrencyEntity getCryptoCurrencyByMetric(String symbol, Metric metric) {

        return switch (metric) {
            case min -> currencyMap.get(symbol.toUpperCase()).get(min);
            case max -> currencyMap.get(symbol.toUpperCase()).get(max);
            case newest -> currencyMap.get(symbol.toUpperCase()).get(newest);
            case oldest -> currencyMap.get(symbol.toUpperCase()).get(oldest);
        };
    }

    @Override
    public TreeSet<CryptoCurrencyEntity> getSortedCryptoCurrencyByNormalizedRange() {
        return sortedCurrency;
    }

    private static CryptoCurrencyEntity getCurrency(List<CryptoCurrencyEntity> entityList) {
        return (Objects.isNull(entityList) || entityList.isEmpty()) ? null : entityList.get(0);
    }
}

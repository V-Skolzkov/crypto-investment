package com.demo.cryptoinvestment.service.impl;

import com.demo.cryptoinvestment.common.Metric;
import com.demo.cryptoinvestment.common.Pair;
import com.demo.cryptoinvestment.domain.entity.CryptoCurrencyEntity;
import com.demo.cryptoinvestment.exception.BadRequestException;
import com.demo.cryptoinvestment.exception.InternalServerErrorException;
import com.demo.cryptoinvestment.exception.NoDataFoundException;
import com.demo.cryptoinvestment.repository.CryptoCurrencyRepository;
import com.demo.cryptoinvestment.service.CacheService;
import com.demo.cryptoinvestment.service.CryptoInvestmentService;
import com.demo.cryptoinvestment.service.CryptoInvestmentStateHolderService;
import com.demo.cryptoinvestment.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CryptoInvestmentServiceImpl implements CryptoInvestmentService {

    private final CryptoCurrencyRepository repository;
    private final CryptoInvestmentStateHolderService stateHolderService;
    private final CacheService cacheService;

    @Override
    public CryptoCurrencyEntity getCryptoCurrencyByMetric(String symbol, Metric metric) {

        serviceReadinessCheck();

        if (!stateHolderService.isSupportedCurrency(symbol.toUpperCase())) {
            throw new BadRequestException(String.format("Currency %s nor supported!", symbol));
        }

        return cacheService.getCryptoCurrencyByMetric(symbol.toUpperCase(), metric);

    }

    @Override
    public CryptoCurrencyEntity getCryptoCurrencyWithHighNormalizedRange(String date) {

        serviceReadinessCheck();

        Pair<Long, Long> dates = CommonUtil.dayToUnixTimestamp(date);
        List<CryptoCurrencyEntity> currencyEntities = repository.getNormalisedRange(dates.getFirst(), dates.getSecond());
        Optional<CryptoCurrencyEntity> result = currencyEntities.stream().max(Comparator.comparing(CryptoCurrencyEntity::getPrice));
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new NoDataFoundException(String.format("No data found for date %s. Try another date.", date));
        }
    }

    @Override
    public TreeSet<CryptoCurrencyEntity> getSortedCryptoCurrencyByNormalizedRange() {
        return cacheService.getSortedCryptoCurrencyByNormalizedRange();
    }

    private void serviceReadinessCheck() {
        if (!stateHolderService.isServiceReady()) {
            throw new InternalServerErrorException("Service not ready to serve request. Please try later.");
        }
    }
}

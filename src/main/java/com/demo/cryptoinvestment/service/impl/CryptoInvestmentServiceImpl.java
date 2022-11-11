package com.demo.cryptoinvestment.service.impl;

import com.demo.cryptoinvestment.api.dto.Metric;
import com.demo.cryptoinvestment.common.Pair;
import com.demo.cryptoinvestment.domain.entity.CryptoCurrencyEntity;
import com.demo.cryptoinvestment.exception.BadRequestException;
import com.demo.cryptoinvestment.exception.InternalServerErrorException;
import com.demo.cryptoinvestment.exception.NoDataFoundException;
import com.demo.cryptoinvestment.repository.CryptoCurrencyRepository;
import com.demo.cryptoinvestment.service.CryptoInvestmentService;
import com.demo.cryptoinvestment.service.CryptoInvestmentStateHolderService;
import com.demo.cryptoinvestment.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CryptoInvestmentServiceImpl implements CryptoInvestmentService {

    private final CryptoCurrencyRepository repository;
    private final CryptoInvestmentStateHolderService stateHolderService;

    @Override
    public CryptoCurrencyEntity getCryptoCurrencyByMetric(String symbol, Metric metric) {

        serviceReadinessCheck();

        if (!stateHolderService.isSupportedCurrency(symbol.toUpperCase())) {
            throw new BadRequestException(String.format("Currency %s nor supported!", symbol));
        }

        return switch (metric) {
            case min -> repository.getMin(symbol.toUpperCase()).get(0);
            case max -> repository.getMax(symbol.toUpperCase()).get(0);
            case newest -> repository.getNewest(symbol.toUpperCase()).get(0);
            case oldest -> repository.getOldest(symbol.toUpperCase()).get(0);
        };
    }

    @Override
    public CryptoCurrencyEntity getCryptoCurrencyWithHighNormalizedRange(String date) {

        Pair<Long, Long> dates = CommonUtil.dayToUnixTimestamp(date);
        List<CryptoCurrencyEntity> currencyEntities = repository.getNormalisedRange(dates.getFirst(), dates.getSecond());
        Optional<CryptoCurrencyEntity> result = currencyEntities.stream().max(Comparator.comparing(CryptoCurrencyEntity::getPrice));
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new NoDataFoundException(String.format("No data found for date %s. Try another date.", date));
        }
    }

    private void serviceReadinessCheck() {
        if (!stateHolderService.isServiceReady()) {
            throw new InternalServerErrorException("Service not ready to serve request. Please try later.");
        }
    }
}

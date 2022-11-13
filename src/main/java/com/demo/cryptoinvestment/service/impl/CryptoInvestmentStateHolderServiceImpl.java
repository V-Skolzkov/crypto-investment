package com.demo.cryptoinvestment.service.impl;

import com.demo.cryptoinvestment.service.CryptoInvestmentStateHolderService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CryptoInvestmentStateHolderServiceImpl implements CryptoInvestmentStateHolderService {

    private static Set<String> CURRENCIES = new HashSet<>();

    private static AtomicBoolean SERVICE_READY = new AtomicBoolean(false);

    @Override
    public void setCurrencies(Set<String> currencies) {
        CURRENCIES.clear();
        CURRENCIES.addAll(currencies);
    }

    @Override
    public boolean isSupportedCurrency(String currency) {
        return CURRENCIES.contains(currency);
    }

    @Override
    public boolean isServiceReady() {
        return SERVICE_READY.get();
    }

    @Override
    public void setServiceReadiness(boolean ready) {
        SERVICE_READY.set(ready);
    }

    @Override
    public Set<String> getCurrencies() {
        return CURRENCIES;
    }
}

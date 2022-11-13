package com.demo.cryptoinvestment.service;

import java.util.Set;

public interface CryptoInvestmentStateHolderService {

    void setCurrencies(Set<String> currencies);

    boolean isSupportedCurrency(String currency);

    boolean isServiceReady();

    void setServiceReadiness(boolean ready);

    Set<String> getCurrencies();

}

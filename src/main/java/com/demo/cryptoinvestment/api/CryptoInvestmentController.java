package com.demo.cryptoinvestment.api;

import com.demo.cryptoinvestment.api.dto.Metric;
import com.demo.cryptoinvestment.api.dto.response.CryptoCurrencyResponse;
import com.demo.cryptoinvestment.domain.entity.CryptoCurrencyEntity;
import com.demo.cryptoinvestment.service.CryptoInvestmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;

@Tag(name = "Public API", description = "Crypto Investment API")
@RestController
@RequestMapping("/v1/cryptoinvestment")
@Slf4j
@RequiredArgsConstructor
public class CryptoInvestmentController {

    private final CryptoInvestmentService investmentService;

    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/currency/{symbol}/metric/{value}"},
            produces = {"application/json"}
    )
    ResponseEntity<CryptoCurrencyResponse> getCryptoCurrency(@PathVariable("symbol") String symbol, @PathVariable("value") Metric metric) {

        CryptoCurrencyEntity currencyEntity = investmentService.getCryptoCurrencyByMetric(symbol, metric);

        CryptoCurrencyResponse response = new CryptoCurrencyResponse();
        return ResponseEntity.status(HttpStatus.OK)
                .body(response.fromDomain(currencyEntity));
    }

    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/normalized/range/{date}/max"},
            produces = {"application/json"}
    )
    ResponseEntity<CryptoCurrencyResponse> getCryptoCurrencyWithHighNormalizedRange(@PathVariable("date") @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") String date) {

        CryptoCurrencyEntity currencyEntity = investmentService.getCryptoCurrencyWithHighNormalizedRange(date);

        CryptoCurrencyResponse response = new CryptoCurrencyResponse();
        return ResponseEntity.status(HttpStatus.OK)
                .body(response.fromDomain(currencyEntity));
    }
}

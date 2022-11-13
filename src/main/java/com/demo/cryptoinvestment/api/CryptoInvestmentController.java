package com.demo.cryptoinvestment.api;

import com.demo.cryptoinvestment.common.Metric;
import com.demo.cryptoinvestment.api.dto.response.CryptoCurrencyResponse;
import com.demo.cryptoinvestment.domain.entity.CryptoCurrencyEntity;
import com.demo.cryptoinvestment.service.CryptoInvestmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import java.util.*;
import java.util.stream.Collectors;

import static com.demo.cryptoinvestment.api.dto.CryptoInvestmentControllerConstants.*;

@Tag(name = "Public API", description = "Crypto Investment API")
@RestController
@RequestMapping("/v1/cryptoinvestment")
@Slf4j
@RequiredArgsConstructor
public class CryptoInvestmentController {

    private final CryptoInvestmentService investmentService;


    @Operation(summary = "Get currency by symbol and metric")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return currency",
                    content = @Content(
                            schema = @Schema(implementation = CryptoCurrencyResponse.class),
                            examples = @ExampleObject(value = CURRENCY_RESPONSE)
                    )),
            @ApiResponse(responseCode = "400", description = "Currency not supported or wrong metric",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = BAD_REQUEST)
                    )),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = INTERNAL_SERVER_ERROR)
                    ))
    })
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/currency/{symbol}/metric/{value}"},
            produces = {"application/json"}
    )
    ResponseEntity<CryptoCurrencyResponse> getCryptoCurrency(@PathVariable("symbol") @Parameter(name = "symbol", example = "ETH") String symbol,
                                                             @PathVariable("value") @Parameter(name = "value", example = "max") Metric metric) {

        CryptoCurrencyEntity currencyEntity = investmentService.getCryptoCurrencyByMetric(symbol, metric);

        CryptoCurrencyResponse response = new CryptoCurrencyResponse();
        return ResponseEntity.status(HttpStatus.OK)
                .body(response.fromDomain(currencyEntity));
    }


    @Operation(summary = "Get currency with the highest normalized range for a specific day")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return currency",
                    content = @Content(
                            schema = @Schema(implementation = CryptoCurrencyResponse.class),
                            examples = @ExampleObject(value = CURRENCY_RESPONSE_NORMALIZED)
                    )),
            @ApiResponse(responseCode = "404", description = "Currency not found fo specified date",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = NOT_FOUND)
                    )),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = INTERNAL_SERVER_ERROR)
                    ))
    })
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/normalized/range/{date}/max"},
            produces = {"application/json"}
    )
    ResponseEntity<CryptoCurrencyResponse> getCryptoCurrencyWithHighNormalizedRange(@PathVariable("date")
                                                                                    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
                                                                                    @Parameter(name = "date", example = "2022-01-01")
                                                                                    String date) {

        CryptoCurrencyEntity currencyEntity = investmentService.getCryptoCurrencyWithHighNormalizedRange(date);

        CryptoCurrencyResponse response = new CryptoCurrencyResponse();
        return ResponseEntity.status(HttpStatus.OK)
                .body(response.fromDomain(currencyEntity));
    }

    @Operation(summary = "Get descending sorted list of all the currencies, comparing the normalized range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return currency list",
                    content = @Content(
                            examples = @ExampleObject(value = CURRENCY_RESPONSE_NORMALIZED_LIST)
                    )),

            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = INTERNAL_SERVER_ERROR)
                    ))
    })
    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/normalized/range"},
            produces = {"application/json"}
    )
    ResponseEntity<List<CryptoCurrencyResponse>> getSortedCryptoCurrencyByNormalizedRange() {

        TreeSet<CryptoCurrencyEntity> currencyEntities = investmentService.getSortedCryptoCurrencyByNormalizedRange();

        List<CryptoCurrencyResponse> result = currencyEntities.stream().map(e -> new CryptoCurrencyResponse().fromDomain(e)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }
}

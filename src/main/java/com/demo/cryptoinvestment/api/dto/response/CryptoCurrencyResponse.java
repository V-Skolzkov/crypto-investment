package com.demo.cryptoinvestment.api.dto.response;

import com.demo.cryptoinvestment.domain.entity.CryptoCurrencyEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CryptoCurrencyResponse {

    @JsonProperty("timestamp")
    Long timestamp;

    @JsonProperty("symbol")
    String symbol;

    @JsonProperty("price")
    BigDecimal price;

    public CryptoCurrencyResponse fromDomain(CryptoCurrencyEntity currencyEntity) {
        this.timestamp = currencyEntity.getTimestamp();
        this.symbol = currencyEntity.getSymbol();
        this.price = currencyEntity.getPrice();

        return this;
    }
}

package com.demo.cryptoinvestment.domain.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "t_crypto_currency")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@ToString
public class CryptoCurrencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "time_stamp")
    Long timestamp;

    @Column(name = "symbol")
    String symbol;

    @Column(name = "price")
    BigDecimal price;
}

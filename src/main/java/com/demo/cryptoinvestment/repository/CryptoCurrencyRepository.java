package com.demo.cryptoinvestment.repository;

import com.demo.cryptoinvestment.domain.entity.CryptoCurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrencyEntity, Long> {

    @Query("SELECT p FROM CryptoCurrencyEntity p where p.symbol = :symbol and p.price = " +
            "(select max(t.price) from CryptoCurrencyEntity t where t.symbol = :symbol)")
    List<CryptoCurrencyEntity> getMax(@Param("symbol") String symbol);

    @Query("SELECT p FROM CryptoCurrencyEntity p where p.symbol = :symbol and p.price = " +
            "(select min(t.price) from CryptoCurrencyEntity t where t.symbol = :symbol)")
    List<CryptoCurrencyEntity> getMin(@Param("symbol") String symbol);

    @Query("SELECT p FROM CryptoCurrencyEntity p where p.symbol = :symbol and p.timestamp = " +
            "(select max(t.timestamp) from CryptoCurrencyEntity t where t.symbol = :symbol)")
    List<CryptoCurrencyEntity> getNewest(@Param("symbol") String symbol);

    @Query("SELECT p FROM CryptoCurrencyEntity p where p.symbol = :symbol and p.timestamp = " +
            "(select min(t.timestamp) from CryptoCurrencyEntity t where t.symbol = :symbol)")
    List<CryptoCurrencyEntity> getOldest(@Param("symbol") String symbol);

    @Query(value = "select max(id) as id, max(time_stamp) as time_stamp, symbol, (max(p.price)-min(p.price))/min(p.price) as price " +
            "from t_crypto_currency p where p.time_stamp >= :from and p.time_stamp < :to group by p.symbol", nativeQuery = true)
    List<CryptoCurrencyEntity> getNormalisedRange(@Param("from") Long from, @Param("to") Long to);
}

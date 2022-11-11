package com.demo.cryptoinvestment.service.impl;

import com.demo.cryptoinvestment.domain.entity.CryptoCurrencyEntity;
import com.demo.cryptoinvestment.exception.InternalServerErrorException;
import com.demo.cryptoinvestment.repository.CryptoCurrencyRepository;
import com.demo.cryptoinvestment.service.Reader;
import lombok.extern.slf4j.Slf4j;
import org.supercsv.cellprocessor.ParseBigDecimal;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;

@Slf4j
public class CsvReader implements Reader {

    private final String filePath;
    private final CryptoCurrencyRepository repository;

    public CsvReader(String filePath, CryptoCurrencyRepository repository) {
        this.filePath = filePath;
        this.repository = repository;
    }

    @Override
    public void run() {
        try {
            try (ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(filePath), CsvPreference.STANDARD_PREFERENCE)) {
                final String[] headers = beanReader.getHeader(true);
                final CellProcessor[] processors = getProcessors();

                CryptoCurrencyEntity currency;
                while ((currency = beanReader.read(CryptoCurrencyEntity.class, headers, processors)) != null) {
                    repository.save(currency);
                }
            }
        } catch (IOException ex) {
            LOG.error("Exception occurred due to process file {}", filePath, ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    private static CellProcessor[] getProcessors() {

        return new CellProcessor[]{
                new NotNull(new ParseLong()),
                new NotNull(),
                new NotNull(new ParseBigDecimal())
        };
    }
}

package com.demo.cryptoinvestment.service.impl;

import com.demo.cryptoinvestment.repository.CryptoCurrencyRepository;
import com.demo.cryptoinvestment.service.CryptoInvestmentStateHolderService;
import com.demo.cryptoinvestment.service.CryptoLoadService;
import com.demo.cryptoinvestment.service.Reader;
import com.demo.cryptoinvestment.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CryptoLoadServiceImpl implements CryptoLoadService {
    private final ExecutorService loadExecutorService = Executors.newFixedThreadPool(5);

    private final CryptoCurrencyRepository repository;
    private final CryptoInvestmentStateHolderService stateHolderService;
    private final ApplicationArguments args;

    public void load() {

        try {
            LOG.info("Loading data ...");

            Path path = Paths.get(args.getNonOptionArgs().get(0));
            List<String> data = Files.readAllLines(path);

            stateHolderService.setCurrencies(data.stream().map(CommonUtil::getCurrency).collect(Collectors.toSet()));

            for (String filePath : data) {
                Reader reader = new CsvReader(filePath, repository);
                loadExecutorService.execute(reader);
            }
            loadExecutorService.shutdown();

            while (!loadExecutorService.isTerminated()) {
                TimeUnit.SECONDS.sleep(1);
            }

            stateHolderService.setServiceReadiness(true);

            LOG.info("Loading data ... done.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}

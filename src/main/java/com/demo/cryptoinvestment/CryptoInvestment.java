package com.demo.cryptoinvestment;

import com.demo.cryptoinvestment.service.CryptoLoadService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "Crypto Investment API", version = "0.1.0"),
        servers = {
                @Server(url = "http://localhost:8080")
        }
)
public class CryptoInvestment {

    public static void main(String[] args) {

        ApplicationContext applicationContext = SpringApplication.run(CryptoInvestment.class, args);
        CryptoLoadService cryptoLoadService = applicationContext.getBean(CryptoLoadService.class);
        cryptoLoadService.load();
    }
}

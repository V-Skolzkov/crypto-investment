package com.demo.cryptoinvestment.api.advice;

import com.demo.cryptoinvestment.api.CryptoInvestmentController;
import com.demo.cryptoinvestment.api.dto.ApiError;
import com.demo.cryptoinvestment.exception.BadRequestException;
import com.demo.cryptoinvestment.exception.CryptoInvestmentException;
import com.demo.cryptoinvestment.exception.InternalServerErrorException;
import com.demo.cryptoinvestment.exception.NoDataFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(assignableTypes = {CryptoInvestmentController.class})
public class CryptoInvestmentControllerAdvice {


    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Hidden
    ApiError badRequest(BadRequestException exception) {
        return processException(exception);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @Hidden
    ApiError internalServerError(InternalServerErrorException exception) {
        return processException(exception);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Hidden
    ApiError internalServerError(NoDataFoundException exception) {
        return processException(exception);
    }

    private ApiError processException(CryptoInvestmentException exception) {

        LOG.error(exception.getMessage());
        return ApiError.builder().errorMessage(exception.getMessage()).build();
    }
}

package ru.netology.diploma.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.netology.diploma.exceptions.ErrorConfirmation;
import ru.netology.diploma.exceptions.ErrorInputData;
import ru.netology.diploma.exceptions.ErrorTransfer;
import ru.netology.diploma.model.*;
import ru.netology.diploma.service.MoneyTransferService;


//@CrossOrigin(origins = "https://serp-ya.github.io")
@CrossOrigin(origins = "http://localhost")
@RestController
public class MoneyTransferController {
    private MoneyTransferService service;

    public MoneyTransferController(MoneyTransferService service) {
        this.service = service;
    }

    @ExceptionHandler(ErrorInputData.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage errorMessage(ErrorInputData exception) {
        return new ErrorMessage(exception.getMessage(),
                HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(ErrorTransfer.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorMessage errorMessage(ErrorTransfer exception) {
        return new ErrorMessage(exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(ErrorConfirmation.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorMessage errorMessage(ErrorConfirmation exception) {
        return new ErrorMessage(exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @PostMapping("/transfer")
    public SuccessTransfer postTransfer(@RequestBody Transfer transfer) {
        return service.postTransfer(transfer);
    }

    @PostMapping("/confirmOperation")
    public SuccessConfirmation confirmOperation(@RequestBody Confirm confirm) {
        return service.confirmOperation(confirm);
    }

}
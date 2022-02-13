package ru.netology.diploma.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.netology.diploma.exceptions.ErrorConfirmation;
import ru.netology.diploma.exceptions.ErrorInputData;
import ru.netology.diploma.exceptions.ErrorTransfer;
import ru.netology.diploma.model.*;
import ru.netology.diploma.service.MoneyTransferService;


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
                Integer.parseInt(HttpStatus.BAD_REQUEST.toString()));
    }

    @ExceptionHandler(ErrorTransfer.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorMessage errorMessage(ErrorTransfer exception) {
        return new ErrorMessage(exception.getMessage(),
                500);
    }

    @ExceptionHandler(ErrorConfirmation.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorMessage errorMessage(ErrorConfirmation exception) {
        return new ErrorMessage(exception.getMessage(),
                Integer.parseInt(HttpStatus.INTERNAL_SERVER_ERROR.toString()));
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
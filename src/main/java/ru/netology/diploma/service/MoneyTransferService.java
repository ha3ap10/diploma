package ru.netology.diploma.service;

import ru.netology.diploma.exceptions.ErrorConfirmation;
import ru.netology.diploma.exceptions.ErrorInputData;
import ru.netology.diploma.exceptions.ErrorTransfer;
import ru.netology.diploma.logger.Logger;
import ru.netology.diploma.model.Confirm;
import ru.netology.diploma.model.SuccessConfirmation;
import ru.netology.diploma.model.SuccessTransfer;
import ru.netology.diploma.model.Transfer;
import ru.netology.diploma.repository.MoneyTransferRepository;

import java.util.UUID;

@org.springframework.stereotype.Service
public class MoneyTransferService {
    private static final String ERROR_INPUT = "Input data";
    private static final String ERROR_TRANSFER = "Transfer";
    private static final String ERROR_CONFIRM = "Confirmation";
    private static final String NULL_STRING = "null";
    private static final String TRANSFER_OK_MSG = "Success transfer: ID: ";
    private static final String CONFIRM_OK_MSG = "Success confirm: ID: ";

    private MoneyTransferRepository repository;
    private final Logger logger = Logger.getInstance();

    public MoneyTransferService(MoneyTransferRepository repository) {
        this.repository = repository;
    }

    public SuccessTransfer postTransfer(Transfer transfer) {
        String operationId = UUID.randomUUID().toString();
        //можно ли так проверять?
        if (transfer.toString().contains(NULL_STRING)) {
            logger.error(ERROR_INPUT);
            throw new ErrorInputData(ERROR_INPUT);
        }
        if (repository.postTransfer(operationId, transfer) != null) {
            logger.error(ERROR_TRANSFER);
            throw new ErrorTransfer(ERROR_TRANSFER);
        }
        logger.info(TRANSFER_OK_MSG + operationId + " " + transfer);
        return new SuccessTransfer(operationId);
    }

    public SuccessConfirmation confirmOperation(Confirm confirm) {
        if (!repository.confirmOperation(confirm)) {
            logger.error(ERROR_CONFIRM);
            throw new ErrorConfirmation(ERROR_CONFIRM);
        }
        logger.info(CONFIRM_OK_MSG + confirm.getOperationId());
        return new SuccessConfirmation(confirm.getOperationId());
    }
}

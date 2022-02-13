package ru.netology.diploma.service;

import ru.netology.diploma.exceptions.ErrorConfirmation;
import ru.netology.diploma.exceptions.ErrorTransfer;
import ru.netology.diploma.model.Confirm;
import ru.netology.diploma.model.SuccessConfirmation;
import ru.netology.diploma.model.SuccessTransfer;
import ru.netology.diploma.model.Transfer;
import ru.netology.diploma.repository.MoneyTransferRepository;

import java.util.UUID;

@org.springframework.stereotype.Service
public class MoneyTransferService {
    private MoneyTransferRepository repository;

    public MoneyTransferService(MoneyTransferRepository repository) {
        this.repository = repository;
    }

    public SuccessTransfer postTransfer(Transfer transfer) {
        String operationId = UUID.randomUUID().toString();
        if (repository.postTransfer(operationId, transfer) != null) {
            throw new ErrorTransfer("Error transfer");
        }
        return new SuccessTransfer(operationId);
    }

    public SuccessConfirmation confirmOperation(Confirm confirm) {
        if (!repository.confirmOperation(confirm)) {
            throw new ErrorConfirmation("Error confirmation");
        }
        return new SuccessConfirmation(confirm.getOperationId());
    }
}

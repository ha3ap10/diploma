package ru.netology.diploma.repository;

import ru.netology.diploma.model.Confirm;
import ru.netology.diploma.model.Transfer;

import java.util.HashMap;
import java.util.Map;

@org.springframework.stereotype.Repository
public class MoneyTransferRepository {
    private final Map<String, Transfer> transferMap = new HashMap<>();

    public synchronized Transfer postTransfer(String operationId, Transfer transfer) {
        return transferMap.put(operationId, transfer);
    }

    public synchronized boolean confirmOperation(Confirm confirm) {
            return transferMap.containsKey(confirm.getOperationId());
    }
}

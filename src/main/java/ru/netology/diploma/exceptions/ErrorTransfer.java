package ru.netology.diploma.exceptions;

public class ErrorTransfer extends RuntimeException {
    public ErrorTransfer(String msg) {
        super(msg);
    }
}

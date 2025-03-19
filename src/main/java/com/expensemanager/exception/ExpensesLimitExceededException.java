package com.expensemanager.exception;

public class ExpensesLimitExceededException extends RuntimeException {
    public ExpensesLimitExceededException(String message) {
        super(message);
    }
}
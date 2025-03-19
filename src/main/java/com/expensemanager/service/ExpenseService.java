package com.expensemanager.service;

import com.expensemanager.dto.ExpenseCategory;
import com.expensemanager.dto.ExpenseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {

    ExpenseDTO addExpense(Long userId, ExpenseDTO expenseDTO);

    ExpenseDTO getExpense(Long userId, Long expenseId);

    List<ExpenseDTO> filterExpenses(Long userId, ExpenseCategory expenseCategory, LocalDate startDate, LocalDate endDate);

    BigDecimal calculatesum(Long userId, LocalDate startDate, LocalDate endDate);

    List<ExpenseCategory> findTop3list(Long userId);
}

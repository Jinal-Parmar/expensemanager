package com.expensemanager.service.imp;

import com.expensemanager.dto.ExpenseCategory;
import com.expensemanager.dto.ExpenseDTO;
import com.expensemanager.entity.Expense;
import com.expensemanager.entity.User;
import com.expensemanager.exception.ExpensesLimitExceededException;
import com.expensemanager.exception.ResourceNotFoundException;
import com.expensemanager.exception.UserNotFoundException;
import com.expensemanager.mapper.ExpenseMapper;
import com.expensemanager.repository.ExpenseRepository;
import com.expensemanager.repository.UserRepository;
import com.expensemanager.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class ExpenseServiceImp implements ExpenseService {
    private static final int MAX_EXPENSE_PER_MONTH = 3;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ExpenseDTO addExpense(Long userId, ExpenseDTO expenseDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        YearMonth currentMonth = YearMonth.from(expenseDTO.getCreateDate());
        LocalDate startOfMonth = currentMonth.atDay(1);
        LocalDate endOfMonth = currentMonth.atEndOfMonth();
        expenseRepository.flush();
        int existingExpensesCount = expenseRepository.countByUserIdAndCreateDateBetween(userId, startOfMonth, endOfMonth);
        System.out.println(existingExpensesCount);
        if (existingExpensesCount >= MAX_EXPENSE_PER_MONTH) {
            throw new ExpensesLimitExceededException("User can only log up to " + MAX_EXPENSE_PER_MONTH + " expenses per month.");
        }
        Expense expense = ExpenseMapper.INSTANCE.toExpense(expenseDTO);

        expense.setUserId(user.getId());
        expenseRepository.save(expense);
        return ExpenseMapper.INSTANCE.toExpenseDTO(expense);
    }

    @Override
    public ExpenseDTO getExpense(Long userId, Long expenseId) {
        Expense expense = expenseRepository.findByIdAndUserId(expenseId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("expense not found"));
        return ExpenseMapper.INSTANCE.toExpenseDTO(expense);
    }

    @Override
    public List<ExpenseDTO> filterExpenses(Long userId, ExpenseCategory expenseCategory, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Expense> expenseEntities;
        if (expenseCategory != null && startDate != null && endDate != null) {
            expenseEntities = expenseRepository.findByUserIdAndCategoryAndCreateDateBetween(user.getId(), expenseCategory, startDate, endDate);
        } else if (expenseCategory != null) {
            expenseEntities = expenseRepository.findByUserIdAndCategory(user.getId(), expenseCategory);
        } else if (startDate != null && endDate != null) {
            expenseEntities = expenseRepository.findByUserIdAndCreateDateBetween(user.getId(), startDate, endDate);
        } else {
            expenseEntities = expenseRepository.findByUserId(userId);
        }
        return ExpenseMapper.INSTANCE.toExpenseDTOList(expenseEntities);
    }

    @Override
    public BigDecimal calculatesum(Long userId, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return expenseRepository.calculateSumBetween(userId, startDate, endDate).orElse(BigDecimal.ZERO);
    }

    @Override
    public List<ExpenseCategory> findTop3list(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return expenseRepository.findTopThree(userId);

    }

}

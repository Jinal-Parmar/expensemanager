package com.expensemanager.controller;

import com.expensemanager.dto.ExpenseCategory;
import com.expensemanager.dto.ExpenseDTO;
import com.expensemanager.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userexpense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("/addExpense/{userId}")
    ResponseEntity<ExpenseDTO> addExpense(@PathVariable Long userId, @RequestBody ExpenseDTO expenseDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(expenseService.addExpense(userId, expenseDTO));
    }

    @GetMapping("/{userId}/{expenseId}")
    public ResponseEntity<ExpenseDTO> getExpense(@PathVariable Long userId, @PathVariable Long expenseId) {
        return ResponseEntity.ok(expenseService.getExpense(userId, expenseId));
    }

    @GetMapping("/filter/{userId}")
    public ResponseEntity<List<ExpenseDTO>> filterExpenses(@PathVariable Long userId, @RequestParam(required = false) ExpenseCategory expenseCategory, @RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate) {
        List<ExpenseDTO> expenseDTOList = expenseService.filterExpenses(userId, expenseCategory, startDate, endDate);
        return ResponseEntity.ok(expenseDTOList);
    }

    @GetMapping("total/{userId}")
    public ResponseEntity<BigDecimal> calculateTotal(@PathVariable Long userId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(expenseService.calculatesum(userId, startDate, endDate));
    }

    @GetMapping("top/{userId}")
    public ResponseEntity<List<ExpenseCategory>> findTopSpendingCategories(@PathVariable Long userId) {
        return ResponseEntity.ok(expenseService.findTop3list(userId));
    }

}

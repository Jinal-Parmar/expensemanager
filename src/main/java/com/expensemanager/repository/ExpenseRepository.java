package com.expensemanager.repository;

import com.expensemanager.dto.ExpenseCategory;
import com.expensemanager.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findByIdAndUserId(Long id, Long userId);

    List<Expense> findByUserIdAndCategoryAndCreateDateBetween(Long userId, ExpenseCategory expenseCategory, LocalDate startDate, LocalDate endDate);

    List<Expense> findByUserIdAndCategory(Long userId, ExpenseCategory expenseCategory);

    List<Expense> findByUserIdAndCreateDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    List<Expense> findByUserId(Long userId);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.userId = :userId AND e.createDate BETWEEN :startDate AND :endDate")
    Optional<BigDecimal> calculateSumBetween(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT e.category FROM Expense e WHERE e.userId = :userId GROUP BY e.category ORDER BY SUM(e.amount) DESC, e.category ASC LIMIT 3")
    List<ExpenseCategory> findTopThree(Long userId);

    int countByUserIdAndCreateDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}

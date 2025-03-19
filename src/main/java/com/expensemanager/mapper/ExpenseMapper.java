package com.expensemanager.mapper;

import com.expensemanager.dto.ExpenseDTO;
import com.expensemanager.entity.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper()
public interface ExpenseMapper {
    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);
    Expense toExpense(ExpenseDTO expenseDTO);
    ExpenseDTO toExpenseDTO(Expense expense);
    List<ExpenseDTO> toExpenseDTOList(List<Expense> expenseList);
}

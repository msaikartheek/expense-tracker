package com.expense.tracker.entity;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDetails {
    List<ExpenseDetails> expenseDetails;
    BudgetDetails budgetDetails;
}

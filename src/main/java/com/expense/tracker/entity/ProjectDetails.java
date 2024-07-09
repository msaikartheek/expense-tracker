package com.expense.tracker.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectDetails {
    //    List<ExpenseDetails> expenseDetails;
//    BudgetDetails budgetDetails;
    String id;
    @NotNull(message = "Project name cannot be null")
    String projectName;
    String projectDescription;
    Double expectedBudget;
    LocalDate startDate;
    LocalDate endDate;

}

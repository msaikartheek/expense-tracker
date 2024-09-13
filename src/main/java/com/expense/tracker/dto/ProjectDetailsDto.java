package com.expense.tracker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectDetailsDto {
    //    List<ExpenseDetails> expenseDetails;
//    BudgetDetails budgetDetails;
    String id;
//    @NotNull(message = "Project name cannot be null")
//    String projectName;
//    String projectDescription;
//    Double expectedBudget;
    LocalDate startDate;
    LocalDate endDate;
    String status;
    Integer days;

}

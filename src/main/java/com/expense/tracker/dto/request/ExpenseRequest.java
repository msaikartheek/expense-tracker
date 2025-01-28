package com.expense.tracker.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ExpenseRequest(String userId,String type,String transactionType,
                             String timeline, LocalDate startDate, LocalDate endDate,String clientId, @NotNull Integer pageSize,
                             @NotNull Integer pageIndex) {
}

package com.expense.tracker.dto.request;

import java.time.LocalDate;

public record ExpenseRequest(String userId,String type,String transactionType, String timeline, LocalDate startDate, LocalDate endDate,String clientId) {
}

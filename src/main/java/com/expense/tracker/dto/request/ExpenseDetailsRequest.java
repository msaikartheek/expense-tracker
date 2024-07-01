package com.expense.tracker.dto.request;

import lombok.NonNull;

import java.time.LocalDate;

public class ExpenseDetailsRequest {
    LocalDate date;
    Double amount;
    String type;
    @NonNull
    String category;
    String comments;
    String userId;
    String mode;
}

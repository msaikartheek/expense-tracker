package com.expense.tracker.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ExpenseDetailsDto {
    String id;
    LocalDate date;
    Double amount;
    String type;
    @NonNull
    String category;
    String comments;
    String userId;
    String mode;
    String transactionType;
    String clientId;
}

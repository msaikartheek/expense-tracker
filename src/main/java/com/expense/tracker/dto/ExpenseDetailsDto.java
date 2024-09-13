package com.expense.tracker.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    String transactionStatus;
    String clientId;
    String clientName;
    Double paidAmount;
    LocalDateTime createdDate;
    LocalDateTime updatedDate;

    public ExpenseDetailsDto(String id, LocalDate date, Double amount, String type, @NonNull String category, String comments, String userId, String mode, String transactionType, String transactionStatus, String clientId, Double paidAmount, String clientName) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.comments = comments;
        this.userId = userId;
        this.mode = mode;
        this.transactionType = transactionType;
        this.transactionStatus = transactionStatus;
        this.clientId = clientId; this.paidAmount = paidAmount;
        this.clientName = clientName;

    }
}

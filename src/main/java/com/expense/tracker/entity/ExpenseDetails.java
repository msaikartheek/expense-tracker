package com.expense.tracker.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "expenseDetails")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ExpenseDetails {
    @Id
    String id;
    LocalDate date;
    Double amount;
    String type;
    String category;
    String comments;
    String userId;
    String mode;
    String transactionType;
    String transactionStatus;
    String clientId;
    Double paidAmount;
    LocalDateTime createdDate;
    LocalDateTime updatedDate;


}

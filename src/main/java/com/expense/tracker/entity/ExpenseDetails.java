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
    @Indexed
    String id;
    LocalDate date;
    Double amount;
    String type;
    String category;
    String comments;
    @Indexed
    String userId;
}

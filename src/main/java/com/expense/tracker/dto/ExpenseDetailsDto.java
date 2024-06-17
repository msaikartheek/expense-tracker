package com.expense.tracker.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpenseDetailsDto {
    String id;
    LocalDate date;
    Double amount;
    String type;
    @NonNull
    String category;
    String comments;
    String userId;
}

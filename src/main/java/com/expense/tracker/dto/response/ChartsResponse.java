package com.expense.tracker.dto.response;

import com.expense.tracker.entity.ExpenseDetails;

import java.util.List;
import java.util.Map;

public record ChartsResponse(List<ExpenseDetails> expenseDetails, Map<String, Double> categoryAmountMap,
                             Double creditedTotalAmount, Double debitedTotalAmount,Double pendingTotalAmount) {
}

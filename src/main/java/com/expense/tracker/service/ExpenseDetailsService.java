package com.expense.tracker.service;

import com.expense.tracker.dto.ExpenseDetailsDto;
import com.expense.tracker.dto.request.ExpenseRequest;
import com.expense.tracker.dto.response.ChartsResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface Expense details service.
 */
public interface ExpenseDetailsService {


    /**
     * Save or update expense details mono.
     *
     * @param expenseDetailsDto the expense details dto
     * @return the mono
     */
    Mono<ExpenseDetailsDto> saveExpenseDetails(ExpenseDetailsDto expenseDetailsDto);

    /**
     * Gets all expense details for a user.
     *
     * @param expenseRequest the expense request
     * @return the all expense details
     */
    Flux<ExpenseDetailsDto> getAllExpenseDetails(ExpenseRequest expenseRequest);

    /**
     * Gets expense chart details.
     *
     * @param expenseRequest the expense request
     * @return the expense chart details
     */
    Mono<ChartsResponse> getExpenseChartDetails(ExpenseRequest expenseRequest);

    /**
     * Gets latest three details.
     *
     * @param expenseRequest the expense request
     * @return the latest three details
     */
    Flux<ExpenseDetailsDto> getLatestThreeDetails(ExpenseRequest expenseRequest);
}

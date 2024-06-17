package com.expense.tracker.controller;

import com.expense.tracker.dto.ExpenseDetailsDto;
import com.expense.tracker.dto.request.ExpenseRequest;
import com.expense.tracker.dto.response.ChartsResponse;
import com.expense.tracker.service.ExpenseDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The type Expense tracker controller.
 */
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ExpenseTrackerController {

    /**
     * The Expense details service.
     */
    ExpenseDetailsService expenseDetailsService;

    /**
     * Create expense details response entity.
     *
     * @param expenseDetailsDto the expense details dto
     * @return the response entity
     */
    @PostMapping("/expense")
    public ResponseEntity<Mono<ExpenseDetailsDto>> createExpenseDetails(@RequestBody ExpenseDetailsDto expenseDetailsDto) {
        return ResponseEntity.ok(expenseDetailsService.saveExpenseDetails(expenseDetailsDto));
    }

    /**
     * All expenses response entity.
     *
     * @param expenseRequest the expense request
     * @return the response entity
     */
    @GetMapping("/expenses")
    public ResponseEntity<Flux<ExpenseDetailsDto>> allExpenses(ExpenseRequest expenseRequest) {
        return ResponseEntity.ok(expenseDetailsService.getAllExpenseDetails(expenseRequest));
    }

    @GetMapping("/expenses/chart")
    public ResponseEntity<Mono<ChartsResponse>> allExpensesChart(ExpenseRequest expenseRequest) {
        return ResponseEntity.ok(expenseDetailsService.getExpenseChartDetails(expenseRequest));
    }
}

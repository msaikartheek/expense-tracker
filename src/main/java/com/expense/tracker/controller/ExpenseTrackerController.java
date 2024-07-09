package com.expense.tracker.controller;

import com.expense.tracker.dto.ExpenseDetailsDto;
import com.expense.tracker.dto.request.ExpenseRequest;
import com.expense.tracker.dto.response.ChartsResponse;
import com.expense.tracker.service.ExpenseDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    public ResponseEntity<Mono<ExpenseDetailsDto>> createExpenseDetails(@RequestBody ExpenseDetailsDto expenseDetailsDto,
                                                                        HttpServletRequest servletRequest) {
        log.info("*** Getting uid for user - {}",servletRequest.getAttribute("email"));
        expenseDetailsDto.setUserId(servletRequest.getAttribute("uid").toString());
        return ResponseEntity.ok(expenseDetailsService.saveExpenseDetails(expenseDetailsDto));
    }

    /**
     * All expenses response entity.
     *
     * @param expenseRequest the expense request
     * @return the response entity
     */
    @GetMapping("/expenses")
    public ResponseEntity<Flux<ExpenseDetailsDto>> allExpenses(ExpenseRequest expenseRequest,HttpServletRequest servletRequest) {

        return ResponseEntity.ok(expenseDetailsService.getAllExpenseDetails(expenseRequest,
                servletRequest.getAttribute("uid").toString()));
    }

    /**
     * All expenses chart response entity.
     *
     * @param expenseRequest the expense request
     * @return the response entity
     */
    @GetMapping("/expenses/chart")
    public ResponseEntity<Mono<ChartsResponse>> allExpensesChart(ExpenseRequest expenseRequest,
                                                                 HttpServletRequest servletRequest) {

        return ResponseEntity.ok(expenseDetailsService.getExpenseChartDetails(expenseRequest,
                servletRequest.getAttribute("uid").toString()));
    }

    /**
     * Gets latest three expenses.
     *
     * @param expenseRequest the expense request
     * @return the latest three expenses
     */
    @GetMapping("/expenses/latestThree")
    public ResponseEntity<Flux<ExpenseDetailsDto>> getLatestThreeExpenses(ExpenseRequest expenseRequest,
                                                                          HttpServletRequest servletRequest) {
        return ResponseEntity.ok(expenseDetailsService.getLatestThreeDetails(expenseRequest,
                servletRequest.getAttribute("uid").toString()));
    }
}

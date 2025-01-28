package com.expense.tracker.controller;

import com.expense.tracker.dto.ExpenseDetailsDto;
import com.expense.tracker.dto.request.ExpenseRequest;
import com.expense.tracker.dto.response.ChartsResponse;
import com.expense.tracker.service.IExpenseDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The type Expense tracker controller.
 */
@RestController
@RequestMapping("/api/v1/expenses")
@AllArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ExpenseTrackerController {

    /**
     * The Expense details service.
     */
    IExpenseDetailsService iExpenseDetailsService;

    /**
     * Create expense details response entity.
     *
     * @param expenseDetailsDto the expense details dto
     * @return the response entity
     */
    @PostMapping("/create")
    public ResponseEntity<Mono<ExpenseDetailsDto>> createExpenseDetails(@RequestBody ExpenseDetailsDto expenseDetailsDto,
                                                                        HttpServletRequest servletRequest) {
        log.info("*** Saving expense details - {} ***", servletRequest.getAttribute("email"));
        expenseDetailsDto.setUserId(servletRequest.getAttribute("uid").toString());
        return ResponseEntity.ok(iExpenseDetailsService.saveExpenseDetails(expenseDetailsDto));
    }

    @GetMapping("/total-count")
    public ResponseEntity<Mono<Integer>> totalExpenseCount(ExpenseRequest expenseRequest, HttpServletRequest servletRequest) {
        log.info("*** Total expense count - {} ***", servletRequest.getAttribute("uid"));

        return ResponseEntity.ok(iExpenseDetailsService
                .getTotalRecordCount(expenseRequest,servletRequest.getAttribute("uid").toString()));
    }

    /**
     * All expenses response entity.
     *
     * @param expenseRequest the expense request
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<Flux<ExpenseDetailsDto>> allExpenses(ExpenseRequest expenseRequest, HttpServletRequest servletRequest) {
        log.info("*** Getting list of expenses for - {}", servletRequest.getAttribute("email"));
        return ResponseEntity.ok(iExpenseDetailsService.getAllExpenseDetails(expenseRequest,
                servletRequest.getAttribute("uid").toString()));
    }

    /**
     * All expenses chart response entity.
     *
     * @param expenseRequest the expense request
     * @return the response entity
     */
    @GetMapping("/chart")
    public ResponseEntity<Mono<ChartsResponse>> allExpensesChart(ExpenseRequest expenseRequest,
                                                                 HttpServletRequest servletRequest) {
        log.info("*** Getting chart data for all expenses ***");
        return ResponseEntity.ok(iExpenseDetailsService.getExpenseChartDetails(expenseRequest,
                servletRequest.getAttribute("uid").toString()));
    }

    /**
     * Gets latest three expenses.
     *
     * @param expenseRequest the expense request
     * @return the latest three expenses
     */
    @GetMapping("/latestThree")
    public ResponseEntity<Flux<ExpenseDetailsDto>> getLatestThreeExpenses(ExpenseRequest expenseRequest,
                                                                          HttpServletRequest servletRequest) {
        log.info("*** Getting the latest three expenses  for loggedIn user - {} ***", servletRequest.getAttribute("email"));
        return ResponseEntity.ok(iExpenseDetailsService.getLatestThreeDetails(expenseRequest,
                servletRequest.getAttribute("uid").toString()));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Mono<String>> removeExpense(@PathVariable String id, HttpServletRequest servletRequest) {
        log.info("*** Removing expense with id - {}", id);
        return ResponseEntity.ok(iExpenseDetailsService.deleteExpense(id));
    }
}

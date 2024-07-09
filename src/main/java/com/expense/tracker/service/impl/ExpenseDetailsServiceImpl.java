package com.expense.tracker.service.impl;

import com.expense.tracker.dto.ExpenseDetailsDto;
import com.expense.tracker.dto.request.ExpenseRequest;
import com.expense.tracker.dto.response.ChartsResponse;
import com.expense.tracker.entity.ExpenseDetails;
import com.expense.tracker.mapper.SourceDestinationMapper;
import com.expense.tracker.repo.ExpenseDetailsRepository;
import com.expense.tracker.service.ExpenseDetailsService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

/**
 * The type Expense details service.
 */
@Service
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ExpenseDetailsServiceImpl implements ExpenseDetailsService {

    /**
     * The Details repository.
     */
    ExpenseDetailsRepository detailsRepository;
    SourceDestinationMapper mapper;
    ReactiveMongoTemplate mongoTemplate;

    /**
     * Instantiates a new Expense details service.
     *
     * @param detailsRepository the details repository
     * @param mapper            the mapper
     * @param mongoTemplate     the mongo template
     */
    @Autowired
    public ExpenseDetailsServiceImpl(ExpenseDetailsRepository detailsRepository, SourceDestinationMapper mapper, ReactiveMongoTemplate mongoTemplate) {
        this.detailsRepository = detailsRepository;
        this.mapper = mapper;
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Save or update expense details mono.
     *
     * @param expenseDetailsDto the expense details dto
     * @return the mono
     */
    @Override
    public Mono<ExpenseDetailsDto> saveExpenseDetails(ExpenseDetailsDto expenseDetailsDto) {
        ExpenseDetails expenseDetail = detailsRepository.save(mapper.dtoToEntity(expenseDetailsDto)).block();
        return Mono.just(mapper.entityToDto(expenseDetail));
    }

    /**
     * Gets all expense details for a user.
     *
     * @param expenseRequest the expense request
     * @return the all expense details
     */
    @Override
    public Flux<ExpenseDetailsDto> getAllExpenseDetails(ExpenseRequest expenseRequest, String userId) {
        Flux<ExpenseDetails> expenseDetailsFlux = detailsRepository.findAllByUserIdAndTypeAndTransactionType(userId, expenseRequest.type(),expenseRequest.transactionType());
        return expenseDetailsFlux.map(mapper::entityToDto);
    }

    /**
     * @param expenseRequest
     * @param uid
     * @return
     */
    @Override
    public Mono<ChartsResponse> getExpenseChartDetails(ExpenseRequest expenseRequest, String uid) {
        LocalDate startDate = null;
        LocalDate endDate = null;
        if (Objects.isNull(expenseRequest.startDate()) && Objects.isNull(expenseRequest.endDate())) {
            switch (expenseRequest.timeline()) {
                case "PAST_MONTH" -> {
                    startDate = LocalDate.now().minusMonths(1);
                    endDate = startDate.plusMonths(1);
                }
                case "PAST_WEEK" -> {
                    startDate = LocalDate.now().minusWeeks(1);
                    endDate = startDate.plusWeeks(1);
                }
                case "PAST_YEAR" -> {
                    startDate = LocalDate.now().minusYears(1);
                    endDate = startDate.plusYears(1);
                }
                case "PAST_DAY" -> {
                    startDate = LocalDate.now().minusDays(1);
                    endDate = startDate.plusDays(1).atStartOfDay().toLocalDate();
                }
                case "PAST_6_MONTHS" -> {
                    startDate = LocalDate.now().minusMonths(6);
                    endDate = startDate.plusMonths(6).atStartOfDay().toLocalDate();
                }
                case null, default -> {
                    startDate = LocalDate.now().withDayOfMonth(1);
                    endDate = LocalDate.now();
                }
            }
        } else {
            startDate = expenseRequest.startDate();
            endDate = expenseRequest.endDate();
        }
        log.info("Start Date >>> {}", startDate);
        log.info("End Date >>> {}", endDate);
        MatchOperation matchOperation = Aggregation.match(Criteria.where("userId")
                .is(uid)
                .and("type").is(expenseRequest.type())
                .and("date").gte(startDate).lte(endDate));
        ProjectionOperation projectionOperation = Aggregation.project("date", "amount", "category", "type","transactionType").andExclude("_id");
        Aggregation aggregation = newAggregation(matchOperation, projectionOperation);
        Flux<ExpenseDetails> aggResults = mongoTemplate.aggregate(aggregation, "expenseDetails", ExpenseDetails.class);
        return aggResults.collectList().flatMap(res -> {
            Map<String, Double> categoryTotal = res.stream().collect(Collectors.groupingBy(ExpenseDetails::getCategory, Collectors.summingDouble(ExpenseDetails::getAmount)));

            Double creditedTotalAmount = res.stream()
                    .filter(d -> d.getTransactionType() != null && d.getTransactionType().equalsIgnoreCase("Credited"))
                    .mapToDouble(ExpenseDetails::getAmount)
                    .sum();

            Double debitedTotalAmount =  res.stream()
                    .filter(d -> d.getTransactionType() != null && d.getTransactionType().equalsIgnoreCase("Debited"))
                    .mapToDouble(ExpenseDetails::getAmount)
                    .sum();
            return Mono.justOrEmpty(new ChartsResponse(null, categoryTotal, creditedTotalAmount, debitedTotalAmount));
        });
    }

    /**
     * @param expenseRequest
     * @param userId
     * @return
     */
    @Override
    public Flux<ExpenseDetailsDto> getLatestThreeDetails(ExpenseRequest expenseRequest, String userId) {
        Flux<ExpenseDetails> expenseDetailsFlux = detailsRepository.findByUserIdAndTypeOrderByDateDesc(userId, expenseRequest.type(), PageRequest.of(0, 3));
        return expenseDetailsFlux.map(mapper::entityToDto);
    }
}

package com.expense.tracker.service.impl;

import com.expense.tracker.dto.ExpenseDetailsDto;
import com.expense.tracker.dto.request.ExpenseRequest;
import com.expense.tracker.dto.response.ChartsResponse;
import com.expense.tracker.entity.ExpenseDetails;
import com.expense.tracker.mapper.SourceDestinationMapper;
import com.expense.tracker.repo.ClientDetailsRepository;
import com.expense.tracker.repo.ExpenseDetailsRepository;
import com.expense.tracker.service.IExpenseDetailsService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.CountOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

/**
 * The type Expense details service.
 */
@Service
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ExpenseDetailsServiceImpl implements IExpenseDetailsService {

    /**
     * The Details repository.
     */
    ExpenseDetailsRepository detailsRepository;
    SourceDestinationMapper mapper;
    ReactiveMongoTemplate mongoTemplate;
    ClientDetailsRepository clientDetailsRepository;

    /**
     * Instantiates a new Expense details service.
     *
     * @param detailsRepository the details repository
     * @param mapper            the mapper
     * @param mongoTemplate     the mongo template
     */
    @Autowired
    public ExpenseDetailsServiceImpl(ExpenseDetailsRepository detailsRepository, SourceDestinationMapper mapper, ReactiveMongoTemplate mongoTemplate, ClientDetailsRepository clientDetailsRepository) {
        this.detailsRepository = detailsRepository;
        this.mapper = mapper;
        this.mongoTemplate = mongoTemplate;
        this.clientDetailsRepository = clientDetailsRepository;
    }

    /**
     * Save or update expense details mono.
     *
     * @param expenseDetailsDto the expense details dto
     * @return the mono
     */
    @Override
    public Mono<ExpenseDetailsDto> saveExpenseDetails(ExpenseDetailsDto expenseDetailsDto) {
        if (StringUtils.hasText(expenseDetailsDto.getId())) {
            expenseDetailsDto.setUpdatedDate(LocalDateTime.now());
        }else{
            expenseDetailsDto.setCreatedDate(LocalDateTime.now());
        }
        ExpenseDetails toEntity = mapper.dtoToEntity(expenseDetailsDto);
        return  detailsRepository.save(toEntity).map(mapper::entityToDto);
    }

    /**
     * Gets all expense details for a user.
     *
     * @param expenseRequest the expense request
     * @return the all expense details
     */
    @Override
    public Flux<ExpenseDetailsDto> getAllExpenseDetails(ExpenseRequest expenseRequest, String userId) {
        log.info("** In all expense details **");
        Flux<ExpenseDetails> expenseDetailsFlux = null;
        Pageable pageDetails = null;
        if(null != expenseRequest.pageIndex() && null != expenseRequest.pageSize()) {
            log.info("** In pagination details **");
             pageDetails = PageRequest.of(expenseRequest.pageIndex(),expenseRequest.pageSize());
        }


        if (Objects.nonNull(expenseRequest.type()) && Objects.nonNull(expenseRequest.transactionType())) {

            expenseDetailsFlux = detailsRepository
                    .findAllByUserIdAndTypeAndTransactionType(userId, expenseRequest.type(),
                            expenseRequest.transactionType(),pageDetails)
                    .sort(Comparator.comparing(ExpenseDetails::getDate, Comparator.reverseOrder()));;
            return expenseDetailsFlux.map(mapper::entityToDto);
        } else if (StringUtils.hasText(expenseRequest.clientId())) {
            log.info("** In all expense details check with client id **");
            return detailsRepository.findALLByUserIdAndClientId(userId,expenseRequest.clientId(),pageDetails)
                    .flatMap(expense -> clientDetailsRepository.findById(expense.getClientId())
                    .map(client -> new ExpenseDetailsDto(
                            expense.getId(),
                            expense.getDate(),
                            expense.getAmount(),
                            expense.getType(),
                            expense.getCategory(),
                            expense.getComments(),
                            expense.getUserId(),
                            expense.getMode(),
                            null,
                            expense.getTransactionStatus(),
                            expense.getClientId(),
                            expense.getPaidAmount(),
                            client.getName()
                    ))).sort(Comparator.comparing(ExpenseDetailsDto::getDate, Comparator.reverseOrder()));
        } else {
            log.info("** In find all expense details with user Id **");
           PageRequest  pageRequest = PageRequest.of(expenseRequest.pageIndex(),expenseRequest.pageSize(),
                   Sort.by(Sort.Direction.DESC,"date"));
            return detailsRepository.findAllByUserId(userId,
                            pageRequest)
                    .flatMap(expense -> clientDetailsRepository.findById(expense.getClientId())
                            .map(client -> new ExpenseDetailsDto(
                                    expense.getId(),
                                    expense.getDate(),
                                    expense.getAmount(),
                                    expense.getType(),
                                    expense.getCategory(),
                                    expense.getComments(),
                                    expense.getUserId(),
                                    expense.getMode(),
                                    null,
                                    expense.getTransactionStatus(),
                                    expense.getClientId(),
                                    expense.getPaidAmount(),
                                    client.getName()
                            ))).sort(Comparator.comparing(ExpenseDetailsDto::getDate, Comparator.reverseOrder()));
//           return detailsRepository.findAllByUserId(userId).flatMap(expense -> clientDetailsRepository.findById(expense.getClientId()))
//                   .map(clientDetails -> new ExpenseDetailsDto().setDate());
        }

        //return expenseDetailsFlux.map(mapper::entityToDto);
        // return null;
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
        MatchOperation matchOperation;
        if (StringUtils.hasText(expenseRequest.clientId())) {
            matchOperation = Aggregation.match(Criteria.where("userId")
                    .is(uid)
                    .and("clientId").is(expenseRequest.clientId()));
                    //.and("type").is(expenseRequest.type())
                   // .and("date").gte(startDate).lte(endDate));
        } else {
            matchOperation = Aggregation.match(Criteria.where("userId")
                    .is(uid)
                    //.and("type").is(expenseRequest.type())
                    .and("date").gte(startDate).lte(endDate));
        }
        ProjectionOperation projectionOperation = Aggregation.project("date", "amount", "category", "type", "transactionStatus","paidAmount").andExclude("_id");
        Aggregation aggregation = newAggregation(matchOperation, projectionOperation);
        Flux<ExpenseDetails> aggResults = mongoTemplate.aggregate(aggregation, "expenseDetails", ExpenseDetails.class);
        return aggResults.collectList().flatMap(res -> {
            Map<String, Double> categoryTotal = res.stream().collect(Collectors.groupingBy(ExpenseDetails::getCategory, Collectors.summingDouble(ExpenseDetails::getAmount)));

            Double creditedTotalAmount = res.stream()
                    .filter(d -> d.getType() != null && d.getType().equalsIgnoreCase("credit"))
                    .mapToDouble(ExpenseDetails::getAmount)
                    .sum();

            Double partiallyPayment = res.stream()
                    .filter(d -> d.getTransactionStatus() != null
                            && (d.getTransactionStatus().equalsIgnoreCase("pPaid")))
                    .mapToDouble(e -> (e.getAmount()-e.getPaidAmount()))
                    .sum();

            Double pendingTotalAmount = res.stream()
                    .filter(d -> d.getTransactionStatus() != null
                            && (d.getTransactionStatus().equalsIgnoreCase("pending")))
                    .mapToDouble(e -> (e.getAmount()-e.getPaidAmount()))
                    .sum();

            Double debitedTotalAmount = res.stream()
                    .filter(d -> d.getType() != null && d.getType().equalsIgnoreCase("debit"))
                    .mapToDouble(ExpenseDetails::getAmount)
                    .sum();

            return Mono.justOrEmpty(new ChartsResponse(null, categoryTotal, creditedTotalAmount, (debitedTotalAmount-partiallyPayment-pendingTotalAmount),(pendingTotalAmount+partiallyPayment),partiallyPayment));
        });
    }

    /**
     * @param expenseRequest
     * @param userId
     * @return
     */
    @Override
    public Flux<ExpenseDetailsDto> getLatestThreeDetails(ExpenseRequest expenseRequest, String userId) {
        if(StringUtils.hasText(expenseRequest.clientId())){
            Flux<ExpenseDetails> expenseDetailsFlux = detailsRepository
                    .findALLByUserIdAndClientIdOrderByDateDesc(userId, expenseRequest.clientId(), PageRequest.of(0, 3));
            return expenseDetailsFlux.map(mapper::entityToDto);
        }
        Flux<ExpenseDetails> expenseDetailsFlux = detailsRepository
                .findByUserIdAndTypeOrderByDateDesc(userId, expenseRequest.type(), PageRequest.of(0, 3));
        return expenseDetailsFlux.map(mapper::entityToDto);
    }

    /**
     * Deleted the exisitng expenses
     *
     * @param id
     * @return
     */
    @Override
    public Mono<String> deleteExpense(String id) {
        return detailsRepository.deleteById(id).then(Mono.just("Expense successfully deleted"));
    }

    /**
     * Returns the total record count for the expenses
     *
     * @param expenseRequest
     * @param userId
     * @return total no of records
     */
    @Override
    public Mono<Integer> getTotalRecordCount(ExpenseRequest expenseRequest, String userId) {
        MatchOperation matchOperation = Aggregation.match(Criteria.where("userId").is(userId));
        CountOperation countOperation = Aggregation.count().as("totalCount");
        Aggregation totalCountAggregation = newAggregation(matchOperation, countOperation);
        return mongoTemplate.aggregate(totalCountAggregation, "expenseDetails", Document.class)
                .next() // Get the first result from the aggregation
                .map(doc -> doc.getInteger("totalCount"))
                .doOnError(err -> log.info("An error occurred while getting total record count {}", err.getMessage()))
                .defaultIfEmpty(0);
    }
}

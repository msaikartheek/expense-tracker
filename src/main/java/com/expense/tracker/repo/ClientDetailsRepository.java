package com.expense.tracker.repo;

import com.expense.tracker.entity.ClientDetails;
import com.expense.tracker.entity.ExpenseDetails;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * The interface Client details repository.
 */
public interface ClientDetailsRepository extends ReactiveMongoRepository<ClientDetails, String> {


    /**
     * Find all by user id flux.
     *
     * @param userId the user id
     * @return the flux
     */
    Flux<ClientDetails> findAllByUserId(String userId);
}

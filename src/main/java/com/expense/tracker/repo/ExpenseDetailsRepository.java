package com.expense.tracker.repo;

import com.expense.tracker.entity.ExpenseDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * The interface Expense details repository.
 */
@Repository
@Component
public interface ExpenseDetailsRepository extends ReactiveMongoRepository<ExpenseDetails, String> {

    /**
     * Find all by user id and type flux.
     *
     * @param UserId the user id
     * @param type   the type
     * @return the flux
     */
    Flux<ExpenseDetails> findAllByUserIdAndType(String UserId,String type);

    /**
     * Find by user id and type order by date desc flux.
     *
     * @param UserId   the user id
     * @param type     the type
     * @param pageable the pageable
     * @return the flux
     */
    Flux<ExpenseDetails> findByUserIdAndTypeOrderByDateDesc(String UserId, String type, Pageable pageable);
}

package com.expense.tracker.repo;

import com.expense.tracker.entity.ExpenseDetails;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@Component
public interface ExpenseDetailsRepository extends ReactiveMongoRepository<ExpenseDetails, String> {

    Flux<ExpenseDetails> findAllByUserIdAndType(String UserId,String type);
}

package com.expense.tracker.service;

import com.expense.tracker.dto.ClientDetailsDto;
import com.expense.tracker.dto.response.ClientsDropDownResponse;
import com.expense.tracker.entity.ClientDetails;
import org.springframework.cache.annotation.Cacheable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface Client details service.
 */
public interface IClientDetailsService {


    @Cacheable("clients")
    Flux<ClientsDropDownResponse> getAllClientsDropDown(String userId);

    /**
     * Gets all clients.
     *
     * @return the all clients
     */
    Flux<ClientDetailsDto> getAllClients(String userId);

    /**
     * Create new client mono.
     *
     * @return the mono
     */
    Mono<ClientDetailsDto> createNewClient(ClientDetails clientDetails);

}

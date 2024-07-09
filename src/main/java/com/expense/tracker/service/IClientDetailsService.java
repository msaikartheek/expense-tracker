package com.expense.tracker.service;

import com.expense.tracker.dto.ClientDetailsDto;
import com.expense.tracker.dto.response.ClientsDropDownResponse;
import org.springframework.cache.annotation.Cacheable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface Client details service.
 */
public interface IClientDetailsService {


    /**
     * Gets all clients drop down.
     *
     * @param userId the user id
     * @return the all clients drop down
     */
    @Cacheable("clients")
    Flux<ClientsDropDownResponse> getAllClientsDropDown(String userId);

    /**
     * Gets all clients.
     *
     * @param userId the user id
     * @return the all clients
     */
    Flux<ClientDetailsDto> getAllClients(String userId);

    /**
     * Create new client mono.
     *
     * @param clientDetails the client details
     * @return the mono
     */
    Mono<ClientDetailsDto> createNewClient(ClientDetailsDto clientDetails);

    /**
     * Delete client details mono.
     *
     * @param clientId the client id
     * @return the mono
     */
    Mono<Void> deleteClientDetails(String clientId);
}

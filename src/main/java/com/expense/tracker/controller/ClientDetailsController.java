package com.expense.tracker.controller;

import com.expense.tracker.dto.ClientDetailsDto;
import com.expense.tracker.dto.response.ClientsDropDownResponse;
import com.expense.tracker.entity.ClientDetails;
import com.expense.tracker.service.IClientDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The type Client details controller.
 */
@RestController
@RequestMapping("/api/v1/client")
@AllArgsConstructor
public class ClientDetailsController {

    /**
     * The Client details service.
     */
    IClientDetailsService clientDetailsService;

    /**
     * Gets all client details.
     *
     * @param userId the user id
     * @return the all client details
     */
    @GetMapping("/dropDown")
    public ResponseEntity<Flux<ClientsDropDownResponse>> getAllClientDetailsDropDown(String userId) {
        return ResponseEntity.ok(clientDetailsService.getAllClientsDropDown(userId));
    }

    /**
     * Gets all client details.
     *
     * @param userId the user id
     * @return the all client details
     */
    @GetMapping
    public ResponseEntity<Flux<ClientDetailsDto>> getAllClientDetails(String userId) {
        return ResponseEntity.ok(clientDetailsService.getAllClients(userId));
    }

    /**
     * Create new clients response entity.
     *
     * @param clientDetails the client details
     * @return the response entity
     */
    @PostMapping("/create")
    public ResponseEntity<Mono<ClientDetailsDto>> createNewClients(@RequestBody ClientDetails clientDetails) {
        return ResponseEntity.ok(clientDetailsService.createNewClient(clientDetails));
    }
}

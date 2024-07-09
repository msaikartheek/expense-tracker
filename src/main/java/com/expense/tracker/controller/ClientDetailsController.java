package com.expense.tracker.controller;

import com.expense.tracker.dto.ClientDetailsDto;
import com.expense.tracker.dto.response.ClientsDropDownResponse;
import com.expense.tracker.entity.ClientDetails;
import com.expense.tracker.service.IClientDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
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
     * @param servletRequest the servlet request
     * @return the all client details
     */
    @GetMapping
    public ResponseEntity<Flux<ClientDetailsDto>> getAllClientDetails(HttpServletRequest servletRequest) {
        return ResponseEntity.ok(clientDetailsService.getAllClients(servletRequest.getAttribute("uid").toString()));
    }

    /**
     * Create new clients response entity.
     *
     * @param clientDetails  the client details
     * @param servletRequest the servlet request
     * @return the response entity
     */
    @PostMapping("/create")
    public ResponseEntity<Mono<ClientDetailsDto>> createNewClients(@Valid @RequestBody ClientDetailsDto clientDetails,
                                                                   HttpServletRequest servletRequest) {
        clientDetails.setUserId(servletRequest.getAttribute("uid").toString());
        return ResponseEntity.ok(clientDetailsService.createNewClient(clientDetails));
    }

    /**
     * Delete client response entity.
     *e
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") String id) {
        log.info("*** Delete the client details ***");
        clientDetailsService.deleteClientDetails(id);
        log.info("*** Client details with id {} is deleted ***",id);
        return ResponseEntity.noContent().build();
    }
}

package com.expense.tracker.service.impl;

import com.expense.tracker.dto.ClientDetailsDto;
import com.expense.tracker.dto.response.ClientsDropDownResponse;
import com.expense.tracker.entity.ClientDetails;
import com.expense.tracker.mapper.SourceDestinationMapper;
import com.expense.tracker.repo.ClientDetailsRepository;
import com.expense.tracker.service.IClientDetailsService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ClientDetailsServiceImpl implements IClientDetailsService {

    ClientDetailsRepository clientDetailsRepository;
    SourceDestinationMapper sourceDestinationMapper;


    @Autowired
    public ClientDetailsServiceImpl(ClientDetailsRepository clientDetailsRepository, SourceDestinationMapper sourceDestinationMapper) {
        this.clientDetailsRepository = clientDetailsRepository;
        this.sourceDestinationMapper = sourceDestinationMapper;

    }

    /**
     * Gets all clients.
     *
     * @return the all clients
     */
    @Cacheable("clients")
    @Override
    public Flux<ClientsDropDownResponse> getAllClientsDropDown(String userId) {
        Flux<ClientDetails> clientDetailsFlux = clientDetailsRepository
                .findAllByUserId(userId);
        return clientDetailsFlux.mapNotNull(sourceDestinationMapper::specifyFieldsToDto);

    }

    @Override
    public Flux<ClientDetailsDto> getAllClients(String userId) {

        Flux<ClientDetails> clientDetailsFlux = clientDetailsRepository
                .findAllByUserId(userId);
        return clientDetailsFlux.mapNotNull(sourceDestinationMapper::clientEntityToDto);
    }

    /**
     * Create new client mono.
     *
     * @return the mono
     */
    @Override
    public Mono<ClientDetailsDto> createNewClient(ClientDetailsDto clientDetails) {
       ClientDetails clientDetails1 =  sourceDestinationMapper.clientDtoToEntity(clientDetails);
        return clientDetailsRepository.save(clientDetails1).mapNotNull(sourceDestinationMapper::clientEntityToDto);
    }

}

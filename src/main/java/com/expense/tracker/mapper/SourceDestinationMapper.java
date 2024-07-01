package com.expense.tracker.mapper;

import com.expense.tracker.dto.ClientDetailsDto;
import com.expense.tracker.dto.ExpenseDetailsDto;
import com.expense.tracker.dto.response.ClientsDropDownResponse;
import com.expense.tracker.entity.ClientDetails;
import com.expense.tracker.entity.ExpenseDetails;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import reactor.core.publisher.Mono;

/**
 * The interface Source destination mapper.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SourceDestinationMapper {

    /**
     * The constant MAPPER.
     */
    SourceDestinationMapper MAPPER = Mappers.getMapper( SourceDestinationMapper.class );

    /**
     * Dto to entity expense details.
     *
     * @param expenseDetailsDto the expense details dto
     * @return the expense details
     */
    ExpenseDetails dtoToEntity(ExpenseDetailsDto expenseDetailsDto);

    /**
     * Entity to dto expense details dto.
     *
     * @param expenseDetails the expense details
     * @return the expense details dto
     */
    ExpenseDetailsDto entityToDto(ExpenseDetails expenseDetails);

    /**
     * Client dto to entity client details.
     *
     * @param clientDetailsDto the client details dto
     * @return the client details
     */
    ClientDetails clientDtoToEntity(ClientDetailsDto clientDetailsDto);

    /**
     * Client entity to dto client details dto.
     *
     * @param clientDetails the client details
     * @return the client details dto
     */
    ClientDetailsDto clientEntityToDto(ClientDetails clientDetails);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target="name", source = "name")
    @Mapping(target="value", source = "id")
    ClientsDropDownResponse specifyFieldsToDto(ClientDetails clientDetails);
}

package com.expense.tracker.mapper;

import com.expense.tracker.dto.ExpenseDetailsDto;
import com.expense.tracker.entity.ExpenseDetails;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
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
}

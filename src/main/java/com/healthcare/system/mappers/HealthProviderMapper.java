package com.healthcare.system.mappers;

import com.healthcare.system.dto.HealthProviderDTO;
import com.healthcare.system.entities.HealthProvider;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface HealthProviderMapper {

    HealthProviderMapper INSTANCE = Mappers.getMapper(HealthProviderMapper.class);

    HealthProvider dtoToEntity(HealthProviderDTO healthProviderDTO);

    HealthProviderDTO entityToDto(HealthProvider nurse);
}

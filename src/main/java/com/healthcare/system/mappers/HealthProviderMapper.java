package com.healthcare.system.mappers;

import com.healthcare.system.dto.HealthProviderDTO;
import com.healthcare.system.entities.HealthProvider;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HealthProviderMapper {

    HealthProviderMapper INSTANCE = Mappers.getMapper(HealthProviderMapper.class);

    HealthProvider dtoToEntity(HealthProviderDTO healthProviderDTO);

    HealthProviderDTO entityToDto(HealthProvider nurse);
}

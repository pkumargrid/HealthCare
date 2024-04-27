package com.healthcare.system.mappers;

import com.healthcare.system.dto.NurseDTO;
import com.healthcare.system.entities.Nurse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NurseMapper {

    NurseMapper INSTANCE = Mappers.getMapper(NurseMapper.class);

    Nurse dtoToEntity(NurseDTO reasonDTO);

    NurseDTO entityToDto(Nurse nurse);
}

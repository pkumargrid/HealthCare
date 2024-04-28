package com.healthcare.system.mappers;

import com.healthcare.system.dto.NurseDTO;
import com.healthcare.system.entities.Nurse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NurseMapper {

    NurseMapper INSTANCE = Mappers.getMapper(NurseMapper.class);

    Nurse dtoToEntity(NurseDTO reasonDTO);

    NurseDTO entityToDto(Nurse nurse);
}

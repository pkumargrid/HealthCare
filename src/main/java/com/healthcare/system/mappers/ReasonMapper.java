package com.healthcare.system.mappers;


import com.healthcare.system.dto.ReasonDTO;
import com.healthcare.system.entities.Reason;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReasonMapper {

    ReasonMapper INSTANCE = Mappers.getMapper(ReasonMapper.class);
    @Mapping(target = "doctorId", ignore = true)
    Reason dtoToEntity(ReasonDTO reasonDTO);

    ReasonDTO entityToDto(Reason reason);

}

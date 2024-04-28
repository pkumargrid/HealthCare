package com.healthcare.system.mappers;


import com.healthcare.system.dto.ReasonDTO;
import com.healthcare.system.entities.Reason;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReasonMapper {

    ReasonMapper INSTANCE = Mappers.getMapper(ReasonMapper.class);

    Reason dtoToEntity(ReasonDTO reasonDTO);

    ReasonDTO entityToDto(Reason reason);

}

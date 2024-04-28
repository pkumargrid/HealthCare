package com.healthcare.system.mappers;

import com.healthcare.system.dto.DoctorDTO;
import com.healthcare.system.entities.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    Doctor dtoToEntity(DoctorDTO reasonDTO);

    DoctorDTO entityToDto(Doctor doctor);

}

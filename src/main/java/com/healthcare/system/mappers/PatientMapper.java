package com.healthcare.system.mappers;

import com.healthcare.system.dto.PatientDTO;
import com.healthcare.system.entities.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);
    Patient dtoToEntity(PatientDTO patientDTO);

    PatientDTO entityToDTO(Patient patient);
}

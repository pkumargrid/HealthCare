package com.healthcare.system.mappers;

import com.healthcare.system.dto.AppointmentDTO;
import com.healthcare.system.entities.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AppointmentMapper {
    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);
    Appointment dtoToEntity(AppointmentDTO appointmentDTO);

    AppointmentDTO entityToDTO(Appointment appointment);
}

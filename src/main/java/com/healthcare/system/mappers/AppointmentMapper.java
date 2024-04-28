package com.healthcare.system.mappers;

import com.healthcare.system.dto.AppointmentDTO;
import com.healthcare.system.entities.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);
    Appointment dtoToEntity(AppointmentDTO appointmentDTO);

    AppointmentDTO entityToDTO(Appointment appointment);
}

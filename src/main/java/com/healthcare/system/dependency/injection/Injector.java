package com.healthcare.system.dependency.injection;

import com.healthcare.system.repositories.*;
import com.healthcare.system.services.*;

public class Injector {

    private  static DoctorRepository doctorRepository;

    private static DoctorService doctorService;
    private static ComplaintRepository complaintRepository;

    private static ComplaintService complaintService;

    private HealthProviderRepository healthProviderRepository;
    private HealthProviderService healthProviderService;

    private ReasonRepository reasonRepository;

    private HealthRecordRepository healthRecordRepository;

    private AppointmentRepository appointmentRepository;

    private AppointmentService appointmentService;
    private PatientRepository patientRepository;
    private PatientService patientService;

    public static void inject() {

    }

}

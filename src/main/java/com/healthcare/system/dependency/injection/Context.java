package com.healthcare.system.dependency.injection;

import com.healthcare.system.repositories.*;
import com.healthcare.system.repositories.implementation.*;
import com.healthcare.system.services.*;
import com.healthcare.system.services.implementation.*;

public class Context {

    public  static DoctorRepository doctorRepository;

    public static DoctorService doctorService;
    public static ComplaintRepository complaintRepository;

    public static ComplaintService complaintService;

    public static HealthProviderRepository healthProviderRepository;
    public static HealthProviderService healthProviderService;

    public static ReasonRepository reasonRepository;

    public static HealthRecordRepository healthRecordRepository;
    public static HealthRecordService healthRecordService;

    public static AppointmentRepository appointmentRepository;

    public static AppointmentService appointmentService;
    public static PatientRepository patientRepository;
    public static PatientService patientService;
    public static ReasonService reasonService;

    public static ReportRepository reportRepository;
    public static NurseRepository nurseRepository;
    public static NurseService nurseService;
    public static void inject() {
        doctorRepository = new DoctorRepositoryImpl();
        reasonRepository = new ReasonRepositoryImpl();
        complaintRepository = new ComplaintRepositoryImpl();
        appointmentRepository = new AppointmentRepositoryImpl();
        patientRepository = new PatientRepositoryImpl();
        nurseRepository = new NurseRepositoryImpl();
        healthProviderRepository = new HealthProviderRepositoryImpl();
        reportRepository = new ReportRepositoryImpl();
        healthRecordRepository = new HealthRecordRepositoryImpl();

        doctorService = new DoctorServiceImpl(doctorRepository, nurseRepository, appointmentRepository, healthProviderRepository,
                reasonRepository, patientRepository, healthRecordRepository);
        healthProviderService = new HealthProviderServiceImpl(healthProviderRepository, patientRepository);
        reasonService = new ReasonServiceImpl(reasonRepository);
        complaintService = new ComplaintServiceImpl(complaintRepository);
        appointmentService = new AppointmentServiceImpl(appointmentRepository);
        patientService = new PatientServiceImpl(patientRepository, appointmentRepository, doctorRepository, nurseRepository, complaintRepository, healthProviderRepository);
        nurseService = new NurseServiceImpl(nurseRepository, healthRecordRepository,healthProviderRepository ,reportRepository);
        healthRecordService = new HealthRecordServiceImpl(healthRecordRepository);
    }

}

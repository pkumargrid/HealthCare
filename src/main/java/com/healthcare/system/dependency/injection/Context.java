package com.healthcare.system.dependency.injection;

import com.healthcare.system.repositories.*;
import com.healthcare.system.repositories.implementation.*;
import com.healthcare.system.services.*;
import com.healthcare.system.services.implementation.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

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
    public static DataSource dataSource;
    public static void inject() {
        dataSource = createDataSource();
        reportRepository = new ReportRepositoryImpl(dataSource);
        reasonRepository = new ReasonRepositoryImpl(dataSource, complaintRepository);
        doctorRepository = new DoctorRepositoryImpl(dataSource, healthProviderRepository, complaintRepository, appointmentRepository, patientRepository, reasonRepository);
        complaintRepository = new ComplaintRepositoryImpl(dataSource, patientRepository);
        appointmentRepository = new AppointmentRepositoryImpl(dataSource, doctorRepository, patientRepository);
        patientRepository = new PatientRepositoryImpl(dataSource, healthProviderRepository, complaintRepository, appointmentRepository, nurseRepository, doctorRepository, healthRecordRepository);
        nurseRepository = new NurseRepositoryImpl(dataSource, healthProviderRepository, complaintRepository, patientRepository, reasonRepository, doctorRepository, healthRecordRepository);
        healthProviderRepository = new HealthProviderRepositoryImpl(dataSource, reasonRepository, healthRecordRepository, patientRepository, doctorRepository, appointmentRepository, complaintRepository, nurseRepository);
        healthRecordRepository = new HealthRecordRepositoryImpl(dataSource, healthProviderRepository, doctorRepository, patientRepository, reportRepository);

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

    public static DataSource createDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/prakumar");
        hikariConfig.setUsername("prakumar");
        hikariConfig.setPassword("prakumar");
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        return new HikariDataSource(hikariConfig);
    }

}

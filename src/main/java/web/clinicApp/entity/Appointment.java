package web.clinicApp.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;


public class Appointment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID appointmentId;

    @OneToOne
    @JoinColumn(name = "patientId")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "doctorId")
    private Doctor doctor;



    private LocalDate date;

    public Appointment() {
    }

    public Appointment(Patient patient, Doctor doctor, LocalDate date) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
    }

    public UUID getAppointmentId() {
        return appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

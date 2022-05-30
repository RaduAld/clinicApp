package web.clinicApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.clinicApp.entity.Appointment;
import web.clinicApp.entity.Hour;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    @Query("SELECT a FROM Appointment a WHERE a.patient.user.email = ?1")
    Collection<Appointment> findByPatientEmail(String email);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.user.email = ?1")
    Collection<Appointment> findByDoctorEmail(String email);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.user.email = :email AND a.date = :date")
    Collection<Appointment> findByDoctorEmailAndDate(String email, LocalDate date);


    @Query("SELECT a FROM Appointment a WHERE a.doctor.user.email = :email AND a.date = :date AND a.time = :time")
    Collection<Appointment> findByDoctorEmailAndDateAndTime(String email, LocalDate date, Hour time);

}

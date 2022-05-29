package web.clinicApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.clinicApp.entity.Doctor;
import web.clinicApp.entity.Patient;

import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
}

package web.clinicApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import web.clinicApp.entity.Patient;
import web.clinicApp.entity.User;

import java.util.UUID;

public interface PatientRepository  extends JpaRepository<Patient, UUID> {

    @Transactional
    void deleteByUser(User user);

    Patient findByUser(User user);

}

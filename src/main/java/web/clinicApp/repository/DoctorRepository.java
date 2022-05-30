package web.clinicApp.repository;

import web.clinicApp.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import web.clinicApp.entity.User;

import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {

    @Transactional
    void deleteByUser(User user);

    Doctor findByUser(User user);
}

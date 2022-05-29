package web.clinicApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.clinicApp.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);
}

package web.clinicApp.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID doctorId;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    private Specialty specialty;

    public Doctor(User user, Specialty specialty) {
        this.user = user;
        this.specialty = specialty;
    }

    public Doctor(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return doctorId.equals(doctor.doctorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId);
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }
}

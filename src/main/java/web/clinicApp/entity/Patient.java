package web.clinicApp.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="patients")
public class Patient {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID patientId;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    private LocalDate dob;

    public Patient( User user, LocalDate dob) {
        this.user = user;
        this.dob = dob;
    }

    public Patient(){}

    public UUID getPatientId() {
        return patientId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}

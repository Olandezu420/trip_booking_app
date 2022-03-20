package model;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "agency_profile")
@Getter
@Setter
public class AgencyProfile {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    public AgencyProfile(User user) {
        this.user = user;
    }

    public AgencyProfile() {

    }
}

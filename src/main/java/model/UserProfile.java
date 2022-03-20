package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_profile")
@Getter
@ToString
@Setter
public class UserProfile {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id")
    @ToString.Exclude //Exclude to avoid circular reference
    private User user;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_vacation_package",
            joinColumns = @JoinColumn(name = "user_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "vacation_package_id"))
    private List<VacationPackage> packages;

    public UserProfile(User user) {
        this.user = user;
    }

    public UserProfile() {}
}

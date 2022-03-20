package model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
@ToString
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy ="user", fetch = FetchType.EAGER)
    private UserProfile userProfile;

    @OneToOne(mappedBy ="user", fetch = FetchType.EAGER)
    private AgencyProfile agencyProfile;

    @Column(unique = true, nullable = false)
    @Size(min = 6, max = 16)
    private String username;

    @Column(nullable = false)
    private String password_digest;;

    public User(){}

    public User(String username, String password_digest) {
        this.username = username;
        this.password_digest = password_digest;
    }


}

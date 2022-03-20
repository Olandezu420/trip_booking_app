package model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vacation_package")
@Getter
@Setter
public class VacationPackage {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;

    @ManyToMany(mappedBy = "packages")
    private List<UserProfile> userProfiles;

    @Column
    private String name;

    @Column
    private java.sql.Date start;

    @Column
    private java.sql.Date end;

    @Column
    private Integer price;

    @Column()
    private Integer units;

    @Column
    private String details;

    private String status;

    public VacationPackage(Destination destination, String name, Date start, Date end, Integer price, Integer units, String details) {
        this.destination = destination;
        this.name = name;
        this.start = start;
        this.end = end;
        this.price = price;
        this.units = units;
        this.details = details;
        this.status = "Booked";
    }

    public VacationPackage() {
    }

    public String getStatus() {
        if(getUnits() == null || this.getUnits() == 0)
            return "BOOKED";
        if(this.userProfiles.size() == 0)
            return "NOT BOOKED";
        else
            return "PENDING";
    }

    public void removeUsers() {
        for(UserProfile up: userProfiles)
            up.getPackages().remove(this);
        this.setUserProfiles(new ArrayList<>());
    }


}

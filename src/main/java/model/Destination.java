package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "destination")
@Getter
@Setter
@NoArgsConstructor
public class Destination {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "destination", fetch = FetchType.EAGER)
    private List<VacationPackage> packages;

    public Destination(String name, List<VacationPackage> packages) {
        this.name = name;
        this.packages = packages;
    }

    public Destination(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void removePackages() {
        for(VacationPackage vp: getPackages())
            vp.setDestination(null);
        setPackages(new ArrayList<>());
    }
}

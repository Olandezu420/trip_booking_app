package service;

import lombok.Getter;
import lombok.Setter;
import model.Destination;
import model.VacationPackage;
import repository.DestinationRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Getter
@Setter
public class DestinationService {
    private String removePackagesError;
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");

    public static List<Destination> getAll() {
        return DestinationRepository.findAll();
    }

    public static void addNew(String string) {
        DestinationRepository.create(string);
    }

    public static Destination findBy(String string) {
        return DestinationRepository.findByName(string);
    }

    public void delete(Long id) {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            Destination destination = em.find(Destination.class, id);
            em.remove(destination);
            em.getTransaction().commit();
            em.close();
        } catch(javax.persistence.PersistenceException pe) {
            setRemovePackagesError("Remove the existing packages first!");
        }
    }
}

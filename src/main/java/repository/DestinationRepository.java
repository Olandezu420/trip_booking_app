package repository;

import model.Destination;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class DestinationRepository {
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");

    public static void create(String name) {
        Destination destination = new Destination(name);
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(destination);
        em.getTransaction().commit();
        em.close();
    }

    public static Destination findByName(String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Destination dest = em.createQuery(
                "SELECT d FROM Destination d where d.name LIKE :name", Destination.class)
                .setParameter("name", name).getSingleResult();
        em.close();
        return dest;
    }

    public static List<Destination> findAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Destination> destinationList = em.createQuery(
                        "SELECT d FROM Destination d", Destination.class)
               .getResultList();
        em.close();
        return destinationList;
    }
}

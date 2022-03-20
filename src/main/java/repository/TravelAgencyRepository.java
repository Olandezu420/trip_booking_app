package repository;

import model.AgencyProfile;
import model.User;
import model.UserProfile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TravelAgencyRepository {
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");

    public static void create(String username, String password_digest) {
        User user = new User(username, password_digest);
        AgencyProfile agencyProfile = new AgencyProfile(user);
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.persist(agencyProfile);
        em.getTransaction().commit();
        em.close();
    }
}

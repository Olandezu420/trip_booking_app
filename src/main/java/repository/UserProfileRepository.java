package repository;

import model.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class UserProfileRepository {
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");

    public static void create(String username, String password_digest) {
        User user = new User(username, password_digest);
        UserProfile newUser = new UserProfile(user);
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.persist(newUser);
        em.getTransaction().commit();
        em.close();
    }

    public static UserProfile findById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        UserProfile user = em.find(UserProfile.class, id);
        em.close();
        return user;
    }

    public static void addPackage(Long user_id, Long vacation_id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        VacationPackage vacationPackage = VacationPackageRepository.findById(vacation_id);
        UserProfile userProfile = UserProfileRepository.findById(user_id);
        userProfile.getPackages().add(vacationPackage);
        vacationPackage.setUnits(vacationPackage.getUnits() - 1);
        em.merge(vacationPackage);
        em.merge(userProfile);
        em.getTransaction().commit();
        em.close();
    }

    public static List<VacationPackage> getVacationPackages(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        UserProfile user = em.find(UserProfile.class, id);
        List<VacationPackage> vacationPackages = user.getPackages();
        em.close();
        return vacationPackages;
    }

}

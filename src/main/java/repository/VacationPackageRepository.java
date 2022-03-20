package repository;

import model.Destination;
import model.User;
import model.VacationPackage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.util.List;

public class VacationPackageRepository {
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");

    public static void create(Destination destination, String name, Date start, Date end, Integer price, Integer units, String details){
        VacationPackage package0 = new VacationPackage(destination, name, start, end, price, units, details);
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(package0);
        em.getTransaction().commit();
        em.close();
    }

    public static List<VacationPackage> findAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<VacationPackage> vps = em.createQuery(
                        "SELECT v FROM VacationPackage v", VacationPackage.class)
                .getResultList();
        em.close();
        return vps;
    }

    public static VacationPackage findById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        VacationPackage vacationPackage = em.find(VacationPackage.class, id);
        em.close();
        return vacationPackage;
    }

    public static void delete(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        VacationPackage vacationPackage = em.find(VacationPackage.class, id);

        if(vacationPackage.getUserProfiles().size() != 0) {
            vacationPackage.removeUsers();
            em.merge(vacationPackage);
        }
        em.remove(vacationPackage);
        em.getTransaction().commit();
        em.close();
    }

    public static void edit(VacationPackage vacationPackage) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(vacationPackage);
        em.getTransaction().commit();
        em.close();
    }

    public List<VacationPackage> findByName(String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<VacationPackage> vacationPackages = em.createQuery(
                        "SELECT v FROM VacationPackage v where v.name LIKE :name", VacationPackage.class)
                .setParameter("name", name).getResultList();
        em.close();
        return vacationPackages;
    }

    public List<VacationPackage> findByDestination(String destination) {
        EntityManager  em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<VacationPackage> vacationPackages = em.createQuery(
                "SELECT v FROM VacationPackage v where v.destination.name LIKE :name", VacationPackage.class)
                .setParameter("name", destination).getResultList();
        em.close();
        return vacationPackages;
    }

    public List<VacationPackage> findByPrice(Integer price){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<VacationPackage> vacationPackages = em.createQuery(
                        "SELECT v FROM VacationPackage v where v.price < :price", VacationPackage.class)
                .setParameter("price", price).getResultList();
        em.close();
        return vacationPackages;
    }
}

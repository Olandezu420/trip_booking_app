package service;

import model.Destination;
import model.VacationPackage;
import repository.VacationPackageRepository;

import java.sql.Date;
import java.util.List;

public class VacationPackageService {

    public static void addPackage(Destination destination, String name, Date start, Date end, Integer price, Integer units, String details) {
        VacationPackageRepository.create(destination, name, start, end, price, units, details);
    }

    public static List<VacationPackage> getAll() {
        return VacationPackageRepository.findAll();
    }

    public static void remove(Long id) { VacationPackageRepository.delete(id);}

    public static void edit(VacationPackage vacationPackage) {
        VacationPackageRepository.edit(vacationPackage);
    }

}

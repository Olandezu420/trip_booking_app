package org.example;


import model.*;
import org.mindrot.jbcrypt.BCrypt;
import repository.*;
import service.UserProfileService;
import service.UserService;
import service.VacationPackageService;

import java.sql.Date;


public class Main {

    public static void main(String[] args) {
       //User user = UserRepository.findById(2L);
       //System.out.println(user.getUserProfile().getPackages());
       //VacationPackage vacationPackage = VacationPackageRepository.findById(1L);
       UserProfileRepository.addPackage(1L,1L);


    }
}

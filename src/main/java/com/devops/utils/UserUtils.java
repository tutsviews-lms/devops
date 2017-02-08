package com.devops.utils;

import com.devops.backend.persistence.domain.backend.User;

/**
 * Created by ALadin Zaier PC IBS on 07/02/2017.
 */
public class UserUtils {

    private UserUtils() {
        throw new AssertionError("Non instansiable");
    }


    /**
     * Create a user with basic attributes set
     * @return
     */
    public static User createBasicUser(){

        User user = new User();
        user.setUsername("Aladin");
        user.setPassword("secret");
        user.setEmail("secret@gmail.com");
        user.setFirstName("Aladin");
        user.setLastName("Zaier");
        user.setPhoneNumber("0606060606");
        user.setCountry("FR");
        user.setEnabled(true);
        user.setDescription("A basic user");
        user.setProfileImageUrl("https://blabla.images.com/123");

        return user;
    }
}

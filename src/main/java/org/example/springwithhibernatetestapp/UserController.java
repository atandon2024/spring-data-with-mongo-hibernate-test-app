package org.example.springwithhibernatetestapp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {


    @GetMapping("/users")
    public String getUsers() {
        SessionFactorySingleton sf = new SessionFactorySingleton();
        Session s = sf.getSession();
        s.getTransaction().begin();
        s.createQuery("from User").list().forEach(System.out::println);
        s.getTransaction().commit();
        return s.createQuery("from User").list().toString();
    }

    @PostMapping("/user")
    public User createUser() {
        SessionFactorySingleton sf = new SessionFactorySingleton();

        User u = new User("Ajay");
        Session s = sf.getSession();
        s.getTransaction().begin();
        s.persist(u);
        s.getTransaction().commit();
        System.out.println("User created: " + u);
        return u;
    }
}

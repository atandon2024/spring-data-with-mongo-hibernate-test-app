package org.example.springwithhibernatetestapp;

import org.hibernate.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {


    @GetMapping("/users")
    public List<User> getUsers() {
        Session s = SessionFactorySingleton.getSessionFactory().getCurrentSession();


        s.beginTransaction();
        List<User> users = s.createQuery("from User").list();
        s.getTransaction().commit();
        return users;
    }

    @PostMapping("/user")
    public User createUser() {
        Session s = SessionFactorySingleton.getSessionFactory().getCurrentSession();

        s.beginTransaction();
        User u = new User("test name");
        s.persist(u);
        s.getTransaction().commit();

        return u;
    }
}

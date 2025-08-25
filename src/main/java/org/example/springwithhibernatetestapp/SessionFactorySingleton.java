package org.example.springwithhibernatetestapp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactorySingleton {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private SessionFactorySingleton() {} // private constructor

    private static SessionFactory buildSessionFactory() {
        try {
            SessionFactory f = new Configuration()
                    .addAnnotatedClass(User.class)
                    .configure()
                    .buildSessionFactory();
            f.getStatistics().setStatisticsEnabled(true);
            return f;
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError("Initial SessionFactory failed " + ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }
}

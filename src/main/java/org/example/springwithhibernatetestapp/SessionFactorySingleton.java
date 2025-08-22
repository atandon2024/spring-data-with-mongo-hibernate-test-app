package org.example.springwithhibernatetestapp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactorySingleton {

    private SessionFactory s;

    public SessionFactorySingleton() {
        SessionFactory f = new Configuration()
                .addAnnotatedClass(User.class)
                .configure()
                .buildSessionFactory();

        f.getStatistics().setStatisticsEnabled(true);
        this.s = f;
    }

    public Session getSession() {
        return s.openSession();
    }


}

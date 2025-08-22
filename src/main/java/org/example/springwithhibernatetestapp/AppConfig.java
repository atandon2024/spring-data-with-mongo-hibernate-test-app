package com.example.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Properties;

@Configuration
public class AppConfig {

    // --- REQUIRED: change these to the actual classes from mongo-hibernate JAR ---
    private static final String MONGO_DIALECT =
            "com.mongodb.hibernate.jdbc.MongoConnectionProvider";
    private static final String MONGO_CONNECTION_PROVIDER =
            "com.yourpkg.mongo.MongoConnectionProvider";
    // ---------------------------------------------------------------------------

    // Put your MongoDB URI here (Atlas/local). Replica set required.
    private static final String MONGODB_URI =
            "mongodb://127.0.0.1:27017/mongo-hibernate-test?directConnection=false";

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        var vendor = new HibernateJpaVendorAdapter();
        vendor.setGenerateDdl(false);
        vendor.setShowSql(false);

        var props = new Properties();
        // Point Hibernate at the mongo-hibernate integration points:
        props.setProperty("hibernate.dialect", MONGO_DIALECT);
        props.setProperty("hibernate.connection.provider_class", MONGO_CONNECTION_PROVIDER);

        // mongo-hibernate reads the Mongo connection string from the standard JPA key:
        // (as shown in the repo’s test/README usage)
        props.setProperty("jakarta.persistence.jdbc.url", MONGODB_URI); // REQUIRED

        // Recommended Hibernate knobs for non‑JDBC backends
        props.setProperty("hibernate.hbm2ddl.auto", "none"); // manage schema/indexes yourself
        props.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");

        var emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan("com.example.domain");
        emf.setJpaVendorAdapter(vendor);
        emf.setJpaProperties(props);
        emf.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        // Note: No DataSource: the ConnectionProvider supplies "connections".
        return emf;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
package org.example.hibernate;

import lombok.Getter;
import org.example.entity.Client;
import org.example.entity.Planet;
import org.example.entity.Ticket;
import org.example.prop.PropertyReader;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {
//    @Getter
    private static final HibernateUtil INSTANCE;
    private static final Logger log = LoggerFactory.getLogger(HibernateUtil.class);

    static { INSTANCE = new HibernateUtil(); }
    @Getter
    private SessionFactory sessionFactory;
//    public SessionFactory getSessionFactory() { return sessionFactory; }
    public static Logger getLogger() { return log; }
    private HibernateUtil() {
        this.sessionFactory = new Configuration()
                .addAnnotatedClass(Client.class)
                .addAnnotatedClass(Planet.class)
                .addAnnotatedClass(Ticket.class)
                .buildSessionFactory();
        flywayMigration(PropertyReader.getUrlConnectionPostgres(),
                PropertyReader.getUserConnectionPostgres(),
                PropertyReader.getPswConnectionPostgres());
    }
    private void flywayMigration(String connectionUrl, String username, String password) {
        Flyway flyway = Flyway.configure().dataSource(connectionUrl, username, password).load();
        flyway.migrate();
    }
    public static HibernateUtil getInstance() { return INSTANCE; }
    public void close() {
        sessionFactory.close();
    }
}

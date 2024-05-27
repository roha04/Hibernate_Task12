package org.example.service;

import org.example.dao.PlanetDao;
import org.example.entity.Client;
import org.example.entity.Planet;
import org.example.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Objects;

public class PlanetCrudService implements PlanetDao {
    private static final String SUCCESSFULLY_MESSAGE = "Planet by name '{}' successfully was {}";
    @Override
    public boolean insertPlanet(Planet planet) {
        boolean result = false;
        if (planet == null)
            return false;
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(planet);
                transaction.commit();
                result = true;
                HibernateUtil.getLogger().info(SUCCESSFULLY_MESSAGE, planet.getName(), "inserted");
            } catch(Exception e) {
                HibernateUtil.getLogger().error(e.getMessage());
                transaction.rollback();
            }
        }
        return result;
    }
    @Override
    public boolean updatePlanet(Planet planet) {
        boolean result = false;
        if (Objects.isNull(planet.getId())) {
            return false;
        }
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(planet);
                transaction.commit();
                result = true;
                HibernateUtil.getLogger().info(SUCCESSFULLY_MESSAGE, planet.getName(), "updated");
            } catch(Exception e) {
                HibernateUtil.getLogger().error(e.getMessage());
                transaction.rollback();
            }
        }
        return result;
    }
    @Override
    public Planet getPlanetById(String pid) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.get(Planet.class, pid);
        }
    }
    @Override
    public List<Planet> getPlanetsByName(String name) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Planet WHERE name LIKE :planetname", Planet.class);
            query.setParameter("planetname", "%"+name+"%");
            return query.list();
        }
    }
    @Override
    public List<Planet> getPlanets() {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery("from Planet", Planet.class).list();
        }
    }
    @Override
    public void deletePlanetById(String pid) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Planet planet = session.get(Planet.class, pid);
            String name = planet.getName();
            session.remove(planet);
            transaction.commit();
            HibernateUtil.getLogger().info(SUCCESSFULLY_MESSAGE, name, "deleted");
        }
    }
    @Override
    public void deletePlanet(Planet planet) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(planet);
            transaction.commit();
        }
    }
}

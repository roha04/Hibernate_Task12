package org.example.service;

import org.example.dao.TicketDao;
import org.example.entity.Planet;
import org.example.entity.Ticket;
import org.example.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Objects;

public class TicketCrudService implements TicketDao {
    private static final String SUCCESSFULLY_MESSAGE = "Ticket number '{}' successfully was {}";
    @Override
    public Long insertTicket(Ticket ticket) {
        Long result = 0L;
        if (ticket == null)
            return result;
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(ticket);
                transaction.commit();
                result = ticket.getId();
                HibernateUtil.getLogger().info(SUCCESSFULLY_MESSAGE, result, "inserted");
            } catch(Exception e) {
                HibernateUtil.getLogger().error(e.getMessage());
                transaction.rollback();
            }
        }
        return result;
    }

    @Override
    public boolean updateTicket(Ticket ticket) {
        boolean result = false;
        if (Objects.isNull(ticket.getId())) {
            return false;
        }
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(ticket);
                transaction.commit();
                result = true;
                HibernateUtil.getLogger().info(SUCCESSFULLY_MESSAGE, ticket.getId(), "updated");
            } catch(Exception e) {
                HibernateUtil.getLogger().error(e.getMessage());
                transaction.rollback();
            }
        }
        return result;
    }

    @Override
    public Ticket getTicketById(long id, boolean withClient, boolean withPlanet) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Ticket ticket = session.get(Ticket.class, id);
            if (withClient) {
                ticket.getClient();
            }
            if (withPlanet) {
                ticket.getPlanetFrom();
                ticket.getPlanetTo();
            }
            return ticket;
        }
    }

    @Override
    public List<Ticket> getTickets() {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery("from Ticket", Ticket.class).list();
        }
    }

    @Override
    public void deleteTicketById(long id) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Ticket ticket = session.get(Ticket.class, id);
            session.remove(ticket);
            transaction.commit();
            HibernateUtil.getLogger().info(SUCCESSFULLY_MESSAGE, id, "deleted");
        }
    }

    @Override
    public void deleteTicket(Ticket ticket) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(ticket);
            transaction.commit();
            HibernateUtil.getLogger().info(SUCCESSFULLY_MESSAGE, ticket.getId(), "deleted");
        }
    }
}

package org.example.service;

import org.example.dao.ClientDao;
import org.example.entity.Client;
import org.example.entity.Ticket;
import org.example.hibernate.HibernateUtil;
import org.example.validator.ClientValidator;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Objects;

public class ClientCrudService implements ClientDao {
    @Override
    public boolean insertClient(Client client) {
        boolean result = false;
        if (client == null)
            return false;
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                client.setId(null);
                session.persist(client);
                transaction.commit();
                result = true;
            } catch(Exception e) {
                HibernateUtil.getLogger().error(e.getMessage());
                transaction.rollback();
            }
        }
        return result;
    }
    @Override
    public boolean updateClient(Client client) {
        boolean result = false;
        if (Objects.isNull(client.getId())) {
            return false;
        }
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(client);
                transaction.commit();
                result = true;
            } catch(Exception e) {
                HibernateUtil.getLogger().error(e.getMessage());
                transaction.rollback();
            }
        }
        return result;
    }
    @Override
    public Client getClientById(long id, boolean withTickets) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Client client = session.get(Client.class, id);
            // TODO: for lazy loading. Initiate get tickets
            if (withTickets) {
                client.getTickets().forEach(Ticket::getId);
            }
            return client;
        }
    }
    @Override
    public List<Client> getClientsByName(String name) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Client WHERE name LIKE :clientname", Client.class);
            query.setParameter("clientname", "%"+name+"%");
            return query.list();
        }
    }
    @Override
    public List<Client> getClients() {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery("FROM Client", Client.class).list();
        }
    }
    @Override
    public void deleteClientById(long id) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Client client = session.get(Client.class, id);
            String name = client.getName();
            session.remove(client);
            transaction.commit();
            HibernateUtil.getLogger().info("Client by name {} successfully was deleted", name);
        }
    }
    @Override
    public void deleteClient(Client client) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(client);
            transaction.commit();
        }
    }
}

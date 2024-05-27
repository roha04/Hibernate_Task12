package org.example.dao;

import org.example.entity.Client;

import java.util.List;

public interface ClientDao {
    boolean insertClient(Client client);
    boolean updateClient(Client client);
    Client getClientById(long id, boolean withTickets);
    List<Client> getClientsByName(String name);
    List<Client> getClients();
    void deleteClientById(long id);
    void deleteClient(Client client);
}

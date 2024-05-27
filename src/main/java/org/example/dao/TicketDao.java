package org.example.dao;

import org.example.entity.Client;
import org.example.entity.Ticket;

import java.util.List;

public interface TicketDao {
    Long insertTicket(Ticket ticket);
    boolean updateTicket(Ticket ticket);
    Ticket getTicketById(long id, boolean withClient, boolean withPlanet);
    List<Ticket> getTickets();
    void deleteTicketById(long id);
    void deleteTicket(Ticket ticket);
}

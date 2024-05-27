package org.example.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn (name = "from_planet_id", nullable = false)
    private Planet planetFrom;

    @ManyToOne
    @JoinColumn (name = "to_planet_id", nullable = false)
    private Planet planetTo;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "id:" + id + ", created:'" + createdAt + "'";
    }
}

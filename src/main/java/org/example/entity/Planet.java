package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table (name = "planet")
public class Planet {
    @Id
    @Pattern(regexp = "^[A-Z]*[0-9]*$", message = "Planet.id {} can be only in upper case and digital characters.")
    private String id;

    @Column (name="name", nullable = false, unique=true)
    @Size(min = 1, max = 500, message = "Planet.name {} must be between 1 and 500 characters")
    private String name;

    @OneToMany(mappedBy = "planetTo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ticket> ticketsTo = new HashSet<>();

    @OneToMany(mappedBy = "planetFrom", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ticket> ticketsFrom = new HashSet<>();

    @Override
    public String toString() {
        return "id:" + id + ", name:'" + name + "'";
    }
}

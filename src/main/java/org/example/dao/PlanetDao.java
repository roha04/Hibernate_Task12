package org.example.dao;

import org.example.entity.Client;
import org.example.entity.Planet;

import java.util.List;

public interface PlanetDao {
    boolean insertPlanet(Planet planet);
    boolean updatePlanet(Planet planet);
    Planet getPlanetById(String pid);
    List<Planet> getPlanetsByName(String name);
    List<Planet> getPlanets();
    void deletePlanetById(String pid);
    void deletePlanet(Planet planet);
}

package engine.data;


import engine.mobile.Orbit;
import engine.mobile.Planet;

import java.util.HashMap;

/**
 * <p>This class represents the singleton instance of the solar system.</p>
 * <p>It contains planets and their orbits and is created only once during the simulation.</p>
 * @version 2.0
 * @since 2026-02-12
 */
public class SolarSystem  {
    /**
    * A group of planets
    */
    private HashMap<String, Planet> planets=new HashMap<>();
    /**
    * A group of orbits related to planets
    */
    private HashMap<String, Orbit> orbits=new HashMap<>();

    private SolarSystem() {
    }
    /**
	 * The unique instance of the class prepared in an eager way
	 */
  
    private static final SolarSystem instance = new SolarSystem();

    /**
    *Static method allows to get the unique instance of the class.
    */
    public static SolarSystem getInstance() {
        return instance;
    }

    /**
     * To register a planet in the solar system with her nam as a key.
     */
    public void register(String name, Planet planet) {
        planets.put(name, planet);
    }
    /**
     * To register an orbit in the solar system .
     */
    public void register(String name, Orbit orbit) {
        orbits.put(name, orbit);
    }


    //GETTERS/SETTERS
    public HashMap<String, Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(HashMap<String, Planet> planets) {
        this.planets = planets;
    }

    public HashMap<String, Orbit> getOrbits() {
        return orbits;
    }
    public void setOrbits(HashMap<String, Orbit> orbits) {
        this.orbits = orbits;
    }
    public void reset() {
        planets.clear();
        orbits.clear();
    }

}

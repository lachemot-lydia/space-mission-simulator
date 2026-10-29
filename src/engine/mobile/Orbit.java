package engine.mobile;

import engine.data.Position;

/**
 * <p>This class defines the height of the orbit of a certain planet, and also it's position in the solar system</p>
 * @version 2.0
 * @since 2026-02-12
 */
public class Orbit {
    /**
     * The name of the orbit
     */
    private String OrbitName;
    /**
    * The planet where the orbit is around
    */
    private Planet centralPlanet;
    /**
    * The height of the orbit from the planet 
    */
    private double altitude;
    /**
    * The position of the orbit in the page 
    */

    private Position position;

    public Orbit(Planet centralPlanet, double altitude, Position position, String OrbitName) {
        this.centralPlanet = centralPlanet;
        this.altitude = altitude;
        this.position = position;
        this.OrbitName = OrbitName;
    }

    //Getters/SETTERS
    public Planet getCentralPlanet() {
        return centralPlanet;
    }
    public void setCentralPlanet(Planet centralPlanet) {
        this.centralPlanet = centralPlanet;
    }
    public double getAltitude() {
        return altitude;
    }
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    public String getOrbitName() {
        return OrbitName;
    }
    public void setOrbitName(String OrbitName) {
        this.OrbitName = OrbitName;
    }



}

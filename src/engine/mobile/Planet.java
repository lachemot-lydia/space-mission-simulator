package engine.mobile;

import engine.data.Position;
import engine.data.VectorMouvement;

/**
 *
 * It is a daughter class that extends from {@link MobileElement}
 * This class define the planet's name,it's radius from the center,
 * its gravity and the distance from the sun.
 * 
 * @version 2.0
 * @since 02-12-2026
 */
public class Planet extends MobileElement {
    /**
    * The name of the planet
    */
    private String planetName;
    /**
    * The radius of the planet from its center 
    */
    private double radius;
    /**
    * The gravitationnal force of the planet
    */
    private double gravity;



    public Planet(String planetName, double mass, double radius, double gravity, Position position,VectorMouvement vectorMouvement) {
        super(mass,position,vectorMouvement);
        this.planetName = planetName;
        this.radius = radius;
        this.gravity = gravity;


    }

    //GETTERS/SETTERS
    public String getPlanetName() {
        return planetName;
    }
    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }
    public double getRadius() {
        return radius;
    }
    public void setRadius(double radius) {
        this.radius = radius;
    }
    public double getGravity() {
        return gravity;
    }
    public void setGravity(double gravity) {
        this.gravity = gravity;
    }


}

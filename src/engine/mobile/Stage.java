package engine.mobile;

import engine.data.Position;
import engine.data.Propulsion;
import engine.data.VectorMouvement;


/**
 *
 * It is a daughter class that extends from {@link MobileElement}
 * This class defines the stages of a {@link Rocket}
 * Each stage got its own rocket engine, its own
 * @version 2.0
 * @date 02-12-2026
 */
public class Stage extends MobileElement {
	/**
	 * The number of the stage
	 */
	private int id;

    /**
     * 
     */
	private Propulsion propulsion;
    
    private double altitude;

	/**
     * the altitude when the stage have to be separated 
     */
    private double separationAltitude;
    
    /**
     * the state of the stage 
     */
    private boolean isSeparated=false;
    
    private double mass;
    /**
     * The mass of the stage without propellant
     */
    private double dryMass;
    
    private double height;
    
    private double angle;

	public Stage(int id ,double dryMass,double mass,Position position,Propulsion propulsion,
    		double altitude,VectorMouvement vectorMouvement,double separationAltitude,double height ) {
        super(mass,position,vectorMouvement);
        this.id=id;
        this.altitude=altitude;
        this.propulsion=propulsion;
        this.isSeparated=false;
        this.separationAltitude=separationAltitude;
        this.mass=mass;
        this.height=height;
        this.dryMass=dryMass;

    }


    //GETTERS/SETTERS*******************************************************
    
    public double getHeight() {
		return height;
	}


	public void setHeight(double height) {
		this.height = height;
	}


	public double getAltitude() {
        return altitude;
    }
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
    
    public boolean getIsSeparated() {
    	return this.isSeparated;
    }
    public void setIsSeparated(boolean isSeparated) {
    	this.isSeparated=isSeparated;
    }
    public double getDryMass() {
    	return this.dryMass;
    }
    public void setDryMass(double dryMass) {
    	this.dryMass=dryMass;
    }
    public void setID(int id) {
    	this.id=id;
    }
    public int getID() {
    	return this.id;
    }
    public void setSeparationAltitude(double separationAltitude) {
    	this.separationAltitude=separationAltitude;
    }
    public double getSeparationAltitude() {
    	return this.separationAltitude;
    }

    public Propulsion getPropulsion() {
		return propulsion;
	}


	public void setPropulsion(Propulsion propulsion) {
		this.propulsion = propulsion;
	}

    public double getMass() {
		return mass;
	}


	public void setMass(double mass) {
		this.mass = mass;
	}



}

package engine.mobile;

import engine.data.Position;
import engine.data.Trajectory;
import engine.data.VectorMouvement;

/**
 *
 * It is a daughter class that extends from {@link MobileElement}
 * This class defines an artificial satellite,
 * Which is located to a specific altitude from earth.
 * 
 * @version 2.0
 * @since 02-12-2026
 */
public class Satellite extends MobileElement {
    /**
    * The height where the satellite is located from hearth
    */
    private double orbitAltitude;
    /**
     * If the satellite is in Orbit
     */
    private boolean isInOrbit;
    /**
     * for the GEO mission
     */
    private boolean isInOrbitLEO;
    
    

	private Trajectory trajectory;
    
    private double altitude;
    
  
	

	public Satellite(double mass, Position position, double orbitAltitude,VectorMouvement vectorMouvement) {
        super(mass, position, vectorMouvement);
        this.orbitAltitude=orbitAltitude;
        isInOrbit=false;
        isInOrbitLEO=false;

    }

    //GETTERS/SETTERS****************************************************************
    public double getOrbitAltitude() {

        return orbitAltitude;
    }
    public void setOrbitAltitude(double orbitAltitude) {

        this.orbitAltitude=orbitAltitude;
    }
    
    public boolean isInOrbit() {
		return isInOrbit;
	}

	public void setInOrbit(boolean isInOrbit) {
		this.isInOrbit = isInOrbit;
	}

	public Trajectory getTrajectory() {
		return trajectory;
	}

	public void setTrajectory(Trajectory trajectory) {
		this.trajectory = trajectory;
	}
	
	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	public boolean isInOrbitLEO() {
		return isInOrbitLEO;
	}

	public void setInOrbitLEO(boolean isInOrbitLEO) {
		this.isInOrbitLEO = isInOrbitLEO;
	}
}

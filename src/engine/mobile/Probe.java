package engine.mobile;
import engine.data.Position;
import engine.data.Trajectory;
import engine.data.VectorMouvement;

/**
 *
 * It is a daughter class that extends from {@link MobileElement}
 * This class defines a space probe wich got a specific planet to orbit.
 * 
 * @version 2.0
 * @since 02-12-2026
 */
public class Probe extends MobileElement {
    /**
    * The planet where the probe goes
    */
    private Planet targetPlanet;
    
    private Orbit targetOrbit;
    
	
	
	private Trajectory trajectory;
	
	private boolean inOrbit;
	
	private boolean inOrbitLEO;
	
	private boolean isTrans;
	private boolean landed=false;

	
    
	
	public Probe(double mass,Position position,VectorMouvement vectorMouvement) {
        super(mass,position,vectorMouvement);
        inOrbit=false;
        inOrbitLEO=false;
        isTrans=false;
    }
   
    //GETTERS/SETTERS*********************************************
    public Planet getTargetPlanet() {
        return targetPlanet;
    }
    public void setTargetPlanet(Planet targetPlanet) {
        this.targetPlanet = targetPlanet;
    }
    public Trajectory getTrajectory() {
		return trajectory;
	}
	public void setTrajectory(Trajectory trajectory) {
		this.trajectory = trajectory;
	}
	
	public boolean isInOrbit() {
		return inOrbit;
	}
	public void setInOrbit(boolean inOrbit) {
		this.inOrbit = inOrbit;
	}
	
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	public boolean isInOrbitLEO() {
		return inOrbitLEO;
	}
	public void setInOrbitLEO(boolean inOrbitLEO) {
		this.inOrbitLEO = inOrbitLEO;
	}
	private double altitude ;
	public boolean isTrans() {
		return isTrans;
	}

	public void setTrans(boolean isTrans) {
		this.isTrans = isTrans;
	}
	 public boolean isLanded() {
	    	return landed;
	    }

	   public void setLanded(boolean landed) {
	    	this.landed = landed;
	    }
	   public Orbit getTargetOrbit() {
			return targetOrbit;
		}

		public void setTargetOrbit(Orbit targetOrbit) {
			this.targetOrbit = targetOrbit;
		}
	
}

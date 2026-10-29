package engine.data;

/**
 * <p>This class describes an engine of a rocket,</p>
 * <p>It is defined by its thrust, its specific impulse and its mass</p>
 * @version 2.0
 * @date 02-12-2026
 */

public class RocketEngine  {
    /**
    * A force that push the rocket
    */
    private double thrust;
    /**
    * It represents the amount of thrust produced per unit of propellant flow rate.
    */
    private double specificImpulse;
    /**
    * the mass of the engine
    */
    private double mass;
    /**
     * the estate of the engine 
     */
    private boolean inFire;
    /**
     * engine power
     */
    private double throttle;

    public RocketEngine(double thrust, double specificImpulse, double mass) {
        this.thrust = thrust;
        this.specificImpulse = specificImpulse;
        this.mass = mass;
        this.inFire=false;
        this.throttle=1.0;
    }

    //GETTERS/SETTERS
    public double getThrust() {
        return thrust;
    }
    public void setThrust(double thrust) {
        this.thrust = thrust;
    }
    public double getSpecificImpulse() {
        return specificImpulse;
    }
    public void setSpecificImpulse(double specificImpulse) {
        this.specificImpulse = specificImpulse;
    }
    public double getMass() {
    	return mass;
    }
    public void setMass(double mass) {
    	this.mass=mass;
    }
    public void setInFire(boolean inFire) {
    	this.inFire=inFire;
    }
    public boolean getInFire() {
    	return this.inFire;
    }
    public void setThrottle(double throttle) {
    	this.throttle=throttle;
    }
    public double getThrottle() {
    	return this.throttle;
    }
    


}

package engine.data;
/**
 *  This class represents the propulsion system of a rocket
 *  combining a rocket engine and its associated propellant tank
 *@version 2.0
 *@since 2026-03-15
 */
public class Propulsion {
	/**
	 * 
	 */
	private RocketEngine rocketEngine;
	/**
	 * 
	 */

	private TankPropellant tankPropellant;

	public Propulsion(RocketEngine rocketEngine,TankPropellant tankPropellant) {
		this.rocketEngine=rocketEngine;
		this.tankPropellant=tankPropellant;
	}

	//GETTERS/SETTERS
	public RocketEngine getRocketEngine() {
		return rocketEngine;
	}

	public void setRocketEngine(RocketEngine rocketEngine) {
		this.rocketEngine = rocketEngine;
	}

	public TankPropellant getTankPropellant() {
		return tankPropellant;
	}

	public void setTankPropellant(TankPropellant tankPropellant) {
		this.tankPropellant = tankPropellant;
	}
	
	
	
}
	

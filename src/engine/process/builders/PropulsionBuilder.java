package engine.process.builders;

import engine.data.Propulsion;
import engine.data.RocketEngine;
import engine.data.TankPropellant;

/**
 * This class centralizes the creation of propulsion systems by 
 * assembling a rocket engine and its associated propellant tank
 *@version 2.0
 *@since 2026-03-15
 */
public class PropulsionBuilder {
	
	public PropulsionBuilder(){
		
	}
	
	public static Propulsion buildPropulsion(double thrust,double propellant,double isp,double engineMass) {
		RocketEngine rocketEngine=new RocketEngine(thrust, isp, engineMass);
	
		TankPropellant tankPropellant=new TankPropellant(propellant);
		
		return new Propulsion(rocketEngine, tankPropellant);
	
	}
}

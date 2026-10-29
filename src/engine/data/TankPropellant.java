package engine.data;


/**
 * <p>This class describes a propellant tank in a rocket,</p>
 * <p>It is defined by its density and its mass</p>
 * @version 2.0
 * @date 02-12-2026
 */
public class TankPropellant {
    /**
    * The density of the propellant in the tank
    */
    private double density;
    /**
    * the mass of the propellant, it varies depending on the engine
    */
    private double propellantMass;
    
    public TankPropellant(double propellantMass) {
        this.propellantMass=propellantMass;
    }

    //GETTERS/SETTERS
    public double getPropellantMass() {
		return propellantMass;
	}
    public void setPropellantMass(double propellantMass) {
		this.propellantMass=propellantMass;
	}
    @Override
    public String toString() {
        return "The mass of the Propellant: "+this.propellantMass+"\nThe density of the Propellant: "+this.density;
    }
}

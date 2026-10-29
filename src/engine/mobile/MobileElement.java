package engine.mobile;

import engine.data.Position;
import engine.data.VectorMouvement;

/**
 *
 * This is a parent class for moving objects,
 * It is defined by its mass and its position.
 * @version 2.0
 * @date 02-12-2026
 */
public abstract class MobileElement {
    /**
    * The mass of the object 
    */
    private double mass;
    /**
    * The position of the object
    */
    private Position currentPosition;
    
    /**
     * The two vectors of the object
     */
    private VectorMouvement vectorMouvement;



    public MobileElement(double mass, Position position,VectorMouvement vectorMouvement) {
        this.mass = mass;
        this.currentPosition = position;
        this.vectorMouvement=vectorMouvement;
    }

    //SETTERS/GETTERS
    public double getMass() {
        return mass;
    }
    public void setMass(double mass) {
        this.mass = mass;
    }
    public Position getCurrentPosition() {
        return currentPosition;
    }
    public void setCurrentPosition(Position position) {
        this.currentPosition = position;
    }
    public VectorMouvement getVectorMouvement(){
    	return this.vectorMouvement;
    }
    public void setVectorMouvement(VectorMouvement vectorMouvement) {
    	this.vectorMouvement=vectorMouvement;
    }
}
 

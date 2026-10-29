package engine.data;

/**
 * This class contains two mouvement vectors for 
 * acceleration and velocity of each mobile
 * @version 2.0
 * @since 2026-03-15
 */
public class VectorMouvement {
	 /**
     * Horizontal velocity in meters per second.
     * */
    private double vx;

    /**
     * Vertical velocity in meters per second.
     * */
    private double vy;

    /**
     * Horizontal acceleration in meters per second squared.
     * */
    private double ax;

    /**
     * Vertical acceleration in meters per second squared.
     * */
    private double ay;
    
    public VectorMouvement(double ax,double ay,double vx,double vy) {
    	this.ax=ax;
    	this.ay=ay;
    	this.vx=vx;
    	this.vy=vy;
    }
    
    //GETTERS/SETTERS
    public double getVx() {
        return vx;
    }
    public void setVx(double vx) {
        this.vx = vx;
    }
    public double getVy() {
        return vy;
    }
    public void setVy(double vy) {
        this.vy = vy;
    }
    public double getAx() {
        return ax;
    }
    public void setAx(double ax) {
        this.ax = ax;
    }
    public double getAy() {
        return ay;
    }
    public void setAy(double ay) {
        this.ay = ay;
    }

    @Override
    public String toString() {
        return "a=(" + this.ax + ", " + this.ay + ")"+"\n"+"v=(" + this.vx + ", " + this.vy + ")";
    }
}

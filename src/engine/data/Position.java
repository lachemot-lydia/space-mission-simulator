package engine.data;

/**
 * <p>This class describes the position of an object in the graphic page</p>
 * @version 2.0
 * @since 2026-02-12
 */
public class Position {
    /**
    * coordonate vertical of the page
    */
    private double x;
    /**
    * coordonate horizontal of the page
    */
    private double y;

    

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
       
    }

    //SETTERS/GETTERS
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
   
    @Override
    public String toString() {
        return "Position: (" + this.x + ", " + this.y + ")";
    }
}

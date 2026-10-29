package config;
/**
 * This class contains the physics constants used for the 
 * rocket's trajectory calculation 
 * @version 2.0
 * @since 2026-03-15
 */
public class PhysicsConstants {
	
	 public static final double GRAVITATIONAL_CONSTANT = 6.67430 * Math.pow(10, -11);
	 
	 public static final double AIR_DENSITY=1.225;
	 
	 public static final double DRAG_COEFFICIENT=0.3;
	 
	 public static final double FRONTAL_AREA1=10;//(m^2)	 
	 
	 public static final double EARTH_ANGULAR_VELOCITY=7.2921*Math.pow(10, -5);
	
	 public static final double DELTA_TIME=1;//(s)
	 
	 public static final double KICK_Altitude=39000;
	//Kennedy Space Center
	 public static final Float spaceCenter_Angle=(float)28.586 ;//28.586°
	    
	 
	 public static final double spaceCenter_Altitude=3;//(m)
	 
	 public static final int FRAME_BEFORE_TLI=3500;
	 public static final int FRAME_BEFORE_LANDING=7000;
	    
	    
	//ORBITS ALTITUDE
	    public static final double ORBIT_LEO_ALTITUDE=200000;//(m)
	    public static final double ORBIT_GEO_ALTITUDE=35786000;//(m)
	    public static final double ORBIT_LUNAR_ALTITUDE=1000000;//(m)
	    public static final double ORBIT_MERCURY_ALTITUDE=200000;//(m)
	    public static final double ORBIT_JUPITER_ALTITUDE=5000000;//(m)
	    public static final double ORBIT_MARS_ALTITUDE=17000000;

	public static final int FRAME_BEFORE_GEO = 0;
}

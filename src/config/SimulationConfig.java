package config;

import java.awt.*;

import engine.data.SolarSystem;
import engine.mobile.Planet;

/**
 * <p>This class contains constants used especially for the graphical interface,</p>
 * <p>It sets the color of objects and the dimensions of the graphic page</p>
 *
 * @version 2.0
 * @since 2026-02-12
 */
public class SimulationConfig {
	//DIMENSION
    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1080;
    
    //DAHSBORD OF THE SIMULATION
    public static final double DASHBOARD_WIDTH=1528;
    public static final double DASHBOARD_HEIGHT=964;
    
    public static double dASHBOARD_CENTERX=DASHBOARD_WIDTH/2;
    public static  double dASHBOARD_CENTERY=DASHBOARD_HEIGHT/2;
    
    
    public static  double INITIAL_REFERENCE_X=4.0E7;
    public static  double INITIAL_REFERENCE_Y=2.5E7;
    
    public static double SCALE = 1.0/30000.0; 
    public static double scale_ROCKET=3000;//to see the rocket
    public static double scale_SATELLITE=450;
    public static double scale_MOON=1;
    public static double scale_Jupiter=1;
    public static double scale_Mercury=1;
    
    public static boolean follow=false;
    
    public static final double DEFAULT_SIMULATION_SPEED=100;
    
    public static final double MIN_SIMULATION_SPEED=0.005;
    public static final double MAX_SIMULATION_SPEED=51200;
    public static double SIMULATION_SPEED=DEFAULT_SIMULATION_SPEED;

    public static final int COUNTDOWN_LAUNCH=5; //(s)

    
    //STYLE
    public static final  Font LABEL_FONT = new Font(Font.MONOSPACED, Font.BOLD, 25);
    public static final Color LABEL_COLOR_FORE=Color.white;
    
    public static final  Font BUTTON_FONT = new Font(Font.DIALOG, Font.BOLD, 20);
    public static final Color BUTTON_COLOR_BACK = Color.decode("#011307");
    public static final Color BUTTON_COLOR_FORE = Color.WHITE;
   
    public static final Color TEXTFIELD_COLOR_BACK=Color.white;
    public static final Font TEXTFIELD_FONT=new Font(Font.MONOSPACED,Font.BOLD,20);
    public static final Color PANEL_COLOR=Color.lightGray;



}

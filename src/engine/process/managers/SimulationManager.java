package engine.process.managers;

import config.EngineConfig;
import config.PhysicsConstants;
import config.SimulationConfig;
import engine.data.Mission;
import engine.data.Propulsion;
import engine.data.SolarSystem;
import engine.process.builders.MissionBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *<p>This class controls the all simulation, it contains both mobiles manager
 *      and the solar system </p>
 * @version 2.0
 * @since 02-12-2026
 */
public class SimulationManager {


    private SolarSystem solarSystem;
    private MobileInterface mobileManager;
    private Mission mission;
    private ArrayList<Propulsion> propulsions;

    public SimulationManager(SolarSystem solarSystem, MobileInterface mobileManager,Mission mission,ArrayList<Propulsion> propulsions) {
        this.solarSystem = solarSystem;
        this.mobileManager = mobileManager;
        this.propulsions=propulsions;
        this.mission=mission;
        
        
    }

    /**
     * Advances the entire simulation by one frame.
     */
   public void nextRound(){
	   if(mission.getMissionType().equals(EngineConfig.LEOName)) {
		   mobileManager.nextRoundLEO();
	   }
	   else if(mission.getMissionType().equals(EngineConfig.GEOName)) {
		   mobileManager.nextRoundGEO();
	   }else if(mission.getMissionType().equals(EngineConfig.InterplaneteryName)) {
		   mobileManager.nextRoundInterplanatery();
	   }

       mission.setDuration(mission.getDuration()+(PhysicsConstants.DELTA_TIME/86400.0));
     
   }

    /**
     *Loads an image from the given file path.
     *
     * @param filePath the path of the image
     * @return
     */
    public static Image readImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            System.err.println("-- Can not read the image file ! --");
            return null;
        }
    }

    //GETTERS/SETTERS
    public SolarSystem getSolarSystem() {
        return solarSystem;
    }
    public MobileInterface getMobileManager() {
        return mobileManager;
    }
    public Mission getMission() {
        return mission;
    }
    public void setMission(Mission mission) {
        this.mission = mission;
    }



}

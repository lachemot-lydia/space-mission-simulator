package engine.process.builders;


import java.util.ArrayList;

import org.apache.log4j.Logger;

import config.EngineConfig;
import engine.data.Mission;
import engine.data.Propulsion;
import engine.data.SolarSystem;
import engine.mobile.*;
import engine.process.managers.MobileElementManager;
import engine.process.managers.SimulationManager;
import log.LoggerUtility;


 /**
 *<p>This class builds the entire simulation environment.</p>
 *<p>It creates the solar system, initializes the mobile element manager</p>
 *<p>and places the mobile elements inside it.</p>
 * @version 2.0
 * @since 02-12-2026
 * */
public class SimulationBuilder {
	
    public SimulationBuilder(){

    }
    
    
    public static SimulationManager buildSimulation(Mission mission,ArrayList<Propulsion> propulsions)  {

        SolarSystem solarSystem = SolarSystemBuilder.buildSolarSystem();
        MobileElementManager mobileManager = new MobileElementManager();
        initializeMobiles(solarSystem,mobileManager,mission,propulsions);
        SimulationManager simulationManager=new SimulationManager(solarSystem,mobileManager,mission,propulsions);
        return simulationManager;

    }

    public static void initializeMobiles(SolarSystem solarSystem,MobileElementManager mobileManager,Mission mission,ArrayList<Propulsion> propulsions) {
       
    	mobileManager.setMission(mission);
        mobileManager.setPropulsion(propulsions);
        int rocketType=0;
    	if(mission.getMissionType().equals(EngineConfig.LEOName) ) {
    		rocketType=1;
    		EngineConfig.isLEO = true;
    		EngineConfig.isGEO = false;
    		EngineConfig.isInterplanetary = false;
    	}else if(mission.getMissionType().equals(EngineConfig.GEOName)) {
    		rocketType=1;
    		EngineConfig.isLEO = false;
    		EngineConfig.isGEO = true;
    		EngineConfig.isInterplanetary = false;
    	}
    	
    	else if(mission.getMissionType().equals(EngineConfig.InterplaneteryName)) {
    		rocketType=2;
    		EngineConfig.isLEO = false;
    		EngineConfig.isGEO = false;
    		EngineConfig.isInterplanetary = true;
    	}
        Rocket rocket=RocketBuilder.buildRocket(rocketType,mission,propulsions);
        mobileManager.setRocket(rocket);
        mobileManager.setPlanet(solarSystem.getPlanets().get(EngineConfig.earthName));

    }


}
package engine.process.managers;

import engine.data.Mission;
import engine.data.Propulsion;
import engine.data.RocketEngine;
import engine.mobile.Planet;
import engine.mobile.Rocket;
import engine.mobile.Satellite;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>This class groups all actions related to the management of mobile elements</p>
 * <p>It provides the operations required to control the different phases of the mission</p>
 * @version 2.0
 * @since 12-02-2026
 *
 */
public interface MobileInterface {

    /**
     *It launches the rocket
     */
    void rocketLaunch();
    /**
     * Update the position of the rocket
     * @param type
     * @param rocket
     */
    void updateRocket();
    /**
     * Updates the state of the current active stage 
     */
    void updateCurrentStage();

    /**
     * It handles the separation of  a stage phase
     */
    void stageSeparation();

    /**
     * It handles the separation of the payload phase
     */
    void payloadSeparation();

    /**
     * Verify if the rocket is in the orbit.
     */
    void orbitInsertion();
      /**
     *  Updates the rocket's mass based on propellant consumption.

     */
    void updateRocketMass();
    /**
     * Updates the positions of all separated stages.
     */
    void updateStage();
    /**
     * Updates the position of the satellite(s) once deployed.
     */
    void updateSatellite();
    
    /** 
     * Performs the Trans-Lunar/Interplanetary Injection burn from LEO.
     * */
    void transInjection();
    
    /**
     *  Inserts the probe into orbit around the target planet upon arrival.
     */
    void arrivalOrbitInsertion();
    
    /** 
     * Executes the descent and landing sequence on the target planet's surface. 
     */
    void landing();
    
    /**
     *  Updates the position and altitude of the probe for the current frame.
     */
    void updateProbe();
    
    /**
     *  Performs the Hohmann transfer injection from LEO toward GEO.
     */
    void geoTransferInjection();
    
    /**
     *  Circularizes the satellite's orbit upon reaching GEO altitude.
     */
    void geoInsertion();
    
    /**
     *  Advances the simulation by one frame for a LEO mission.
     */
    void nextRoundLEO();
    
    /**
     *  Advances the simulation by one frame for a GEO mission. 
     */
    void nextRoundGEO();
    
    /**
     *  Advances the simulation by one frame for an interplanetary mission.
     */
    void nextRoundInterplanatery();
    

    //GETTERS/SETTERS

    Rocket getRocket();
    void setRocket(Rocket rocket);
    Planet getPlanet();
    void setPlanet(Planet planet);
    void setMission(Mission mission);
    void setPropulsion(ArrayList<Propulsion> propulsions);
}

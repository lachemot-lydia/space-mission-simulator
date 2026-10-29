package engine.process.builders;

import config.EngineConfig;
import config.PhysicsConstants;
import engine.data.Position;
import engine.data.Trajectory;
import engine.data.VectorMouvement;
import engine.mobile.Payload;
import engine.mobile.Probe;
import engine.mobile.Rocket;
import engine.mobile.Satellite;
import engine.process.SimulationCalculation;
import log.LoggerUtility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * <p>This class builds the payload of the rocket which may contain a satellite, </p>
 * <p>or a probe depending on the mission.It initializes the position of both of them </p>
 * @version 2.0
 * @since 02-12-2026
 *
 */

public class PayloadBuilder {

	private static Logger logger = LoggerUtility.getLogger(PayloadBuilder.class, "html");

    private PayloadBuilder() {
    }

    public static Payload buildPayload(Rocket rocket) {
    	
    	Payload payload=null;
        List<Satellite> satellites = new ArrayList<>();
        double centerAltitude=0.0;
        
       	double payloadMass=0.0;
    	
       	//IF NOT INTERPLANATERY *****************************************************************
    	if(rocket.getType()==1) {
    	double firstStageHeight=EngineConfig.FIRST_STAGE_HEIGHT_LG;
    	double secondStageHeight=EngineConfig.SECOND_STAGE_HEIGHT_LG;
    	double payloadHeight=EngineConfig.PAYLOAD_HEIGHT_LG;
    	double offset=firstStageHeight/2+secondStageHeight+payloadHeight/2;
    	
    	double payloadCenterX=rocket.getCurrentPosition().getX()+offset*Math.cos(SimulationCalculation.calculateRadian(PhysicsConstants.spaceCenter_Angle));
    	double payloadCenterY=rocket.getCurrentPosition().getY()+offset*Math.sin(SimulationCalculation.calculateRadian(PhysicsConstants.spaceCenter_Angle));
    	
    	centerAltitude=firstStageHeight+secondStageHeight+EngineConfig.PAYLOAD_HEIGHT_LG/2;
    	
        Position satellitePosition=new Position(payloadCenterX, payloadCenterY);
        
        VectorMouvement vectorMouvement1=new VectorMouvement(0,0,0,0);
    	double altitude=0;
        if (EngineConfig.isLEO || EngineConfig.isGEO){
        	altitude = PhysicsConstants.ORBIT_LEO_ALTITUDE;
        }
    	Satellite satelliteLEO=new Satellite(EngineConfig.MASS_SATELLITE, satellitePosition, altitude,vectorMouvement1);
    	
    	Position positionLEO=new Position(payloadCenterX, payloadCenterY);
    	ArrayList<Position> positions =new ArrayList<Position>();
    	positions.add(positionLEO);
    	Trajectory trajectory=new Trajectory(positions);
    	
    	satelliteLEO.setTrajectory(trajectory);
    	satellites.add(satelliteLEO);

    
    	Iterator<Satellite> itSatellites=satellites.iterator();
    	
    	while (itSatellites.hasNext()) {
        	Satellite tmpSatellite=itSatellites.next();
    		payloadMass+=tmpSatellite.getMass();
		}
    	
    	Position position=new Position(payloadCenterX, payloadCenterY);
        VectorMouvement vectorMouvement2=new VectorMouvement(0,0,0,0);
        
    	double separationAltitude=0;
        if (EngineConfig.isLEO || EngineConfig.isGEO){
        	separationAltitude = EngineConfig.SEPARATED_ALTITUDE_PAYLOAD_LEO;
		}
    	payload =new Payload(payloadMass, position,vectorMouvement2,centerAltitude,payloadHeight,separationAltitude);
    	
    	logger.info("Payload creation with rocket type: "+rocket.getType());
    	
    	payload.addSatellite(satelliteLEO);
      

    	
    	}else {
    		double firstStageHeight=EngineConfig.FIRST_STAGE_HEIGHT_INTER;
        	double secondStageHeight=EngineConfig.SECOND_STAGE_HEIGHT_INTER;
        	double thirdStageHeight=EngineConfig.THIRD_STAGE_HEIGHT_INTER;
        	double payloadHeight=EngineConfig.PAYLOAD_HEIGHT_INTER;
        	
        	double offset=firstStageHeight/2+secondStageHeight+thirdStageHeight+payloadHeight/2;
        	
        	double payloadCenterX=rocket.getCurrentPosition().getX()+offset*Math.cos(SimulationCalculation.calculateRadian(PhysicsConstants.spaceCenter_Angle));
        	double payloadCenterY=rocket.getCurrentPosition().getY()+offset*Math.sin(SimulationCalculation.calculateRadian(PhysicsConstants.spaceCenter_Angle));
        	
        	centerAltitude=firstStageHeight+secondStageHeight+thirdStageHeight+EngineConfig.PAYLOAD_HEIGHT_INTER/2;
        	
            Position probePosition=new Position(payloadCenterX, payloadCenterY);
            
            VectorMouvement vectorMouvement1=new VectorMouvement(0,0,0,0);
        	Probe probe=new Probe(EngineConfig.PROBE_MASS, probePosition,vectorMouvement1);
        	
        	
        	ArrayList<Position> positions =new ArrayList<Position>();
        	positions.add(probePosition);
        	Trajectory trajectory=new Trajectory(positions);
        	
        	probe.setTrajectory(trajectory);
        	
        	payloadMass+=probe.getMass();
            VectorMouvement vectorMouvement2=new VectorMouvement(0,0,0,0);
            Position payloadPosition=new Position(payloadCenterX, payloadCenterY);


        	payload =new Payload(payloadMass, payloadPosition,vectorMouvement2,centerAltitude,payloadHeight,EngineConfig.SEPARATED_ALTITUDE_PAYLOAD_INTER);
        	
        	logger.info("Payload creation with rocket type: "+rocket.getType());
        	//same as leo
        	payload.setProbe(probe);

        	
    	}
    	
        return payload;


    }
}

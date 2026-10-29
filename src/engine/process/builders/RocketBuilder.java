package engine.process.builders;
import java.util.ArrayList;

import org.apache.log4j.Logger;


import config.EngineConfig;
import config.PhysicsConstants;
import engine.data.Mission;
import engine.data.Position;
import engine.data.Propulsion;
import engine.data.SolarSystem;
import engine.data.Trajectory;
import engine.data.VectorMouvement;
import engine.mobile.Payload;
import engine.mobile.Planet;
import engine.mobile.Rocket;
import engine.mobile.Stage;
import engine.process.SimulationCalculation;
import engine.process.StageFactory;
import log.LoggerUtility;

/**
 *<p>This class builds a rocket by adding the payload and  different stages </p>
 * @version 2.0
 * @since 02-12-2026
 */
public class RocketBuilder {
	
	private static Logger logger = LoggerUtility.getLogger(RocketBuilder.class, "html");

    public RocketBuilder(){

    }
    
    //type=1 (leo/geo) type=2 (interplanatery)
    public static Rocket buildRocket(int type,Mission mission,ArrayList<Propulsion> propulsions){

        Rocket rocket=null;
    	Planet earth=SolarSystem.getInstance().getPlanets().get(EngineConfig.earthName);
    
        if(type==1) { //geo/leo
        	double stageHeight1=EngineConfig.FIRST_STAGE_HEIGHT_LG;
        	double stageHeight2=EngineConfig.SECOND_STAGE_HEIGHT_LG;
        	

    		double baseAltitude=PhysicsConstants.spaceCenter_Altitude;

        	double theta=SimulationCalculation.calculateRadian(PhysicsConstants.spaceCenter_Angle);
        	
        	
        	VectorMouvement vectorMouvement=new VectorMouvement(0,0,0,0);
        	        	
    		double earthCenterX=earth.getCurrentPosition().getX();
    		double earthCenterY=earth.getCurrentPosition().getY();
    		
        	double centerAltitude=baseAltitude+EngineConfig.FIRST_STAGE_HEIGHT_LG/2;
        	double distance=earth.getRadius()+centerAltitude;
        	
        	double rocketCenterX=earthCenterX+distance*Math.cos(theta);
        	double rocketCenterY=earthCenterY+distance*Math.sin(theta);

        	Position rocketPosition=new Position(rocketCenterX, rocketCenterY);
        	
        	Stage firstStage=StageFactory.createFirstStage(propulsions.get(0),rocketPosition,stageHeight1);
        	Stage secondStage=StageFactory.createSecondStage(propulsions.get(1),rocketPosition,stageHeight1,stageHeight2);
        	
        	double rocketHeight=stageHeight1+stageHeight2+EngineConfig.PAYLOAD_HEIGHT_LG;
        	//1: type 1 (geo/leo)
        	double rocketMass=firstStage.getMass()+secondStage.getMass();
        	double distance2 = SimulationCalculation.distance(rocketPosition, earth.getCurrentPosition());
        	double altitude = SimulationCalculation.calculateAltitude(distance2, earth.getRadius());
        	rocket=new Rocket(1,rocketMass,altitude, rocketPosition, null,vectorMouvement,rocketHeight);
        	rocket.setType(1);
        	
        	Payload payload = PayloadBuilder.buildPayload(rocket);
        	
        	rocket.setPayload(payload);
        	rocket.setMass(rocket.getMass()+payload.getMass());
        	rocket.addStage(firstStage);
        	rocket.addStage(secondStage);
        	rocket.setStageNumber(EngineConfig.MAX_STAGES_NUMBER_LG);
        	
        	rocket.setAngle(Math.toDegrees(theta));
        	
        	Position position=new Position(rocketCenterX, rocketCenterY);
        	ArrayList<Position> positions =new ArrayList<Position>();
        	positions.add(position);
        	Trajectory trajectory=new Trajectory(positions);
        	rocket.setTrajectory(trajectory);
        	
        }else {
        	
        	
        	
        	double stageHeight1=EngineConfig.FIRST_STAGE_HEIGHT_INTER;
        	double stageHeight2=EngineConfig.SECOND_STAGE_HEIGHT_INTER;
        	double stageHeight3=EngineConfig.THIRD_STAGE_HEIGHT_INTER;
        	
    		double baseAltitude=PhysicsConstants.spaceCenter_Altitude;

        	double theta=SimulationCalculation.calculateRadian(PhysicsConstants.spaceCenter_Angle);
        	
        	
        	VectorMouvement vectorMouvement=new VectorMouvement(0,0,0,0);
        	        	
    		double earthCenterX=earth.getCurrentPosition().getX();
    		double earthCenterY=earth.getCurrentPosition().getY();
    		
        	double centerAltitude=baseAltitude+EngineConfig.FIRST_STAGE_HEIGHT_INTER/2;
        	double distance=earth.getRadius()+centerAltitude;
        	
        	double rocketCenterX=earthCenterX+distance*Math.cos(theta);
        	double rocketCenterY=earthCenterY+distance*Math.sin(theta);

        	Position rocketPosition=new Position(rocketCenterX, rocketCenterY);
        	
        	Stage firstStage=StageFactory.createFirstStage(propulsions.get(0),rocketPosition,stageHeight1);
        	Stage secondStage=StageFactory.createSecondStage(propulsions.get(1),rocketPosition,stageHeight1,stageHeight2);
        	Stage thirdStage=StageFactory.createThirdStage(propulsions.get(2), rocketPosition, stageHeight1, stageHeight2, stageHeight3);
        	
        	double rocketHeight=stageHeight1+stageHeight2+stageHeight3+EngineConfig.PAYLOAD_HEIGHT_INTER;
        	
        	double rocketMass=firstStage.getMass()+secondStage.getMass()+thirdStage.getMass();
        	double distance2 = SimulationCalculation.distance(rocketPosition, earth.getCurrentPosition());
        	
        	double altitude = SimulationCalculation.calculateAltitude(distance2, earth.getRadius());
        	
        	rocket=new Rocket(2,rocketMass,altitude, rocketPosition, null,vectorMouvement,rocketHeight);
        	rocket.setType(2);
        	
        	Payload payload = PayloadBuilder.buildPayload(rocket);
        	
        	rocket.setPayload(payload);
        	rocket.setMass(rocket.getMass()+payload.getMass());
        	rocket.addStage(firstStage);
        	rocket.addStage(secondStage);
        	rocket.addStage(thirdStage);
        	rocket.setStageNumber(EngineConfig.MAX_STAGES_NUMBER_INTER);
        	
        	rocket.setAngle(Math.toDegrees(theta));
        	
        	Position position=new Position(rocketCenterX, rocketCenterY);
        	ArrayList<Position> positions =new ArrayList<Position>();
        	positions.add(position);
        	Trajectory trajectory=new Trajectory(positions);
        	rocket.setTrajectory(trajectory);
        	
        	
        	
        }
        
        logger.info("Rocket creation with ID:"+rocket.getType());
        return rocket;
    }




}

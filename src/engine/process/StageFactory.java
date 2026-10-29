package engine.process;

import org.apache.log4j.Logger;

import config.EngineConfig;
import config.PhysicsConstants;
import engine.data.Mission;
import engine.data.Position;
import engine.data.Propulsion;
import engine.data.RocketEngine;
import engine.data.TankPropellant;
import engine.data.VectorMouvement;
import engine.mobile.Stage;
import log.LoggerUtility;
/**
 * This class group the creation of the choosen rocket's stages, 
 * and also the postion of each one 
 * @version 2.0
 * @since 2026-03-15
 */
public class StageFactory {
	
	private static Logger logger = LoggerUtility.getLogger(StageFactory.class, "html");

	
	public StageFactory(){
		
	}
	/**
	 * Create the first stage of the rocket by adding the mass,position
	 *  and initialise the mouvement vectors to zero 
	 * @param tankPropellant
	 * @param rocketEngine
	 * @param height
	 * @return
	 */
	public static Stage createFirstStage(Propulsion propulsion,Position rocketPosition,double height) {
		TankPropellant tankPropellant=propulsion.getTankPropellant();
		RocketEngine rocketEngine=propulsion.getRocketEngine();
		double dryMass=EngineConfig.STAGE1_DRY_MASS1+rocketEngine.getMass();
		double stageMass=tankPropellant.getPropellantMass()+dryMass;
		double separationAltitude=0.0;
		if(EngineConfig.isLEO || EngineConfig.isGEO) {
		    separationAltitude = EngineConfig.SEPARATED_ALTITUDE_STAGE1_LEO;
		}else if(EngineConfig.isInterplanetary) {
		    separationAltitude = EngineConfig.SEPARATED_ALTITUDE_STAGE1_INTER;
		}
    	VectorMouvement vectorMouvement=new VectorMouvement(0, 0, 0, 0);
    	int id=0;
    	double centerAltitude=PhysicsConstants.spaceCenter_Altitude+height/2;
    	Stage stage= new Stage(id,dryMass,stageMass,rocketPosition ,propulsion, centerAltitude,vectorMouvement,separationAltitude,height);
    	logger.info("Stage one creation with ID: "+stage.getID());
    	return stage;
    	
	}
 /**
  * Create the second stage of the rocket by adding the mass,position 
  * and initialise the mouvement vectors to zero 
  * @param tankPropellant
  * @param rocketEngine
  * @param firstHeight
  * @param height
  * @return
  */
	public static Stage createSecondStage(Propulsion propulsion,Position rocketPosition,double firstHeight,double height) {
		
		TankPropellant tankPropellant=propulsion.getTankPropellant();
		RocketEngine rocketEngine=propulsion.getRocketEngine();
		
		double dryMass=EngineConfig.STAGE2_DRY_MASS1+rocketEngine.getMass();
		double stageMass=tankPropellant.getPropellantMass()+dryMass;
    	double angleStage=SimulationCalculation.calculateRadian(PhysicsConstants.spaceCenter_Angle);
   
		
		double altitudeCenter=PhysicsConstants.spaceCenter_Altitude+firstHeight+height/2;
    	double offset=firstHeight/2+height/2;
    	 	
    	double stageCenterX=rocketPosition.getX()+offset*Math.cos(angleStage);
    	double stageCenterY=rocketPosition.getY()+offset*Math.sin(angleStage);
    	
    	double separationAltitude=0.0;
    	
    	if(EngineConfig.isLEO) {
    	 separationAltitude=EngineConfig.SEPARATED_ALTITUDE_STAGE2_LEO;
    	}else if(EngineConfig.isGEO) {
          	 separationAltitude=EngineConfig.SEPARATED_ALTITUDE_STAGE2_GEO;

       	}
    	else if(EngineConfig.isInterplanetary) {
       	 separationAltitude=EngineConfig.SEPARATED_ALTITUDE_STAGE2_INTER;

    	}
    	
    	Position stagePosition= new Position(stageCenterX, stageCenterY);
    	VectorMouvement vectorMouvement=new VectorMouvement(0, 0, 0, 0);
    	int id=1;
    	Stage stage= new Stage(id,dryMass,stageMass,stagePosition,propulsion, altitudeCenter,vectorMouvement,separationAltitude,height);
    	logger.info("Stage two creation with ID: "+stage.getID());
    	return stage;
    	
	}
	/**
	 * Create the third stage of the rocket by adding the mass,position 
	 * and initialise the mouvement vectors to zero 
	 * @param tankPropellant
	 * @param rocketEngine
	 * @param firstHeight
	 * @param secondHeight
	 * @param height
	 * @return
	 */
	public static Stage createThirdStage(Propulsion propulsion,Position rocketPosition,double firstHeight,double secondHeight,double height) {
		
		TankPropellant tankPropellant=propulsion.getTankPropellant();
		RocketEngine rocketEngine=propulsion.getRocketEngine();
		
		
    	double dryMass=EngineConfig.STAGE3_DRY_MASS2+rocketEngine.getMass();
    	double stageMass=tankPropellant.getPropellantMass()+dryMass;
    	double angleStage=SimulationCalculation.calculateRadian(PhysicsConstants.spaceCenter_Angle);

    	double altitudeCenter=PhysicsConstants.spaceCenter_Altitude+firstHeight+secondHeight+height/2;
    	double offset=firstHeight/2+secondHeight/2+height/2;

    	double stageCenterX=rocketPosition.getX()+offset*Math.cos(angleStage);
    	double stageCenterY=rocketPosition.getY()+offset*Math.sin(angleStage);

    	Position stagePosition= new Position(stageCenterX, stageCenterY);
    	VectorMouvement vectorMouvement=new VectorMouvement(0, 0, 0, 0);
    	int id=2;
    	double separationAltitude=EngineConfig.SEPARATED_ALTITUDE_STAGE3;
    	Stage stage= new Stage(id,dryMass,stageMass,stagePosition,propulsion, altitudeCenter,vectorMouvement,separationAltitude,height);
    	logger.info("Stage three creation with ID: "+stage.getID());

    	return stage;
    	
	}
	
	
	

}

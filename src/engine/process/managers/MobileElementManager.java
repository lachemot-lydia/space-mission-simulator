package engine.process.managers;


import engine.data.Mission;
import engine.data.Position;
import engine.data.Propulsion;
import engine.data.RocketEngine;
import engine.data.SolarSystem;
import engine.data.TankPropellant;
import engine.data.VectorMouvement;
import engine.mobile.*;
import engine.process.SimulationCalculation;
import engine.process.builders.RocketBuilder;
import log.LoggerUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.pattern.LogEvent;

import config.EngineConfig;
import config.PhysicsConstants;
import config.SimulationConfig;

/**
 * <p>This class controls the mobiles in the full simulation </p>
 * @version 3.0
 * @since 02-12-2026
 */

public class MobileElementManager implements MobileInterface {


	private static Logger logger = LoggerUtility.getLogger(MobileElementManager.class, "html");
	
	
	
    private Rocket rocket;
    private Planet planet;
    private Mission mission;
	private ArrayList<Propulsion> propulsions;
    private boolean isInOrbitLEO;
	private boolean isInOrbitGEO;
    private boolean isInOrbitInter;
    private boolean failure;
    private boolean isEscapeVelocity;
    private boolean isTransInjected;
    private int frameBeforeTli;
    private int frameBeforeLanding;
	private int frameBeforeGeo;	
    
    public  MobileElementManager() {
		frameBeforeTli=0;
		frameBeforeLanding=0;
		frameBeforeGeo=0;	
		isInOrbitLEO=false;
		isInOrbitGEO=false;
	    isInOrbitInter=false;
	    failure=false;
	    isEscapeVelocity=false;
	    isTransInjected=false;
		
	}
    
    /**
     *Simulates the complete flight sequence of the rocket
     *This method is called repeatedly frame by frame.
     *type=1 (leo/geo) type=2(interplanatery)
     */
    @Override
    public void rocketLaunch(){
    	
    	Stage stage1=rocket.getStages().get(0);
    	RocketEngine rocketEngine1=stage1.getPropulsion().getRocketEngine();
    	rocketEngine1.setInFire(true);
    	rocket.setCurrentStage(stage1);
    	logger.info("Stage with ID"+stage1.getID()+"burn");
    	rocket.setRocketState("Stage one burn");
    	int stageNumber=rocket.getStageNumber();
		rocket.setStageNumber(stageNumber);
		
    }
    
    /**
     * Updates the rocket's total mass by computing propellant
     * consumption and recalculating the mass of each active stage
     * @param rocket
     */
    @Override 
    public void updateRocketMass() {
    	
    	Stage currentStage=rocket.getCurrentStage();
    	if (currentStage==null)return;

    	TankPropellant currentTankPropellant=currentStage.getPropulsion().getTankPropellant();
		RocketEngine currentRocketEngine=currentStage.getPropulsion().getRocketEngine();
    	double thrust= currentRocketEngine.getThrust()*currentRocketEngine.getThrottle();
        double isp=currentRocketEngine.getSpecificImpulse();
        
    	double massFlowRate=SimulationCalculation.calculateMassFlowRate(thrust,isp,planet);
    	logger.trace("The mass flow rate : "+massFlowRate);

    	double propellantMass=currentTankPropellant.getPropellantMass();
    	double newPropellantMass=SimulationCalculation.calculatePropellantMass(propellantMass, massFlowRate, PhysicsConstants.DELTA_TIME);
    	logger.trace("The propellant Mass : "+newPropellantMass);
    	currentTankPropellant.setPropellantMass(newPropellantMass);
    	double dryMass=rocket.getCurrentStage().getDryMass();
		double stageMass=newPropellantMass+dryMass;    
    	currentStage.setMass(stageMass);
    	
    	logger.trace("The mass of stage with ID : "+currentStage.getID()+" equals : "+currentStage.getMass());
    	
    	double rocketMass=0.0;
    	
    	ArrayList<Stage> stages= rocket.getStages();
    	for(Stage stage: stages) {
    		if(!stage.getIsSeparated()) {
    			rocketMass+=stage.getMass();
    		}
    	}
    	
    	
    	
    	double payloadMass=0.0;
    	if(rocket.getPayload()!=null && !rocket.getPayload().getIsSeparated()) {
    		payloadMass= rocket.getPayload().getMass();
    	}
    	rocketMass+=payloadMass;
    	rocket.setMass(rocketMass);
    	logger.trace("Rocket Mass: "+rocket.getMass());
    
    	
    }
    
   
    /**
     * Updates the rocket's physical state for the current
     * simulation frame
     * @param type
     * @param rocket
     */
    @Override
    public void updateRocket(){    
    	if(rocket.getCurrentStage()==null)return;
    	double deltaT=PhysicsConstants.DELTA_TIME;
    	
    
    	//ALTITUDE
    	double distance=SimulationCalculation.distance(rocket.getCurrentPosition(), planet.getCurrentPosition());
    	double altitude= SimulationCalculation.calculateAltitude(distance, planet.getRadius());
    	rocket.setAltitude(altitude);
    	rocket.getCurrentStage().setAltitude(altitude);
    	
    	if(rocket.getPayload()!=null) {
    		rocket.getPayload().setAltitude(altitude);
    	}
    	
    	
    	//logger.trace("Rocket altitude : "+rocket.getAltitude());
    	
    	if(altitude<=0 && !isInOrbitLEO) {
    		failure=true;
    	}
    	
    	for(Stage stage: rocket.getStages()) {
    		if(!stage.getIsSeparated()) {
    			stage.setAltitude(altitude);
    		}
    	}
    	
    	//ANGLE
    	double angle=SimulationCalculation.calculateGravityTurn(altitude);
    	rocket.setAngle(angle);
    	logger.trace("Rocket angle : "+rocket.getAngle());
    	
    	
    	//VECTOR
    	SimulationCalculation.calculateRocketAcceleration(rocket,planet);
    	SimulationCalculation.calculateRocketVelocity(rocket, deltaT);
    	SimulationCalculation.calculateRocketPosition(rocket, deltaT);

    	
    }
    
    /**
     * Handles the transition between stages after separation
     * @param rocket
     */
    @Override
    public void updateCurrentStage() {
    	Stage currentStage=rocket.getCurrentStage();
    	if(currentStage==null)return;    
    	
		logger.trace("The rocket Height : "+rocket.getHeight());

    	if(currentStage.getIsSeparated() && !currentStage.getPropulsion().getRocketEngine().getInFire()) {
    		//update rocket Height
    		double currentRocketHeight=rocket.getHeight();
         	double newRocketHeight=currentRocketHeight-currentStage.getHeight();
         	rocket.setHeight(newRocketHeight);

         	//remove current stage and add it to the separated list
    		rocket.getSeparatedStages().add(currentStage);
     		rocket.removeStage(currentStage);
     		
     		
         	if(!rocket.getStages().isEmpty()) {
         		
         		rocket.setCurrentStage(rocket.getStages().get(0));
         		rocket.getCurrentStage().getPropulsion().getRocketEngine().setInFire(true);
         		
         		int currentId=rocket.getCurrentStage().getID();
         		
         		switch(currentId) {
         		case 1:
         			logger.info("Stage with ID"+currentId+"burn");
         			rocket.setRocketState("Stage two burn");
         			break;
         		case 2:
         			logger.info("Stage with ID"+currentId+"burn");
         			rocket.setRocketState("Stage three burn");
         			break;
         		case 3:
         			logger.info("Stage with ID"+currentId+"burn");
         			rocket.setRocketState("Stage"+(currentId+1) +"burn");
         			break;
         		default:
         			
         			
         			rocket.setCurrentStage(null);
         			logger.info("All stages separated");
             		rocket.setRocketState("All stages separated");
             		break;
         		}
         		
         	
    	    }
    	}
    
    }
    

    /**
     * Triggers the separation of the current stage when
     * its propellant 
     */

    @Override
    public void stageSeparation(){
    	
    	Stage currentStage=rocket.getCurrentStage();
    	if(currentStage==null) return;
    	if(currentStage.getIsSeparated()) return;

    	TankPropellant currentTankPropellant=currentStage.getPropulsion().getTankPropellant();
		RocketEngine currentRocketEngine=currentStage.getPropulsion().getRocketEngine();
		
    	if((currentTankPropellant.getPropellantMass()<=0 && currentStage.getAltitude()>=currentStage.getSeparationAltitude() )){
    	
    		currentRocketEngine.setInFire(false);
    		
         	rocket.setRocketState("Stage "+currentStage.getID()+" separated");
         	
         	logger.info("Stage "+currentStage.getID()+" separated");
    		currentStage.setMass(currentStage.getDryMass());
    		
    		rocket.getCurrentStage().setIsSeparated(true);
    		
    		//update the number of the stages left
    		int stageNumber=rocket.getStageNumber();
    		rocket.setStageNumber(stageNumber-1);
    		
    		
    		}
    	
    }

    /**
     * Updates the postion of all separated stages as the fall
     * or drift after detachement from the main rocket
     */
    @Override
    public void updateStage() {
    	for(Stage stage:rocket.getSeparatedStages()) {
    	 double distance=SimulationCalculation.distance(stage.getCurrentPosition(), planet.getCurrentPosition());
    	 SimulationCalculation.calculateStagePosition(rocket,stage,PhysicsConstants.DELTA_TIME,planet);
    	 double altitude= SimulationCalculation.calculateAltitude(distance, planet.getRadius());
    	 stage.setAltitude(altitude);
    	 logger.trace("The altitude of stage with ID "+stage.getID()+": "+stage.getAltitude());
    	}
    }
    
    
    /**
     * Releases the payload once the required separation altitude is reached
     */
    @Override
    public void payloadSeparation(){
    	
    	if(rocket.getPayload()==null) return;
    	
        double deltaX = rocket.getCurrentPosition().getX() - planet.getCurrentPosition().getX();
        double deltaY= rocket.getCurrentPosition().getY() - planet.getCurrentPosition().getY();
	    double distance= SimulationCalculation.distance(rocket.getCurrentPosition(),planet.getCurrentPosition() );
        boolean  hasSeparationAltitude=rocket.getPayload().getAltitude()>=rocket.getPayload().getSeparationAltitude();
        
        if(hasSeparationAltitude) {
        	if(EngineConfig.isLEO || EngineConfig.isGEO) {
        	
	        double orbitalVelocity = SimulationCalculation.calculateOrbitVelocity(distance, planet);

			if(EngineConfig.isGEO) {																							//Continue de bruler le carburant du dernier étage même si en LEO
	            Stage currentStage = rocket.getCurrentStage();
	            if(currentStage == null) return;
	            TankPropellant tank = currentStage.getPropulsion().getTankPropellant();
	            if(tank.getPropellantMass() > 0) return;
	        }	
    		   for(Satellite satellite:rocket.getPayload().getSatellites()) {
    			   
    			   double tangentX=-deltaY / distance;
    			   double tangentY= deltaX / distance;

    			   double currentVx=rocket.getVectorMouvement().getVx();
    			   double currentVy=rocket.getVectorMouvement().getVy();
    			   if (currentVx*tangentX+currentVy*tangentY<0) {
    			       tangentX=-tangentX;
    			       tangentY=-tangentY;
    			       
    			   }

    			   if(EngineConfig.isGEO) {																				//pour GEO le satellite obtient la même vitesse que le dernier étage
    				    satellite.getVectorMouvement().setVx(rocket.getVectorMouvement().getVx());
    				    satellite.getVectorMouvement().setVy(rocket.getVectorMouvement().getVy());
    				} else {
    				    satellite.getVectorMouvement().setVx(-orbitalVelocity*(deltaY/distance));
    				    satellite.getVectorMouvement().setVy(orbitalVelocity*(deltaX/distance));
    				}satellite.setAltitude(rocket.getAltitude());
        		   satellite.getTrajectory().getPositions().add(new Position(rocket.getCurrentPosition().getX(), rocket.getCurrentPosition().getY()));
    			   rocket.getSaveSatellites().add(satellite);
    		   
    		   }
    		   
    	  
	    
	    }else if(EngineConfig.isInterplanetary){ 
	    
	        	  
    		   Probe probe =rocket.getPayload().getProbe();
   	           double orbitalVelocity = SimulationCalculation.calculateOrbitVelocity(distance, planet);

    		   probe.getVectorMouvement().setVx(-orbitalVelocity*(deltaY/distance));
    		   probe.getVectorMouvement().setVy(orbitalVelocity*(deltaX/distance));
    		   probe.setCurrentPosition(new Position(rocket.getCurrentPosition().getX(), rocket.getCurrentPosition().getY()));
    		   probe.setAltitude(rocket.getAltitude());
              
    		   Planet targetPlanet=SolarSystem.getInstance().getPlanets().get(mission.getPlanetArrival());
    		   Orbit targetOrbit=SolarSystem.getInstance().getOrbits().get(mission.getPlanetArrival());
    		  
    		   if(targetPlanet!=null &&  targetOrbit != null) {
       			 rocket.getPayload().getProbe().setTargetPlanet(targetPlanet);
       			 rocket.getPayload().getProbe().setTargetOrbit(targetOrbit);
    		   }
    		   
    		   
    		   rocket.setSaveProbe(probe);
    		   
        	   
	          
	    	}
        	
           rocket.getPayload().setIsSeparated(true);
     	   rocket.setPayload(null);
     	   rocket.setRocketState("Payload separation");
     	   logger.info("Payload is separated");
        }
    	   
    	   
    	 
    	}

    
    
    
    
    
    
    /**
     * Detects when the rocket reaches the target orbit altitude and transitions
     * the simulation into orbital mode
     */
    @Override
    public void orbitInsertion(){
        	
        	if (rocket.getCurrentStage()==null) return;
            if (isInOrbitLEO) return;

           if(EngineConfig.isLEO || EngineConfig.isGEO) {
        	   
        	   		if(rocket.getSaveSatellites().isEmpty()) return;
            		Satellite satellite=rocket.getSaveSatellites().get(0);
                	double distanceSatellite=SimulationCalculation.distance(satellite.getCurrentPosition(),  planet.getCurrentPosition());
                	double orbitalVelocity=SimulationCalculation.calculateOrbitVelocity(distanceSatellite, planet);
                	logger.trace("The orbital velocity of the satellite : "+orbitalVelocity);
                	double vx=satellite.getVectorMouvement().getVx();
                	double vy=satellite.getVectorMouvement().getVy();
                	double velocity=Math.sqrt(vx*vx+vy*vy);
                	logger.trace("The satellite velocity : "+velocity);
                	 if(velocity>=orbitalVelocity*0.95 && satellite.getAltitude()>200000) {
                		 
                    	isInOrbitLEO=true;
                    	if(EngineConfig.isGEO) {
                            rocket.setRocketState("In LEO orbit, preparing GEO transfer");
                            satellite.setInOrbitLEO(true);
                        } else {
                            rocket.setRocketState("In LEO orbit");
                            satellite.setInOrbit(true);
                        }
                    	
                    	//IN CASE THE STAGE IS IN ORBIT
                    	Stage currentStage=rocket.getCurrentStage();
                    	if(currentStage!=null && !currentStage.getIsSeparated()) {
                    		  currentStage.getPropulsion().getRocketEngine().setInFire(false);
                    		  currentStage.setIsSeparated(true);
                    		  currentStage.setMass(currentStage.getDryMass());
                    		  rocket.getSeparatedStages().add(currentStage);
                    		  rocket.removeStage(currentStage);
                    		  rocket.setStageNumber(rocket.getStageNumber() - 1);
                    		  int stageNumber=currentStage.getID()+1;
                             logger.info("Stage "+stageNumber+"separated");
              
                    	  }
                    	
                	 }
            		
            	}else if(EngineConfig.isInterplanetary) {

            		if(isTransInjected) return;
            		if (rocket.getSaveProbe()==null) return;
            		Probe probe=rocket.getSaveProbe();
            		double distanceProbe=SimulationCalculation.distance(probe.getCurrentPosition(),  planet.getCurrentPosition());
                	double orbitalVelocity=SimulationCalculation.calculateOrbitVelocity(distanceProbe, planet);
                	logger.trace("The orbital velocity of the probe : "+orbitalVelocity);
                	double vx=probe.getVectorMouvement().getVx();
                	double vy=probe.getVectorMouvement().getVy();
                	double velocity=Math.sqrt(vx*vx+vy*vy);
                	logger.trace("The probe velocity : "+velocity);
                	
                	 if(Math.abs(velocity-orbitalVelocity)<orbitalVelocity*0.05 && probe.getAltitude()>200000) {
                    	isInOrbitLEO=true;
                    	rocket.setRocketState("In LEO orbit");
                    	//IN CASE THE STAGE IS IN ORBIT
                    	Stage currentStage=rocket.getCurrentStage();
                    	if(currentStage!=null && !currentStage.getIsSeparated()) {
                    		  currentStage.getPropulsion().getRocketEngine().setInFire(false);
                    		  currentStage.setIsSeparated(true);
                    		  currentStage.setMass(currentStage.getDryMass());
                    		  rocket.getSeparatedStages().add(currentStage);
                    		  rocket.removeStage(currentStage);
                    		  rocket.setStageNumber(rocket.getStageNumber() - 1);
                    		  int stageNumber=currentStage.getID()+1;
                             logger.info("Stage "+stageNumber+"separated");
              
                    	  }
                    	probe.setInOrbitLEO(true);
                	 }
        			
        			
        			
        		}else {
        			failure=true;
        			rocket.setRocketState("Planet not found");
        		}

	  
          }

    /**
     * Updates the position of the deployed satellite(s) along their circular orbit.
     */
    @Override
    public void updateSatellite() {
    	
      for(Satellite satellite:rocket.getSaveSatellites()) {
    	  
	      double distance=SimulationCalculation.distance(satellite.getCurrentPosition(), planet.getCurrentPosition());
	    	
	      SimulationCalculation.calculateSatellitePosition(satellite, PhysicsConstants.DELTA_TIME,planet);
	    	
	      double altitude=SimulationCalculation.calculateAltitude(distance, planet.getRadius());
	    	
	      satellite.setAltitude(altitude);
    	
      }
      
    }
    
    
    /**
     * Performs the Trans Lunar Injection (TLI) maneuver, boosting the probe
     * out of LEO toward its target planet.
     */	
    @Override
    public void transInjection() {
        if (isTransInjected) return;
        if (!isInOrbitLEO) return;
        Probe probe=rocket.getSaveProbe();
        if (probe==null) return;

        frameBeforeTli++;
        double frame=PhysicsConstants.FRAME_BEFORE_TLI;
        if (probe.getTargetPlanet().getPlanetName().equals(EngineConfig.moonName)||probe.getTargetPlanet().getPlanetName().equals(EngineConfig.moonDName)) {
        	 frame=2130;
        }
        if (probe.getTargetPlanet().getPlanetName().equals(EngineConfig.marsName)) {
        	frame=2400;;
        }
        if (probe.getTargetPlanet().getPlanetName().equals(EngineConfig.mercuryName)||probe.getTargetPlanet().getPlanetName().equals(EngineConfig.mercuryDName)) {
            frame=4528;
        }
        if (probe.getTargetPlanet().getPlanetName().equals(EngineConfig.jupiterName)) {
            frame=1820;
        }
        if (frameBeforeTli<frame) return;

        probe.setTrans(true);
        isTransInjected = true;

        Planet targetPlanet = probe.getTargetPlanet();

        double r1=SimulationCalculation.distance(probe.getCurrentPosition(), planet.getCurrentPosition());
        double r2=SimulationCalculation.distance(targetPlanet.getCurrentPosition(), planet.getCurrentPosition());

        double semiMajorAxis=(r1+r2)/2.0;
        double transferVelocity=Math.sqrt(PhysicsConstants.GRAVITATIONAL_CONSTANT*planet.getMass()*(2.0/r1-1.0/semiMajorAxis) );

        double deltaX=probe.getCurrentPosition().getX()-planet.getCurrentPosition().getX();
        double deltaY=probe.getCurrentPosition().getY()-planet.getCurrentPosition().getY();
        double distance=Math.sqrt(deltaX*deltaX+deltaY*deltaY);

        double tangentX=-deltaY/distance;
        double tangentY=deltaX/distance;

        double currentVx=probe.getVectorMouvement().getVx();
        double currentVy=probe.getVectorMouvement().getVy();
        if (currentVx*tangentX+currentVy*tangentY<0) {
            tangentX=-tangentX;
            tangentY=-tangentY;
        }

        probe.getVectorMouvement().setVx(transferVelocity*tangentX);
        probe.getVectorMouvement().setVy(transferVelocity*tangentY);
        rocket.setRocketState("Trans Injection ");
    }
    
    /**
     * Inserts the probe into orbit around its target planet upon arrival.
     */
    @Override
    public void arrivalOrbitInsertion() {
        if (isInOrbitInter) return;
        if (rocket.getSaveProbe() == null) return;
        Probe probe=rocket.getSaveProbe();
        Planet targetPlanet = probe.getTargetPlanet();
        Orbit targetOrbit=probe.getTargetOrbit();
        if (targetPlanet==null) return;

        double distance=SimulationCalculation.distance(probe.getCurrentPosition(), targetPlanet.getCurrentPosition());
        double orbitInsersionRadius=targetPlanet.getRadius()+targetOrbit.getAltitude();
        if(distance>orbitInsersionRadius) return;
        
        double deltaX=probe.getCurrentPosition().getX()-targetPlanet.getCurrentPosition().getX();
        double deltaY=probe.getCurrentPosition().getY()-targetPlanet.getCurrentPosition().getY();
        double orbitalVelocity=SimulationCalculation.calculateOrbitVelocity(orbitInsersionRadius, targetPlanet);
        double tangentX=-(deltaY/distance);
        double tangentY=(deltaX/distance);
        
        if(targetPlanet.getPlanetName().equals(EngineConfig.marsName) ) {
        	tangentX=-tangentX;
        	tangentY=-tangentY;
        }
        
        double newX=targetPlanet.getCurrentPosition().getX()+(deltaX/distance)*orbitInsersionRadius;
        double newY=targetPlanet.getCurrentPosition().getY()+(deltaY/distance)*orbitInsersionRadius;
        
        probe.setCurrentPosition(new Position(newX, newY));
        probe.setVectorMouvement(new VectorMouvement(0, 0, orbitalVelocity*tangentX, orbitalVelocity*tangentY));
        probe.setAltitude(targetOrbit.getAltitude());
        probe.setTrans(false);
        probe.setInOrbit(true);
        isInOrbitInter=true;
        rocket.setRocketState("In orbit");
    }
    
    /**
     * Updates the probe's position, velocity, and altitude for the current simulation step.
     */
    @Override
    public void updateProbe() {
    	Probe probe=rocket.getSaveProbe();
    	if(probe==null)return;
    	SimulationCalculation.calculateProbePosition(probe ,PhysicsConstants.DELTA_TIME, probe.getTargetPlanet());

        Planet targetPlanet=rocket.getSaveProbe().getTargetPlanet();
	    double distance=SimulationCalculation.distance(probe.getCurrentPosition(), targetPlanet.getCurrentPosition());
	    double altitude=SimulationCalculation.calculateAltitude(distance,targetPlanet.getRadius());
	    probe.setAltitude(altitude);
	    logger.trace("The altitude of the probe  target Planet:"+probe.getAltitude());
	    		
    }
    /**
     * Progressively descends the probe from orbit to the surface of its target planet.
     */
    @Override
    public void landing() {
        if (!isInOrbitInter) return;
        Probe probe=rocket.getSaveProbe();
        if (probe==null) return;
        Planet targetPlanet=probe.getTargetPlanet();
       
        if (probe.isLanded())return;
        frameBeforeLanding++;
		if(targetPlanet.getPlanetName().equals(EngineConfig.marsName) ) {
        	frameBeforeLanding = PhysicsConstants.FRAME_BEFORE_LANDING;
        }
        if (frameBeforeLanding<PhysicsConstants.FRAME_BEFORE_LANDING) return;
        Orbit targetOrbit=probe.getTargetOrbit();
        double deltaX=probe.getCurrentPosition().getX()-targetPlanet.getCurrentPosition().getX();
        double deltaY=probe.getCurrentPosition().getY()-targetPlanet.getCurrentPosition().getY();
        double distance=Math.sqrt(deltaX*deltaX+deltaY*deltaY);
        double altitude=SimulationCalculation.calculateAltitude(distance,targetPlanet.getRadius());
        if (altitude<=0) {
            double angle=Math.atan2(deltaY, deltaX);
            double landX=targetPlanet.getCurrentPosition().getX() + targetPlanet.getRadius() * Math.cos(angle);
            double landY=targetPlanet.getCurrentPosition().getY() + targetPlanet.getRadius() * Math.sin(angle);
            probe.setCurrentPosition(new Position(landX, landY));
            probe.setVectorMouvement(new VectorMouvement(0, 0, 0, 0));
            probe.setInOrbit(false);
            probe.setLanded(true);
            rocket.setRocketState("Landed on " + targetPlanet.getPlanetName());
            return;
        }
        double vx=probe.getVectorMouvement().getVx();
        double vy=probe.getVectorMouvement().getVy();
        double radialX=deltaX/distance;
        double radialY=deltaY/distance;
        double tangentX=-deltaY/distance;
        double tangentY=deltaX/distance;
        
        if(targetPlanet.getPlanetName().equals(EngineConfig.mercuryName) ||targetPlanet.getPlanetName().equals(EngineConfig.marsName)) {
        	tangentX=-tangentX;
        	tangentY=-tangentY;
        }
        double orbitalVelocity=SimulationCalculation.calculateOrbitVelocity(distance, targetPlanet);
        double progress=1.0-Math.min(altitude/targetOrbit.getAltitude(),1.0);
        double tangentialSpeed=vx*tangentX+vy*tangentY;
        double targetTangentialSpeed=orbitalVelocity*(1.0-progress*0.45);
        double tangentialDelta=(targetTangentialSpeed-tangentialSpeed)*0.012;
        vx +=tangentX*tangentialDelta;
        vy +=tangentY*tangentialDelta;
        double radialSpeed=vx*(-radialX)+vy*(-radialY);
        double targetRadialSpeed=progress*progress*orbitalVelocity*0.18;
        double radialDelta=(targetRadialSpeed-radialSpeed)*0.015;
        vx-=radialX*radialDelta;
        vy-=radialY*radialDelta;
        double gravity=targetPlanet.getGravity();
        vx -=radialX*gravity*PhysicsConstants.DELTA_TIME;
        vy -=radialY*gravity*PhysicsConstants.DELTA_TIME;
        double dragFactor=1.0-progress*progress*0.004;
        vx *=dragFactor;
        vy *=dragFactor;
        probe.getVectorMouvement().setVx(vx);
        probe.getVectorMouvement().setVy(vy);
    }
    /**
     * Performs the Hohmann transfer injection from LEO to GEO for the first satellite.
     */
    @Override
	public void geoTransferInjection() {
        if (isInOrbitGEO) return;
        if (!isInOrbitLEO) return;

        if (rocket.getSaveSatellites().isEmpty()) return;
        Satellite satellite=rocket.getSaveSatellites().get(0);
        
        //time duration to launch satellite in GEO transfert
        frameBeforeGeo++;
        if (frameBeforeGeo<PhysicsConstants.FRAME_BEFORE_GEO) return;

        double distanceLEO=SimulationCalculation.distance(satellite.getCurrentPosition(), planet.getCurrentPosition());
        double distanceGEO=planet.getRadius()+PhysicsConstants.ORBIT_GEO_ALTITUDE;

        // Hohmann transfert velocity for the curve trajectory
        double transferVelocity = Math.sqrt(PhysicsConstants.GRAVITATIONAL_CONSTANT*planet.getMass()*(2.0/distanceLEO-1.0/((distanceLEO+distanceGEO)/2.0)));

        // retrieving values of the satellite velocity to create the movement
        double vx=satellite.getVectorMouvement().getVx();
        double vy=satellite.getVectorMouvement().getVy();
        double velocity=Math.sqrt(vx*vx+vy*vy);

        double directionX=vx/velocity;
        double directionY=vy/velocity;

        satellite.getVectorMouvement().setVx(transferVelocity*directionX);
        satellite.getVectorMouvement().setVy(transferVelocity*directionY);

        isInOrbitGEO = true;
        rocket.setRocketState("transfer injection");
        logger.info("GEO transfer velocity applied: "+transferVelocity);
    }

	/**
	 * Circularizes the satellite's orbit upon reaching GEO altitude .
	 */
    @Override
	public void geoInsertion() {
        if (!isInOrbitGEO) return;
        if (isInOrbitInter) return;

        if (rocket.getSaveSatellites().isEmpty()) return;
        Satellite satellite=rocket.getSaveSatellites().get(0);

        double distance = SimulationCalculation.distance(
            satellite.getCurrentPosition(), planet.getCurrentPosition()
        );
        double altitude=SimulationCalculation.calculateAltitude(distance, planet.getRadius());

        // the altitude where the satellite goes in GEO trajectory
        if (altitude<PhysicsConstants.ORBIT_GEO_ALTITUDE* 0.9999) return;

        // velocity of the satellite when he reaches the orbit
        double geoVelocity=SimulationCalculation.calculateOrbitVelocity(distance, planet);

        // the satellite acquire the same trajectory as the GEO
        double deltaX=satellite.getCurrentPosition().getX()-planet.getCurrentPosition().getX();
        double deltaY=satellite.getCurrentPosition().getY() - planet.getCurrentPosition().getY();

        double directionX=-(deltaY / distance);
        double directionY=(deltaX / distance);

        satellite.getVectorMouvement().setVx(geoVelocity*directionX);
        satellite.getVectorMouvement().setVy(geoVelocity*directionY);

        isInOrbitInter = true;
        satellite.setInOrbit(true);
        rocket.setRocketState("In GEO orbit");
        logger.info("GEO circularization done, velocity: "+geoVelocity);
    }
    
 
	/**
	 * Advances the simulation by one frame for a LEO mission.
	 */
    @Override
    public void nextRoundLEO(){
    	if(!failure) {
	    	if(!isInOrbitLEO) {
	    	updateRocketMass();
	    	updateRocket();
	    	updateStage();
	    	stageSeparation();
	    	updateCurrentStage();
	    	payloadSeparation();
	    	orbitInsertion();
	    	}else {
	    		updateStage();
	    		updateSatellite();
	    	}
    	}else {
    		rocket.setRocketState("FAILURE");
    	}
    	
    }
   
    /**
     * Advances the simulation by one frame for a GEO mission.
     */
    @Override
    public void nextRoundGEO(){
        if(!failure) {
            if(!isInOrbitLEO) {
                updateRocketMass();
                updateRocket();
                updateStage();
                stageSeparation();
                updateCurrentStage();
                payloadSeparation();
                orbitInsertion();
            } else {
                updateStage();
                geoTransferInjection();						
                geoInsertion();									
                updateSatellite();
            }
        } else {
            rocket.setRocketState("FAILURE");
        }
    }
    /**
     * Advances the simulation by one frame for an interplanetary mission.
     */
    @Override
    public void nextRoundInterplanatery() {
    	if(!failure) {
    		
    		if(!isInOrbitLEO) {
    			updateRocketMass();
    	    	updateRocket();
    	    	updateStage();
    	    	stageSeparation();
    	    	updateCurrentStage();
    	    	payloadSeparation();
    	    	orbitInsertion();
    	}else {
    	    	updateStage();
    	    	transInjection();
    	    	arrivalOrbitInsertion();
    	    	landing();  
    	    	if (!rocket.getSaveProbe().isLanded()) { 
    	            updateProbe();
    	        }
    	    	
    	    	
    	    	
    	    	
    		}
    	}else {
    		rocket.setRocketState("FAILURE");
    	}
    	
    	
    	
    }

    //GETTERS/SETTERS***********************************************************************
    @Override
    public Rocket getRocket(){
        return rocket;
    }
    @Override
    public void setRocket(Rocket rocket){
        this.rocket = rocket;
    }

    
    @Override
    public Planet getPlanet(){
        return planet;
    }
    @Override
    public void setPlanet(Planet planet){
        this.planet = planet;
    }
    @Override
    public void setMission(Mission mission) {
    	this.mission=mission;
    }
    
    @Override
	public void setPropulsion(ArrayList<Propulsion> propulsions) {
		this.propulsions = propulsions;
	}




}

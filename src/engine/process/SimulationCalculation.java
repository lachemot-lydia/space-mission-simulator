package engine.process;

import org.apache.log4j.Logger;

import config.EngineConfig;
import config.PhysicsConstants;
import engine.data.Position;
import engine.data.RocketEngine;
import engine.data.SolarSystem;
import engine.data.VectorMouvement;
import engine.mobile.Payload;
import engine.mobile.Planet;
import engine.mobile.Probe;
import engine.mobile.Rocket;
import engine.mobile.Satellite;
import engine.mobile.Stage;
import engine.process.builders.RocketBuilder;
import log.LoggerUtility;

/**
 *<p>This class groups together the physics and mathematics calculations used in the simulation. </p>
 *<p>Distances, Forces, Masses, angles and other numerical values required by the engine </p>
 * @version 0.1
 * @since 02-12-2026
 */


public  class SimulationCalculation {
	
	private static Logger logger = LoggerUtility.getLogger(SimulationCalculation.class, "html");

	
	//private static Planet earth=SolarSystem.getInstance().getPlanets().get("earth");
    /**
     * transforms a numeric value into its corresponding angle in radians
     */
	
    public static double calculateRadian(double spacecenterAngle) {
        return Math.toRadians(spacecenterAngle);
    }
    
    
    /**
     * Calculates the distance between two objects
     * @param objectPosition1 
     * @param objectPosition2
     * @return the distance
     */
    public static double distance(Position objectPosition1,Position objectPosition2) {
    	double x1=objectPosition1.getX();
    	double x2=objectPosition2.getX();
    	double y1=objectPosition1.getY();
    	double y2=objectPosition2.getY();
    	return Math.sqrt(Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2));
    	
    }
    
    
    /**
     * Calculates the aerodynamic drag force acting on the rocket
     * @param velocity
     * @param altitude
     * @return
     */
    public static double drag(double velocity,double altitude) {
    	
    	double dragCoeff=PhysicsConstants.DRAG_COEFFICIENT;
    	double frontalArea=PhysicsConstants.FRONTAL_AREA1;
    	return (0.5)*dragCoeff*calculateAirDensity(altitude)*frontalArea*Math.pow(velocity, 2);

    }
    
    /**
     * Calculates the mass flow rate of the actif stage of the rocket 
     * its depends on the thrust and the isp of the rocket engine
     * @param rocket
     * @return
     */
    public static double calculateMassFlowRate(double thrust,double isp,Planet planet) {
	 	    return thrust/(isp*planet.getGravity());
	 	    
	 	  }
    
    
    /**
     * Calculates the mass of the propellant last in the tank 
     * of the actif stage  
     * @param rocket
     * @param deltaT
     */
    public static double calculatePropellantMass(double propellantMass,double massFlowRate, double deltaT) {
 	   
 	   double consumedMassPropellant=massFlowRate*deltaT; 
 	   double deltaMass=propellantMass-consumedMassPropellant;
 	   
 	   if(deltaMass>0)
 	   {
 		   return deltaMass;
 	   }else {
 		   return 0;

 	   }
 	   
    }

    /**
     * Calculates the acceleration of the rocket frame by frame 
     * @param rocket
     */
    public static void calculateRocketAcceleration(Rocket rocket,Planet planet) {
    	double G=PhysicsConstants.GRAVITATIONAL_CONSTANT;
        double rocketMass=rocket.getMass();  

    	double distance=distance(rocket.getCurrentPosition(), planet.getCurrentPosition());
    	double deltaX=rocket.getCurrentPosition().getX()-planet.getCurrentPosition().getX();
        double deltaY=rocket.getCurrentPosition().getY()-planet.getCurrentPosition().getY();
        
    	double gravityForce=G*planet.getMass()*rocket.getMass()/(distance*distance);
        double gravityX=-gravityForce*(deltaX/distance);
        double gravityY=-gravityForce*(deltaY/distance);

    	//thrust
        
    	Stage currentStage =rocket.getCurrentStage();
		RocketEngine rocketEngine=currentStage.getPropulsion().getRocketEngine();
		
		double thrustStage=rocketEngine.getThrottle()*rocketEngine.getThrust();
		
    	double radialAngle=Math.atan2(deltaY, deltaX);
    	double theta=radialAngle+calculateRadian(90-rocket.getAngle());
    	double thrustX=thrustStage*Math.cos(theta);
        double thrustY=thrustStage*Math.sin(theta);
    	
       
        //Drag force
       double vx=rocket.getVectorMouvement().getVx();
       double vy=rocket.getVectorMouvement().getVy();
       
       double velocity=Math.sqrt(vx*vx+vy*vy);
       
       double altitude=calculateAltitude(distance, planet.getRadius());
       
       double dragX=0.0;
       double dragY=0.0;
     
       if(velocity>0) {
           double drag=drag(velocity,altitude);
	       dragX=-drag*(vx/velocity);
	       dragY=-drag*(vy/velocity);
	       
       }
       
        double totalForceX=thrustX+dragX+gravityX;
        double totalForceY=thrustY+dragY+gravityY;
       
       //calcul of the accelaration
       double ax=(totalForceX/rocketMass);
       double ay=(totalForceY/rocketMass);
       
      
       rocket.getCurrentStage().getVectorMouvement().setAx(ax);
       rocket.getCurrentStage().getVectorMouvement().setAy(ay);
      
      
       rocket.getVectorMouvement().setAx(ax);
       rocket.getVectorMouvement().setAy(ay);
       
       
       
    }
    
    /**
     * Calculates the velocity of the rocket frame by fram e
     * @param stage
     * @param deltaT
     */
    public static void calculateRocketVelocity(Rocket rocket ,double deltaT) {

    	
    	double ax=rocket.getVectorMouvement().getAx();
    	double ay=rocket.getVectorMouvement().getAy();
    	
    	double vx=rocket.getVectorMouvement().getVx()+ax*deltaT;
    	double vy=rocket.getVectorMouvement().getVy()+ay*deltaT;
    	
    	rocket.getVectorMouvement().setVx(vx);
    	rocket.getVectorMouvement().setVy(vy);
        rocket.getCurrentStage().setVectorMouvement(new VectorMouvement(ax, ay, vx, vy));
        
        
        
        
    	
    	 
         
    }
    
   /**
    * Calculates the position of the rocket frame by frame 
    * @param stage
    * @param deltaT
    */
   public static void calculateRocketPosition(Rocket rocket,double deltaT) {

	   
	   double vx=rocket.getVectorMouvement().getVx();
	   double vy=rocket.getVectorMouvement().getVy();
	   //(x,y)
	   double x=rocket.getCurrentPosition().getX()+vx*deltaT;
	   double y=rocket.getCurrentPosition().getY()+vy*deltaT;
	  
	   rocket.getCurrentPosition().setX(x);
	   rocket.getCurrentPosition().setY(y);
	   
       rocket.getCurrentStage().setCurrentPosition(new Position(rocket.getCurrentPosition().getX(), rocket.getCurrentPosition().getY()));

       if(EngineConfig.isLEO || EngineConfig.isGEO ) {
    	   
    	if(rocket.getPayload()!=null) {
       		for(Satellite satellite:rocket.getPayload().getSatellites()) {
         	   satellite.setCurrentPosition(new Position(rocket.getCurrentPosition().getX(), rocket.getCurrentPosition().getY()));
       		}
       	}else {
       		for(Satellite satellite:rocket.getSaveSatellites()) {
          	   satellite.setCurrentPosition(new Position(rocket.getCurrentPosition().getX(), rocket.getCurrentPosition().getY()));
       		}
       	}
    	 
      }else if(EngineConfig.isInterplanetary) {
    	  if(rocket.getPayload()!=null) {
     
           	   rocket.getPayload().getProbe().setCurrentPosition(new Position(rocket.getCurrentPosition().getX(), rocket.getCurrentPosition().getY()));
    	  }
    	  }
	   rocket.getTrajectory().getPositions().add(new Position(x, y));
   }
   /**
    * Caculates the position, velocit of a rocket stage over a time step
    * @param rocket
    * @param stage
    * @param deltaT
    */
   public static void calculateStagePosition(Rocket rocket,Stage stage, double deltaT,Planet planet) {
   	
      //weight force
       double stageMass=stage.getMass();
       double vx=stage.getVectorMouvement().getVx();
       double vy=stage.getVectorMouvement().getVy();
       double distance=distance(stage.getCurrentPosition(), planet.getCurrentPosition());
       
       double gravityForce=PhysicsConstants.GRAVITATIONAL_CONSTANT*planet.getMass()/(distance*distance);
       double deltaX=stage.getCurrentPosition().getX()-planet.getCurrentPosition().getX();
       double deltaY=stage.getCurrentPosition().getY()-planet.getCurrentPosition().getY();
       double gravityX=-gravityForce*(deltaX/distance);
       double gravityY=-gravityForce*(deltaY/distance);
       
           
      double velocity=Math.sqrt(vx*vx+vy*vy);
      
  	
      double altitude=rocket.getAltitude();
      double drag=drag(velocity,altitude);
      
      double dragX=0.0;
      double dragY=0.0;
      
      if(velocity>0) {
	       dragX=-drag*(vx/velocity);
	       dragY=-drag*(vy/velocity);
	       
      }
      
       double totalForceX=dragX+gravityX;
       double totalForceY=dragY+gravityY;
      
      //calcul of the accelaration
       double ax=0.0;
       double ay=0.0;
      if(stageMass>0) {
    	  	ax=totalForceX/stageMass;
    	  	ay=totalForceY/stageMass;
      }else {
    	  ax=0;
    	  ay=0;
      }
      stage.getVectorMouvement().setAx(ax);
      stage.getVectorMouvement().setAy(ay);
      

  	  vx=stage.getVectorMouvement().getVx()+ax*deltaT;
  	  vy=stage.getVectorMouvement().getVy()+ay*deltaT;
  	
      stage.getVectorMouvement().setVx(vx);
  	  stage.getVectorMouvement().setVy(vy);
  	  //(x,y)
	  double x=stage.getCurrentPosition().getX()+vx*deltaT;
	  double y=stage.getCurrentPosition().getY()+vy*deltaT;

	  stage.getCurrentPosition().setX(x);
	  stage.getCurrentPosition().setY(y);
     
   }
   /**
    * Calculates the position and velocity of a satellite in orbit
    * @param rocket
    * @param satellite
    * @param deltaT
    */
   public static void calculateSatellitePosition(Satellite satellite,double deltaT,Planet planet) {
	 //weight force
       double distance=distance(satellite.getCurrentPosition(), planet.getCurrentPosition());
       double gravityForce=PhysicsConstants.GRAVITATIONAL_CONSTANT*planet.getMass()/(distance*distance);
       
       double deltaX=satellite.getCurrentPosition().getX()-planet.getCurrentPosition().getX();
       double deltaY=satellite.getCurrentPosition().getY()-planet.getCurrentPosition().getY();
       
       double gravityX=-gravityForce*(deltaX/distance);
       double gravityY=-gravityForce*(deltaY/distance);
     //calcul of the accelaration
       double ax=gravityX;
       double ay=gravityY;
       satellite.getVectorMouvement().setAx(ax);
       satellite.getVectorMouvement().setAy(ay);
       
       
      double vx=satellite.getVectorMouvement().getVx();
      double vy=satellite.getVectorMouvement().getVy();
           
      vx=vx+ax*deltaT;
      vy=vy+ay*deltaT;
  
      satellite.getVectorMouvement().setVx(vx);
  	  satellite.getVectorMouvement().setVy(vy);
  	  //(x,y)
	  double x=satellite.getCurrentPosition().getX()+vx*deltaT;
	  double y=satellite.getCurrentPosition().getY()+vy*deltaT;
	  satellite.getCurrentPosition().setX(x);
	  satellite.getCurrentPosition().setY(y);
	  
	  satellite.getTrajectory().getPositions().add(new Position(x, y));
  	
   }
   /**
    * Calculates and updates the position of a probe over a time step using
    * Newtonian gravity and Euler integration.
    * @param probe  the probe whose position, velocity, and trajectory are updated;
	* @param deltaT the time step in seconds; should be a small positive value
	* @param planet the target planet used for gravitational calculation when the
	*               probe is in orbit;
    */
   public static void calculateProbePosition(Probe probe, double deltaT, Planet planet) {
	    double G=PhysicsConstants.GRAVITATIONAL_CONSTANT;
	    double ax=0.0;
	    double ay=0.0;

	    Planet earth=SolarSystem.getInstance().getPlanets().get(EngineConfig.earthName);

	    if (probe.isInOrbitLEO() && !probe.isTrans()) {
	        double distance=distance(probe.getCurrentPosition(),earth.getCurrentPosition());
	        double gravityForce=G*earth.getMass()/(distance*distance);
	        double deltaX=probe.getCurrentPosition().getX()-earth.getCurrentPosition().getX();
	        double deltaY=probe.getCurrentPosition().getY()-earth.getCurrentPosition().getY();
	        ax=-gravityForce*(deltaX/distance);
	        ay=-gravityForce*(deltaY/distance);
	    }

	    if (probe.isTrans()) {
	        double distanceEarth=distance(probe.getCurrentPosition(), earth.getCurrentPosition());
	        double gravityEarth=G*earth.getMass()/(distanceEarth*distanceEarth);
	        double dxEarth=probe.getCurrentPosition().getX()-earth.getCurrentPosition().getX();
	        double dyEarth=probe.getCurrentPosition().getY()-earth.getCurrentPosition().getY();
	        ax+=-gravityEarth*(dxEarth/distanceEarth);
	        ay+=-gravityEarth*(dyEarth/distanceEarth);

	        if (planet!=null) {
	            double distanceTarget=distance(probe.getCurrentPosition(),planet.getCurrentPosition());
	            double gravityTarget=G*planet.getMass()/(distanceTarget*distanceTarget);
	            double dxTarget=probe.getCurrentPosition().getX()-planet.getCurrentPosition().getX();
	            double dyTarget=probe.getCurrentPosition().getY()-planet.getCurrentPosition().getY();
	            ax+=-gravityTarget*(dxTarget/distanceTarget);
	            ay+=-gravityTarget*(dyTarget/distanceTarget);
	        }
	    }

	    if (probe.isInOrbit()) {
	        double distance=distance(probe.getCurrentPosition(), planet.getCurrentPosition());
	        double gravityForce=G*planet.getMass()/(distance*distance);
	        double deltaX=probe.getCurrentPosition().getX()-planet.getCurrentPosition().getX();
	        double deltaY=probe.getCurrentPosition().getY()-planet.getCurrentPosition().getY();
	        ax=-gravityForce*(deltaX/distance);
	        ay=-gravityForce*(deltaY/distance);
	    }

	    probe.getVectorMouvement().setAx(ax);
	    probe.getVectorMouvement().setAy(ay);

	    double vx=probe.getVectorMouvement().getVx()+ax*deltaT;
	    double vy=probe.getVectorMouvement().getVy()+ay*deltaT;
	    probe.getVectorMouvement().setVx(vx);
	    probe.getVectorMouvement().setVy(vy);

	    double x=probe.getCurrentPosition().getX()+vx*deltaT;
	    double y=probe.getCurrentPosition().getY()+vy*deltaT;
	    probe.getCurrentPosition().setX(x);
	    probe.getCurrentPosition().setY(y);

	    probe.getTrajectory().getPositions().add(new Position(x, y));
	}
   
   /**
    * Computes the escape velocity
    * @param rocket
    * @param planet
    * @return
    */
   public static double calculateEscapeVelocity(Rocket rocket,Planet planet) {
	   double gravitationalConstant=PhysicsConstants.GRAVITATIONAL_CONSTANT;
	   double planetMass=planet.getMass();
	   double distance=distance(rocket.getCurrentPosition(), planet.getCurrentPosition());
	   return Math.sqrt((2*gravitationalConstant*planetMass)/distance);
	   
	   
   }
   /**
    * Calculates the orbit velocity 
    * @param rocket
    * @param planet
    * @return
    */
   public static double calculateOrbitVelocity(double distance,Planet planet) {
	   double gravitationalConstant=PhysicsConstants.GRAVITATIONAL_CONSTANT;
	   double planetMass=planet.getMass();
	   return Math.sqrt((gravitationalConstant*planetMass)/distance);
	   
   }
   /**
    * Calculate the rocket altitude
    * @param rocket
    * @param planet
    * @return
    */
   public static double calculateAltitude(double distance,double planetRadius) {
	   if(distance-planetRadius>0) {
		   return distance-planetRadius;
	   }else {
		   return 0;
	   }
	  
   }
   
  
   
   /**
    * Calculate the air density
    * @param altitude
    * @return
    */
  public static double calculateAirDensity(double altitude) {
	  
	  double airDensity=PhysicsConstants.AIR_DENSITY;
	  return airDensity*Math.exp(-altitude/8500.0);
  
  }

  /**
   * Computes the recommended pitch angle for performing a gravity turn
   * based on the rocket's current altitude 
   * @param rocketAltitude
   * @return
   */
   public static double calculateGravityTurn(double rocketAltitude) {
	   if(rocketAltitude<1000) {
	   		return 90;
	   	}else if( rocketAltitude<5000) {
	   		return 85;
	   	}else if(rocketAltitude<10000) {
	   		return 80;
	   	}else if(rocketAltitude<15000) {
	   		return 75;
	   	}else if(rocketAltitude<20000){
	   		return 70;
	   	}else if(rocketAltitude<30000) {
	   		return 60;
	   	}else if(rocketAltitude<40000) {
	   		return 50;
	   	}else if(rocketAltitude<60000) {
	   		return 40;
	   	}else if(rocketAltitude<80000) {
	   		return 30;
	   	}else if(rocketAltitude<100000) {
	   		return 10;
	   	}
	   return 0;
	 
   }
   
    
    
}

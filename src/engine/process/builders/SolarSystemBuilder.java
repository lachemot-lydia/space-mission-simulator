package engine.process.builders;

import engine.mobile.Orbit;
import engine.data.Position;
import engine.data.SolarSystem;
import engine.data.VectorMouvement;
import engine.mobile.Planet;
import log.LoggerUtility;
import test.input.InputPlanets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import config.EngineConfig;
import config.PhysicsConstants;

/**
 *<p>This class builds the solar system by adding the orbits and the central planet of each one </p>
 *<p> after initializing their positions </p>
 * @version 2.0
 * @since 02-12-2026
 *
 */
public class SolarSystemBuilder {
	
	private static Logger logger = LoggerUtility.getLogger(SolarSystemBuilder.class, "html");

    public SolarSystemBuilder(){

    }

    public static SolarSystem buildSolarSystem(){

        SolarSystem solarSystem=SolarSystem.getInstance();
        solarSystem.reset();
        initializePlanets(solarSystem);
        initializeOrbits(solarSystem);

        return solarSystem;
    }

    private static void initializePlanets(SolarSystem solarSystem){


        try{
        	File file= new File(InputPlanets.INPUT_PATH);
            BufferedReader br=new BufferedReader(new FileReader(file));
            String line=br.readLine();
            while(line!=null){
                String[] parts=line.split(";");
                String planetName=parts[0];
                double planetMass=Double.parseDouble(parts[1]);
                double planetRadius=Double.parseDouble(parts[2]);
                double planetGravity=Double.parseDouble(parts[3]);

                //Position
                double x=Double.parseDouble(parts[4]);
                double y=Double.parseDouble(parts[5]);
                Position position=new Position(x,y);
                VectorMouvement vectorMouvement=new VectorMouvement(0, 0, 0, 0);
                Planet planet=new Planet(planetName,planetMass,planetRadius,planetGravity,position,vectorMouvement);
                logger.info("Planet creation with name: "+planet.getPlanetName());
                solarSystem.register(planetName,planet);
                line=br.readLine();

            }
            br.close();

        }catch(IOException e){
            System.err.println(e.getMessage());
        }catch(Exception e){
            System.err.println(e.getMessage());
        }



    }
    private static void initializeOrbits(SolarSystem solarSystem){

        Planet earth=solarSystem.getPlanets().get(EngineConfig.earthName);

        double earthCenterX=earth.getCurrentPosition().getX();
        double earthCenterY=earth.getCurrentPosition().getY();


        //LEO ORBIT
        double orbitLEOAltitude= PhysicsConstants.ORBIT_LEO_ALTITUDE;
        
        double orbitLEOCenterX=earthCenterX;
        double orbitLEOCenterY=earthCenterY;
        
        Position orbitLEOPosition=new Position(orbitLEOCenterX,orbitLEOCenterY);
        Orbit orbitLEO=new Orbit(earth,orbitLEOAltitude,orbitLEOPosition,EngineConfig.LEOName);
        
        logger.info("orbit creation with name: "+orbitLEO.getOrbitName());
        
        solarSystem.register(orbitLEO.getOrbitName(),orbitLEO);
        //GEO ORBIT
        double orbitGEOAltitude=PhysicsConstants.ORBIT_GEO_ALTITUDE;

        double orbitGEOCenterX=earthCenterX;
        double orbitGEOCenterY=earthCenterY;
        
        Position orbitGEOPosition=new Position(orbitGEOCenterX,orbitGEOCenterY);
        Orbit orbitGEO=new Orbit(earth,orbitGEOAltitude,orbitGEOPosition,EngineConfig.GEOName);
        
        logger.info("orbit creation with name: "+orbitGEO.getOrbitName());
        
        solarSystem.register(orbitGEO.getOrbitName(),orbitGEO);


        //LUNAR ORBIT
        Planet moon=solarSystem.getPlanets().get(EngineConfig.moonName);
        double  moonCenterX=moon.getCurrentPosition().getX();
        double  moonCenterY=moon.getCurrentPosition().getY();
        double orbitLunarAltitude=PhysicsConstants.ORBIT_LUNAR_ALTITUDE;
        double orbitLunarCenterX=moonCenterX;
        double orbitLunarCenterY=moonCenterY;
        Position orbitLunarPosition=new Position(orbitLunarCenterX,orbitLunarCenterY);
        Orbit orbitLunar=new Orbit(moon,orbitLunarAltitude,orbitLunarPosition,EngineConfig.moonName);
        logger.info("orbit creation with name: "+orbitLunar.getOrbitName());
        solarSystem.register(orbitLunar.getOrbitName(),orbitLunar);
        
        
        Planet moonD=solarSystem.getPlanets().get(EngineConfig.moonDName);
        double  moonDCenterX=moonD.getCurrentPosition().getX();
        double  moonDCenterY=moonD.getCurrentPosition().getY();
        double orbitLunarDAltitude=PhysicsConstants.ORBIT_LUNAR_ALTITUDE;
        double orbitLunarDCenterX=moonDCenterX;
        double orbitLunarDCenterY=moonDCenterY;
        Position orbitLunarDPosition=new Position(orbitLunarDCenterX,orbitLunarDCenterY);
        Orbit orbitLunarD=new Orbit(moon,orbitLunarDAltitude,orbitLunarDPosition,EngineConfig.moonDName);
        logger.info("orbit creation with name: "+orbitLunarD.getOrbitName());
        solarSystem.register(orbitLunarD.getOrbitName(),orbitLunarD);

        
        
        Planet jupiter=solarSystem.getPlanets().get(EngineConfig.jupiterName);
    
        
        double  jupiterCenterX=jupiter.getCurrentPosition().getX();
        double  jupiterCenterY=jupiter.getCurrentPosition().getY();
        double  orbitJupiterAltitude=PhysicsConstants.ORBIT_JUPITER_ALTITUDE;
        double  orbitJupiterCenterX=jupiterCenterX;
        double  orbitJupiterCenterY=jupiterCenterY;
        Position orbitJupiterPosition=new Position(orbitJupiterCenterX,orbitJupiterCenterY);
        Orbit orbitJupiter=new Orbit(jupiter,orbitJupiterAltitude,orbitJupiterPosition,EngineConfig.jupiterName);
            
       logger.info("orbit creation with name: "+orbitJupiter.getOrbitName());
       solarSystem.register(orbitJupiter.getOrbitName(),orbitJupiter);
       
        
       
        Planet mercury=solarSystem.getPlanets().get(EngineConfig.mercuryName);
        double  mercuryCenterX=mercury.getCurrentPosition().getX();
        double  mercuryCenterY=mercury.getCurrentPosition().getY();
        double  orbitMercuryAltitude=PhysicsConstants.ORBIT_MERCURY_ALTITUDE;
        double  orbitMercuryCenterX=mercuryCenterX;
        double  orbitMercuryCenterY=mercuryCenterY;
        Position orbitMercuryPosition=new Position(orbitMercuryCenterX,orbitMercuryCenterY);
        Orbit orbitMercury=new Orbit(mercury,orbitMercuryAltitude,orbitMercuryPosition,EngineConfig.mercuryName);
        logger.info("orbit creation with name: "+orbitMercury.getOrbitName());
        solarSystem.register(orbitMercury.getOrbitName(),orbitMercury);
        
        
        Planet mercuryD=solarSystem.getPlanets().get(EngineConfig.mercuryDName);
        double  mercuryDCenterX=mercuryD.getCurrentPosition().getX();
        double  mercuryDCenterY=mercuryD.getCurrentPosition().getY();
        double  orbitMercuryDAltitude=PhysicsConstants.ORBIT_MERCURY_ALTITUDE;
        double  orbitMercuryDCenterX=mercuryDCenterX;
        double  orbitMercuryDCenterY=mercuryDCenterY;
        Position orbitMercuryDPosition=new Position(orbitMercuryDCenterX,orbitMercuryDCenterY);
        Orbit orbitMercuryD=new Orbit(mercuryD,orbitMercuryDAltitude,orbitMercuryDPosition,EngineConfig.mercuryDName);
        logger.info("orbit creation with name: "+orbitMercuryD.getOrbitName());
        solarSystem.register(orbitMercuryD.getOrbitName(),orbitMercuryD);
      
      
        Planet mars=solarSystem.getPlanets().get(EngineConfig.marsName);
        double  marsCenterX=mars.getCurrentPosition().getX();
        double  marsCenterY=mars.getCurrentPosition().getY();
        double  orbitMarsAltitude=PhysicsConstants.ORBIT_MARS_ALTITUDE;
        double  orbitMarsCenterX=marsCenterX;
        double  orbitMarsCenterY=marsCenterY;
        Position orbitMarsPosition=new Position(orbitMarsCenterX,orbitMarsCenterY);
        Orbit orbitMars=new Orbit(mars,orbitMarsAltitude,orbitMarsPosition,EngineConfig.marsName);
      
        
      
        logger.info("orbit creation with name: "+orbitMars.getOrbitName());
        solarSystem.register(orbitMars.getOrbitName(),orbitMars);
    }





}

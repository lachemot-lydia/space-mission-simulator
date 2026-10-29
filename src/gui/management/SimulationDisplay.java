package gui.management;

import engine.mobile.*;
import engine.process.managers.SimulationManager;
import test.input.InputImages;

import javax.swing.*;

import config.EngineConfig;
import config.SimulationConfig;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


/**
 *<p>This class groups together the managers and the painting  </p>
 *<p>It uses the SimulationManager to access the mobiles and the solar system.</p>
 * @version 3.0
 * @since 02-12-2026
 *
 */
public class SimulationDisplay extends JPanel {

    private static final long serialVersionUID = 1L;

    private SimulationManager simulationManager;
    private SimulationPaintStrategy paintStrategy=new SimulationPaintStrategy();


    public SimulationDisplay(SimulationManager simulationManager) {

        this.simulationManager=simulationManager;

    }
    /**
     * Graphical method used to draw every components of the simulation display
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(SimulationManager.readImage(InputImages.ESPACE), 0, 0, getWidth(), getHeight(), null);
        
        Rocket rocket = simulationManager.getMobileManager().getRocket();
        Planet earth=simulationManager.getSolarSystem().getPlanets().get(EngineConfig.earthName);
   
       
        double referencePointX=SimulationConfig.dASHBOARD_CENTERX-(SimulationConfig.INITIAL_REFERENCE_X*SimulationConfig.SCALE);
        double referencePointY=SimulationConfig.dASHBOARD_CENTERY-(SimulationConfig.INITIAL_REFERENCE_Y*SimulationConfig.SCALE);
        
        
        Orbit orbitLEO=simulationManager.getSolarSystem().getOrbits().get(EngineConfig.LEOName);
        Orbit orbitGEO=simulationManager.getSolarSystem().getOrbits().get(EngineConfig.GEOName);
        
        paintStrategy.paint(earth,orbitLEO,SimulationConfig.SCALE,referencePointX,referencePointY,g);
        paintStrategy.paint(earth,orbitGEO,SimulationConfig.SCALE,referencePointX,referencePointY,g);
        paintStrategy.paint(earth,SimulationConfig.SCALE,referencePointX,referencePointY, Color.blue, g);
        
        
         
        
        
      if(EngineConfig.isInterplanetary) {
        String targetPlanetName=simulationManager.getMission().getPlanetArrival();
        Planet targetPlanet=simulationManager.getSolarSystem().getPlanets().get(targetPlanetName);
        Orbit targetOrbit=simulationManager.getSolarSystem().getOrbits().get(targetPlanet.getPlanetName());
        
        double scale=0.0;
        if(targetPlanet!=null && targetOrbit!=null) {
        	
        	if(targetPlanet.getPlanetName().equals(EngineConfig.moonName)) {
        		scale=SimulationConfig.scale_MOON;
        	}else if(targetPlanet.getPlanetName().equals(EngineConfig.jupiterName)) {
        		scale=SimulationConfig.scale_Jupiter;
        	}else {
        		scale=SimulationConfig.scale_Mercury;
        	}
            paintStrategy.paint(targetPlanet,targetOrbit,SimulationConfig.SCALE*scale,referencePointX,referencePointY ,g);
            paintStrategy.paint(targetPlanet,SimulationConfig.SCALE*scale,referencePointX,referencePointY, Color.white,g);
        }
       
        
        paintStrategy.paint(rocket,SimulationConfig.scale_ROCKET,referencePointX,referencePointY,EngineConfig.STAGE_WIDTH2, g);
        if(rocket.getSaveProbe()!=null) {
  		  paintStrategy.paint(rocket.getSaveProbe(),SimulationConfig.SCALE*scale, referencePointX, referencePointY, g);
        }

      }else if(EngineConfig.isLEO|| EngineConfig.isGEO) {
          paintStrategy.paint(rocket,SimulationConfig.scale_ROCKET,referencePointX,referencePointY,EngineConfig.STAGE_WIDTH1, g);
 
    	  for(Satellite satellite: rocket.getSaveSatellites()) {
    		  paintStrategy.paint(satellite, SimulationConfig.scale_SATELLITE, referencePointX, referencePointY, g);
    	  }

      }
        
      
        
      
        for(Stage stage: rocket.getSeparatedStages()) {
        	paintStrategy.paint(stage, SimulationConfig.scale_ROCKET, referencePointX, referencePointY, g);
        }
        
    }

    //SETTERS/GETTERS
    public void setSimulationManager(SimulationManager simulationManager) {

        this.simulationManager=simulationManager;
    }

    

}

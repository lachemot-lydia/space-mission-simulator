package gui.management;

import config.EngineConfig;
import config.SimulationConfig;
import engine.process.managers.SimulationManager;
import test.input.InputImages;
import engine.data.Position;
import engine.data.Trajectory;
import engine.mobile.*;


import java.awt.*;
import java.util.ArrayList;

/**
 *This class responsible for drawing  specifics objects on a JPanel
 * @version 2.0
 * @since 02-12-2026
 */
public class SimulationPaintStrategy {
	
    private static final int ROCKET_TRAIL_LENGTH=300;
    private static final int SATELLITE_TRAIL_LENGTH=1000;
    private static final int PROBE_TRAIL_LENGTH=300;
    private static final int HISTORY_STEP=500;
	

    /**
     *Draw the stage of the rocket on the graphics
     * @param rocket the rocket to draw his stage
     * @param graphics the graphics context
     * @see {@link SimulationConfig}
     */

    public void paint(Rocket rocket,double scale,double referencePointX,double referencePointY ,double stageWidth, Graphics graphics) { 
    	
            stageWidth=stageWidth*scale*SimulationConfig.SCALE;
        	
            double rocketHeight=rocket.getHeight()*scale*SimulationConfig.SCALE;
           
            graphics.setColor(Color.WHITE);
              
            
            
            double rocketPixelX=(rocket.getCurrentPosition().getX())*SimulationConfig.SCALE+referencePointX;
            double rocketPixelY=(rocket.getCurrentPosition().getY())*SimulationConfig.SCALE+referencePointY;
            
	  if(EngineConfig.isLEO|| EngineConfig.isGEO) {
         
          if(rocket.getSeparatedStages().isEmpty()) {
            	
            	graphics.drawImage(SimulationManager.readImage(InputImages.ROCKETLEO),(int)(rocketPixelX-stageWidth/2),(int) (rocketPixelY-rocketHeight/2),(int)stageWidth,(int)rocketHeight,null);
            
            
            	
           }else {
            	if(!rocket.getStages().isEmpty() && rocket.getStages().get(0)!=null  && rocket.getStages().get(0).getID()==1) {
               
            		graphics.drawImage(SimulationManager.readImage(InputImages.PAYLOAD),(int)(rocketPixelX-stageWidth/2),(int) (rocketPixelY-rocketHeight/2),(int)stageWidth,(int)rocketHeight,null);
            	}
          }
	  }else if(EngineConfig.isInterplanetary) {
		  if(rocket.getSeparatedStages().isEmpty()) {
          	
        	graphics.drawImage(SimulationManager.readImage(InputImages.ROCKETINTER),(int)(rocketPixelX-stageWidth/2),(int) (rocketPixelY-rocketHeight/2),(int)stageWidth,(int)rocketHeight,null);

         
         }else {
          	if(!rocket.getStages().isEmpty() && rocket.getStages().get(0)!=null  && rocket.getStages().get(0).getID()==1) {
             
            	graphics.drawImage(SimulationManager.readImage(InputImages.STAGE2INTER),(int)(rocketPixelX-stageWidth/2),(int) (rocketPixelY-rocketHeight/2),(int)stageWidth,(int)rocketHeight,null);

          	}
        }
	  }
	  
	  ArrayList<Position> positions=new ArrayList<>(rocket.getTrajectory().getPositions());
	  int start=Math.max(0, positions.size()-ROCKET_TRAIL_LENGTH);
	  Graphics2D g2=(Graphics2D) graphics;
	  paintHistoricalPoints(positions, start, referencePointX, referencePointY, g2, 3, new Color(255, 200, 120, 140));

	  for(int i=positions.size()-1; i>start && i-1 >=0;i--) {
	      Position p1=positions.get(i);
	      Position p2=positions.get(i - 1);

	      int opacity=(int)(255 * (double)(i-start)/(positions.size()-start));
	      g2.setColor(new Color(255, 165, 0, Math.max(0, Math.min(255, opacity))));

	      double x1=p1.getX()*SimulationConfig.SCALE + referencePointX;
	      double y1=p1.getY()*SimulationConfig.SCALE + referencePointY;
	      double x2=p2.getX()*SimulationConfig.SCALE + referencePointX;
	      double y2=p2.getY()*SimulationConfig.SCALE + referencePointY;

	      g2.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
	  }
	  
    }

    /**
     * Draw the planet on the graphics
     * @param planet a specific planet
     * @param orbit the orbit of the planet
     * @param graphics graphics context
     */
    public void paint(Planet planet, double scale, double referencePointX, double referencePointY, Color planetColor, Graphics graphics) {

        double planetCenterX=planet.getCurrentPosition().getX();
        double planetCenterY=planet.getCurrentPosition().getY();

        double planetPixelX=(planetCenterX)*SimulationConfig.SCALE+referencePointX;
        double planetPixelY=(planetCenterY)*SimulationConfig.SCALE+referencePointY;

        double planetDiameter=2*planet.getRadius()*scale;
        double planetX=planetPixelX-planetDiameter/2;
        double planetY=planetPixelY-planetDiameter/2;

        String name=planet.getPlanetName();
        String image=null;
        switch (name) {
            case EngineConfig.earthName:image=InputImages.EARTH;  break;
            case EngineConfig.moonName:image=InputImages.MOON; break;
            case EngineConfig.moonDName:image=InputImages.MOON;break;
            case EngineConfig.jupiterName:image=InputImages.JUPITER;break;
            case EngineConfig.mercuryName:image=InputImages.MERCURY;break;
            case EngineConfig.mercuryDName:image=InputImages.MERCURY;break;
            case EngineConfig.marsName:image=InputImages.MARS;break;
        }
        
        if (image!=null) {
            graphics.drawImage(SimulationManager.readImage(image),(int) planetX, (int) planetY, (int) planetDiameter, (int) planetDiameter, null);
        } else {
            Color previousColor = graphics.getColor();
            graphics.setColor(getPlanetColor(name));
            graphics.fillOval((int) planetX, (int) planetY, (int) planetDiameter, (int) planetDiameter);
            graphics.setColor(Color.WHITE);
            graphics.drawOval((int) planetX, (int) planetY, (int) planetDiameter, (int) planetDiameter);
            graphics.setColor(previousColor);
        }
    }
    /**
     * Draw the orbit of a planet
     * @param planet
     * @param orbit
     * @param scale
     * @param referencePointX
     * @param referencePointY
     * @param graphics
     */
    public void paint(Planet planet,Orbit orbit,double scale,double referencePointX,double referencePointY,Graphics graphics) {

        double orbitCenterX= orbit.getPosition().getX();
        double orbitCenterY= orbit.getPosition().getY();
        
        double orbitPixelX=(orbitCenterX)*SimulationConfig.SCALE+referencePointX;
        double orbitPixelY=(orbitCenterY)*SimulationConfig.SCALE+referencePointY;
        
        graphics.setColor(Color.WHITE);
        
        double orbitDiameter=2*(orbit.getAltitude()+planet.getRadius())*scale;
        double orbitX=orbitPixelX-orbitDiameter/2;
        double orbitY=orbitPixelY-orbitDiameter/2;
        
        Graphics2D graphics2d=(Graphics2D) graphics;
        Composite previousComposite=graphics2d.getComposite();
        Color previousColor=graphics2d.getColor();
        graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        graphics2d.setColor(Color.white);
        
        graphics.drawOval((int) (orbitX),(int) ( orbitY) , (int)orbitDiameter,(int) orbitDiameter);
        graphics2d.setComposite(previousComposite);
        graphics2d.setColor(previousColor);

    	
    }
    /**
     * Draw a stage of the rocket
     * @param stage
     * @param scale
     * @param referencePointX
     * @param referencePointY
     * @param graphics
     */
    
    public void paint(Stage stage,double scale,double referencePointX,double referencePointY,Graphics graphics) {
    	double stageCenterX=stage.getCurrentPosition().getX();
    	double stageCenterY=stage.getCurrentPosition().getY();
    	
    	double stagePixelX=(stageCenterX)*SimulationConfig.SCALE+referencePointX;
        double stagePixelY=(stageCenterY)*SimulationConfig.SCALE+referencePointY;
   
        double stageWidth=EngineConfig.STAGE_WIDTH1*scale*SimulationConfig.SCALE;
        double stageHeight=stage.getHeight()*scale*SimulationConfig.SCALE;
        
      if(EngineConfig.isLEO|| EngineConfig.isGEO) {
    	  
        if(stage.getID()==0) {
        	graphics.drawImage(SimulationManager.readImage(InputImages.STAGE1LEO),(int)(stagePixelX-stageWidth/2 ),(int) (stagePixelY-stageHeight/2),(int)stageWidth,(int)stageHeight,null);
        }else if(stage.getID()==1) {
        	graphics.drawImage(SimulationManager.readImage(InputImages.STAGE2LEO),(int)(stagePixelX-stageWidth/2 ),(int) (stagePixelY-stageHeight/2),(int)stageWidth,(int)stageHeight,null);

        }
        
      }else if(EngineConfig.isInterplanetary) {
    	  
    	  if(stage.getID()==0) {
        	graphics.drawImage(SimulationManager.readImage(InputImages.STAGE1INTER),(int)(stagePixelX-stageWidth/2),(int) (stagePixelY-stageHeight/2),(int)stageWidth,(int)stageHeight,null);

    	  }else if(stage.getID()==1) {
        	graphics.drawImage(SimulationManager.readImage(InputImages.STAGE2INTER),(int)(stagePixelX-stageWidth/2),(int) (stagePixelY-stageHeight/2),(int)stageWidth,(int)stageHeight,null);

          }else if(stage.getID()==2) {
          	graphics.drawImage(SimulationManager.readImage(InputImages.STAGE3INTER),(int)(stagePixelX-stageWidth/2),(int) (stagePixelY-stageHeight/2),(int)stageWidth,(int)stageHeight,null);

          }
    	  
      }
        
    	
    }
    /**
     * Draw a satellite
     * @param satellite
     * @param scale
     * @param referencePointX
     * @param referencePointY
     * @param graphics
     */
    public void paint(Satellite satellite,double scale,double referencePointX,double referencePointY,Graphics graphics) {
    	
    	double satelliteCenterX=satellite.getCurrentPosition().getX();
    	double satelliteCenterY=satellite.getCurrentPosition().getY();
    	
    	double satellitePixelX=(satelliteCenterX)*SimulationConfig.SCALE+referencePointX;
        double satellitePixelY=(satelliteCenterY)*SimulationConfig.SCALE+referencePointY;
   
        
        double satelliteWidth=20;
        double satelliteHeight=20;
        
        graphics.setColor(Color.cyan);
    	graphics.drawImage(SimulationManager.readImage(InputImages.SATELLITE),(int)(satellitePixelX-satelliteWidth/2 ),(int) (satellitePixelY-satelliteHeight/2),(int)satelliteWidth,(int)satelliteHeight,null);
    	graphics.setColor(Color.orange);
        
	    ArrayList<Position> positions=new ArrayList<>(satellite.getTrajectory().getPositions());
	   	  int start=Math.max(0, positions.size()-SATELLITE_TRAIL_LENGTH);
	   	  Graphics2D g2=(Graphics2D) graphics;
	   	  paintHistoricalPoints(positions, start, referencePointX, referencePointY, g2, 3, Color.orange);

   	    for(int i=positions.size()-1; i>start && i-1 >=0;i--) {
   	      Position p1=positions.get(i);
   	      Position p2=positions.get(i - 1);

   	      int opacity=(int)(255 * (double)(i-start)/(positions.size()-start));
   	      g2.setColor(new Color(255, 165, 0, Math.max(0, Math.min(255, opacity))));

   	      double x1=p1.getX()*SimulationConfig.SCALE + referencePointX;
   	      double y1=p1.getY()*SimulationConfig.SCALE + referencePointY;
   	      double x2=p2.getX()*SimulationConfig.SCALE + referencePointX;
   	      double y2=p2.getY()*SimulationConfig.SCALE + referencePointY;

   	      g2.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
   	  }
   	  

    }
    
    
    
    public void paint(Probe probe,double scaleTarget,double referencePointX,double referencePointY,Graphics graphics) {
    	
    	int probeCenterX=(int) probe.getCurrentPosition().getX();
    	int probeCenterY=(int) probe.getCurrentPosition().getY();
    	
    	int probePixelX=(int) ((probeCenterX)*SimulationConfig.SCALE+referencePointX);
        int probePixelY=(int) ((probeCenterY)*SimulationConfig.SCALE+referencePointY);
       
        int probeWidth=15;
        int probeHeight=15;
        graphics.setColor(Color.cyan);
    	graphics.drawImage(SimulationManager.readImage(InputImages.PROBE),(int)(probePixelX-probeWidth/2 ),(int) (probePixelY-probeHeight/2),(int)probeWidth,(int)probeHeight,null);
    	graphics.setColor(Color.orange);
        
    	 ArrayList<Position> positions=new ArrayList<>(probe.getTrajectory().getPositions());
	   	  int start=Math.max(0, positions.size()-PROBE_TRAIL_LENGTH);
	   	  Graphics2D g2=(Graphics2D) graphics;
	   	  paintHistoricalPoints(positions, start, referencePointX, referencePointY, g2,3, Color.orange);
	
	   	  for(int i=positions.size()-1; i>start && i-1 >=0;i--) {
	   	      Position p1=positions.get(i);
	   	      Position p2=positions.get(i - 1);
	
	   	      int opacity=(int)(255 * (double)(i-start)/(positions.size()-start));
	   	      g2.setColor(new Color(255, 165, 0, Math.max(0, Math.min(255, opacity))));
	
	   	      double x1=p1.getX()*SimulationConfig.SCALE + referencePointX;
	   	      double y1=p1.getY()*SimulationConfig.SCALE + referencePointY;
	   	      double x2=p2.getX()*SimulationConfig.SCALE + referencePointX;
	   	      double y2=p2.getY()*SimulationConfig.SCALE + referencePointY;
	
	   	      g2.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
	   	  }
    	
    	
	   	 double planetCenterX = probe.getTargetPlanet().getCurrentPosition().getX();
	   	 double planetCenterY = probe.getTargetPlanet().getCurrentPosition().getY();

         int planetPixelX=(int)((planetCenterX)*SimulationConfig.SCALE+referencePointX);
         int planetPixelY=(int)((planetCenterY)*SimulationConfig.SCALE+referencePointY);
         
         int planetDiameter=(int)(2*probe.getTargetPlanet().getRadius()*scaleTarget);
         
         int planetX=(int) (planetPixelX);
         int planetY=(int) (planetPixelY-planetDiameter/2-(probe.getTargetOrbit().getAltitude()*scaleTarget));
        		 
         Graphics2D graphics2d=(Graphics2D) graphics;
         Composite previousComposite=graphics2d.getComposite();
         Color previousColor=graphics2d.getColor();
         graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
         graphics2d.setColor(Color.white);
         
        if(!probe.isInOrbit() && !probe.isLanded()) {
    	 graphics2d.drawLine(probePixelX ,probePixelY, planetX, planetY);
        }
         graphics2d.setComposite(previousComposite);
         graphics2d.setColor(previousColor);
    	

    }
    
    private void paintHistoricalPoints(ArrayList<Position> positions, int trailStart, double referencePointX,
            double referencePointY, Graphics2D graphics, int markerSize, Color markerColor) {
        if(trailStart<=0) {
            return;
        }
        Composite previousComposite=graphics.getComposite();
        Color previousColor=graphics.getColor();
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, markerColor.getAlpha() / 255f));
        graphics.setColor(new Color(markerColor.getRed(), markerColor.getGreen(), markerColor.getBlue()));

        for(int i=0;i<trailStart;i+=HISTORY_STEP){
            Position position=positions.get(i);
            int pixelX=(int)(position.getX()*SimulationConfig.SCALE+referencePointX);
            int pixelY=(int)(position.getY()*SimulationConfig.SCALE+referencePointY);
            graphics.fillOval(pixelX-markerSize/2, pixelY-markerSize/2,markerSize,markerSize);
        }

        graphics.setComposite(previousComposite);
        graphics.setColor(previousColor);
    }

    private Color getPlanetColor(String planetName) {
        if(EngineConfig.jupiterName.equals(planetName)) {
            return new Color(196, 145, 92);
        }else {
        	return Color.magenta;
        }
      
    }

}

package gui.management;


import config.EngineConfig;
import engine.data.TankPropellant;
import engine.mobile.Rocket;
import engine.mobile.Satellite;

/**
 * Extracts chart-ready telemetry data from the rocket or its active payload.
 * @version 3.0
 */
public class ChartsData {

	Rocket rocket;
	
	public ChartsData(Rocket rocket) {
		this.rocket=rocket;
	}
	
	
	public double[] updateChartsData() {
		double vx=0.0;
		double vy=0.0;
		double velocity=0.0;
		double altitude=0.0;
		
	
	   if(EngineConfig.isLEO|| EngineConfig.isGEO) {
		   
		   if(!rocket.getSaveSatellites().isEmpty() ) {
			    Satellite satellite=rocket.getSaveSatellites().get(0);
			    vx=satellite.getVectorMouvement().getVx();
			    vy=satellite.getVectorMouvement().getVy();
			    altitude=satellite.getAltitude();
	   	
	   	   }else {
		   		vx=rocket.getVectorMouvement().getVx();
			    vy=rocket.getVectorMouvement().getVy();
			      
			    altitude=rocket.getAltitude();
	   	   }	 
	        
	        	
	        	
	  }else if(EngineConfig.isInterplanetary) {
	        
		 if(rocket.getSaveProbe()==null) {
			 vx=rocket.getVectorMouvement().getVx();
		     vy=rocket.getVectorMouvement().getVy();
			 altitude=rocket.getAltitude();
		            
	       }else{
			   vx=rocket.getSaveProbe().getVectorMouvement().getVx();
			   vy=rocket.getSaveProbe().getVectorMouvement().getVy();
			   altitude=rocket.getSaveProbe().getAltitude();
	        	 }
	        	 
		}
	    
	    velocity=Math.sqrt(vx*vx+vy*vy);
	    
	    return new double[] {velocity,altitude};
	}
	
	public double updatePropellantChart() {
		
		if(rocket.getCurrentStage()==null) return 0.0;
		
		TankPropellant tankPropellant=rocket.getCurrentStage().getPropulsion().getTankPropellant();
		
		return tankPropellant.getPropellantMass();
		
		        	
		
	}
	
	
	public int getCurrentStageId() {
		
		if(rocket.getCurrentStage()==null) return -1;
		
		return rocket.getCurrentStage().getID();
	}
		
		
	
	
}


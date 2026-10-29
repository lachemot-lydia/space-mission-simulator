package gui.management;

import config.EngineConfig;
import config.SimulationConfig;
import engine.data.Position;
import engine.mobile.Rocket;

/**
 * Manages the simulation viewport focus, keeping the camera centered
 * on the active vehicle during the mission.
 * @version 3.0
 */
public class FocusManager {
	
	public FocusManager() {
		
	}
	
	public void updateFocus(Rocket rocket) {
		if(EngineConfig.isLEO|| EngineConfig.isGEO) {
			updateFocusLEO(rocket);
		}else if(EngineConfig.isInterplanetary) {
			updateFocusInterplanetery(rocket);
		}
	}
	
	public void updateFocusLEO(Rocket rocket) {
    	if(!rocket.getSaveSatellites().isEmpty() ) {
    		focusOn(rocket.getSaveSatellites().get(0).getCurrentPosition());
    	
    	}else {
    		focusOn(rocket.getCurrentPosition());
    	}
	}
	
	public void updateFocusInterplanetery(Rocket rocket) {
		 if(rocket.getSaveProbe()!=null) {
			 focusOn(rocket.getSaveProbe().getCurrentPosition());
			 
		 }else {
			 focusOn(rocket.getCurrentPosition());
		 }
	}
	
	public void focusOn(Position position) {
		
		SimulationConfig.dASHBOARD_CENTERX=(SimulationConfig.DASHBOARD_WIDTH/2)-position.getX()
				*SimulationConfig.SCALE+SimulationConfig.INITIAL_REFERENCE_X * SimulationConfig.SCALE;
		SimulationConfig.dASHBOARD_CENTERY=(SimulationConfig.DASHBOARD_HEIGHT/2)-position.getY()
				*SimulationConfig.SCALE+SimulationConfig.INITIAL_REFERENCE_Y * SimulationConfig.SCALE;
		
		
	}

}

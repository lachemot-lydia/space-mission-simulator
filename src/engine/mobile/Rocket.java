package engine.mobile;


import config.EngineConfig;
import engine.data.Position;
import engine.data.Trajectory;
import engine.data.VectorMouvement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * It is a daughter class that extends from {@link MobileElement}
 * This class defines a spatial rocket, it is made of several stages,
 * Those stages can be separated from the main part during the launch.
 * 
 * @version 2.0
 * @since 02-12-2026
 */
public class Rocket extends MobileElement {

    /**
    *  A group of {@link Stage}
    */
    private ArrayList<Stage> stages=new ArrayList<Stage>();
    /**
    * The number of stages in the rocket 
    * @see EngineConfig
    */
    private int NumberStages;
   
	/**
    * Shows the state of the rocket during it's launch
    */
    private String rocketState;
    /**
    * The playload wich is located in the head of the rocket
    * @see Payload
    */
    private Payload payload;
    /**
     * the rocket for geo/leo or interplanatery mission
     */
    private int type;
    /**
     * the Actif stage
     */
    private Stage currentStage;
    /**
     * the angle of the stage
     */
    private double angle;
     
    private double height;
    
    private double altitude;
    
    private ArrayList<Stage> separatedStages=new ArrayList<Stage>();
    
   
	private Trajectory trajectory;

   private Planet targetPlanet;
	
 
   
   
/**
    * To save  the satellites after letting go the payload
    */
   private List<Satellite> saveSatellites=new ArrayList<Satellite>();

   /**
    * 
    */
   private Probe saveProbe;

		



	public Rocket(int type,double mass,double altitude, Position position,Payload payload,VectorMouvement vectorMouvement,double height) {

        super(mass,position,vectorMouvement);
        this.payload = payload;
        this.type=type;
        this.height=height;
        this.altitude=altitude;
        
    }

    //GETTERS/SETTERS********************************************

   
    public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public Payload getPayload(){
        return payload;
    }
    public ArrayList<Stage> getStages() {
		return stages;
	}

	public void setStages(ArrayList<Stage> stages) {
		this.stages = stages;
	}

	public void setPayload(Payload payload){
        this.payload = payload;
    }
    public int getStageNumber(){
        return NumberStages;
    }
    public void setStageNumber(int stageNumber){
        this.NumberStages=stageNumber;
    }
    public void setType(int type) {
    	this.type=type;
    }
    public int getType() {
    	return this.type;
    }
    public void setCurrentStage(Stage stage) {
    	this.currentStage=stage;
    	
    }
    public Stage getCurrentStage() {
    	return this.currentStage;
    }
   
    public double getAngle() {
    	return this.angle;
    }
    public void setAngle(double angle) {
    	this.angle=angle;
    }

    public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	 public String getRocketState() {
			return rocketState;
		}

		public void setRocketState(String rocketState) {
			this.rocketState = rocketState;
		}
		public ArrayList<Stage> getSeparatedStages() {
			return separatedStages;
		}

		public void setSeparatedStages(ArrayList<Stage> separatedStages) {
			this.separatedStages = separatedStages;
		}

		public Trajectory getTrajectory() {
			return trajectory;
		}

		public void setTrajectory(Trajectory trajectory) {
			this.trajectory = trajectory;
		}
		
		 public Planet getTargetPlanet() {
				return targetPlanet;
			}

			public void setTargetPlanet(Planet targetPlanet) {
				this.targetPlanet = targetPlanet;
			}
			public List<Satellite> getSaveSatellites() {
				return saveSatellites;
			}

			public Probe getSaveProbe() {
				return saveProbe;
			}

			public void setSaveSatellites(List<Satellite> saveSatellites) {
				this.saveSatellites = saveSatellites;
			}

			public void setSaveProbe(Probe saveProbe) {
				this.saveProbe = saveProbe;
			}
			
   //METHODS************************************

    public void addStage(Stage stage){

        stages.add(stage);
    }

    public void removeStage(Stage stage){
        stages.remove(stage);
    }
    
   





}

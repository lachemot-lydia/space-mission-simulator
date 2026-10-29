package engine.data;

/**
 *
 * <p>This class describes the mission done by the user to save it in a specific file.</p>
 * @version 2.0
 * @since 2026-02-12
 */
public class Mission {
    /**
    * <p>The name of the space mission</p>
    */
    private String name;
    /**
    * <p>The description of the mission</p>
    */
    private String description;
    /**
    * <p>The planet where the mission begin</p>
    */
    private String PlanetDeparture;
    /**
    * <p>The planet where the mission ends</p>
    */
    private String PlanetArrival;
   /**
    * <p>The time spent during the mission</p>
    */
    private double missionDuration;
    /**
     * The type of the mission
     */
    private String missionType;

    public Mission(String missionType,String name, String description, String planetDeparture, String planetArrival) {
    	this.missionType=missionType;
    	this.name = name;
        this.description = description;
        this.PlanetDeparture = planetDeparture;
        this.PlanetArrival = planetArrival;
        this.missionDuration=0;
       
        
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlanetDeparture() {
        return PlanetDeparture;
    }
    public void setPlanetDeparture(String planetDeparture) {
        this.PlanetDeparture = planetDeparture;
    }

    public String getPlanetArrival() {
        return PlanetArrival;
    }
    public void setPlanetArrival(String planetArrival) {
        this.PlanetArrival = planetArrival;
    }

   

    public double getDuration() {
        return missionDuration;
    }
    public void setDuration(double missionDuration) {
        this.missionDuration = missionDuration;
    }
    public void setMissionType(String missionType) {
		this.missionType = missionType;
	}
    public String getMissionType() {
    	return this.missionType;
    }




    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Type: ").append(this.missionType).append("\n");
        sb.append("Name: ").append(this.name).append("\n");
        sb.append("Description: ").append(this.description).append("\n");
        sb.append("Planet Departure: ").append(this.PlanetDeparture).append("\n");
        sb.append("Planet Arrival: ").append(this.PlanetArrival).append("\n");
       
        sb.append("Mission Duration: ").append(this.getDuration()).append(" days\n");
        return sb.toString();
    }


}

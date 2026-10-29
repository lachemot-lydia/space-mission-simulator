package engine.mobile;


import config.EngineConfig;
import engine.data.Position;
import engine.data.VectorMouvement;

import java.util.ArrayList;
import java.util.List;

/**
 * It is a daughter class that extends from {@link MobileElement}
 * This is class defines a payload of a rocket,
 * Which is a place where we stock satellites or a probes.
 * 
 * @version 2.0
 * @since 02-12-2026
 */
public class Payload extends MobileElement {
    /**
    * A list of satellites implemented by an Array list
    */
    private List<Satellite> satellites=new ArrayList<Satellite>();
    //private List<Probe> probe=new Arraylist<Satellite>();
    
    /**
    * The amount of satellites stocked in the payload{@linked Constants}
    */
    private int satelliteNumber= EngineConfig.MAX_SATTELITE_NUMBER;

    /**
    * initialization of a probe
    */
    private Probe probe;
    
    /**
     * 
     */
    
    private double separationAltitude;
    private double altitude;
    
    private boolean isSeparated;
    /*
     * The height of the payload 
     */
    private double height;
    
    public Payload(double mass,Position position,VectorMouvement vectorMouvement,double altitude,double height,double separationAltitude) {
        super(mass,position,vectorMouvement);
        this.altitude=altitude;
        this.height=height;
        this.separationAltitude=separationAltitude;
    }
   
	//GETTERS/SETTERS*****************************************
    public void setSatellites(List<Satellite> satellites){
        this.satellites=satellites;
    }
    public List<Satellite> getSatellites() {
        return satellites;
    }
    public void setProbe(Probe probe) {
        this.probe=probe;

    }
    public Probe getProbe() {
        return probe;
    }
    public int getSatelliteNumber() {
        return satelliteNumber;
    }
    public void setSatelliteNumber(int satelliteNumber) {
        this.satelliteNumber = satelliteNumber;
    }
    public boolean getIsSeparated() {
    	return this.isSeparated;
    }
    public void setIsSeparated(boolean isSeparated) {
    	this.isSeparated=isSeparated;
    }

    /**
    * Adding a new satellite into the payload
    * @param satellite
    */
    public void addSatellite(Satellite satellite){
        satellites.add(satellite);
    }
    /**
    * Removing a satellite from the payload
    * @param satellite
    */
    public void removeSatellite(Satellite satellite){
        satellites.remove(satellite);
    }
    public void setSeparationAltitude(double separationAltitude) {
    	this.separationAltitude=separationAltitude;
    }
    public double getSeparationAltitude() {
    	return this.separationAltitude;
    }
    public double getAltitude() {
        return altitude;
    }
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
    public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public void setSeparated(boolean isSeparated) {
		this.isSeparated = isSeparated;
	}


}

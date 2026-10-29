package test.unit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import config.EngineConfig;
import engine.data.Mission;
import engine.data.Propulsion;
import engine.mobile.Rocket;
import engine.process.builders.MissionBuilder;
import engine.process.builders.PropulsionBuilder;
import engine.process.builders.RocketBuilder;
import engine.process.builders.SimulationBuilder;
import engine.process.managers.SimulationManager;
import gui.EntryGUI;

/**
 * Unit test of the rocketBuilder
 * @version 2.0
 * @since 2026-03-15
 *
 */


public class TestRocketBuilder {
	
	private Rocket rocket;
	private SimulationManager simulationManager;
	private Mission mission;
	private ArrayList<Propulsion> propulsions=new ArrayList<Propulsion>();
	private Propulsion propulsion1;
	private Propulsion propulsion2;
	
	@Before
	public void prepareSimulation() {
		mission=MissionBuilder.buildMission(EngineConfig.LEOName,"","","earth");
		propulsion1=PropulsionBuilder.buildPropulsion(EngineConfig.FIRST_THRUST_LG,
				EngineConfig.FIRST_PROPELLANT_LG, EngineConfig.FIRST_ISP, EngineConfig.FIRST_ENGINE_MASS_LG);
		propulsions.add(propulsion1);
		
		propulsion2=PropulsionBuilder.buildPropulsion(EngineConfig.SECOND_THRUST_LG,
				EngineConfig.SECOND_PROPELLANT_LG, EngineConfig.SECOND_ISP, EngineConfig.SECOND_ENGINE_MASS_LG);
		propulsions.add(propulsion2);
	
		simulationManager=SimulationBuilder.buildSimulation(mission, propulsions);
		    	
		rocket=RocketBuilder.buildRocket(1, mission, propulsions);
		
	}
	
	
	
	@Test
	public void testSimulationBuilder() {
		assertNotNull(propulsion1);
		assertNotNull(propulsion2);
		assertNotNull(mission);
		assertNotNull(propulsions);
		assertNotNull(simulationManager);
	}
	
	
	
	@Test
	public void testRocketBuilder() {
		assertNotNull(rocket);
		assertNotNull(rocket.getStages().get(0));
		assertEquals(422400.0, rocket.getStages().get(0).getMass(),0.0001);
		assertEquals(97670.0,rocket.getStages().get(1).getMass(),0.00001);
		assertEquals(70, rocket.getHeight(),3);
	}
	
	
	
	

}

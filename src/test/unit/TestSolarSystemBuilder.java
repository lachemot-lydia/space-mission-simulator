package test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import config.EngineConfig;
import config.PhysicsConstants;

/**
 * Unit test of the solar system builder
 */

import engine.data.SolarSystem;
import engine.mobile.Planet;
import engine.process.builders.SolarSystemBuilder;
/**
 * The unit test of the solar system builder
 * @version 2.0
 * @since 2026-03-15
 *
 */

public class TestSolarSystemBuilder {

	private SolarSystem solarSystem;
	
	@Before
	public void prepareSolarSystem() {
		
		solarSystem=SolarSystemBuilder.buildSolarSystem();
		
	}
	@Test
	public void testSolarSystem() {
		assertNotNull(solarSystem);
	}
	@Test 
	public void testSolarSystemValues() {
		Planet earth=solarSystem.getPlanets().get(EngineConfig.earthName);
		assertNotNull(earth);
		assertEquals("earth", earth.getPlanetName());
		assertEquals(40000000.0,earth.getCurrentPosition().getX(),0.00001);
		assertEquals(25000000.0, earth.getCurrentPosition().getY(),0.00001);
		
		
		Planet moon=solarSystem.getPlanets().get(EngineConfig.moonName);
		assertNotNull(moon);
		assertEquals("moon",moon.getPlanetName());
		
		Planet moonD=solarSystem.getPlanets().get(EngineConfig.moonDName);
		assertNotNull(moonD);
		assertEquals("moonD",moonD.getPlanetName());

		Planet jupiter=solarSystem.getPlanets().get(EngineConfig.jupiterName);
		assertNotNull(jupiter);
		assertEquals("jupiter", jupiter.getPlanetName());
		assertNotNull(solarSystem.getOrbits().get(EngineConfig.jupiterName));
		assertEquals(PhysicsConstants.ORBIT_JUPITER_ALTITUDE,
				solarSystem.getOrbits().get(EngineConfig.jupiterName).getAltitude(),0.00001);
		
		Planet mercury=solarSystem.getPlanets().get(EngineConfig.mercuryName);
		assertNotNull(mercury);
		assertEquals("mercury", mercury.getPlanetName());
		assertNotNull(solarSystem.getOrbits().get(EngineConfig.mercuryName));
		assertEquals(PhysicsConstants.ORBIT_MERCURY_ALTITUDE,solarSystem.getOrbits().get(EngineConfig.mercuryName).getAltitude(),0.00002);
		
		Planet mercuryD=solarSystem.getPlanets().get(EngineConfig.mercuryDName);
		assertNotNull(mercuryD);
		assertEquals("mercuryD", mercuryD.getPlanetName());
		assertNotNull(solarSystem.getOrbits().get(EngineConfig.mercuryDName));
		assertEquals(PhysicsConstants.ORBIT_MERCURY_ALTITUDE,solarSystem.getOrbits().get(EngineConfig.mercuryName).getAltitude(),0.00002);
	
		
		
		Planet mars=solarSystem.getPlanets().get(EngineConfig.marsName);
		assertNotNull(mars);
		assertEquals("mars", mars.getPlanetName());
		assertNotNull(solarSystem.getOrbits().get(EngineConfig.marsName));
		assertEquals(PhysicsConstants.ORBIT_MARS_ALTITUDE,solarSystem.getOrbits().get(EngineConfig.marsName).getAltitude(),0.00001);
	
	
	}

	
}

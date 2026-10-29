package config;

/**
 * <p>This class contains values used throughout the projects  ,</p>
 * <p>such us height, width of each stage, and more.</p>
 * @version 2.0
 * @since 2026-02-12
 */
public class EngineConfig {
	
    //ISP
    public static final double FIRST_ISP=282.0;//(s)
    public static final double SECOND_ISP=348.0;//(s)
    public static final double THIRD_ISP=435.0;//(s)
    
    //TYPE 1 OF ROCKET (EXP: Falcon9)***************************************************************
    public static final int STAGE_WIDTH1=4;
    public static final int MAX_SATTELITE_NUMBER=3;
    public static final int MAX_STAGES_NUMBER_LG=2;
    //SATELLITE
    public static final double MASS_SATELLITE=1500; //(kg)
    public static final double SATELLITE_HEIGHT=4; //(m)
    public static final double SATELLITE_WIDTH=2; //(m)
    
    //STAGES HEIGHTS LEO/GEO MISSION (ex: Falcon9)
    public static final double FIRST_STAGE_HEIGHT_LG=41.2;//(m)
    public static final double SECOND_STAGE_HEIGHT_LG=13.8;//(m)
    public static final double PAYLOAD_HEIGHT_LG=13;//(m)
    //MASS OF THE ENGINE ROCKET
    public static final double FIRST_ENGINE_MASS_LG=4500;//(kg)
    public static final double SECOND_ENGINE_MASS_LG=1000;//(kg)
    
  //THRUST / PROPELLANT (user can choose or change)
    public static double FIRST_THRUST_LG=7607000;//(N)
    public static double SECOND_THRUST_LG=981000;//(N)
    	
    
    public static double FIRST_PROPELLANT_LG=395700;//(KG)
    public static double SECOND_PROPELLANT_LG=92670;
    
  //SEPARATION ALTITUDE
    public static final double SEPARATED_ALTITUDE_STAGE1_LEO=80000;//(m)
    public static final double SEPARATED_ALTITUDE_STAGE2_LEO=180000;//(m)
    public static final double SEPARATED_ALTITUDE_STAGE2_GEO=PhysicsConstants.ORBIT_GEO_ALTITUDE;//(m)
    public static final double SEPARATED_ALTITUDE_PAYLOAD_LEO=PhysicsConstants.ORBIT_LEO_ALTITUDE;//(m)

  //STAGE DRY MASS
    public static final double STAGE1_DRY_MASS1=22200;//(kg)
    public static final double STAGE2_DRY_MASS1=4000;//(kg)

    
    //****************************************************************************************************

    
    //TYPE 2 OF ROCKET(EXP: SaturnV)*****************************************************************
    public static final int STAGE_WIDTH2=10;
    public static final int MAX_STAGES_NUMBER_INTER=3;
    //STAGES HEIGHTS INTERPLANATERY MISSION (ex: SATURN v)
    public static final double FIRST_STAGE_HEIGHT_INTER=42.1;//(m)
    public static final double SECOND_STAGE_HEIGHT_INTER=24.9;//(m)
    public static final double THIRD_STAGE_HEIGHT_INTER=17.8;//(m)
    public static final double PAYLOAD_HEIGHT_INTER=17.8;//(m)
    //MASS OF THE ENGINE ROCKET
    public static final double FIRST_ENGINE_MASS_INTER=10000;//(kg)
    public static final double SECOND_ENGINE_MASS_INTER=5000;//(kg)
    public static final double THIRD_ENGINE_MASS_INTER=3000;//(kg)
    
  //THRUST / PROPELLANT (user can choose or change) 
    public static double FIRST_THRUST_INTER=35100000; //(N)
    public static double SECOND_THRUST_INTER=15000000; //(N)
    public static double THIRD_THRUST_INTER=2000000; //(N)
    public static double FIRST_PROPELLANT_INTER=2300000; //(kg)
    public static double SECOND_PROPELLANT_INTER=450000; //(kg)
    public static double THIRD_PROPELLANT_INTER=150000; //(kg)
    
  //STAGE DRY MASS
    public static final double STAGE1_DRY_MASS2=131000;//(kg)
    public static final double STAGE2_DRY_MASS2=36000;//(kg)
    public static final double STAGE3_DRY_MASS2=10000;//(kg)
    
    //PROBE
    public static final double PROBE_MASS=318;
    public static final double PROBE_WIDTH=2.7;
    public static final double PROBE_HEIGHT=1.5;

    
    //SEPARATION ALTITUDE 
    public static final double SEPARATED_ALTITUDE_STAGE1_INTER=62000;//(m)
    public static final double SEPARATED_ALTITUDE_STAGE2_INTER=185000;
    public static final double SEPARATED_ALTITUDE_STAGE3=180000;
    public static final double SEPARATED_ALTITUDE_PAYLOAD_INTER=200000;//(m)

   
  //mission choice
    public static boolean isLEO=true;
    
    public static boolean isGEO=false;
    public static boolean isInterplanetary=false;
    
    public static final String moonName="moon";
    public static final String moonDName="moonD";
    public static final String jupiterName="jupiter";
    public static final String mercuryName="mercury";
    public static final String mercuryDName="mercuryD";
    public static final String marsName="mars";
    public static final String earthName="earth";
    public static final String LEOName="leo";
    public static final String GEOName="geo";
    public static final String InterplaneteryName="Interplanetary";
    
   
   

    
    
   
}

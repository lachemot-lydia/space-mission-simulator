package engine.process.builders;

import config.EngineConfig;
import engine.data.Mission;

/**
 * <p>This class builds a mission and saves it into a text file after receiving it. </p>
 * @version 2.0
 * @date 02-12-2026
 */

public class MissionBuilder {

        public MissionBuilder() {

        }

        public static Mission buildMission(String missionType,String name, String description,String planetArrival)  {
        	return new Mission(missionType,name,description,EngineConfig.earthName,planetArrival);
            
        }

    }

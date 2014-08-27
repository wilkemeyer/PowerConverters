package powercrystals.powerconverters.power;

import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to manage the power systems.
 */
public class PowerSystemManager {
    private static PowerSystemManager instance;

    private Map<String, PowerSystem> powerSystems = new HashMap<String, PowerSystem>();

    private List<String> systemIds = new ArrayList<String>();

    public static PowerSystemManager getInstance() {
        if(instance == null) {
            instance = new PowerSystemManager();
        }
        return instance;
    }


    public void registerPowerSystem(PowerSystem powerSystem) {
        registerPowerSystem(powerSystem.getId(), powerSystem);
        systemIds.add(powerSystem.getId());
    }

    public void registerPowerSystem(String id, PowerSystem powerSystem) {
        powerSystems.put(id, powerSystem);
    }

    public PowerSystem getPowerSystemByName(String name) {
        return powerSystems.containsKey(name) ? powerSystems.get(name) : null;
    }

    public int getPowerSystemId(String name) {
        return systemIds.lastIndexOf(name);
    }

    public PowerSystem getPowerSystemById(int id) {
        String name = systemIds.get(id);
        return getPowerSystemByName(name);
    }

    public void registerBlocks() {
        for(PowerSystem powerSystem : powerSystems.values()) {
            powerSystem.registerBlocks();
        }
    }

    public void registerCommonRecipes() {
        for(PowerSystem powerSystem : powerSystems.values()) {
            powerSystem.registerCommonRecipes();
        }
    }

    public void loadConfigs(Configuration c) {
        for(PowerSystem powerSystem : powerSystems.values()) {
            powerSystem.loadConfig(c);
        }
    }

    public void saveConfigs(Configuration c) {
        for(PowerSystem powerSystem : powerSystems.values()) {
            powerSystem.saveConfig(c);
        }
    }
}

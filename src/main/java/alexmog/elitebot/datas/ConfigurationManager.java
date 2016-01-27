package alexmog.elitebot.datas;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import alexmog.elitebot.Main;

public class ConfigurationManager {
    public static final String CONFIGS_FILE = Main.USERDATA_PATH + "configs.properties";
    private static final ConfigurationManager instance = new ConfigurationManager();
    
    private ConfigurationManager() {}
    
    public static ConfigurationManager getInstance() {
        return instance;
    }
    
    public Properties createConfigProperties() {
        return new Properties(); // TODO
    }
    
    public void setConfigs(Properties config) {
        // TODO
    }
    
    public void initializeConfig() throws IOException {
        File f = new File(CONFIGS_FILE);
        if (!f.exists()) {
            f.createNewFile();
        }
        ResourcesManager.getInstance().loadConfiguration();
    }
}

package alexmog.elitebot.datas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public class ResourcesManager {
    private static final ResourcesManager instance = new ResourcesManager();
    private EliteDangerousLauncherFiner mEliteDangerousFiner = new EliteDangerousLauncherFiner();
    
    private ResourcesManager() {}
    
    public static ResourcesManager getInstance() {
        return instance;
    }
    
    public boolean findEliteDangerousPath() throws IOException {
        for (File f : File.listRoots()) {
            Files.walkFileTree(f.toPath(), mEliteDangerousFiner);
            if (mEliteDangerousFiner.getPathsFound().size() > 0) return true;
        }
        return false;
    }
    
    public void loadConfiguration() throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(new File(ConfigurationManager.CONFIGS_FILE));
        prop.load(fis);
        fis.close();
        ConfigurationManager.getInstance().setConfigs(prop);
    }
    
    public void saveConfiguration() throws IOException {
        FileOutputStream fos = new FileOutputStream(new File(ConfigurationManager.CONFIGS_FILE));
        ConfigurationManager.getInstance().createConfigProperties().store(fos, "Config file of EliteBot");
        fos.close();
    }
}

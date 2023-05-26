package mc.xega.skyblock.Utils;

import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;

public class FileUtils {

    public static boolean createFile(String category, String name) {
        new File("plugins" + File.separator + "Skyblock" + File.separator + category).mkdirs();
        boolean success = false;
        try {
            File file = new File("plugins" + File.separator + "Skyblock" + File.separator + category + File.separator + name + ".yml");
            if(!file.exists()) {
                success = file.createNewFile();
            }
            else {
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public static YamlConfiguration getFile(String category, String name) {
        return YamlConfiguration.loadConfiguration(new File("plugins" + File.separator + "Skyblock" + File.separator + category + File.separator + name + ".yml"));
    }

}

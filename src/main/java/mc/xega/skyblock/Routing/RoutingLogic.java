package mc.xega.skyblock.Routing;

import mc.xega.skyblock.Utils.FileUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class RoutingLogic {

    static YamlConfiguration routingConfig = FileUtils.getFile("Routing", "bosslocs");
    public static List<String> bosses = new ArrayList<>();

    public static void addLocation(String boss, Location loc, String name) throws IOException {
        if (routingConfig.getConfigurationSection(boss) == null && bosses.contains(boss)) {
            routingConfig.createSection(boss);
        }
        loc = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        routingConfig.createSection(boss + "." + name);
        routingConfig.getConfigurationSection(boss).set(name, loc);
        routingConfig.save("plugins" + File.separator + "Skyblock" + File.separator + "Routing" + File.separator + "bosslocs.yml");
    }

    public static void removeLocation(String boss, String name) throws IOException {
        routingConfig.getConfigurationSection(boss).set(name, null);
        routingConfig.save("plugins" + File.separator + "Skyblock" + File.separator + "Routing" + File.separator + "bosslocs.yml");
    }

    public static List<String> listLocations() {
        List<String> locs = new ArrayList<>();
        Set<String> bosses = routingConfig.getValues(false).keySet();
        for (String boss: bosses) {
            Map<String, Object> vals = routingConfig.getConfigurationSection(boss).getValues(false);
            for (String name: vals.keySet()) {
                Location loc = (Location) vals.get(name);
                locs.add("Boss: " + boss + ", Name: " + name + ", x: " + loc.getX() + ", y: " + loc.getY() + ", z: " + loc.getZ());
            }
        }
        return locs;
    }

}

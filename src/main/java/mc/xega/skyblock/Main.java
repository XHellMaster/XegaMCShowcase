package mc.xega.skyblock;

import mc.xega.skyblock.Commands.Commands;
import mc.xega.skyblock.Commands.XegaTabCompleter;
import mc.xega.skyblock.Mobs.Bosses.BossEventsHandler;
import mc.xega.skyblock.Utils.FileUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {

    private static Main INSTANCE;
    private final List<String> commandsToRegister = new ArrayList<>();

    @Override
    public void onEnable() {
        INSTANCE = this;

        commandsToRegister.add("xega");

        for (String s : commandsToRegister)
            registerCommand(s, new Commands(this));
        getCommand("xega").setTabCompleter(new XegaTabCompleter());
        getServer().getPluginManager().registerEvents(new BossEventsHandler(), this);

        FileUtils.createFile("Dialogue", "dialogue");
        FileUtils.createFile("Routing", "bosslocs");
    }

    private void registerCommand(String cmdName, CommandExecutor executor) {
        PluginCommand cmd = getCommand(cmdName);

        if (cmd == null) {
            getLogger().warning("Cannot register command \"" + cmdName + "\". (is it registered in plugin.yml?)");
            return;
        }

        cmd.setExecutor(executor);
    }

    private void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public static Main getInstance() {
        return INSTANCE;
    }
}

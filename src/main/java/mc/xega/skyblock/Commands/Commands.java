package mc.xega.skyblock.Commands;

import mc.xega.skyblock.Main;
import mc.xega.skyblock.Mobs.Bosses.Bosses.Scorch.Scorch;
import mc.xega.skyblock.Mobs.Bosses.Bosses.SkeletonKing.SkeletonKing;
import mc.xega.skyblock.Routing.RoutingLogic;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor {

    Main main;
    public Commands(Main main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = sender instanceof Player ? ((Player) sender) : null;

        if (p == null) {
            return false;
        }

        if (p.isOp()) {
            if (command.getName().equalsIgnoreCase("xega")) {
                switch (args[0]) {
                    case "summon":
                        switch (args[1]) {

                            case "boss":
                                switch (args[2]) {
                                    case "sk" -> {
                                        List<Float> durations = new ArrayList<>();
                                        durations.add(13f);
                                        durations.add(8f);
                                        SkeletonKing sk = new SkeletonKing(p.getLocation(), main, durations);
                                        ((CraftWorld) p.getLocation().getWorld()).getHandle().addFreshEntity(sk);
                                    }
                                    case "scorch" -> {
                                        List<Float> durations1 = new ArrayList<>();
                                        durations1.add(3f);
                                        durations1.add(5f);
                                        durations1.add(1f);
                                        durations1.add(0.2f);
                                        Scorch s = new Scorch(p.getLocation(), main, durations1);
                                        ((CraftWorld) p.getLocation().getWorld()).getHandle().addFreshEntity(s);
                                    }
                                }
                                break;
                            case "entity":
                                break;

                        }
                    case "bosspoints":
                        switch (args[1]) {
                            case "add" -> {
                                String name = args[3];
                                String boss = switch (args[2]) {
                                    case "sk" -> "sk";
                                    case "scorch" -> "scorch";
                                    default -> "";
                                };
                                try {
                                    RoutingLogic.addLocation(boss, p.getLocation(), name);
                                    p.sendMessage(ChatColor.GREEN + "Successfully added player spawn " + name + "!");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            case "remove" -> {
                                String name1 = args[3];
                                String boss1 = switch (args[2]) {
                                    case "sk" -> "sk";
                                    case "scorch" -> "scorch";
                                    default -> "";
                                };
                                try {
                                    RoutingLogic.removeLocation(boss1, name1);
                                    p.sendMessage(ChatColor.GREEN + "Successfully removed player spawn " + name1 + "!");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            case "list" -> {
                                List<String> locs = RoutingLogic.listLocations();
                                if (locs.isEmpty()) {
                                    p.sendMessage(ChatColor.RED + "There are currently no spawn locations.");
                                }
                                for (String loc: locs) {
                                    p.sendMessage(loc);
                                }
                            }
                        }
                        break;
                }

            }
        }
        return true;
    }
}

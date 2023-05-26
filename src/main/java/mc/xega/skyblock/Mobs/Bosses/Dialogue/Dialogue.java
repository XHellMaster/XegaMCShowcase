package mc.xega.skyblock.Mobs.Bosses.Dialogue;

import mc.xega.skyblock.Main;
import mc.xega.skyblock.Mobs.Bosses.Boss;
import mc.xega.skyblock.Utils.FileUtils;
import net.minecraft.world.entity.Entity;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Dialogue {

    private final Map<String, Double> startDialogue = new LinkedHashMap<String, Double>();
    private final Map<String, Double> endDialogue = new LinkedHashMap<String, Double>();
    private final Map<Integer, String> phaseDialogue = new LinkedHashMap<Integer, String>();

    private final List<String> damagedDialogue = new ArrayList<String>();

    public boolean startFight = false;
    private final Main main;

    private final org.bukkit.entity.Entity ent;
    private final Entity nmsEntity;

    private int dChance = 10;


    public Dialogue(String bossName, Entity e, Main m) {
        main = m;
        YamlConfiguration dfile = FileUtils.getFile("Dialogue", "dialogue");
        for (Object val : dfile.getConfigurationSection(bossName + ".START").getValues(false).values()) {
            MemorySection dialogue = (MemorySection) val;
            startDialogue.put(dialogue.getString("content"), dialogue.getDouble("delay"));
        }
        for (Object val : dfile.getConfigurationSection(bossName + ".END").getValues(false).values()) {
            MemorySection dialogue = (MemorySection) val;
            endDialogue.put(dialogue.getString("content"), dialogue.getDouble("delay"));
        }
        for (Object val : dfile.getConfigurationSection(bossName + ".HURT").getValues(false).values()) {
            MemorySection dialogue = (MemorySection) val;
            damagedDialogue.add(dialogue.getString("content"));
        }
        for (Object val : dfile.getConfigurationSection(bossName + ".PHASE").getValues(false).values()) {
            MemorySection dialogue = (MemorySection) val;
            phaseDialogue.put(dialogue.getInt("phase"), dialogue.getString("content"));
        }
        ent = e.getBukkitEntity();
        nmsEntity = e;
    }

    public void setDChance(int chance) {
        this.dChance = chance;
    }

    public void startDialogue() {
        for (String dialogue : startDialogue.keySet()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player p : ent.getWorld().getNearbyEntitiesByType(Player.class, ent.getLocation(), 30)) {
                        if (dialogue.contains("%LAST%")) {
                            String dialogue1 = dialogue.replace("%LAST%", "");
                            p.sendMessage(dialogue1);
                            if (!startFight) {
                                startFight = true;
                                startFight();
                            }
                        }
                        else {
                            p.sendMessage(dialogue);
                        }
                    }
                }
            }.runTaskLater(main, (int) (20 * startDialogue.get(dialogue)));
        }
    }

    public void endDialogue() {
        for (String dialogue : endDialogue.keySet()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player p : ent.getWorld().getNearbyEntitiesByType(Player.class, ent.getLocation(), 30)) {
                        p.sendMessage(dialogue);
                    }
                }
            }.runTaskLater(main, (int) (20 * endDialogue.get(dialogue)));
        }
    }
    //events

    public void damagedDialogue() {
        //make it so that on average 1 in every X times triggers this
        int proceed = (int) (Math.random() * dChance);
        if (proceed == 1) {
            int length = damagedDialogue.size();
            int randIndex = (int) (Math.random() * length);
            for (Player p : ent.getWorld().getNearbyEntitiesByType(Player.class, ent.getLocation(), 30)) {
                p.sendMessage(damagedDialogue.get(randIndex));
            }
        }
    }

    public void phaseDialogue(int phase) {

        for (Player p : ent.getWorld().getNearbyEntitiesByType(Player.class, ent.getLocation(), 30)) {
            p.sendMessage(phaseDialogue.get(phase));
        }
    }



    public void startFight() {
        if (nmsEntity instanceof Boss b) {
            b.addGoals();
        }
    }
}
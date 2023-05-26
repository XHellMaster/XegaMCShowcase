package mc.xega.skyblock.Mobs.Bosses.Abilities.abilities.SkeletonKing;

import mc.xega.skyblock.Main;
import mc.xega.skyblock.Mobs.Bosses.Abilities.Ability;
import mc.xega.skyblock.Mobs.Bosses.Abilities.AbilityType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public class RoseCorruption extends Ability {

    public RoseCorruption(AbilityType type, Entity ent) {
        super(type, ent);
    }

    public void trigger() {

        if (!ent.isDead()) {
            LivingEntity le = (LivingEntity) ent;
            Location loc = le.getLocation();
            int radius = 15;
            World w = loc.getWorld();
            for (int x = -radius; x < radius; x++) {
                for (int z = -radius; z < radius; z++) {
                    Block block = w.getBlockAt(loc.getBlockX()+x, loc.getBlockY() - 1, loc.getBlockZ()+z);
                    if (!block.getType().equals(Material.GRASS_BLOCK) && block.getLocation().add(0, 1, 0).getBlock().getType().equals(Material.AIR)) {
                        int chance = (int) (Math.random() * 100) + 1;
                        if (chance <= 15) {
                            Material originalBlock = block.getType();
                            block.setType(Material.GRASS_BLOCK);
                            block.getLocation().add(0,1,0).getBlock().setType(Material.WITHER_ROSE);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    w.createExplosion(block.getLocation().add(0,1,0), 7f, false, false);
                                    block.setType(originalBlock);
                                    block.getLocation().add(0,1,0).getBlock().setType(Material.AIR);
                                }
                            }.runTaskLater(Main.getInstance(), 60L);
                        }
                    }
                }
            }
        }
    }
}

package mc.xega.skyblock.Mobs.Bosses.Abilities.abilities.Scorch;

import mc.xega.skyblock.Main;
import mc.xega.skyblock.Mobs.Bosses.Abilities.Ability;
import mc.xega.skyblock.Mobs.Bosses.Abilities.AbilityType;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class FireGrenade extends Ability {

    Main plugin;
    float rate = 0.04f;
    public FireGrenade(AbilityType type, Entity ent) {
        super(type, ent);
        this.plugin = Main.getInstance();

    }
    public FireGrenade(AbilityType type, Entity ent, float rate) {
        super(type, ent);
        this.plugin = Main.getInstance();
        this.rate = rate;
    }

    public void trigger() {
        LivingEntity le = (LivingEntity) ent;
        World w = le.getWorld();
        Location loc = le.getLocation();

        List<Player> players = new ArrayList<Player>();

        Location loc2 = null;
        for (Entity e: w.getNearbyEntities(le.getLocation(), 30,8,30)) {
            if (e instanceof Player p && p.getGameMode().equals(GameMode.SURVIVAL)) {
                if (loc2 == null){
                    loc2 = p.getLocation();
                }
                players.add(p);
            }
        }

        if(loc2 != null && !le.isDead()) {
            Location loc3 = loc2.clone().add(0, 10, 0);

            Location finalLoc = loc2;
            final ArmorStand as = (ArmorStand) w.spawnEntity(loc, EntityType.ARMOR_STAND);
            as.setInvisible(true);
            as.setInvulnerable(true);
            as.setArms(false);
            as.setBasePlate(false);
            as.setMarker(true);
            as.setSmall(true);
            as.getEquipment().setItemInMainHand(new ItemStack(Material.BLAZE_POWDER));

            final int[] phase = {0};
            BukkitRunnable visuals2 = new BukkitRunnable() {
                int phase2 = 0;

                @Override
                public void run() {
                    if (!le.isDead()) {
                        if (phase2 % 2 == 0) {
                            as.getEquipment().setItemInMainHand(new ItemStack(Material.REDSTONE));
                        }
                        else {
                            as.getEquipment().setItemInMainHand(new ItemStack(Material.BLAZE_POWDER));
                        }
                        for (Player p: players)
                            p.playSound(as.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 2.0f);
                        if (phase2 == 4) {
                            if (!as.isDead()) {
                                cancel();
                                w.createExplosion(as.getLocation(), 3f, true, false);
                                as.remove();
                                phase[0] = 3;
                            }
                        }
                        for (Entity entity : as.getLocation().getChunk().getEntities()) {
                            if (!as.isDead()) {
                                if (as.getLocation().distanceSquared(entity.getLocation()) <= 5) {
                                    if (entity instanceof Player p && p.getGameMode().equals(GameMode.SURVIVAL)) {
                                        p.damage(120, le);
                                        p.setFireTicks(60);
                                        w.createExplosion(p.getLocation(), 3f, true, false);
                                        as.remove();
                                        cancel();
                                    }
                                }
                            }
                        }
                        phase2++;
                    }
                    else {
                        as.remove();
                        cancel();
                    }
                }
            };

            BukkitRunnable visuals = new BukkitRunnable() {
                int phase2 = 0;

                @Override
                public void run() {
                    if(!le.isDead()) {
                        if (phase2 % 2 == 0) {
                            as.getEquipment().setItemInMainHand(new ItemStack(Material.REDSTONE));
                        }
                        else {
                            as.getEquipment().setItemInMainHand(new ItemStack(Material.BLAZE_POWDER));
                        }
                        for (Player p: players)
                            p.playSound(as.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 2.0f);
                        if (phase2 == 4) {
                            cancel();
                            visuals2.runTaskTimer(plugin, 0L, 5L);
                        }
                        for (Entity entity : as.getLocation().getChunk().getEntities()) {
                            if (!as.isDead()) {
                                if (as.getLocation().distanceSquared(entity.getLocation()) <= 5) {
                                    if (entity instanceof Player p && p.getGameMode().equals(GameMode.SURVIVAL)) {
                                        p.damage(120, le);
                                        p.setFireTicks(60);
                                        w.createExplosion(p.getLocation(), 3f, true, false);
                                        as.remove();
                                        cancel();
                                    }
                                }
                            }
                        }
                        phase2++;
                    }
                    else {
                        as.remove();
                        cancel();
                    }
                }
            };

            for (Player p: players)
                p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0f, 3.0f);

            new BukkitRunnable() {
                float t = 0;

                public void run() {
                    if (!le.isDead() && phase[0] != 3) {
                        if (!as.getLocation().add(0, 1, 0).getBlock().getRelative(BlockFace.DOWN).getType().isSolid() || t <0.3) {
                            Location tempLoc = quadraticBezier(t, loc, loc3, finalLoc);
                            as.teleport(tempLoc);
                            Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(0, 0, 0), 2);
                            w.spawnParticle(Particle.REDSTONE, tempLoc.getX(), tempLoc.getY(), tempLoc.getZ(), 1, 0, 0, 0, dust);
                            t+=rate;
                        }
                        else {
                            phase[0] = 1;
                        }

                        if (as.getLocation().add(0, 1, 0).getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
                            if (phase[0] == 1) {
                                visuals.runTaskTimer(plugin, 0L, 10L);
                                phase[0] = 2;
                            }
                        }

                        for (Entity entity : as.getLocation().getChunk().getEntities()) {
                            if (!as.isDead()) {
                                if (as.getLocation().distanceSquared(entity.getLocation()) <= 5) {
                                    if (entity instanceof Player p && p.getGameMode().equals(GameMode.SURVIVAL)) {
                                        p.damage(120, le);
                                        p.setFireTicks(60);
                                        w.createExplosion(p.getLocation(), 3f, true, false);
                                        as.remove();
                                        cancel();
                                        visuals.cancel();
                                        visuals2.cancel();
                                    }
                                }
                            }
                        }
                    }

                    else {
                        cancel();
                        as.remove();
                    }

                }

            }.runTaskTimer(plugin, 0L, 1L);

        }

    }
    private Location quadraticBezier(float t, Location p0, Location p1, Location p2) {
        return p0.clone().multiply((1 - t)*(1 - t)).add(p1.clone().multiply(2 * (1 - t) * t)).add(p2.clone().multiply(t*t));
    }
}
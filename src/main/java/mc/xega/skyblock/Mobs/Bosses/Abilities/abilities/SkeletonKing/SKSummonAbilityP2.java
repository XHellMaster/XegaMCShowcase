package mc.xega.skyblock.Mobs.Bosses.Abilities.abilities.SkeletonKing;

import mc.xega.skyblock.Main;
import mc.xega.skyblock.Mobs.Bosses.Abilities.Ability;
import mc.xega.skyblock.Mobs.Bosses.Abilities.AbilityType;
import mc.xega.skyblock.Mobs.Bosses.Bosses.SkeletonKing.SkeletonKing;
import mc.xega.skyblock.Mobs.Bosses.Bosses.SkeletonKing.SkeletonMinion;
import mc.xega.skyblock.Mobs.Bosses.Bosses.SkeletonKing.SkeletonMinion2;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class SKSummonAbilityP2 extends Ability {

    public SKSummonAbilityP2(AbilityType type, Entity ent) {
        super(type, ent);
    }

    public void trigger() {
        SkeletonKing sk = (SkeletonKing) ((CraftEntity) ent).getHandle();
        if (!sk.died) {
            Location loc = ent.getLocation();
            World w = ent.getWorld();

            Block b = loc.getBlock();
            Location loc1 = b.getRelative(BlockFace.EAST_NORTH_EAST).getLocation();
            Location loc2 = b.getRelative(BlockFace.EAST_SOUTH_EAST).getLocation();
            Location loc3 = b.getRelative(BlockFace.WEST_NORTH_WEST).getLocation();
            Location loc4 = b.getRelative(BlockFace.WEST_SOUTH_WEST).getLocation();
            Location loc01 = b.getRelative(BlockFace.EAST_NORTH_EAST).getLocation().subtract(0,2,0);
            Location loc02 = b.getRelative(BlockFace.EAST_SOUTH_EAST).getLocation().subtract(0,2,0);
            Location loc03 = b.getRelative(BlockFace.WEST_NORTH_WEST).getLocation().subtract(0,2,0);
            Location loc04 = b.getRelative(BlockFace.WEST_SOUTH_WEST).getLocation().subtract(0,2,0);

            List<Float> durations = new ArrayList<>();
            durations.add(3f);

            SkeletonMinion s1 = new SkeletonMinion(loc01, Main.getInstance(), durations);
            SkeletonMinion s2 = new SkeletonMinion(loc02, Main.getInstance(), durations);
            SkeletonMinion2 s3 = new SkeletonMinion2(loc03, Main.getInstance(), durations);
            SkeletonMinion2 s4 = new SkeletonMinion2(loc04, Main.getInstance(), durations);

            double scaleX = 1;
            double aScaleX = 0.5;
            double scaleZ = 1;
            double aScaleZ = 0.5;
            double density = 0.2;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!sk.died) {
                        if(!s1.isDeadOrDying()) {
                            for (double i=0; i < 2 * Math.PI ; i +=density) {
                                double x = Math.cos(i) * scaleX;
                                double x1 = Math.cos(i) * aScaleX;
                                double z = Math.sin(i) * scaleZ;
                                double z1 = Math.sin(i) * aScaleZ;

                                Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(0, 0, 0), 1);
                                Particle.DustOptions dust1 = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1);

                                w.spawnParticle(Particle.REDSTONE, loc1.getX() + x, loc1.getY() + 0.2,  loc1.getZ() + z, 1, 0, 0, 0, dust);
                                w.spawnParticle(Particle.REDSTONE, loc1.getX() + x1, loc1.getY() + 0.2,  loc1.getZ() + z1, 1, 0, 0, 0, dust1);
                            }
                        }
                        if(!s2.isDeadOrDying()) {
                            for (double i=0; i < 2 * Math.PI ; i +=density) {
                                double x = Math.cos(i) * scaleX;
                                double x1 = Math.cos(i) * aScaleX;
                                double z = Math.sin(i) * scaleZ;
                                double z1 = Math.sin(i) * aScaleZ;

                                Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(0, 0, 0), 1);
                                Particle.DustOptions dust1 = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1);

                                w.spawnParticle(Particle.REDSTONE, loc2.getX() + x, loc2.getY() + 0.2,  loc2.getZ() + z, 1, 0, 0, 0, dust);
                                w.spawnParticle(Particle.REDSTONE, loc2.getX() + x1, loc2.getY() + 0.2,  loc2.getZ() + z1, 1, 0, 0, 0, dust1);

                            }
                        }
                        if(!s3.isDeadOrDying()) {
                            for (double i=0; i < 2 * Math.PI ; i +=density) {
                                double x = Math.cos(i) * scaleX;
                                double x1 = Math.cos(i) * aScaleX;
                                double z = Math.sin(i) * scaleZ;
                                double z1 = Math.sin(i) * aScaleZ;
                                Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(0, 0, 0), 1);
                                Particle.DustOptions dust1 = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1);

                                w.spawnParticle(Particle.REDSTONE, loc3.getX() + x, loc3.getY() + 0.2,  loc3.getZ() + z, 1, 0, 0, 0, dust);
                                w.spawnParticle(Particle.REDSTONE, loc3.getX() + x1, loc3.getY() + 0.2,  loc3.getZ() + z1, 1, 0, 0, 0, dust1);
                            }
                        }

                        if(!s4.isDeadOrDying()) {
                            for (double i=0; i < 2 * Math.PI ; i +=density) {
                                double x = Math.cos(i) * scaleX;
                                double x1 = Math.cos(i) * aScaleX;
                                double z = Math.sin(i) * scaleZ;
                                double z1 = Math.sin(i) * aScaleZ;

                                Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(0, 0, 0), 1);
                                Particle.DustOptions dust1 = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1);

                                w.spawnParticle(Particle.REDSTONE, loc4.getX() + x, loc4.getY() + 0.2,  loc4.getZ() + z, 1, 0, 0, 0, dust);
                                w.spawnParticle(Particle.REDSTONE, loc4.getX() + x1, loc4.getY() + 0.2,  loc4.getZ() + z1, 1, 0, 0, 0, dust1);

                            }
                        }
                    } else
                        cancel();
                }
            }.runTaskTimer(Main.getInstance(), 0L, 1L);




            ((CraftWorld) w).getHandle().addFreshEntity(s1);
            ((CraftWorld) w).getHandle().addFreshEntity(s2);
            ((CraftWorld) w).getHandle().addFreshEntity(s3);
            ((CraftWorld) w).getHandle().addFreshEntity(s4);
            BukkitTask r = new BukkitRunnable() {
                @Override
                public void run() {
                    if(!sk.died) {
                        s1.getBukkitEntity().teleport(s1.getBukkitEntity().getLocation().add(0, 0.05, 0));
                        s2.getBukkitEntity().teleport(s2.getBukkitEntity().getLocation().add(0, 0.05, 0));
                        s3.getBukkitEntity().teleport(s3.getBukkitEntity().getLocation().add(0, 0.05, 0));
                        s4.getBukkitEntity().teleport(s4.getBukkitEntity().getLocation().add(0, 0.05, 0));
                    } else
                        cancel();
                }
            }.runTaskTimer(Main.getInstance(), 0L, 1L);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!sk.died) {
                        r.cancel();
                        s1.addPathFinders();
                        s2.addPathFinders();
                        s3.addPathFinders();
                        s4.addPathFinders();
                    } else
                        cancel();
                }
            }.runTaskLater(Main.getInstance(), 40L);
        }
    }
}

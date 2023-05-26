package mc.xega.skyblock.Mobs.Bosses.Abilities.abilities.SkeletonKing;

import mc.xega.skyblock.Main;
import mc.xega.skyblock.Mobs.Bosses.Abilities.Ability;
import mc.xega.skyblock.Mobs.Bosses.Abilities.AbilityType;
import mc.xega.skyblock.Mobs.Bosses.Bosses.SkeletonKing.SkeletonKing;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public class ExplodePhaseAbility extends Ability {
    public ExplodePhaseAbility(AbilityType type, Entity ent) {
        super(type, ent);
    }

    public void trigger() {
        Location loc = ent.getLocation();
        World w = loc.getWorld();
        LivingEntity le = (LivingEntity) ent;
        ent.setInvulnerable(true);
        le.setAI(false);
        Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(0, 0, 0), 1);
        final boolean[] proceed = {false};
        final boolean[] isRunning = {false};
        final boolean[] isRunning1 = {false};
        final boolean[] isRunning2 = {false};
        final boolean[] isRunning3 = {false};
        final boolean[] isRunning4 = {false};
        final boolean[] isRunning5 = {false};

        new BukkitRunnable() {
            @Override
            public void run() {
                if(!proceed[0]) {
                    ent.teleport(loc);
                    double scaleX = 3;
                    double aScaleX = 2.5;
                    double bScaleX = 2;
                    double cScaleX = 1.5;
                    double dScaleX = 1;
                    double eScaleX = 0.5;
                    double scaleZ = 3;
                    double aScaleZ = 2.5;
                    double bScaleZ = 2;
                    double cScaleZ = 1.5;
                    double dScaleZ = 1;
                    double eScaleZ = 0.5;
                    double density = 0.1;

                    for (double i=0; i < 2 * Math.PI ; i +=density+0.5) {
                        double x = Math.cos(i) * scaleX;
                        double z = Math.sin(i) * scaleZ;
                        w.spawnParticle(Particle.REDSTONE, loc.getX() + x, loc.getY() + 0.5,  loc.getZ() + z, 1, 0, 0, 0, dust);
                    }
                    if (!isRunning[0]) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (!proceed[0]) {
                                    for (double i = 0; i < 2 * Math.PI; i += density + 0.3) {
                                        double x = Math.cos(i) * aScaleX;
                                        double z = Math.sin(i) * aScaleZ;
                                        w.spawnParticle(Particle.REDSTONE, loc.getX() + x, loc.getY() + 0.5, loc.getZ() + z, 1, 0, 0, 0, dust);
                                    }
                                    if (!isRunning1[0]) {
                                        new BukkitRunnable() {
                                            @Override
                                            public void run() {
                                                if (!proceed[0]) {
                                                    for (double i = 0; i < 2 * Math.PI; i += density + 0.2) {
                                                        double x = Math.cos(i) * bScaleX;
                                                        double z = Math.sin(i) * bScaleZ;
                                                        w.spawnParticle(Particle.REDSTONE, loc.getX() + x, loc.getY() + 0.5, loc.getZ() + z, 1, 0, 0, 0, dust);
                                                    }
                                                    if (!isRunning2[0]) {
                                                        new BukkitRunnable() {
                                                            @Override
                                                            public void run() {
                                                                if (!proceed[0]) {
                                                                    for (double i = 0; i < 2 * Math.PI; i += density + 0.1) {
                                                                        double x = Math.cos(i) * cScaleX;
                                                                        double z = Math.sin(i) * cScaleZ;
                                                                        w.spawnParticle(Particle.REDSTONE, loc.getX() + x, loc.getY() + 0.5, loc.getZ() + z, 1, 0, 0, 0, dust);
                                                                    }
                                                                    if (!isRunning3[0]) {
                                                                        new BukkitRunnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                if (!proceed[0]) {
                                                                                    for (double i = 0; i < 2 * Math.PI; i += density) {
                                                                                        double x = Math.cos(i) * dScaleX;
                                                                                        double z = Math.sin(i) * dScaleZ;
                                                                                        w.spawnParticle(Particle.REDSTONE, loc.getX() + x, loc.getY() + 0.5, loc.getZ() + z, 1, 0, 0, 0, dust);
                                                                                    }
                                                                                    if (!isRunning4[0]) {
                                                                                        new BukkitRunnable() {
                                                                                            @Override
                                                                                            public void run() {
                                                                                                if (!proceed[0]) {
                                                                                                    for (double i = 0; i < 2 * Math.PI; i += density) {
                                                                                                        double x = Math.cos(i) * eScaleX;
                                                                                                        double z = Math.sin(i) * eScaleZ;
                                                                                                        w.spawnParticle(Particle.REDSTONE, loc.getX() + x, loc.getY() + 0.5, loc.getZ() + z, 1, 0, 0, 0, dust);
                                                                                                    }
                                                                                                    if (!isRunning5[0]) {
                                                                                                        new BukkitRunnable() {
                                                                                                            @Override
                                                                                                            public void run() {
                                                                                                                proceed[0] = true;
                                                                                                                w.createExplosion(loc, 8f, false, false);
                                                                                                                ((SkeletonKing) ((CraftEntity) ent).getHandle()).addGoals();
                                                                                                            }
                                                                                                        }.runTaskLater(Main.getInstance(), 10L);
                                                                                                        isRunning5[0] = true;
                                                                                                    }

                                                                                                } else
                                                                                                    cancel();

                                                                                            }
                                                                                        }.runTaskTimer(Main.getInstance(), 10L, 1L);
                                                                                        isRunning4[0] = true;
                                                                                    }

                                                                                } else
                                                                                    cancel();

                                                                            }
                                                                        }.runTaskTimer(Main.getInstance(), 10L, 1L);
                                                                        isRunning3[0] = true;
                                                                    }

                                                                } else
                                                                    cancel();

                                                            }
                                                        }.runTaskTimer(Main.getInstance(), 10L, 1L);
                                                        isRunning2[0] = true;
                                                    }

                                                } else
                                                    cancel();

                                            }
                                        }.runTaskTimer(Main.getInstance(), 10L, 1L);
                                        isRunning1[0] = true;
                                    }

                                } else
                                    cancel();

                            }
                        }.runTaskTimer(Main.getInstance(), 10L, 1L);
                        isRunning[0] = true;
                    }
                }
                else
                    cancel();

            }
        }.runTaskTimer(Main.getInstance(), 0L, 1L);
    }
}

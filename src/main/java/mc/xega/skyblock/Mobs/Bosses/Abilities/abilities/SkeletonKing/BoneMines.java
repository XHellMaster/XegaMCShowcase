package mc.xega.skyblock.Mobs.Bosses.Abilities.abilities.SkeletonKing;

import mc.xega.skyblock.Main;
import mc.xega.skyblock.Mobs.Bosses.Abilities.Ability;
import mc.xega.skyblock.Mobs.Bosses.Abilities.AbilityType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.List;

public class BoneMines extends Ability {

    public BoneMines(AbilityType type, Entity ent) {
        super(type, ent);
    }

    public void trigger() {
        if (!ent.isDead()) {
            LivingEntity le = (LivingEntity) ent;
            Location loc = le.getLocation();
            Location loc1 = le.getLocation().add(0, 3, 0);
            World w = le.getWorld();
            double scaleX = 7;
            double scaleZ = 7;
            double density = 0.5;
            List<Location> locs = new ArrayList<>();
            for (double i=0; i < 2 * Math.PI ; i +=density) {
                double x = Math.cos(i) * scaleX;
                double z = Math.sin(i) * scaleZ;
                locs.add(loc.add(loc.getX() + x, loc.getY(), loc.getZ() + z));
            }
            List<ArmorStand> ass = new ArrayList<>();

            for (Location l: locs) {
                ArmorStand as = (ArmorStand) ent.getWorld().spawnEntity(le.getEyeLocation(), EntityType.ARMOR_STAND);
                as.setInvisible(true);
                as.setInvulnerable(true);
                as.setArms(false);
                as.setBasePlate(false);
                as.setMarker(true);
                as.setGravity(false);
                as.setSmall(true);
                as.setRightArmPose(new EulerAngle(Math.toRadians(90), Math.toRadians(0), Math.toRadians(0)));
                as.getEquipment().setItemInMainHand(new ItemStack(Material.BONE));
                ass.add(as);
            }
            new BukkitRunnable() {
                float tick = 0;
                @Override
                public void run() {
                    for (ArmorStand as: ass) {
                        Location l = as.getLocation();
                        if (!ent.isDead()) {
                            if (tick <= 1) {
                                Location res = quadraticBezier(tick, loc, loc1, l);
                                as.teleport(res);
                                tick += 0.3;
                            }
                            /*
                            for(Entity e: as.getChunk().getEntities()) {
                                if (e instanceof Player p) {
                                    if (!as.isDead()) {
                                        if (as.getLocation().distanceSquared(p.getLocation()) <= 5) {
                                            p.damage(20);
                                            w.createExplosion(l, 4f, false, false);
                                            as.remove();
                                        }
                                    }
                                    cancel();
                                }
                            }*/
                        }
                        else {
                            w.createExplosion(l, 4f, false, false);
                            as.remove();
                            cancel();
                        }
                    }
                }
            }.runTaskTimer(Main.getInstance(), 0L, 2L);
        }

    }


    private Location quadraticBezier(float t, Location p0, Location p1, Location p2) {
        return p0.clone().multiply((1 - t)*(1 - t)).add(p1.clone().multiply(2 * (1 - t) * t)).add(p2.clone().multiply(t*t));
    }

}
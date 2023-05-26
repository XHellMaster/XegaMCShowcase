package mc.xega.skyblock.Mobs.Bosses.Abilities.abilities.Scorch;

import mc.xega.skyblock.Main;
import mc.xega.skyblock.Mobs.Bosses.Abilities.Ability;
import mc.xega.skyblock.Mobs.Bosses.Abilities.AbilityType;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class FireCurtain extends Ability {
    public FireCurtain(AbilityType type, Entity ent) {
        super(type, ent);
    }

    public void trigger() {

        LivingEntity le = (LivingEntity) ent;
        Location loc = le.getEyeLocation();
        ArmorStand as = (ArmorStand) ent.getWorld().spawnEntity(le.getLocation(), EntityType.ARMOR_STAND);
        as.setInvisible(true);
        as.setInvulnerable(true);
        as.setArms(false);
        as.setBasePlate(false);
        as.setMarker(true);
        as.setGravity(false);
        as.setSmall(true);
        as.getEquipment().setItemInMainHand(new ItemStack(Material.BLAZE_ROD));

        Location dest = as.getLocation().add(le.getEyeLocation().getDirection().multiply(10));
        final Vector vector = dest.subtract(as.getEyeLocation()).toVector();

        Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(255, 119, 0), 5);
        for (Entity e: as.getChunk().getEntities())
            if (e instanceof Player p)
                p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0f, 7.0f);
        World w = le.getWorld();
        new BukkitRunnable() {
            int i = 0;
            final int distance = 15;

            public void run() {

                if (!le.isDead()) {
                    if (as.getTargetBlockExact(1) == null || as.getLocation().add(0,1,0).getBlock().getType().equals(Material.AIR)) {
                        as.teleport(as.getLocation().add(vector.normalize().multiply(2)));
                        Location l = as.getLocation();
                        if (l.getBlock().getType().equals(Material.AIR))
                            l.getBlock().setType(Material.FIRE);
                        if (!l.clone().add(0,1,0).getBlock().getType().equals(Material.AIR)) {
                            l.clone().add(0, 1, 0).getBlock().setType(Material.FIRE);
                        }
                        w.spawnParticle(Particle.REDSTONE, l.getX(), l.getY(), l.getZ(), 1, 0, 0, 0, dust);

                        if (as.getTargetBlockExact(1) != null && !(as.getTargetBlockExact(1).isPassable())) {

                            if (!as.isDead()) {
                                w.createExplosion(as.getLocation(), 4f, true, false);
                                as.remove();
                                cancel();
                            }
                        }

                        for (Entity entity : as.getLocation().getChunk().getEntities()) {
                            if (!as.isDead()) {
                                if (as.getLocation().distanceSquared(entity.getLocation()) <= 3) {
                                    if (entity instanceof Player p) {
                                        p.damage(90, le);
                                        p.setFireTicks(100);
                                    }
                                }
                            }
                        }
                    }
                    else {
                        cancel();
                        w.createExplosion(as.getLocation(), 4f, true, false);
                        as.remove();

                    }
                }

                if (i > distance) {
                    w.createExplosion(as.getLocation(), 4f, true, false);
                    as.remove();
                    cancel();
                }
                else if (le.isDead()) {
                    cancel();
                    as.remove();
                }
                i++;


            }

        }.runTaskTimer(Main.getInstance(), 0L, 1L);
    }
}

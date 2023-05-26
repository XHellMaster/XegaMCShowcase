package mc.xega.skyblock.Mobs.Bosses.Abilities.abilities.SkeletonKing;

import mc.xega.skyblock.Main;
import mc.xega.skyblock.Mobs.Bosses.Abilities.Ability;
import mc.xega.skyblock.Mobs.Bosses.Abilities.AbilityType;
import mc.xega.skyblock.Mobs.Bosses.Bosses.SkeletonKing.SkeletonKing;
import mc.xega.skyblock.Mobs.Bosses.Bosses.SkeletonKing.SkeletonMinion;
import mc.xega.skyblock.Mobs.Bosses.Bosses.SkeletonKing.SkeletonMinion2;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class DeathAbsorb extends Ability {

    public DeathAbsorb(AbilityType type, Entity ent) {
        super(type, ent);
    }

    public void trigger() {
        int numEntities = 0;
        LivingEntity le = (LivingEntity) ent;
        le.setAI(false);
        le.setInvulnerable(true);
        World w = le.getWorld();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!le.isDead())
                    le.teleport(le.getLocation().add(0, 0.05, 0));
                else
                    cancel();
            }
        }.runTaskTimer(Main.getInstance(), 0L, 1L);
        for (Entity e: w.getNearbyEntities(le.getLocation(), 40, 40, 40)) {
            if (((CraftEntity) e).getHandle() instanceof SkeletonMinion || ((CraftEntity) e).getHandle() instanceof SkeletonMinion2) {
                LivingEntity el = (LivingEntity) e;
                el.setAI(false);
                el.setInvulnerable(true);
                Location loc = el.getLocation();
                Vector p2 = loc.toVector();
                new BukkitRunnable() {
                    @Override
                    public void run() {

                        if(!el.isDead()) {
                            Location center = le.getLocation();
                            Vector p1 = center.toVector();
                            final Vector vector = p1.subtract(p2);
                            el.teleport(el.getLocation().add(vector.normalize().multiply(0.3)));
                            for (Entity e: w.getNearbyEntities(el.getLocation(), 3, 3, 3)) {
                                if (((CraftEntity) e).getHandle() instanceof SkeletonKing) {
                                    cancel();
                                }
                            }
                        }
                        else
                            cancel();
                    }
                }.runTaskTimer(Main.getInstance(), 0L, 1L);
            }
        }
        new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                List<Entity> l = new ArrayList<>();
                for (Entity e: w.getNearbyEntities(le.getLocation(), 40, 40, 40)) {
                    if (((CraftEntity) e).getHandle() instanceof SkeletonMinion || ((CraftEntity) e).getHandle() instanceof SkeletonMinion2) {
                        count++;
                        l.add(e);
                    }
                }
                if (!l.isEmpty())
                    for (Entity e: l) {
                        LivingEntity el = (LivingEntity) e;
                        el.remove();
                    }
                w.createExplosion(le.getLocation(), (float) (4 + (count/2.0)), false, false);
                le.remove();
            }
        }.runTaskLater(Main.getInstance(), 100L);
    }
}

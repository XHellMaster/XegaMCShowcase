package mc.xega.skyblock.Mobs.Bosses;

import mc.xega.skyblock.Main;
import mc.xega.skyblock.Mobs.Bosses.Bosses.Scorch.Scorch;
import mc.xega.skyblock.Mobs.Bosses.Bosses.SkeletonKing.SkeletonKing;
import mc.xega.skyblock.Mobs.Bosses.Bosses.SkeletonKing.SkeletonMinion;
import mc.xega.skyblock.Mobs.Bosses.Bosses.SkeletonKing.SkeletonMinion2;
import net.minecraft.world.entity.Entity;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftEntity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BossEventsHandler implements Listener {

    @EventHandler
    public void bossSpawn(EntitySpawnEvent e) {
        if (((CraftEntity) e.getEntity()).getHandle() instanceof Boss b) {
            b.getDialogue().startDialogue();

        }
    }

    @EventHandler
    public void bossDeath(EntityDeathEvent e) {
        if (((CraftEntity) e.getEntity()).getHandle() instanceof Boss b) {
            if (b.hasPassengers)
                b.getPassengers().forEach(Entity::kill);
            if (b instanceof SkeletonKing sk && !sk.died) {
                e.setCancelled(true);
                sk.died = true;
                sk.setHealth(1f);
                sk.triggerDeathAbility();
                b.getDialogue().endDialogue();
            }
            if (b instanceof Scorch s && !s.died) {
                e.setCancelled(true);
                s.died = true;
                s.setInvulnerable(true);
                s.changePhase();
                b.getDialogue().phaseDialogue(4);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        ((LivingEntity) s.getBukkitEntity()).remove();
                        s.getDialogue().endDialogue();
                    }
                }.runTaskLater(Main.getInstance(), 200L);
            }
        }
    }

    @EventHandler
    public void bossDamaged(EntityDamageEvent e) {

        if (((CraftEntity) e.getEntity()).getHandle() instanceof Boss b) {
            if (b instanceof SkeletonKing k) {
                if (e.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) {
                    e.setCancelled(true);
                }
            }
            b.getDialogue().damagedDialogue();
            //sets default phase change from 1-2 to trigger at half health
            if (!(b instanceof Scorch)) {
                if (b.getHealth() < b.getMaxHealth() / 2 && b.getPhase() == 1) {
                    b.changePhase();
                    b.getDialogue().phaseDialogue(2);
                }
            }
            else {
                if (b.getHealth() < (b.getMaxHealth() * 2 / 3) && b.getPhase() == 1) {
                    b.changePhase();
                    b.getDialogue().phaseDialogue(2);
                }
                if (b.getHealth() < (b.getMaxHealth() * 1 / 3) && b.getPhase() == 2) {
                    System.out.println("Test");
                    b.changePhase();
                    b.getDialogue().phaseDialogue(3);
                }
            }
        }

        if (((CraftEntity) e.getEntity()).getHandle() instanceof SkeletonMinion || ((CraftEntity) e.getEntity()).getHandle() instanceof SkeletonMinion2) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void fireballDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Fireball f && e.getEntity() instanceof Player p) {
            if (f.getName().equals("SCORCH_FIREBALL"))
                p.damage(15);
        }
    }
}

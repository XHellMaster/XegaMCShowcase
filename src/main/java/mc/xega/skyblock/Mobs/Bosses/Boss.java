package mc.xega.skyblock.Mobs.Bosses;

import mc.xega.skyblock.Main;
import mc.xega.skyblock.Mobs.Bosses.Dialogue.Dialogue;
import mc.xega.skyblock.Mobs.Entities.CEntity;
import net.minecraft.world.entity.EntityType;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;


public abstract class Boss extends CEntity {


    public Dialogue d;
    boolean hasPassengers;

    public Boss(Location loc, EntityType ent, Main main, List<Float> duration, boolean hasPassengers) {
        super(loc, ent, main, duration);
        this.setPos(loc.getX(), loc.getY(), loc.getZ());
        LivingEntity le = (LivingEntity) this.getBukkitEntity();
        le.setAI(false);
        le.setInvulnerable(true);
        this.hasPassengers = hasPassengers;
        startAttacks(1);
    }


    public Dialogue getDialogue() {
        return d;
    }
    public abstract void addGoals();

    public void startAttacks(int phase) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!isDeadOrDying() && phase != currentPhase && getDialogue().startFight)
                    triggerTimedAbility();
                else {
                    cancel();
                    startAttacks(phase + 1);
                }
            }
        }.runTaskTimer(main, (long) duration *20, (long) duration * 20);
    }

}

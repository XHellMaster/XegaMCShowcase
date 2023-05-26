package mc.xega.skyblock.Mobs.Entities;

import mc.xega.skyblock.Main;
import mc.xega.skyblock.Mobs.Bosses.Abilities.Ability;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public abstract class CEntity extends PathfinderMob {
    public final Main main;

    public List<Float> durations;
    public float duration;
    public int currentPhase = 1;
    public LinkedHashMap<Integer, List<Ability>> allTimedAbilities = new LinkedHashMap<>();
    public LinkedHashMap<Integer, Ability> allPhaseAbilities = new LinkedHashMap<>();

    public Ability deathAbility;

    List<Ability> timedAbilities = new ArrayList<Ability>();
    public CEntity(Location loc, EntityType ent, Main main, List<Float> duration) {
        super(ent, ((CraftWorld) loc.getWorld()).getHandle());
        this.setPos(loc.getX(), loc.getY(), loc.getZ());
        this.main = main;
        this.duration = duration.get(0);
        durations = duration;
    }
    public void addTimedAbilities(List<Ability> a, int phase) {
        allTimedAbilities.put(phase, a);
        if (phase == 1) {
            timedAbilities.addAll(a);
        }
    }
    public void removeTimedAbility(Ability a) {
        timedAbilities.remove(a);
    }
    public void addPhaseAbility(Ability a, int phase) {
        allPhaseAbilities.put(phase, a);
    }
    public void setDeathAbility(Ability a) {
        deathAbility = a;
    }
    public void triggerTimedAbility() {
        int rIndex = (int) (Math.random() * timedAbilities.size());
        Ability selected = timedAbilities.get(rIndex);
        selected.trigger();
    }
    public void triggerPhaseAbility(int phase) {
        allPhaseAbilities.get(phase).trigger();
    }

    public void triggerDeathAbility() {
        deathAbility.trigger();
    }

    public void startAttacks(int phase) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!isDeadOrDying()) {
                    if (phase != currentPhase)
                        triggerTimedAbility();
                    else {
                        cancel();
                        startAttacks(phase + 1);
                    }
                }
                else
                    cancel();
            }
        }.runTaskTimer(main, 100, (long) duration * 20);
    }

    public void changePhase() {
        currentPhase ++;
        timedAbilities.clear();
        List<Ability> phaseAbils = allTimedAbilities.get(currentPhase);
        timedAbilities.addAll(phaseAbils);
        triggerPhaseAbility(currentPhase);
        changeDuration(durations.get(currentPhase - 1));
    }
    public void changeDuration(float duration) {
        this.duration = duration;
    }
    public int getPhase() {
        return currentPhase;
    }
}
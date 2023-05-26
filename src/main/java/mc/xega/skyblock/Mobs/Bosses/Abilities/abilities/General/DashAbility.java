package mc.xega.skyblock.Mobs.Bosses.Abilities.abilities.General;

import mc.xega.skyblock.Mobs.Bosses.Abilities.Ability;
import mc.xega.skyblock.Mobs.Bosses.Abilities.AbilityType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class DashAbility extends Ability {
    float distance = 0;
    public DashAbility(AbilityType type, Entity ent, float distance) {
        super(type, ent);
        this.distance = distance;
    }

    public void trigger() {
        LivingEntity le = (LivingEntity) ent;
        ent.setVelocity(le.getLocation().getDirection().multiply(distance));
    }
}

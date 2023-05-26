package mc.xega.skyblock.Mobs.Bosses.Abilities.abilities.Scorch;

import mc.xega.skyblock.Mobs.Bosses.Abilities.Ability;
import mc.xega.skyblock.Mobs.Bosses.Abilities.AbilityType;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class ScorchPhaseAbility extends Ability {

    public ScorchPhaseAbility(AbilityType type, Entity ent) {
        super(type, ent);
    }

    public void trigger() {
        World w = ent.getWorld();
        w.createExplosion(ent.getLocation(), 10f, true, false);
    }
}

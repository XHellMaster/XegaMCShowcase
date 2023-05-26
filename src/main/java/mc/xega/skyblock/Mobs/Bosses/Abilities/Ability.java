package mc.xega.skyblock.Mobs.Bosses.Abilities;

import org.bukkit.entity.Entity;

public abstract class Ability {

    private AbilityType type;
    public Entity ent;
    public Ability(AbilityType type, Entity ent) {
        this.type = type;
        this.ent = ent;
    }

    public abstract void trigger();

}

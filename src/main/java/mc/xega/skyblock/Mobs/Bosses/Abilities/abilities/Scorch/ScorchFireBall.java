package mc.xega.skyblock.Mobs.Bosses.Abilities.abilities.Scorch;

import mc.xega.skyblock.Mobs.Bosses.Abilities.Ability;
import mc.xega.skyblock.Mobs.Bosses.Abilities.AbilityType;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;

public class ScorchFireBall extends Ability {
    public ScorchFireBall(AbilityType type, Entity ent) {
        super(type, ent);
    }

    public void trigger() {
        World w = ent.getWorld();
        Fireball f = (Fireball) w.spawnEntity(ent.getLocation(), EntityType.FIREBALL);
        f.setCustomName("SCORCH_FIREBALL");
        f.setCustomNameVisible(false);
        f.setDirection(ent.getLocation().getDirection());
        f.setVisualFire(true);
        f.setVelocity(ent.getLocation().getDirection().normalize().multiply(2));
    }
}

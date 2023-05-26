package mc.xega.skyblock.Mobs.Bosses.Bosses.SkeletonKing;

import mc.xega.skyblock.Main;
import mc.xega.skyblock.Mobs.Bosses.Abilities.Ability;
import mc.xega.skyblock.Mobs.Bosses.Abilities.AbilityType;
import mc.xega.skyblock.Mobs.Bosses.Abilities.abilities.SkeletonKing.MinionBoneThrow;
import mc.xega.skyblock.Mobs.Entities.CEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkeletonMinion extends CEntity {
    LivingEntity le;
    public SkeletonMinion(Location loc, Main main, List<Float> durations) {
        super(loc, EntityType.SKELETON, main, durations);
        le = (LivingEntity) this.getBukkitEntity();
        le.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
        le.setHealth(20);
        le.setCustomName(ChatColor.DARK_GRAY + "Skeletal Minion");
        le.setCustomNameVisible(true);
        le = (LivingEntity) this.getBukkitEntity();
        le.getEquipment().setItemInMainHand(new ItemStack(Material.BONE));
        le.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));


    }
    public void addPathFinders() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 0));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 0, true, false, null));
        startAttacks(1);
        List<Ability> phase1Abilities = new ArrayList<Ability>();
        phase1Abilities.add(new MinionBoneThrow(AbilityType.TIMED, le));
        addTimedAbilities(phase1Abilities, 1);
    }
}

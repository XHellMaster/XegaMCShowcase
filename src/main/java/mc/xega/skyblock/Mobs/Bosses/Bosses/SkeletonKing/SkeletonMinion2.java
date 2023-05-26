package mc.xega.skyblock.Mobs.Bosses.Bosses.SkeletonKing;

import mc.xega.skyblock.Main;
import mc.xega.skyblock.Mobs.Bosses.Abilities.Ability;
import mc.xega.skyblock.Mobs.Bosses.Abilities.AbilityType;
import mc.xega.skyblock.Mobs.Bosses.Abilities.abilities.General.DashAbility;
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

public class SkeletonMinion2 extends CEntity {
    LivingEntity le;
    public SkeletonMinion2(Location loc, Main main, List<Float> durations) {
        super(loc, EntityType.SKELETON, main, durations);
        le = (LivingEntity) this.getBukkitEntity();
        le.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(25);
        le.setHealth(25);
        le.setCustomName(ChatColor.RED + "Skeletal Minion");
        le.setCustomNameVisible(true);
        le = (LivingEntity) this.getBukkitEntity();
        le.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
        le.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
        le.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        le.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        le.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));


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
        phase1Abilities.add(new DashAbility(AbilityType.TIMED, le, 2.3f));
        addTimedAbilities(phase1Abilities, 1);
    }
}

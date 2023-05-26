package mc.xega.skyblock.Mobs.Bosses.Bosses.SkeletonKing;

import mc.xega.skyblock.Main;
import mc.xega.skyblock.Mobs.Bosses.Abilities.Ability;
import mc.xega.skyblock.Mobs.Bosses.Abilities.AbilityType;
import mc.xega.skyblock.Mobs.Bosses.Abilities.abilities.SkeletonKing.DeathAbsorb;
import mc.xega.skyblock.Mobs.Bosses.Abilities.abilities.SkeletonKing.ExplodePhaseAbility;
import mc.xega.skyblock.Mobs.Bosses.Abilities.abilities.SkeletonKing.SKSummonAbility;
import mc.xega.skyblock.Mobs.Bosses.Abilities.abilities.SkeletonKing.SKSummonAbilityP2;
import mc.xega.skyblock.Mobs.Bosses.Boss;
import mc.xega.skyblock.Mobs.Bosses.Dialogue.Dialogue;
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

public class SkeletonKing extends Boss {

    public boolean died = false;
    public SkeletonKing(Location loc, Main main, List<Float> duration) {
        super(loc, EntityType.WITHER_SKELETON, main, duration, false);
        LivingEntity le = (LivingEntity) this.getBukkitEntity();
        le.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(100);
        le.setHealth(100);
        le.setCustomName(ChatColor.BOLD + "Skeleton " + ChatColor.GOLD + "KING");
        le.setCustomNameVisible(true);

        d = new Dialogue("SKELETON_KING", this, main); // sets the chance for dialogue to be executed when damaged (IE: 1/5 chance for this)
        d.setDChance(5);
        List<Ability> phase1Abilities = new ArrayList<Ability>();
        phase1Abilities.add(new SKSummonAbility(AbilityType.TIMED, this.getBukkitEntity()));
        List<Ability> phase2Abilities = new ArrayList<Ability>();
        phase2Abilities.add(new SKSummonAbilityP2(AbilityType.TIMED, this.getBukkitEntity()));
        //phase2Abilities.add(new RoseCorruption(AbilityType.TIMED, this.getBukkitEntity()));
        addTimedAbilities(phase1Abilities, 1);
        addTimedAbilities(phase2Abilities, 2);
        addPhaseAbility(new ExplodePhaseAbility(AbilityType.PHASE_CHANGE, this.getBukkitEntity()), 2);
        setDeathAbility(new DeathAbsorb(AbilityType.DEATH, this.getBukkitEntity()));
        le.getEquipment().setHelmet(new ItemStack(Material.NETHERITE_HELMET));
        le.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
        le.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
        le.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
        le.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
    }

    public void addGoals() {
        LivingEntity le = (LivingEntity) this.getBukkitEntity();
        le.setAI(true);
        le.setInvulnerable(false);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 0));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 0, true, false, null));
    }

}
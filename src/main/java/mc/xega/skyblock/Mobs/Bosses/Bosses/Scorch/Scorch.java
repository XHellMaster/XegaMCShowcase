package mc.xega.skyblock.Mobs.Bosses.Bosses.Scorch;

import mc.xega.skyblock.Main;
import mc.xega.skyblock.Mobs.Bosses.Abilities.Ability;
import mc.xega.skyblock.Mobs.Bosses.Abilities.AbilityType;
import mc.xega.skyblock.Mobs.Bosses.Abilities.abilities.General.DashAbility;
import mc.xega.skyblock.Mobs.Bosses.Abilities.abilities.Scorch.*;
import mc.xega.skyblock.Mobs.Bosses.Boss;
import mc.xega.skyblock.Mobs.Bosses.Dialogue.Dialogue;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class Scorch extends Boss {
    public boolean died = false;

    public Scorch(Location loc, Main main, List<Float> duration) {
        super(loc, EntityType.BLAZE, main, duration, false);
        LivingEntity le = (LivingEntity) this.getBukkitEntity();
        le.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(900);
        le.setHealth(900);
        le.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(40);
        le.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(40);
        le.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(10);
        le.setCustomName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Scorch");
        le.setCustomNameVisible(true);
        d = new Dialogue("SCORCH", this, main);
        d.setDChance(7);
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
        List<Ability> phase1Abilities = new ArrayList<>();
        phase1Abilities.add(new ScorchFireBall(AbilityType.TIMED, this.getBukkitEntity()));
        phase1Abilities.add(new FireCurtain(AbilityType.TIMED, this.getBukkitEntity()));
        List<Ability> phase2Abilities = new ArrayList<>();
        phase2Abilities.add(new FireGrenade(AbilityType.TIMED, this.getBukkitEntity()));
        phase2Abilities.add(new FireGrenade(AbilityType.TIMED, this.getBukkitEntity()));
        phase2Abilities.add(new FireGrenade(AbilityType.TIMED, this.getBukkitEntity()));
        phase2Abilities.add(new FireCircles(AbilityType.TIMED, this.getBukkitEntity()));
        phase2Abilities.add(new FireCircles(AbilityType.TIMED, this.getBukkitEntity()));
        phase2Abilities.add(new DashAbility(AbilityType.TIMED, this.getBukkitEntity(), 3));
        List<Ability> phase3Abilities = new ArrayList<>();
        phase3Abilities.add(new FireGrenade(AbilityType.TIMED, this.getBukkitEntity(), 0.06f));
        phase3Abilities.add(new FireGrenade(AbilityType.TIMED, this.getBukkitEntity(), 0.06f));
        phase3Abilities.add(new FireGrenade(AbilityType.TIMED, this.getBukkitEntity(), 0.06f));
        phase3Abilities.add(new FireCircles(AbilityType.TIMED, this.getBukkitEntity()));
        phase3Abilities.add(new FireCircles(AbilityType.TIMED, this.getBukkitEntity()));
        phase3Abilities.add(new FireCircles(AbilityType.TIMED, this.getBukkitEntity()));
        phase3Abilities.add(new DashAbility(AbilityType.TIMED, this.getBukkitEntity(), 3));
        phase3Abilities.add(new DashAbility(AbilityType.TIMED, this.getBukkitEntity(), 3));
        List<Ability> phase4Abilities = new ArrayList<>();
        phase4Abilities.add(new FireGrenade(AbilityType.TIMED, this.getBukkitEntity(), 0.07f));
        phase4Abilities.add(new FireGrenade(AbilityType.TIMED, this.getBukkitEntity(), 0.07f));
        phase4Abilities.add(new FireGrenade(AbilityType.TIMED, this.getBukkitEntity(), 0.07f));
        phase4Abilities.add(new FireGrenade(AbilityType.TIMED, this.getBukkitEntity(), 0.07f));
        phase4Abilities.add(new FireGrenade(AbilityType.TIMED, this.getBukkitEntity(), 0.07f));
        phase4Abilities.add(new FireGrenade(AbilityType.TIMED, this.getBukkitEntity(), 0.07f));
        phase4Abilities.add(new FireCircles(AbilityType.TIMED, this.getBukkitEntity()));

        addTimedAbilities(phase1Abilities, 1);
        addTimedAbilities(phase2Abilities, 2);
        addTimedAbilities(phase3Abilities, 3);
        addTimedAbilities(phase4Abilities, 4);
        addPhaseAbility(new ScorchPhaseAbility(AbilityType.PHASE_CHANGE, this.getBukkitEntity()), 2);
        addPhaseAbility(new ScorchPhaseAbility(AbilityType.PHASE_CHANGE, this.getBukkitEntity()), 3);
        addPhaseAbility(new ScorchPhaseAbility(AbilityType.PHASE_CHANGE, this.getBukkitEntity()), 4);


    }
}

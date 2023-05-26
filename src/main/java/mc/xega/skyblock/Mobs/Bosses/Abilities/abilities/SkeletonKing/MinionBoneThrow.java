package mc.xega.skyblock.Mobs.Bosses.Abilities.abilities.SkeletonKing;

import mc.xega.skyblock.Main;
import mc.xega.skyblock.Mobs.Bosses.Abilities.Ability;
import mc.xega.skyblock.Mobs.Bosses.Abilities.AbilityType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
public class MinionBoneThrow extends Ability {

    public MinionBoneThrow(AbilityType type, Entity ent) {
        super(type, ent);
    }

    public void trigger() {

        LivingEntity le = (LivingEntity) ent;
        le.swingMainHand();
        ArmorStand as = (ArmorStand) ent.getWorld().spawnEntity(le.getEyeLocation(), EntityType.ARMOR_STAND);

        as.setInvisible(true);
        as.setInvulnerable(true);
        as.setArms(false);
        as.setBasePlate(false);
        as.setMarker(true);
        as.setGravity(false);
        as.setSmall(true);
        as.setRightArmPose(new EulerAngle(Math.toRadians(90), Math.toRadians(0), Math.toRadians(0)));
        as.getEquipment().setItemInMainHand(new ItemStack(Material.BONE));
        World world = ent.getWorld();


        Location dest = as.getLocation().add(le.getEyeLocation().getDirection().multiply(10));
        final Vector vector = dest.subtract(as.getEyeLocation()).toVector();

        new BukkitRunnable() {
            int i = 0;
            final int distance = 10;

            public void run() {

                if (!le.isDead()) {

                    EulerAngle initRot = as.getRightArmPose();
                    EulerAngle newRot = initRot.add(20, 0, 0);
                    as.setRightArmPose(newRot);
                    as.teleport(as.getLocation().add(vector.normalize()));
                    world.spawnParticle(Particle.WHITE_ASH, as.getEyeLocation().add(0, 0.7, 0), 5);
                    if (as.getTargetBlockExact(1) != null && !(as.getTargetBlockExact(1).isPassable())) {

                        if (!as.isDead()) {
                            as.remove();
                            cancel();
                        }
                    }

                    for (Entity entity : as.getLocation().getChunk().getEntities()) {
                        if (!as.isDead()) {
                            if (as.getLocation().distanceSquared(entity.getLocation()) <= 3) {
                                if (entity instanceof Player p) {
                                    p.damage(5, le);
                                }
                            }
                        }
                    }
                }

                if (i > distance) {

                    as.remove();
                    cancel();


                }
                else if (le.isDead()) {
                    cancel();
                    as.remove();
                }
                i++;


            }

        }.runTaskTimer(Main.getInstance(), 0L, 2L);
    }
}

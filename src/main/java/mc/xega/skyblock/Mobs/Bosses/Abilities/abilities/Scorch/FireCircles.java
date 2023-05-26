package mc.xega.skyblock.Mobs.Bosses.Abilities.abilities.Scorch;

import mc.xega.skyblock.Main;
import mc.xega.skyblock.Mobs.Bosses.Abilities.Ability;
import mc.xega.skyblock.Mobs.Bosses.Abilities.AbilityType;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class FireCircles extends Ability {

    Main plugin;

    public FireCircles(AbilityType type, Entity ent) {
        super(type, ent);
        plugin = Main.getInstance();
    }

    public void trigger() {

        LivingEntity le = (LivingEntity) ent;

        World w = le.getWorld();

        List<Player> players = new ArrayList<Player>();
        List<Location> locs = new ArrayList<>();

        Location loc2 = null;
        for (Entity e: w.getNearbyEntities(le.getLocation(), 30,4,30)) {
            if (e instanceof Player p && p.getGameMode().equals(GameMode.SURVIVAL)) {
                if (loc2 == null){
                    loc2 = p.getLocation();
                }
                players.add(p);
                locs.add(p.getLocation());
            }
        }

        if(players.size() != 0 && !le.isDead()) {
            List<ArmorStand> ams = new ArrayList<>();
            for (Location l: locs) {
                final ArmorStand as = (ArmorStand) w.spawnEntity(l, EntityType.ARMOR_STAND);
                as.setInvisible(true);
                as.setInvulnerable(true);
                as.setArms(false);
                as.setBasePlate(false);
                as.setMarker(true);
                as.setSmall(true);
                as.getEquipment().setHelmet(new ItemStack(Material.BLAZE_ROD));
                ams.add(as);
                for (Player p: players)
                    p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0f, 3.0f);
            }
            new BukkitRunnable() {
                double scaleX = 3;
                double aScaleX = 1.5;
                double scaleZ = 3;
                double aScaleZ = 1.5;
                double density = 0.2;
                boolean scheduled = false;
                boolean cancel = false;
                @Override
                public void run() {

                    for (ArmorStand as: ams) {
                        if(!as.isDead()) {
                            Location loc = as.getLocation();

                            for (double i=0; i < 2 * Math.PI ; i +=density) {
                                double x = Math.cos(i) * scaleX;
                                double x1 = Math.cos(i) * aScaleX;
                                double z = Math.sin(i) * scaleZ;
                                double z1 = Math.sin(i) * aScaleZ;

                                Particle.DustOptions dust1 = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 3);
                                Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(255, 115, 0), 3);

                                w.spawnParticle(Particle.REDSTONE, loc.getX() + x, loc.getY() + 0.2,  loc.getZ() + z, 1, 0, 0, 0,1, dust);
                                w.spawnParticle(Particle.REDSTONE, loc.getX() + x1, loc.getY() + 0.2,  loc.getZ() + z1, 1, 0, 0, 0, 1, dust1);

                            }
                            if (!scheduled) {
                                new BukkitRunnable() {
                                    List<Player> ps = new ArrayList<>();
                                    List<Player> ps1 = new ArrayList<>();
                                    @Override
                                    public void run() {
                                        for (Entity entity : w.getNearbyEntities(loc, 3,2,3)) {
                                            if (entity instanceof Player p) {
                                                ps.add(p);
                                            }
                                        }
                                        for (Entity entity : w.getNearbyEntities(loc, 1.5,2,1.5)) {
                                            if (entity instanceof Player p) {
                                                ps1.add(p);
                                            }
                                        }
                                        for (Player p: ps) {
                                            if (ps1.contains(p)) {
                                                p.damage(150, le);
                                            }
                                            else {
                                                p.damage(70, le);
                                            }
                                        }
                                        as.remove();
                                        w.createExplosion(as.getLocation(), 1f, true, false);

                                        cancel = true;
                                    }
                                }.runTaskLater(plugin, 20L);
                                scheduled = true;
                            }
                            if (cancel) {
                                cancel();
                            }

                        }
                        else {
                            cancel();
                            as.remove();
                        }
                    }

                }
            }.runTaskTimer(plugin, 0L, 1L);
        }

    }
}

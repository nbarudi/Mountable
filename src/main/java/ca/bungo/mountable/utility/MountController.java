package ca.bungo.mountable.utility;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.bukkit.Material;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class MountController {

    private static class MountableGoal extends Goal {

        private PathfinderMob mob;
        private Material controlItem;
        private double speed;

        private Player bukkitPlayer;

        public MountableGoal(PathfinderMob mob, Material controlItem, double speed) {
            this.mob = mob;
            this.controlItem = controlItem;
            this.speed = speed;
        }

        @Override
        public boolean canUse() {
            if(!this.mob.getPassengers().isEmpty()){
                if(this.mob.getPassengers().getFirst() instanceof net.minecraft.world.entity.player.Player player){
                    var bukkitPlayer = player.getBukkitEntity();

                    if(!(bukkitPlayer instanceof Player)) return false;

                    this.bukkitPlayer = (Player) bukkitPlayer;
                    if(((Player) bukkitPlayer).getInventory().getItemInMainHand().getType().equals(controlItem) ||
                            ((Player) bukkitPlayer).getInventory().getItemInOffHand().getType().equals(controlItem))
                        return true;
                    return false;
                }
            }
            return false;
        }

        private Vector getVector(){
            if(!this.mob.getPassengers().isEmpty()){
                if(this.mob.getPassengers().getFirst() instanceof net.minecraft.world.entity.player.Player player){
                    var bukkitPlayer = player.getBukkitEntity();
                    if(bukkitPlayer instanceof Player) {
                        return ((Player)bukkitPlayer).getLocation().getDirection();
                    }
                }
            }
            return new Vector(0, 0, 0);
        }

        @Override
        public void start() {
            var vector = this.getVector();
            this.mob.getNavigation().moveTo(vector.getX(), vector.getY(), vector.getZ(), speed);
        }

        @Override
        public boolean canContinueToUse() {
            return !this.mob.getNavigation().isDone();
        }

        @Override
        public void tick() {
            var vector = this.getVector();
            if(this.bukkitPlayer == null) return;

            var location = bukkitPlayer.getLocation().add(vector.multiply(2));
            this.mob.getNavigation().moveTo(location.x(), location.y(), location.z(), speed);
        }
    }


    public void addMountPathfinder(Entity entity, Player player, Material controlItem, double speed) {
        var nmsEntity = ((CraftEntity)entity).getHandle();
        if(!(nmsEntity instanceof PathfinderMob pathfinderMob)) return;

        ((PathfinderMob) nmsEntity).goalSelector.addGoal(0, new MountableGoal(pathfinderMob, controlItem, speed));
    }
}

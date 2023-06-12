package ca.bungo.mountable.events;

import ca.bungo.mountable.Mountable;
import ca.bungo.mountable.manager.MountManager;
import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.util.Vector;

public class MountEvents implements Listener {

    @EventHandler
    public void onTick(ServerTickEndEvent event){
        return;
        /*
        for(Entity ent : MountManager.mountList){
            if(ent.getPassengers().size() == 0) continue;
            if(!(ent.getPassengers().get(0) instanceof Player player)) continue;

            Material controller = Material.BAMBOO;

            String materialName = Mountable.getInstance().getConfig().getString("control-item");
            if(materialName != null)
                controller = Material.valueOf(materialName.toUpperCase());


            if(player.getInventory().getItemInMainHand().getType().equals(controller)
                    || player.getInventory().getItemInOffHand().getType().equals(controller)){

                Vector direction = player.getLocation().getDirection();
                direction.setY(-1);

                double multiplier = 0.5;
                if(Mountable.getInstance().getConfig().getDouble("speed-multiplier") != 0)
                    multiplier = Mountable.getInstance().getConfig().getDouble("speed-multiplier");

                ent.setVelocity(direction.multiply(multiplier));
                ent.getLocation().setDirection(direction);
            }
        }*/
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event){
        if(MountManager.isMount(event.getRightClicked()) && event.getRightClicked().getPassengers().size() == 0){
            event.getRightClicked().addPassenger(event.getPlayer());
        }
    }

}

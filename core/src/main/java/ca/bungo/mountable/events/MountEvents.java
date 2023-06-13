package ca.bungo.mountable.events;

import ca.bungo.mountable.Mountable;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;

public class MountEvents implements Listener {

    private Map<EntityType, MountableType> mount = new HashMap<>();

    class MountableType{
        public EntityType entityType;
        public double speed;
        public Material controlItem;
        public Material mountItem;
    }

    public MountEvents(){

        FileConfiguration config = Mountable.getInstance().getConfig();

        if(config.getConfigurationSection("mounts") == null) return;

        for(String key : config.getConfigurationSection("mounts").getKeys(false)){
            ConfigurationSection section = config.getConfigurationSection("mounts." + key);

            String _entityType = section.getString("entity");
            double speed = section.getDouble("speed");
            String _controlItemType = section.getString("control-item");
            String _mountItemType = section.getString("mount-item");

            EntityType entityType = EntityType.valueOf(_entityType.toUpperCase());

            Material controlItem = Material.valueOf(_controlItemType.toUpperCase());
            Material mountItem = Material.valueOf(_mountItemType.toUpperCase());

            MountableType type = new MountableType();
            type.controlItem = controlItem;
            type.entityType = entityType;
            type.mountItem = mountItem;
            type.speed = speed;

            mount.put(entityType, type);

        }

    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event){
        PlayerInventory inventory = event.getPlayer().getInventory();
        Entity entity = event.getRightClicked();

        if(mount.containsKey(entity.getType())){

            MountableType mountableType = mount.get(entity.getType());

            if(inventory.getItemInMainHand().getType().equals(mountableType.mountItem)
                    || inventory.getItemInOffHand().getType().equals(mountableType.mountItem)){
                entity.addPassenger(event.getPlayer());
                Mountable.getInstance().abstractedHandler.addMountPathfinder(entity, event.getPlayer(),
                        mountableType.controlItem,
                        mountableType.speed);
            }

        }


    }

}

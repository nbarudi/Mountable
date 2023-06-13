package ca.bungo.mountable.abstracted;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractedHandler {
    protected AbstractedLink helper;

    public AbstractedHandler(AbstractedLink helper){
        this.helper = helper;
    }

    public abstract void addMountPathfinder(Entity entity, Player player, Material heldItem, double speed);

}

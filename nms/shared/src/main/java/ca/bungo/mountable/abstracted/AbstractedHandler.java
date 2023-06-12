package ca.bungo.mountable.abstracted;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class AbstractedHandler {
    protected AbstractedLink helper;

    public AbstractedHandler(AbstractedLink helper){
        this.helper = helper;
    }

    public abstract void addMountPathfinder(Entity entity, Player player);

}

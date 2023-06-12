package ca.bungo.mountable.commands;

import ca.bungo.mountable.Mountable;
import ca.bungo.mountable.manager.MountManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand extends Command {

    public SpawnCommand(@NotNull String name) {
        super(name);
        this.setPermission("mountable.spawn");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(!(sender instanceof Player player)) return false;

        if(args.length != 1){
            sender.sendMessage("Invalid usage! /" + this.getName() + " <EntityType>");
            return false;
        }

        String entityType = args[0];
        EntityType type = EntityType.valueOf(entityType.toUpperCase());

        Entity entity = player.getWorld().spawnEntity(player.getLocation(), type);
        MountManager.addMount(entity);

        Mountable.getInstance().abstractedHandler.addMountPathfinder(entity, player);

        sender.sendMessage("Your mount has been spawned!");
        return false;
    }
}

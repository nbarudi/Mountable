package ca.bungo.mountable;

import ca.bungo.mountable.commands.SpawnCommand;
import ca.bungo.mountable.events.MountEvents;
import ca.bungo.mountable.utility.MountController;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public final class Mountable extends JavaPlugin {

    private static Mountable instance;
    public MountController mountController;

    private final String pkg = this.getClass().getCanonicalName().substring(0,
            this.getClass().getCanonicalName().length()-this.getClass().getSimpleName().length());

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        this.saveDefaultConfig();

        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Mountable getInstance(){
        return instance;
    }

    private void registerCommands(){
        //this.getServer().getCommandMap().register("mountable", new SpawnCommand("mount"));
    }
    private void registerEvents(){
        this.getServer().getPluginManager().registerEvents(new MountEvents(), this);
    }
}

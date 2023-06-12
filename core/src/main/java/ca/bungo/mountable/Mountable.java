package ca.bungo.mountable;

import ca.bungo.mountable.abstracted.AbstractedHandler;
import ca.bungo.mountable.abstracted.AbstractedLink;
import ca.bungo.mountable.commands.SpawnCommand;
import ca.bungo.mountable.events.MountEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public final class Mountable extends JavaPlugin {

    private static Mountable instance;
    public AbstractedHandler abstractedHandler;

    private final String pkg = this.getClass().getCanonicalName().substring(0,
            this.getClass().getCanonicalName().length()-this.getClass().getSimpleName().length());

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        loadAbstract();

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
        this.getServer().getCommandMap().register("mountable", new SpawnCommand("mount"));
    }
    private void registerEvents(){
        this.getServer().getPluginManager().registerEvents(new MountEvents(), this);
    }

    private void loadAbstract(){

        AbstractedLink helper =  new AbstractedLink(){
            @Override
            public Plugin getInstance() {
                return Mountable.getInstance();
            }
        };

        String ver = Bukkit.getServer().getClass().getPackage().getName().replace('.', ',').split(",")[3];
        getLogger().info("Attempting to load version: " + ver);
        try {
            Class<?> handler = Class.forName(pkg + "abstracted." + ver + ".Abstracted" + ver);
            this.abstractedHandler = (AbstractedHandler) handler.getConstructor(AbstractedLink.class).newInstance(helper);
            getLogger().info("Loaded NMS version: " + ver + "!");
        } catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                InvocationTargetException e){
            e.printStackTrace();
            getLogger().warning("Failed to find Abstract Handlder for version: " + ver);
            getLogger().warning("Attempted Class: " + pkg + "abstracted." + ver + ".Abstracted" + ver);
        }
    }
}

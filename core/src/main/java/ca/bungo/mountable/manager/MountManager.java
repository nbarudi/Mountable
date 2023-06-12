package ca.bungo.mountable.manager;

import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class MountManager {

    public static final List<Entity> mountList = new ArrayList<>();

    public static boolean isMount(Entity ent){
        return mountList.contains(ent);
    }

    public static void addMount(Entity ent){
        mountList.add(ent);
    }

}

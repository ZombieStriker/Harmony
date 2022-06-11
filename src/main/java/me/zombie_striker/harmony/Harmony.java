package me.zombie_striker.harmony;

import me.zombie_striker.harmony.custom.CustomItemManager;
import me.zombie_striker.harmony.resourcepack.ResourcepackManager;
import me.zombie_striker.harmony.resourcepack.daemon.ResourcepackDaemon;
import me.zombie_striker.harmony.util.BukkitUtil;

public class Harmony {

    private static Harmony harmony;
    private final HarmonyPlugin plugin;


    private final ResourcepackManager manager;
    private final ResourcepackDaemon resourcepackDaemon;
    private final CustomItemManager customManager;


    public static Harmony getInstance(){
        return harmony;
    }
    protected Harmony(HarmonyPlugin plugin, String resourcepackname, int daemonport){
        harmony = this;
        this.plugin=plugin;
        this.manager = new ResourcepackManager(plugin,resourcepackname+".zip", BukkitUtil.getIp(),daemonport);
        this.resourcepackDaemon = new ResourcepackDaemon(daemonport);
        this.customManager = new CustomItemManager(plugin);
    }

    public CustomItemManager getCustomItemManager() {
        return customManager;
    }

    public HarmonyPlugin getPlugin() {
        return plugin;
    }

    public ResourcepackDaemon getResourcepackDaemon() {
        return resourcepackDaemon;
    }

    public ResourcepackManager getManager() {
        return manager;
    }
}

package me.zombie_striker.harmony;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class HarmonyListener implements Listener {

    private final HarmonyPlugin plugin;
    public HarmonyListener(HarmonyPlugin harmonyPlugin) {
        this.plugin = harmonyPlugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(plugin.getConfig().getBoolean("resourcepack.send_on_join")){
            Harmony.getInstance().getManager().sendResourcePack(event.getPlayer(),Harmony.getInstance().getManager().getResourcepackName());
        }
    }


}

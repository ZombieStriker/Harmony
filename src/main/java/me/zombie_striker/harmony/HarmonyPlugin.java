package me.zombie_striker.harmony;

import me.zombie_striker.harmony.events.CustomItemLoadCompleteEvent;
import me.zombie_striker.harmony.events.PrepareCustomItemLoadEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public final class HarmonyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        if(!new File(getDataFolder(),"config.yml").exists()){
            getConfig().set("resourcepack.provider_daemon.port",25569);
            getConfig().set("resourcepack.file_name","resourcepack");
            getConfig().set("resourcepack.send_on_join",true);
            saveConfig();
        }
        String filename = getConfig().getString("resourcepack.file_name");
        int port = getConfig().getInt("resourcepack.provider_daemon.port");
        new Harmony(this,filename,port);

        HarmonyCommand command = new HarmonyCommand(this);
        getCommand("harmony").setExecutor(command);

        Bukkit.getPluginManager().registerEvents(new HarmonyListener(this),this);


        /**
         * This section is for loading textures and models for the resourcepack.
         */

        Harmony.getInstance().getManager().transferTextureFile("red",Harmony.class.getResourceAsStream("resourcepack_textures/red.png"));
        Harmony.getInstance().getManager().transferTextureFile("black",Harmony.class.getResourceAsStream("resourcepack_textures/black.png"));
        Harmony.getInstance().getManager().transferTextureFile("gray13",Harmony.class.getResourceAsStream("resourcepack_textures/gray13.png"));
        Harmony.getInstance().getManager().transferTextureFile("gray21",Harmony.class.getResourceAsStream("resourcepack_textures/gray21.png"));
        Harmony.getInstance().getManager().transferTextureFile("gray42",Harmony.class.getResourceAsStream("resourcepack_textures/gray42.png"));
        Harmony.getInstance().getManager().transferTextureFile("brown1",Harmony.class.getResourceAsStream("resourcepack_textures/brown1.png"));
        Harmony.getInstance().getManager().transferTextureFile("brown2",Harmony.class.getResourceAsStream("resourcepack_textures/brown2.png"));
        Harmony.getInstance().getManager().transferTextureFile("brown3",Harmony.class.getResourceAsStream("resourcepack_textures/brown3.png"));
        Harmony.getInstance().getManager().transferTextureFile("brown4",Harmony.class.getResourceAsStream("resourcepack_textures/brown4.png"));


        new BukkitRunnable(){
            @Override
            public void run() {
                PrepareCustomItemLoadEvent event1 = new PrepareCustomItemLoadEvent();
                Bukkit.getPluginManager().callEvent(event1);
                CustomItemLoadCompleteEvent event2 = new CustomItemLoadCompleteEvent();
                Bukkit.getPluginManager().callEvent(event2);

                Harmony.getInstance().getManager().generateResourcepack();
            }
        }.runTask(this);
    }

    @Override
    public void onDisable() {
        Harmony.getInstance().getResourcepackDaemon().shutdown();
    }
}

package me.zombie_striker.harmony;

import me.zombie_striker.harmony.permissions.PermissionsHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class HarmonyCommand implements CommandExecutor {

    private final HarmonyPlugin plugin;
    public HarmonyCommand(HarmonyPlugin harmonyPlugin) {
    this.plugin = harmonyPlugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission(PermissionsHandler.PERM_COMMAND)){
            if(args.length == 0){
                sender.sendMessage("--=Harmony v"+plugin.getDescription().getVersion());
                sender.sendMessage("-Created by "+plugin.getDescription().getAuthors().toString());
                return true;
            }
            if(args[0].equalsIgnoreCase("reload")){
                sender.sendMessage("Reloaded, but idk why you need to at the moment.");
                return true;
            }
        }
        return true;
    }
}

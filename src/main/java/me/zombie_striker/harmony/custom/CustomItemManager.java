package me.zombie_striker.harmony.custom;

import me.zombie_striker.harmony.HarmonyPlugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class CustomItemManager {

    private final List<CustomItem> customitems = new LinkedList<>();
    private final HarmonyPlugin plugin;


    public CustomItemManager(HarmonyPlugin plugin){
        this.plugin = plugin;
    }

    public CustomItem registerCustomItem(Material material, int id, String model_json_name){
        CustomItem customItem = new CustomItem(material,id,model_json_name);
        customitems.add(customItem);
        return customItem;
    }

    public int findFirstOpenSlot(Material material, int minId){
        int slot = minId;
        doloop: do{
            for(CustomItem ci : customitems){
                if(ci.getBaseMaterial()==material){
                    if(slot==ci.getModelID())
                        slot++;
                        continue doloop;
                }
            }
            return slot;
        }while(true);
    }

    public boolean isCustomItem(ItemStack is){
        for(CustomItem ci : customitems){
            if(ci.getBaseMaterial()==is.getType()){
                if(!is.hasItemMeta()||!is.getItemMeta().hasCustomModelData())
                    continue;
                if(ci.getModelID()==is.getItemMeta().getCustomModelData())
                    return true;
            }
        }
        return false;
    }

    public CustomItem getCustomItemFromItemStack(ItemStack is){
        for(CustomItem ci : customitems){
            if(ci.getBaseMaterial()==is.getType()){
                if(!is.hasItemMeta()||!is.getItemMeta().hasCustomModelData())
                    continue;
                if(ci.getModelID()==is.getItemMeta().getCustomModelData())
                    return ci;
            }
        }
        return null;
    }
    public List<CustomItem> getCustomitems() {
        return customitems;
    }
}

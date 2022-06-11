package me.zombie_striker.harmony.custom;

import org.bukkit.Material;

public class CustomItem {

    private final Material baseMaterial;
    private final int modelID;
    private final String model_json_name;

    public CustomItem(Material material, int modelid, String model_json_name){
        this.baseMaterial = material;
        this.modelID = modelid;
        this.model_json_name = model_json_name;
    }

    public String getModel_json_name() {
        return model_json_name;
    }

    public int getModelID() {
        return modelID;
    }

    public Material getBaseMaterial() {
        return baseMaterial;
    }

}

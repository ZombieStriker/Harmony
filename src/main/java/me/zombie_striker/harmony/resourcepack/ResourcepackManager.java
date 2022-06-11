package me.zombie_striker.harmony.resourcepack;

import me.zombie_striker.harmony.Harmony;
import me.zombie_striker.harmony.HarmonyPlugin;
import me.zombie_striker.harmony.custom.CustomItem;
import me.zombie_striker.harmony.util.SimpleFileWriter;
import me.zombie_striker.harmony.util.Util;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ResourcepackManager {

    private final HarmonyPlugin plugin;
    private String resourcepackName;

    private String ip;
    private int port;

    public ResourcepackManager(HarmonyPlugin plugin, String resourcepackName, String ip, int port){
        this.plugin = plugin;
        this.resourcepackName = resourcepackName;
        this.ip = ip;
        this.port = port;
    }

    public String getResourcepackName() {
        return resourcepackName;
    }

    public File getResourcepackFile(){
        return new File(plugin.getDataFolder(),resourcepackName);
    }
    public File getDumpDataFile(){
        return new File(plugin.getDataFolder(),"resourcepack");
    }
    public File getBlockStatesFolder(){
        return new File(getDumpDataFile(),"assets/minecraft/blockstates");
    }
    public File getModelsFolder(){
        return new File(getDumpDataFile(),"assets/minecraft/models");
    }
    public File getModelItemFolder(){
        return new File(getModelsFolder(),"item");
    }

    public void transferItemModelJsonFile(String modelName, InputStream fileContents){
        File json = new File(getModelItemFolder(),modelName+".json");
        if(!json.exists()){
            try {
                json.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            FileWriter fw = new FileWriter(json);
            int read = fileContents.read();
            while(read!=-1){
                fw.write(read);
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateResourcepack(){
        File dump = getDumpDataFile();
        if(!dump.exists()){
            dump.mkdirs();
        }
        File packmcmeta = new File(dump,"pack.mcmeta");
        SimpleFileWriter sfw = new SimpleFileWriter(packmcmeta);
        sfw.writeIndentLine("{",0);
        sfw.writeIndentLine("\"pack\": {",1);
        sfw.writeIndentLine("\"pack_format\": 6,",2);
        sfw.writeIndentLine("\"description\": Harmony generated resourcepack.",2);
        sfw.writeIndentLine("}",1);
        sfw.writeIndentLine("}",0);
        sfw.saveFile();

        if(!getModelItemFolder().exists())
            getModelItemFolder().mkdirs();

        generateRabbitFootItem();
    }

    private void generateRabbitFootItem() {
        File rabbit_footjson = new File(getModelItemFolder(),"rabbit_foot.json");
        SimpleFileWriter sfw = new SimpleFileWriter(rabbit_footjson);
        sfw.writeIndentLine("{",0);
        sfw.writeIndentLine("\"parent\": \"minecraft:item/generated\",",1);
        sfw.writeIndentLine("\"textures\": {",1);
        sfw.writeIndentLine("\"layter0\": \"minecraft:item/rabbit_foot\"",2);
        sfw.writeIndentLine("},",1);
        sfw.writeIndentLine("\"overrides\": [",1);

        List<CustomItem> customitems = Harmony.getInstance().getCustomItemManager().getCustomitems();
        for(int i = 0; i < customitems.size();i++){
            CustomItem ci = customitems.get(i);
            sfw.writeIndentLine("{",2);
            sfw.writeIndentLine("\"predicate\": {",3);
            sfw.writeIndentLine("\"custom_model_data\": "+ci.getModelID(),4);
            sfw.writeIndentLine("},",3);
            sfw.writeIndentLine("\"model\": \"item/"+ci.getModel_json_name()+"\"",3);
            if(i == customitems.size()-1){
                sfw.writeIndentLine("}",2);
            }else{
                sfw.writeIndentLine("},",2);
            }
        }

        sfw.writeIndentLine("]",1);
        sfw.writeIndentLine("}",0);
    }


    /**
     * Send a resource pack to the desired player
     *
     * @param player
     * The player to send it to, note that they must have the right permission to download the pack
     * @param resourcepack
     * The id of the resourcepack to send; must be valid
     */
    public void sendResourcePack(Player player, String resourcepack ) {
        File file = Harmony.getInstance().getManager().getResourcepackFile();
        try {
            player.setResourcePack( "http://" + ip + ":" + port + "/" + resourcepack, Util.calcSHA1( file ) );
        } catch (NoSuchAlgorithmException | IOException e ) {
            e.printStackTrace();
        }
    }


}

package com.gmail.awburchette.plugin.SuperBow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
//import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
//import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.config.Configuration;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

/**
 * SuperBow for Bukkit
 *
 * @author bitswitch
 */
public class SuperBow extends JavaPlugin implements Runnable {
    SuperBowEntityListener entityListener;
    SuperBowPlayerListener playerListener;
    SuperBowBlockListener blockListener;
    SuperBowArrowController arrowController;
    Configuration config;
    public static PermissionHandler Permissions;
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    
    private void setupPermissions() {
        Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");

        if (this.Permissions == null) {
            if (test != null) {
                this.Permissions = ((Permissions)test).getHandler();
            } else {
                System.out.println("Permissions not detected.");
                System.out.println("Permissions required.");
                this.getServer().getPluginManager().disablePlugin(this);
            }
        }
    }
    
    @Override
    public void run(){
       arrowController.checkArrows();
    }

    
    public void readConfig() {
    	String dir = "plugins/SuperBow/";
    	String filename = "config.yml";
		File file = new File(dir);
		if (!(file.exists())){
			file.mkdir();
		}
		file = new File(dir + filename);
    	if (!file.exists()){
    	      try{
    	    	    // Create file 
    	    	    FileWriter fstream = new FileWriter(dir + filename);
    	    	    BufferedWriter out = new BufferedWriter(fstream);
    	    	    out.write("SuperBow:\n");
    	    	    out.write("    Default:\n");
    	    	    out.write("        damageMultiplier: 1\n");
    	    	    out.write("        fireArrows:\n");
    	    	    out.write("            block: false\n");
    	    	    out.write("            entity: false\n");
    	    	    out.write("            flight: false\n");
    	    	    out.write("    Users:\n");
    	    	    //Close the output stream
    	    	    out.close();
    	    	    }catch (Exception e){//Catch exception if any
    	    	      System.out.println("SuperBow could not write the default config file.");
    	    	    }
    	}
    	config = new Configuration(file);
    	config.load();
    }
    
    public void reloadConfig() {
    	config.load();
    }
        
    public void onEnable() {
    	entityListener = new SuperBowEntityListener(this);
    	playerListener = new SuperBowPlayerListener(this);
    	arrowController = new SuperBowArrowController(this);
        // Register our events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.ENTITY_DAMAGED, entityListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_ITEM, playerListener, Priority.Normal, this);
        //pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Priority.Normal, this);
        readConfig();
        setupPermissions();
        getServer().getScheduler().scheduleSyncRepeatingTask(this, this, 0, 60);
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    
    public void onDisable() {
    	PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!" );
    }
    
    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
}


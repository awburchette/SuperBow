package com.gmail.awburchette.plugin.SuperBow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import org.bukkit.entity.Player;
//import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
//import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.config.Configuration;

/**
 * SuperBow for Bukkit
 *
 * @author bitswitch
 */
public class SuperBow extends JavaPlugin {
    //SuperBowPlayerListener playerListener;
    //SuperBowBlockListener blockListener;
    SuperBowEntityListener entityListener;
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    
    public void readConfig() {
    	String dir = "plugins/SuperBow/";
    	String filename = "config.yml";
		File file = new File(dir);
		if (!(file.exists())){
			file.mkdir();
		}
    	Configuration _config = new Configuration(new File(dir + filename));

    	_config.load();
		file = new File(dir + filename);
    	if (!file.exists()){
    	      try{
    	    	    // Create file 
    	    	    FileWriter fstream = new FileWriter(dir + filename);
    	    	    BufferedWriter out = new BufferedWriter(fstream);
    	    	    out.write("SuperBow:\n");
    	    	    out.write("    useFireArrows: false\n");
    	    	    out.write("    damageMultiplier: 1\n");
    	    	    //Close the output stream
    	    	    out.close();
    	    	    }catch (Exception e){//Catch exception if any
    	    	      System.out.println("SuperBow could not write the default config file.");
    	    	    }
    	}
    	// Reading from yml file
    	Boolean useFireArrows = _config.getBoolean("SuperBow.useFireArrows", false);
    	int intDamageMultiplier = _config.getInt("SuperBow.damageMultiplier", 1);
    	SuperBowEntityListener.setUseFireArrows(useFireArrows);
    	SuperBowEntityListener.setDamageMultiplier(intDamageMultiplier);
        }
    
    public void onEnable() {
    	//playerListener = new SuperBowPlayerListener(this);
    	//blockListener = new SuperBowBlockListener(this);
    	entityListener = new SuperBowEntityListener(this);
        // Register our events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.ENTITY_DAMAGED, entityListener, Priority.Normal, this);
        readConfig();
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


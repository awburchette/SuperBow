package com.gmail.awburchette.plugin.SuperBow;

//import org.bukkit.Location;
//import org.bukkit.entity.Item;
//import org.bukkit.entity.Player;
//import org.bukkit.event.Event.Type;
//import org.bukkit.event.player.PlayerChatEvent;
//import org.bukkit.event.player.PlayerEvent;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerItemEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
//import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.config.Configuration;

/**
 * Handle events for all Player related events
 * @author bitswitch
 */
public class SuperBowPlayerListener extends PlayerListener {
    private final SuperBow plugin;
    private boolean useFireArrows;
    private int intDamageMultiplier;

    public SuperBowPlayerListener(SuperBow instance) {
        plugin = instance;
    }
    
    public void onPlayerItem(PlayerItemEvent event) {
    	Player p = event.getPlayer();
		Inventory inv = p.getInventory();
    	ItemStack inHand = p.getItemInHand();
    	if(inHand.getType() == Material.BOW) {
    		if(inv.contains(Material.ARROW)) { 
    			useFireArrows = plugin.config.getBoolean("SuperBow." + p.getName() + ".fireArrows.flight", plugin.config.getBoolean("SuperBow.Default.fireArrows.flight", false));
    			event.setCancelled(true);
    			Arrow a = p.shootArrow();
    			ItemStack oneArrow = new ItemStack(Material.ARROW, 1);
    			inv.removeItem(oneArrow);
    			if(useFireArrows) {
    				a.setFireTicks(200);
    			}
    		}
    	}
    }
    
    public void onPlayerCommandPreprocess(PlayerChatEvent event) {
    	String[] split = event.getMessage().split(" ");
		Player p = event.getPlayer();
		if (split[0].equalsIgnoreCase("/superbow")){
			if (split.length >= 2){
				if (split[1].equalsIgnoreCase("reload")){
					if(p.isOp()) {
						plugin.reloadConfig();
					}
				}
				if (split[1].equalsIgnoreCase("block")) {
					if(split.length == 2) {
						String key = ".fireArrows.block";
						useFireArrows = plugin.config.getBoolean("SuperBow." + p.getName() + key, plugin.config.getBoolean("SuperBow.Default" + key, false));
						plugin.config.setProperty("SuperBow." + p.getName() + key, !useFireArrows);
						if(plugin.config.save()) {
							p.sendMessage("Saved change");
						} else {
							p.sendMessage("Failed to save change");
						}
						if(!useFireArrows) {
							p.sendMessage("Arrows will ignite blocks! (Currently not working)");
						} else {
							p.sendMessage("Arrows will not ignite blocks! (Currently not working)");
						}
					} else {
						p.sendMessage("Usage: /superbow block");
					}
				}
				if (split[1].equalsIgnoreCase("entity")) {
					if(split.length == 2) {
						String key = ".fireArrows.entity";
						useFireArrows = plugin.config.getBoolean("SuperBow." + p.getName() + key, plugin.config.getBoolean("SuperBow.Default" + key, false));
						plugin.config.setProperty("SuperBow." + p.getName() + key, !useFireArrows);
						if(plugin.config.save()) {
							p.sendMessage("Saved change");
						} else {
							p.sendMessage("Failed to save change");
						}
						if(!useFireArrows) {
							p.sendMessage("Arrows will ignite entities!");
						} else {
							p.sendMessage("Arrows will not ignite entities");
						}
					} else {
						p.sendMessage("Usage: /superbow entity");
					}
				}
				if (split[1].equalsIgnoreCase("flight")) {
					if(split.length == 2) {
						String key = ".fireArrows.flight";
						useFireArrows = plugin.config.getBoolean("SuperBow." + p.getName() + key, plugin.config.getBoolean("SuperBow.Default" + key, false));
						plugin.config.setProperty("SuperBow." + p.getName() + key, !useFireArrows);
						if(plugin.config.save()) {
							p.sendMessage("Saved change");
						} else {
							p.sendMessage("Failed to save change");
						}
						if(!useFireArrows) {
							p.sendMessage("Arrows will be on fire during flight!");
						} else {
							p.sendMessage("Arrows will not be on fire during flight!");
						}
					} else {
						p.sendMessage("Usage: /superbow flight");
					}
				}
				if (split[1].equalsIgnoreCase("damage")) {
					if (split.length == 3) {
						intDamageMultiplier = Integer.parseInt(split[2].trim());
						if (intDamageMultiplier >= 0 && intDamageMultiplier <=6) {
							plugin.config.setProperty("SuperBow." + p.getName() + ".damageMultiplier", intDamageMultiplier);
							if(plugin.config.save()) {
								p.sendMessage("Saved change");
							} else {
								p.sendMessage("Failed to save change");
							}
							p.sendMessage("Your damage multiplier is now: " + Integer.toString(intDamageMultiplier));
						} else {
							p.sendMessage("Usage: /superbow dm [0-6]");
						}
					} else {
						p.sendMessage("Usage: /superbow dm [0-6]");
					}
				}
				if (split[1].equalsIgnoreCase("help")) {
					if (split.length == 2) {
						p.sendMessage("SuperBow Help:");
						p.sendMessage("/superbow help - this message");
						p.sendMessage("/superbow damage [0-6] - sets the damage multiplier");
						p.sendMessage("/superbow blocks - toggles block ignition");
						p.sendMessage("/superbow entity - toggles entity ignition");
						p.sendMessage("/superbow flight - toggles arrow ignition");
					}
				}
			}
		}
    }
}


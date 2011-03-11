package com.gmail.awburchette.plugin.SuperBow;

//import org.bukkit.Location;
//import org.bukkit.entity.Item;
//import org.bukkit.entity.Player;
//import org.bukkit.event.Event.Type;
//import org.bukkit.event.player.PlayerChatEvent;
//import org.bukkit.event.player.PlayerEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerItemEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
//import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.bukkit.util.config.Configuration;

/**
 * Handle events for all Player related events
 * @author bitswitch
 */
public class SuperBowPlayerListener extends PlayerListener {
    private final SuperBow plugin;
    private boolean useFireArrows;
    private int intDamageMultiplier;
	String strTopKey = "SuperBow.";
	String strUserKey = strTopKey + "Users.";
	String strDefaultKey = strTopKey + "Default";
	String strDMKey = ".damageMultiplier";
	String strFABKey = ".fireArrows.block";
	String strFAEKey = ".fireArrows.entity";
	String strFAFKey = ".fireArrows.flight";
	boolean usePermissions;
    List<Arrow> arrows;

    public SuperBowPlayerListener(SuperBow instance) {
        plugin = instance;
        arrows = new ArrayList<Arrow>();
    }
    
    public void onPlayerItem(PlayerItemEvent event) {
    	Player p = event.getPlayer();
		Inventory inv = p.getInventory();
    	ItemStack inHand = p.getItemInHand();
    	if(inHand.getType() == Material.BOW) {
    		if(inv.contains(Material.ARROW)) { 
    			useFireArrows = plugin.config.getBoolean("SuperBow.Users." + p.getName() + ".fireArrows.flight", plugin.config.getBoolean("SuperBow.Default.fireArrows.flight", false));
    			event.setCancelled(true);
    			Arrow a = p.shootArrow();
    			ItemStack oneArrow = new ItemStack(Material.ARROW, 1);
    			inv.removeItem(oneArrow);
    			if(useFireArrows) {
    				a.setFireTicks(300);
    			}
    			useFireArrows = plugin.config.getBoolean("SuperBow.Users." + p.getName() + ".fireArrows.block", plugin.config.getBoolean("SuperBow.Default.fireArrows.flight", false));
    			if(useFireArrows) {
    				SuperBowArrow sba = new SuperBowArrow(p, a, useFireArrows);
    				plugin.arrowController.addArrow(sba);
    			}
    		}
    	}
    }
    
//    public void onPlayerMove(PlayerMoveEvent event) {
//    	String strTopKey = "SuperBow.";
//    	String strUserKey = strTopKey + "Users.";
//    	String strFABKey = ".fireArrows.block";
//    	boolean blnSetBlocksOnFire = plugin.config.getBoolean(strUserKey + event.getPlayer().getName() + strFABKey,false);
//    	if(blnSetBlocksOnFire) {
//    		if(!arrows.isEmpty()) {
//		    	Iterator itr = arrows.iterator();
//		    	while(itr.hasNext()) {
//		    		Arrow a = (Arrow) itr.next();
//		    		boolean blnSetFire = false;
//	    			Block b = a.getLocation().getBlock();
//	    			World world = b.getWorld();
//	    			if(a.getVelocity().equals(new Vector())) {
//		    			for(int x = -1;x < 2; x++) {
//		    				for(int y = -1;y < 2; y++) {
//		    					for(int z = -1;z < 2; z++) {
//		    						if(world.getBlockAt(b.getX() + x, b.getY() + y, b.getZ() + z).getType() == Material.AIR) {
//		    						//if(world.getBlockAt(al.getBlockX())) {
//		    							blnSetFire = true;
//		    							world.getBlockAt(b.getX() + x, b.getY() + y, b.getZ() + z).setType(Material.FIRE);
//		    						}
//		    					}
//		    				}
//		    			}
//	    			}
//	    			if(blnSetFire) {
//	    				arrows.remove(a);
//	    				itr = arrows.iterator();
//	    			}
//		    	}
//    		}
//    	}
//    }
    
    private void info(Player p) {
    	if(plugin.Permissions.has(p,"SuperBow.info")) {
			String strName = "SuperBow.Default";
			if (plugin.config.getNode("SuperBow.Users." + p.getName()) != null) {
				strName = strUserKey + p.getName();
			}
			p.sendMessage("Your SuperBow settings:");
			p.sendMessage("Damage Multiplier: " + Integer.toString((plugin.config.getInt(strName + strDMKey, 1))));
			p.sendMessage("Block: " + plugin.config.getBoolean(strName + strFABKey, false));
			p.sendMessage("Entity: " + plugin.config.getBoolean(strName + strFAEKey, false));
			p.sendMessage("Flight: " + plugin.config.getBoolean(strName + strFAFKey, false));
    	}
    }
    
    private void help(Player p) {
    	if(plugin.Permissions.has(p,"SuperBow.help")) {
			p.sendMessage("SuperBow Help:");
			p.sendMessage("/superbow help - this message");
			p.sendMessage("/superbow info - tells you your settings");
			if(plugin.Permissions.has(p,"SuperBow.damage")) {
				p.sendMessage("/superbow damage [0-6] - sets the damage multiplier");
			}
			if(plugin.Permissions.has(p,"SuperBow.block")) {
				p.sendMessage("/superbow block - toggles block ignition");
			}
			if(plugin.Permissions.has(p,"SuperBow.entity")) {
				p.sendMessage("/superbow entity - toggles entity ignition");
			}
			if(plugin.Permissions.has(p,"SuperBow.flight")) {
				p.sendMessage("/superbow flight - toggles arrow ignition");
			}
    	}
    }
    
    private void damage(Player p, String[] split) {
    	if(plugin.Permissions.has(p,"SuperBow.damage")) {
			if (split.length == 3) {
				intDamageMultiplier = Integer.parseInt(split[2].trim());
				if (intDamageMultiplier >= 0 && intDamageMultiplier <=6) {
					plugin.config.setProperty(strUserKey + p.getName() + strDMKey, intDamageMultiplier);
					if(plugin.config.save()) {
						p.sendMessage("Saved change");
					} else {
						p.sendMessage("Failed to save change");
					}
					p.sendMessage("Your damage multiplier is now: " + Integer.toString(intDamageMultiplier));
				} else {
					p.sendMessage("Usage: /superbow damage [0-6]");
				}
			} else {
				p.sendMessage("Usage: /superbow damage [0-6]");
			}
    	}
    }
  
    private void block(Player p, String[] split) {
    	if(plugin.Permissions.has(p,"SuperBow.block")) {
			if(split.length == 2) {
				String key = strUserKey + p.getName() + strFABKey;
				useFireArrows = plugin.config.getBoolean(key, plugin.config.getBoolean(strDefaultKey + strFABKey, false));
				plugin.config.setProperty(key, !useFireArrows);
				if(plugin.config.save()) {
					p.sendMessage("Saved change");
				} else {
					p.sendMessage("Failed to save change");
				}
				if(!useFireArrows) {
					p.sendMessage("Arrows will ignite blocks!");
				} else {
					p.sendMessage("Arrows will not ignite blocks!");
				}
			} else {
				p.sendMessage("Usage: /superbow block");
			}
    	}
    }
    
    private void entity(Player p, String[] split) {
		if(plugin.Permissions.has(p,"SuperBow.entity")) {
			if(split.length == 2) {
				String key = strUserKey + p.getName() + strFAEKey;
				useFireArrows = plugin.config.getBoolean(key, plugin.config.getBoolean(strDefaultKey + strFAEKey, false));
				plugin.config.setProperty(key, !useFireArrows);
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
    }
    
    private void flight(Player p, String[] split) {
		if(plugin.Permissions.has(p,"SuperBow.flight")) {
			if(split.length == 2) {
				String key = strUserKey + p.getName() + strFAFKey;
				useFireArrows = plugin.config.getBoolean(key, plugin.config.getBoolean(strDefaultKey + strFAFKey, false));
				plugin.config.setProperty(key, !useFireArrows);
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
    }
    
    public void onPlayerCommandPreprocess(PlayerChatEvent event) {
    	String[] split = event.getMessage().split(" ");
		Player p = event.getPlayer();
		if (split[0].equalsIgnoreCase("/superbow")){
			if (split.length >= 2){
				// Begin - info
				if (split[1].equalsIgnoreCase("info")) {
					info(p);
				}
				// End - info
				// Begin - help
				if (split[1].equalsIgnoreCase("help")) {
					help(p);
				}
				// End - help
				// Begin - block
				if (split[1].equalsIgnoreCase("block")) {
					block(p, split);
				}
				// End - block
				// Begin - damage
				if (split[1].equalsIgnoreCase("damage")) {
					damage(p,split);
				}
				// End - damage
				// Begin - entity
				if (split[1].equalsIgnoreCase("entity")) {
					entity(p,split);
				}
				// End - entity
				// Begin - flight
				if (split[1].equalsIgnoreCase("flight")) {
					flight(p,split);
				}
				// End - flight
			}
		} // End - if (split[0].equalsIgnoreCase("/superbow")){
    }
}


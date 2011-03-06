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

    public SuperBowPlayerListener(SuperBow instance) {
        plugin = instance;
    }
    
    public void onPlayerItem(PlayerItemEvent event) 
    {
    	Player p = event.getPlayer();
		Configuration c = plugin.config;
		Inventory inv = p.getInventory();
    	ItemStack inHand = p.getItemInHand();
    	if(inHand.getType() == Material.BOW) {
    		if(inv.contains(Material.ARROW)) { 
    			useFireArrows = c.getBoolean("SuperBow." + p.getName() + ".useFireArrows", c.getBoolean("SuperBow.Default.useFireArrows", false));
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
}


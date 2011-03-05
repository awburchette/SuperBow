package com.gmail.awburchette.plugin.SuperBow;

//import org.bukkit.Location;
//import org.bukkit.entity.Item;
//import org.bukkit.entity.Player;
//import org.bukkit.event.Event.Type;
//import org.bukkit.event.player.PlayerChatEvent;
//import org.bukkit.event.player.PlayerEvent;
//import org.bukkit.event.player.PlayerItemEvent;
//import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerListener;
//import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Handle events for all Player related events
 * @author bitswitch
 */
public class SuperBowPlayerListener extends PlayerListener {
    private final SuperBow plugin;

    public SuperBowPlayerListener(SuperBow instance) {
        plugin = instance;
    }
}


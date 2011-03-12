package com.gmail.awburchette.plugin.SuperBow;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockInteractEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;

/**
 * SuperBow block listener
 * @author bitswitch
 */
public class SuperBowBlockListener extends BlockListener {
    private final SuperBow plugin;

    public SuperBowBlockListener(final SuperBow plugin) {
        this.plugin = plugin;
    }
    @Override
    public void onBlockDamage(BlockDamageEvent event) {
    	
    }
    //public void onBlockPhysics(BlockPhysicsEvent event) {
    //	System.out.println(event.getEventName());
    //}
    
    
    //put all Block related code here
}

package com.gmail.awburchette.plugin.SuperBow;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.Material;
import org.bukkit.event.block.BlockCanBuildEvent;
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

    //put all Block related code here
}

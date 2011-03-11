package com.gmail.awburchette.plugin.SuperBow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SuperBowArrowController {
	private final SuperBow plugin;
	public List<SuperBowArrow> arrows;
    public SuperBowArrowController(SuperBow instance) {
        plugin = instance;
        arrows = new ArrayList<SuperBowArrow>();
    }
    public void addArrow(SuperBowArrow sba) {
    	arrows.add(sba);
    }
    public void checkArrows() {
		if(!arrows.isEmpty()) {
	    	Iterator itr = arrows.iterator();
	    	while(itr.hasNext()) {
	    		SuperBowArrow a = (SuperBowArrow) itr.next();
	    		if(!a.waitTillNextCall) {
		    		Location curLoc = a.arrow.getLocation();
		    		if(!curLoc.equals(a.lastPostion)) {
		    			a.lastPostion = curLoc;
		    		}
		    		if(curLoc.equals(a.lastPostion)) {
			    		boolean blnSetFire = false;
		    			Block b = curLoc.getBlock();
		    			World world = b.getWorld();
		    			for(int x = -1;x < 2; x++) {
		    				for(int y = -1;y < 2; y++) {
		    					for(int z = -1;z < 2; z++) {
		    						if(world.getBlockAt(b.getX() + x, b.getY() + y, b.getZ() + z).getType() == Material.AIR) {
		    							blnSetFire = true;
		    							world.getBlockAt(b.getX() + x, b.getY() + y, b.getZ() + z).setType(Material.FIRE);
		    						}
		    					}
		    				}
		    			}
		    			if(blnSetFire) {
		    				arrows.remove(a);
		    				itr = arrows.iterator();
		    			}
		    		}
	    		} else {
	    			a.waitTillNextCall = false;
	    		}
	    	}
		}
    }
}

package com.gmail.awburchette.plugin.SuperBow;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;

public class SuperBowEntityListener extends EntityListener {
    private final SuperBow plugin;
    private static boolean useFireArrows;
    private static int intDamageMultiplier;

    public SuperBowEntityListener(SuperBow instance) {
        plugin = instance;
    }
	
    public static void setUseFireArrows(Boolean c){
		 useFireArrows = c;
	 }
    
    public static void setDamageMultiplier(int i){
    	intDamageMultiplier = i;
    }
    //Insert Player related code here
    @Override
    public void onEntityDamage(EntityDamageEvent event)
    {
        super.onEntityDamage(event);
        if (event instanceof EntityDamageByProjectileEvent)
        {
        	EntityDamageByProjectileEvent edee = (EntityDamageByProjectileEvent) event;
        	if(edee.getProjectile() instanceof Arrow){
        		if(edee.getDamager() instanceof Player) {
        			Player p = (Player) edee.getDamager();
        			if(p.isOp()) {
        				event.setDamage(event.getDamage()*intDamageMultiplier);
        				if(useFireArrows) {
        					edee.getEntity().setFireTicks(10);
        					edee.getProjectile().setFireTicks(10);
        				}
        			}
        		}
        	}
        }
    }
}

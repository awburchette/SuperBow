package com.gmail.awburchette.plugin.SuperBow;

import org.bukkit.entity.Arrow;
//import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.util.config.Configuration;

public class SuperBowEntityListener extends EntityListener {
    private final SuperBow plugin;
    private static boolean useFireArrows;
    private static int intDamageMultiplier;

    public SuperBowEntityListener(SuperBow instance) {
        plugin = instance;
    }

    //Insert Player related code here
    @Override
    public void onEntityDamage(EntityDamageEvent event)
    {
        super.onEntityDamage(event);
        if (event instanceof EntityDamageByProjectileEvent)
        {
        	EntityDamageByProjectileEvent edee = (EntityDamageByProjectileEvent) event;
        	if(edee.getProjectile() instanceof Arrow) {
        		if(edee.getDamager() instanceof Player) {
        			Player p = (Player) edee.getDamager();
        			intDamageMultiplier = plugin.config.getInt("SuperBow.Users." + p.getName() + ".damageMultiplier", plugin.config.getInt("SuperBow.Default.damageMultiplier", 1));
        			useFireArrows = plugin.config.getBoolean("SuperBow.Users." + p.getName() + ".fireArrows.entity", plugin.config.getBoolean("SuperBow.Default.fireArrows.entity", false));
        			event.setDamage(event.getDamage()*intDamageMultiplier);
        			if(useFireArrows) {
        				LivingEntity e = (LivingEntity) edee.getEntity();
        				int ticks = (e.getHealth()*2)*10;
        				e.setFireTicks(ticks);
        			}
        		}
        	}
        }
    }
}

package com.gmail.awburchette.plugin.SuperBow;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

public class SuperBowArrow {
	public Arrow arrow;
	public Player owner;
	public Location lastPostion;
	public boolean setBlockOnFire;
	public boolean waitTillNextCall;
	
	public SuperBowArrow(Player p, Arrow a, boolean block) {
		owner = p;
		arrow = a;
		setBlockOnFire = block;
		lastPostion = a.getLocation();
		waitTillNextCall = true;
	}
}

package me.quickscythe.hyversechat.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.quickscythe.hyversechat.utils.Utils;
import me.quickscythe.hyversechat.Main;
import me.quickscythe.hyversecore.utils.CoreUtils;

public class PlayerListener implements Listener {
	
	public PlayerListener(Main plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e){
		if(Utils.inStaffChat(e.getPlayer().getName())){
			Utils.sendStaffChat(e.getPlayer(), e.getMessage());
			e.setCancelled(true);
			return;
		}
		if(Utils.isPlayerChatGlobal()){
			CoreUtils.sendPluginMessage(e.getPlayer(), "hyverse:hyverse", "HyversePlayerChat", Utils.replaceholders(e.getPlayer(), Utils.getPlayerFormat(), e.getMessage()));
			e.setCancelled(true);
		} else {
			for(Player s : Bukkit.getOnlinePlayers()){
				if(s.hasPermission("chat.staffchat")) s.sendMessage(Utils.replaceholders(e.getPlayer(), Utils.getPlayerFormat(), e.getMessage()));
				Bukkit.getConsoleSender().sendMessage(Utils.replaceholders(e.getPlayer(), Utils.getPlayerFormat(), e.getMessage()));
			}
			e.setCancelled(true);
		}
	}

}

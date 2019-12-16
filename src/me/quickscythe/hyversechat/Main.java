package me.quickscythe.hyversechat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.quickscythe.hyversechat.commands.StaffChatCommand;
import me.quickscythe.hyversechat.listeners.MessageListener;
import me.quickscythe.hyversechat.listeners.PlayerListener;
import me.quickscythe.hyversechat.utils.Utils;


public class Main extends JavaPlugin {
	
	private static Main plugin;
	
	
	
	int attempt = 1;
	int maxattempts = 3;

	public void onEnable() {
		if(!getServer().getPluginManager().isPluginEnabled("HyverseCore")){
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&l" + getDescription().getName() + " &f>&7 Can't find HyverseCore. Trying again.. Attempt " + attempt + " out of " + maxattempts));
			attempt+=1;
			if(attempt==maxattempts+1){
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&l" + getDescription().getName() + " &f>&7 Couldn't find HyverseCore after " + maxattempts + " tries. Plugin will not load."));
				getServer().getPluginManager().disablePlugin(this);
				return;
			}
			Bukkit.getScheduler().runTaskLater(this, new Runnable(){

				@Override
				public void run() {
					onEnable();
				}
				
			}, 20*3);
			return;
		} else {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&l" + getDescription().getName() + " &f>&7 Found HyverseCore! Loading plugin.."));
		}
		plugin = this;
		new PlayerListener(this);
		new StaffChatCommand(this, "staffchat");
		
		getServer().getMessenger().registerIncomingPluginChannel(me.quickscythe.hyversecore.Main.getPlugin(), "hyverse:hyverse", new MessageListener());
		getServer().getMessenger().registerOutgoingPluginChannel(me.quickscythe.hyversecore.Main.getPlugin(), "hyverse:hyverse");
			
		
		Utils.start();
		
	}
	
	public void onDisable() {
		getServer().getMessenger().unregisterIncomingPluginChannel(me.quickscythe.hyversecore.Main.getPlugin(), "hyverse:hyverse");
		getServer().getMessenger().unregisterOutgoingPluginChannel(me.quickscythe.hyversecore.Main.getPlugin(), "hyverse:hyverse");
		
	}
	
	public static Main getPlugin(){
		return plugin;
	}
}
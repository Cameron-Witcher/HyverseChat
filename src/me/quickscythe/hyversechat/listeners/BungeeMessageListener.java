package me.quickscythe.hyversechat.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class BungeeMessageListener implements PluginMessageListener {

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();
		if (subchannel.equals("HyversePlayerChat")) {
			Bukkit.broadcastMessage(in.readUTF());
		}
		if (subchannel.equals("HyverseStaffChat")) {
			String p = in.readUTF();
			for(Player s : Bukkit.getOnlinePlayers()){
				if(s.hasPermission("chat.staffchat")) s.sendMessage(p);
				
			}
			Bukkit.getConsoleSender().sendMessage(p);
		}
	}
}

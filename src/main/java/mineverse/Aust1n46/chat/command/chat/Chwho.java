package mineverse.Aust1n46.chat.command.chat;

import static mineverse.Aust1n46.chat.MineverseChat.LINE_LENGTH;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;


import mineverse.Aust1n46.chat.MineverseChat;
import mineverse.Aust1n46.chat.api.MineverseChatAPI;
import mineverse.Aust1n46.chat.api.MineverseChatPlayer;
import mineverse.Aust1n46.chat.channel.ChatChannel;
import mineverse.Aust1n46.chat.command.VentureCommand;
import mineverse.Aust1n46.chat.localization.LocalizedMessage;

public class Chwho implements VentureCommand {
	private MineverseChat plugin = MineverseChat.getInstance();

	@Override
	public void execute(CommandSender sender, String command, String[] args) {
		String playerlist = "";
		if(sender.hasPermission("venturechat.chwho")) {
			if(args.length > 0) {
				ChatChannel channel = ChatChannel.getChannel(args[0]);
				if(channel != null) {
					if(channel.hasPermission()) {
						if(!sender.hasPermission(channel.getPermission())) {
							MineverseChatPlayer mcp = MineverseChatAPI.getOnlineMineverseChatPlayer(((Player) sender));
							mcp.removeListening(channel.getName());
							mcp.getPlayer().sendMessage(LocalizedMessage.CHANNEL_NO_PERMISSION_VIEW.toString());
							return;
						}
					}
					
					if(channel.getBungee() && sender instanceof Player) {
						MineverseChatPlayer mcp = MineverseChatAPI.getOnlineMineverseChatPlayer((Player) sender);
						ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
						DataOutputStream out = new DataOutputStream(byteOutStream);
						try {
							out.writeUTF("Chwho");
							out.writeUTF("Get");
							out.writeUTF(mcp.getUUID().toString());
							out.writeUTF(mcp.getName());
							out.writeUTF(channel.getName());
							mcp.getPlayer().sendPluginMessage(plugin, MineverseChat.PLUGIN_MESSAGING_CHANNEL, byteOutStream.toByteArray());
							out.close();
						}
						catch(Exception e) {
							e.printStackTrace();
						}
						return;
					}
					
					PluginManager pluginManager = plugin.getServer().getPluginManager();
					long linecount = LINE_LENGTH;
					for(MineverseChatPlayer p : MineverseChatAPI.getOnlineMineverseChatPlayers()) {
						if(p.getListening().contains(channel.getName())) {
							if(sender instanceof Player) {
								if(!((Player) sender).canSee(p.getPlayer())) {
									continue;
								}
							}
							if(channel.hasDistance() && sender instanceof Player) {
								if(!this.isPlayerWithinDistance((Player) sender, p.getPlayer(), channel.getDistance())) {
									continue;
								}
							}
							if(playerlist.length() + p.getName().length() > linecount) {
								playerlist += "\n";
								linecount = linecount + LINE_LENGTH;
							}
							if(!p.isMuted(channel.getName())) {
								playerlist += ChatColor.WHITE + p.getName();
							}
							else {
								playerlist += ChatColor.RED + p.getName();
							}
							playerlist += ChatColor.WHITE + ", ";
						}
					}
					if(playerlist.length() > 2) {
						playerlist = playerlist.substring(0, playerlist.length() - 2);
					}
					sender.sendMessage(LocalizedMessage.CHANNEL_PLAYER_LIST_HEADER.toString()
							.replace("{channel_color}", (channel.getColor()).toString())
							.replace("{channel_name}", channel.getName()));
					sender.sendMessage(playerlist);
					return;
				}
				else {
					sender.sendMessage(LocalizedMessage.INVALID_CHANNEL.toString()
							.replace("{args}", args[0]));
					return;
				}
			}
			else {
				sender.sendMessage(LocalizedMessage.COMMAND_INVALID_ARGUMENTS.toString()
						.replace("{command}", "/chwho")
						.replace("{args}", "[channel]"));
				return;
			}
		}
		else {
			sender.sendMessage(LocalizedMessage.COMMAND_NO_PERMISSION.toString());
			return;
		}
	}

	private boolean isPlayerWithinDistance(Player p1, Player p2, double Distance) {
		Double chDistance = Distance;
		Location locreceip;
		Location locsender = p1.getLocation();
		Location diff;
		if(chDistance > (double) 0) {
			locreceip = p2.getLocation();
			if(locreceip.getWorld() == p1.getWorld()) {
				diff = locreceip.subtract(locsender);
				if(Math.abs(diff.getX()) > chDistance || Math.abs(diff.getZ()) > chDistance || Math.abs(diff.getY()) > chDistance) {
					return false;
				}
			}
			else {
				return false;
			}
		}
		return true;
	}
}

package mineverse.Aust1n46.chat.command.mute;

import mineverse.Aust1n46.chat.utilities.Format;
import org.bukkit.command.CommandSender;

import mineverse.Aust1n46.chat.MineverseChat;
import mineverse.Aust1n46.chat.api.MineverseChatAPI;
import mineverse.Aust1n46.chat.api.MineverseChatPlayer;
import mineverse.Aust1n46.chat.channel.ChatChannel;
import mineverse.Aust1n46.chat.command.VentureCommand;
import mineverse.Aust1n46.chat.localization.LocalizedMessage;

public class Muteall implements VentureCommand {

	@Override
	public void execute(CommandSender sender, String command, String[] args) {
		if(sender.hasPermission("venturechat.mute")) {
			if(args.length < 1) {
				sender.sendMessage(LocalizedMessage.COMMAND_INVALID_ARGUMENTS.toString()
						.replace("{command}", "/muteall")
						.replace("{args}", "[player] {reason}"));
				return;
			}
			MineverseChatPlayer player = MineverseChatAPI.getMineverseChatPlayer(args[0]);
			if(player == null || (!player.isOnline() && !sender.hasPermission("venturechat.mute.offline"))) {
				sender.sendMessage(LocalizedMessage.PLAYER_OFFLINE.toString()
						.replace("{args}", args[0]));
				return;
			}
			String reason = "";
			if(args.length > 1) {
				StringBuilder reasonBuilder = new StringBuilder();
				for(int a = 1; a < args.length; a ++) {
					reasonBuilder.append(args[a]).append(" ");
				}
				reason = Format.FormatStringAll(reasonBuilder.toString().trim());
			}
			if(reason.isEmpty()) {
				boolean bungee = false;
				for(ChatChannel channel : ChatChannel.getChatChannels()) {
					if(channel.isMutable()) {
						player.addMute(channel.getName());				
						if(channel.getBungee()) {
							bungee = true;
						}
					}
				}
				if(bungee) {
					MineverseChat.synchronize(player, true);
				}
				sender.sendMessage(LocalizedMessage.MUTE_PLAYER_ALL_SENDER.toString()
						.replace("{player}", player.getName()));
				if(player.isOnline()) {
					player.getPlayer().sendMessage(LocalizedMessage.MUTE_PLAYER_ALL_PLAYER.toString());
				}
				else 
					player.setModified(true);
				return;
			}
			else {
				boolean bungee = false;
				for(ChatChannel channel : ChatChannel.getChatChannels()) {
					if(channel.isMutable()) {
						player.addMute(channel.getName(), reason);				
						if(channel.getBungee()) {
							bungee = true;
						}
					}
				}
				if(bungee) {
					MineverseChat.synchronize(player, true);
				}
				sender.sendMessage(LocalizedMessage.MUTE_PLAYER_ALL_SENDER_REASON.toString()
						.replace("{player}", player.getName())
						.replace("{reason}", reason));
				if(player.isOnline()) {
					player.getPlayer().sendMessage(LocalizedMessage.MUTE_PLAYER_ALL_PLAYER_REASON.toString()
							.replace("{reason}", reason));
				}
				else 
					player.setModified(true);
				return;
			}
		}
		else {
			sender.sendMessage(LocalizedMessage.COMMAND_NO_PERMISSION.toString());
			return;
		}
	}
}

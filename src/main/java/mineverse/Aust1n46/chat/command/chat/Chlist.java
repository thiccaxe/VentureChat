package mineverse.Aust1n46.chat.command.chat;

import org.bukkit.command.CommandSender;

import mineverse.Aust1n46.chat.channel.ChatChannel;
import mineverse.Aust1n46.chat.command.VentureCommand;
import mineverse.Aust1n46.chat.localization.LocalizedMessage;

public class Chlist implements VentureCommand {

	@Override
	public void execute(CommandSender sender, String command, String[] args) {
		sender.sendMessage(LocalizedMessage.CHANNEL_LIST_HEADER.toString());
		for(ChatChannel chname : ChatChannel.getChatChannels()) {
			if(chname.hasPermission()) {
				if(sender.hasPermission(chname.getPermission())) {
					sender.sendMessage(LocalizedMessage.CHANNEL_LIST_WITH_PERMISSIONS.toString()
							.replace("{channel_color}", (chname.getColor()).toString())
							.replace("{channel_name}", chname.getName())
							.replace("{channel_alias}", chname.getAlias()));
				}
			}
			else {
				sender.sendMessage(LocalizedMessage.CHANNEL_LIST.toString()
						.replace("{channel_color}", chname.getColor().toString())
						.replace("{channel_name}", chname.getName())
						.replace("{channel_alias}", chname.getAlias()));
			}
		}
		return;
	}
}

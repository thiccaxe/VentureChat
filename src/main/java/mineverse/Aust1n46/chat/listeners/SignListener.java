package mineverse.Aust1n46.chat.listeners;

import mineverse.Aust1n46.chat.MineverseChat;
import mineverse.Aust1n46.chat.api.MineverseChatAPI;
import mineverse.Aust1n46.chat.api.MineverseChatPlayer;
import mineverse.Aust1n46.chat.utilities.Format;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

//This class listens for text being added to signs, and it formats them to allow colors and formatting.
public class SignListener implements Listener {
	MineverseChat plugin = MineverseChat.getInstance();

	@EventHandler(priority = EventPriority.HIGH)
	public void onSignChange(SignChangeEvent event) {
		MineverseChatPlayer mcp = MineverseChatAPI.getOnlineMineverseChatPlayer(event.getPlayer());
		for(int a = 0; a < event.getLines().length; a++) {
			String line = event.getLine(a);
			if(mcp.getPlayer().hasPermission("venturechat.color.legacy")) {
				line = Format.FormatStringLegacyColor(line);
			}
			if(mcp.getPlayer().hasPermission("venturechat.color")) {
				line = Format.FormatStringColor(line);
			}
			if(mcp.getPlayer().hasPermission("venturechat.format")) {
				line = Format.FormatString(line);
			}
			event.setLine(a, line);
		}
	}
}
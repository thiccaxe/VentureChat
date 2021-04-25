package mineverse.Aust1n46.chat.mention;

import mineverse.Aust1n46.chat.api.MineverseChatAPI;
import mineverse.Aust1n46.chat.api.MineverseChatPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import me.ruben_artz.api.*;
public class MentionListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onCooldown(PlayerMentionCooldownEvent event) {
        Player cooldownPlayer = event.getPlayerWithCooldown();
        Player mentionedPlayer = event.getPlayerMentioned();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onMention(PlayerMentionEvent event) {
        Player mentionedPlayer = event.getPlayerMentioned();
        Player mentioningPlayer = event.getPlayerWhoMentions();

        MineverseChatPlayer mentionedChatPlayer = MineverseChatAPI.getMineverseChatPlayer(mentionedPlayer);
        MineverseChatPlayer mentioningChatPlayer = MineverseChatAPI.getMineverseChatPlayer(mentioningPlayer);

        if (mentionedChatPlayer.getCurrentChannel() != mentioningChatPlayer.getCurrentChannel() ||
                mentioningChatPlayer.isMuted(mentioningChatPlayer.getCurrentChannel().getName()) ||
                !mentionedChatPlayer.isListening(mentioningChatPlayer.getCurrentChannel().getName())
        ) {
            event.setCancelled(true);
        }



    }

}

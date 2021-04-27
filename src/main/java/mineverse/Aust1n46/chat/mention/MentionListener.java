package mineverse.Aust1n46.chat.mention;

import com.loohp.interactivechat.api.events.PlayerMentionPlayerEvent;
import com.loohp.interactivechat.modules.MentionDisplay;
import mineverse.Aust1n46.chat.api.MineverseChatAPI;
import mineverse.Aust1n46.chat.api.MineverseChatPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MentionListener implements Listener {


    @EventHandler()
    public void onMention(PlayerMentionPlayerEvent event) {
        Player mentionedPlayer = event.getReciver();

        MineverseChatPlayer mentionedChatPlayer = MineverseChatAPI.getMineverseChatPlayer(mentionedPlayer);
        MineverseChatPlayer mentioningChatPlayer = MineverseChatAPI.getMineverseChatPlayer(event.getSender());


        if (mentioningChatPlayer != null && mentionedChatPlayer != null) {
            if (mentionedChatPlayer.getCurrentChannel() != mentionedChatPlayer.getCurrentChannel()) {
                event.setCancelled(true);
            }
        }









    }

}


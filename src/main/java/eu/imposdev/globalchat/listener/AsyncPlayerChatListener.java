package eu.imposdev.globalchat.listener;

import eu.imposdev.globalchat.util.Connection;
import eu.imposdev.globalchat.util.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (message.startsWith("!gc")) {
            event.setCancelled(true);
            message = message.replaceAll("!gc", "").replaceAll("!gc ", "");
            Utils.getClient().sendData(Connection.REQUEST_MESSAGE.getConnectionString() + player.getUniqueId() + ";" + player.getName() + ";" + message);
        }

    }

}

package eu.imposdev.globalchat;

import eu.imposdev.globalchat.client.Connect;
import eu.imposdev.globalchat.listener.AsyncPlayerChatListener;
import eu.imposdev.globalchat.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class GlobalChat extends JavaPlugin {

    private static GlobalChat instance;
    private Connect connect;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        getConfig().options().header("GlobalChat Client by Espen");
        getConfig().addDefault("Settings.host", "imposdev.eu");
        getConfig().addDefault("Settings.port", 8888);
        saveConfig();

        loadClient();
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);

    }

    @Override
    public void onDisable() {
        Utils.getClient().close();
    }

    private void loadClient() {
        String host = getConfig().getString("Settings.host");
        int port = getConfig().getInt("Settings.port");
        try {
            connect = new Connect(host, port, Utils.generateString(15), Utils.generateString(5));
        } catch (IOException exception) {
            exception.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public static GlobalChat getInstance() {
        return instance;
    }

    public Connect getConnect() {
        return connect;
    }
}

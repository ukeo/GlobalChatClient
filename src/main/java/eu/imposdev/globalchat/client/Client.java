package eu.imposdev.globalchat.client;

import eu.imposdev.globalchat.GlobalChat;
import eu.imposdev.globalchat.util.Connection;
import eu.imposdev.globalchat.util.Utils;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

public class Client {

    private Socket client;
    private String uid;
    private String key;
    private boolean isAuth = false;

    public Client(Socket client, String uid, String key) {
        this.client = client;
        this.uid = uid;
        this.key = key;

        //READ DATA
        Thread readData = new Thread(new ClientReader(this));
        readData.start();

        //AUTH
        auth();
    }

    public void auth() {
        sendData(Connection.CLIENT_AUTH.getConnectionString() + uid + ";" + key);
    }

    public void readData(String data) {
        //READ DATA HERE
        if (data.equalsIgnoreCase(Connection.OK.getConnectionString())) {
            GlobalChat.getInstance().getLogger().info("Client authenticated successfully. 200 OK");
            setAuth(true);
            return;
        }
        if (data.equalsIgnoreCase(Connection.BAD.getConnectionString())) {
            GlobalChat.getInstance().getLogger().warning("Authentication failed. 100 BAD");
            setAuth(false);
            return;
        }
        if (data.equalsIgnoreCase(Connection.CLIENT_ALREADY_CONNECTED.getConnectionString())) {
            GlobalChat.getInstance().getLogger().warning("Client is already connected");
            return;
        }
        if (data.equalsIgnoreCase(Connection.SERVER_CLOSED.getConnectionString())) {
            GlobalChat.getInstance().getLogger().warning("Remote server closed. Resetting uid and key");
            Utils.setKey(null);
            Utils.setUid(null);
            setAuth(false);
            return;
        }
        if (data.startsWith(Connection.SEND_MESSAGE.getConnectionString())) {
            String[] splitter = data.replace(Connection.SEND_MESSAGE.getConnectionString(), "").split(";");
            String name = splitter[0];
            String message = splitter[1];

            Bukkit.broadcastMessage("§8[§e§lGlobalChat§8] §a" + name + "§8: §f" + message);

        }
    }

    public void close() {
        sendData(Connection.CLIENT_CLOSED.getConnectionString());
        try {
            client.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void sendData(String data) {
        if (!client.isClosed()) {
            try {
                OutputStream outputStream = client.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outputStream);

                printWriter.write(data + "\n");
                printWriter.flush();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        } else {
            GlobalChat.getInstance().getLogger().warning("Client connection not established!");
        }
    }

    public Socket getClient() {
        return client;
    }

    public String getUid() {
        return uid;
    }

    public String getKey() {
        return key;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

}

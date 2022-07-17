package eu.imposdev.globalchat.client;

import eu.imposdev.globalchat.util.Utils;

import java.io.IOException;
import java.net.Socket;

public class Connect {

    public Connect(String host, int port, String uid, String key) throws IOException {
        Socket socket = new Socket(host, port);
        Client client = new Client(socket, uid, key);
        Utils.setClient(client);
    }

}

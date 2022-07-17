package eu.imposdev.globalchat.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class ClientReader extends Thread {

    Client client;

    public ClientReader(Client client) {
        this.client = client;
    }

    public void run() {
        while (!client.getClient().isClosed()) {
            try {
                InputStream inputStream = client.getClient().getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String data = null;

                while (!client.getClient().isClosed() && (data = bufferedReader.readLine()) != null) {
                    client.readData(Objects.requireNonNull(data));
                }

            } catch (Exception exc) {
                System.out.println("[DataReader] Error while reading data:");
                exc.printStackTrace();
                try {
                    client.getClient().close();
                    this.stop();
                } catch (Exception exc2) {
                    System.out.println("[DataReader] Failed to stop the Thread");
                }
            }
        }
    }

}

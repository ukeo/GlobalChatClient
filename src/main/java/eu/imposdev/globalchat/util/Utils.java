package eu.imposdev.globalchat.util;

import eu.imposdev.globalchat.client.Client;

import java.util.Random;

public class Utils {

    private static Client client;
    private static String uid, key;

    public static String getKey() {
        return key;
    }

    public static String getUid() {
        return uid;
    }

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        Utils.client = client;
    }

    public static void setKey(String key) {
        Utils.key = key;
    }

    public static void setUid(String uid) {
        Utils.uid = uid;
    }

    public static String generateString(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}

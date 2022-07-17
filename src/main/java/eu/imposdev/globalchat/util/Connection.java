package eu.imposdev.globalchat.util;

public enum Connection {

    OK(200, "OK"),
    BAD(100, "BAD"),
    CLIENT_CLOSED(101, "CLIENT CLOSED"),
    SERVER_CLOSED(102, "SERVER CLOSED"),
    HEARTBEAT(999, "HTBT"),
    CLIENT_ALREADY_CONNECTED(300, "CLIENT ALREADY CONNECTED"),
    CLIENT_AUTH(400, "AUTH"),
    REQUEST_MESSAGE(500, "REQ MSG"),
    SEND_MESSAGE(501, "SND GMSG");

    private final int code;
    private final String connectionString;

    private Connection(int code, String connectionString) {
        this.code = code;
        this.connectionString = connectionString;
    }

    public int getCode() {
        return code;
    }

    public String getConnectionString() {
        return connectionString;
    }

}

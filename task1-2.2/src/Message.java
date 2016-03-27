import java.sql.Timestamp;
import java.util.UUID;


public class Message {

    private String id;
    private String message;
    private String author;
    private long timestamp;

    public Message(String msg, String nickname) {
        Timestamp data = new Timestamp(System.currentTimeMillis());
        UUID uuid = UUID.randomUUID();
        id = uuid.toString();
        message = msg;
        author = nickname;
        timestamp = data.getTime();
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public long getTimestamp() {
        return timestamp;
    }

}

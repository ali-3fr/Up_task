import java.sql.Timestamp;
import java.util.UUID;


public class Message {

    private String id;
    private String message;
    private String author;
    private long timestamp;

    public Message(String b, String c){
        Timestamp d = new Timestamp(System.currentTimeMillis());
        UUID idOne = UUID.randomUUID();
        id = idOne.toString();
        message = b;
        author = c;
        timestamp = d.getTime();
    }

    public String outMessage(){
        return message;
    }

    public String outId(){
        return id;
    }

    public String outAuthor(){
        return author;
    }

    public long outTimestamp(){
        return timestamp;
    }


}

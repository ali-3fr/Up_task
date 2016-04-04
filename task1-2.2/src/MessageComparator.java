import java.util.Comparator;


public class Comp implements Comparator<Message> {
    @Override
    public int compare(Message a, Message b) {

        return Long.valueOf(a.getTimestamp()).compareTo(Long.valueOf(b.getTimestamp()));

    }
}

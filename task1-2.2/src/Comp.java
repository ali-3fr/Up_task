import java.util.Comparator;


public class Comp implements Comparator<Message> {
    @Override
    public int compare(Message a, Message b) {

        return Long.valueOf(a.outTimestamp()).compareTo(Long.valueOf(b.outTimestamp()));

    }
}

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.regex.*;


public class MessageHandling {

    private List<Message> messages;

    public MessageHandling() {
        messages = new ArrayList<>();
    }

    public void getFileData(String path) throws IOException {

        FileReader fr = new FileReader(path);
        Scanner sc = new Scanner(fr);
        Gson gson = new Gson();
        Type collectionType = new TypeToken<ArrayList<Message>>() {
        }.getType();
        String tempString;
        if (sc.hasNext()) {
            tempString = sc.nextLine();
            messages = gson.fromJson(tempString, collectionType);
        } else System.out.print("File is empty!\n");
        fr.close();
        sc.close();

    }

    public void saveToFile(String path) throws IOException {

        FileWriter fw = new FileWriter(path);
        Gson gson = new Gson();
        String json = gson.toJson(messages);
        fw.write(json);
        fw.close();

    }


    public void addMsg(Scanner sc) {

        String nick;
        String message;

        System.out.print("Enter your nickname:\n");
        message = sc.nextLine();
        System.out.print("Enter your message:\n");
        nick = sc.nextLine();

        messages.add(new Message(nick, message));

    }

    private void sortMessagesByDate() {

        Collections.sort(messages, new Comp());

    }

    public void deleteMessage(Scanner sc) {

        int delNumber = 0;
        String deleteId;

        deleteId = sc.nextLine();

        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getId().equals(deleteId)) {
                delNumber = i;
                break;
            }
        }
        messages.remove(delNumber);
    }

    private void printMessage(Message m) {

        Date date = new Date(m.getTimestamp());
        System.out.print(date.toString() + " : " + m.getAuthor() + ": " + m.getMessage() + "\n");

    }

    public void printSortedMessages() {

        sortMessagesByDate();

        for (Message temp : messages) {
            printMessage(temp);
        }
    }

    public void findByAuthor(Scanner sc) {

        String auth;
        System.out.print("Enter author name:\n");

        auth = sc.nextLine();

        for (Message temp : messages) {
            if (temp.getAuthor().equals(auth)) {
                printMessage(temp);
            }
        }
    }

    public void findByKeyWord(Scanner sc) {

        String keyWord;

        System.out.print("Enter word:\n");
        keyWord = sc.nextLine();

        for (Message temp : messages) {
            if (temp.getMessage().contains(keyWord)) {
                printMessage(temp);
            }
        }
    }

    public void findByRegular(Scanner sc) {

        System.out.print("Enter regular expression:\n");
        Pattern pattern = Pattern.compile(sc.nextLine());

        for (Message temp : messages) {
            Matcher m = pattern.matcher(temp.getMessage());
            if (m.matches()) printMessage(temp);
        }
    }

    public void findByDate(Scanner sc) throws ParseException {

        Date defaultDate;
        Date dateBefore;
        Date dateAfter;
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm");

        System.out.print("Enter date in \"dd.MM.yyyy 'at' HH:mm\" format:\n");

        dateBefore = format.parse(sc.nextLine());
        dateAfter = format.parse(sc.nextLine());

        for (Message messageTemp : messages) {
            defaultDate = new Date(messageTemp.getTimestamp());
            if (defaultDate.before(dateBefore) && defaultDate.after(dateAfter)) {
                printMessage(messageTemp);
            }
        }
    }
}

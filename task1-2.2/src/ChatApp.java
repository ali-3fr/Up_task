import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;


public class ChatApp {

    public static void main(String[] args) throws IOException, ParseException {

        MessageHandling mh = new MessageHandling();
        boolean exitCheck = false;
        Scanner sc = new Scanner(System.in);
        System.out.print("Welcome to awesome chat app\n");

        while (!exitCheck) {
            mh.getFileData("data.json");
            System.out.print("Press:\n" +
                    "1 - if you want to add message\n" +
                    "2 - if you want to see message history\n" +
                    "3 - if you want to delete message\n" +
                    "4 - if you want to find message\n" +
                    "5 - if you want to quit app\n");
            switch (sc.nextInt()) {
                case 1:
                    mh.addMsg(sc);
                    break;
                case 2:
                    mh.printSortedMessages();
                    break;
                case 3:
                    mh.deleteMessage(sc);
                    break;
                case 4:
                    System.out.print("Press\n" +
                            "1 - if you want to find message by author\n" +
                            "2 - if you want to find message by word\n" +
                            "3 - if you want to find message by date\n" +
                            "4 - if you want to find message by regular expression\n");
                    switch (sc.nextInt()) {
                        case 1:
                            mh.findByAuthor(sc);
                            break;
                        case 2:
                            mh.findByKeyWord(sc);
                            break;
                        case 3:
                            mh.findByDate(sc);
                            break;
                        case 4:
                            mh.findByRegular(sc);
                            break;
                    }
                    break;
                case 5:
                    exitCheck = true;
                    break;
            }
            mh.saveToFile("data.json");
        }
        sc.close();
    }
}

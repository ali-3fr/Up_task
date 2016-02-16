import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.regex.*;


public class MsgProc {

    private ArrayList<Message> msgs;

    public MsgProc() {
        msgs = new ArrayList<>();
    }

    public void getFileData(String path) throws IOException {

        FileReader fr = new FileReader(path);
        Scanner sc = new Scanner(fr);
        Gson gson = new Gson();
        Type collectionType = new TypeToken<ArrayList<Message>>() {
        }.getType();
        String str = sc.nextLine();
        msgs = gson.fromJson(str, collectionType);
        fr.close();

    }

    public void saveToFile(String path) throws IOException {

        FileWriter fw = new FileWriter(path);
        Gson gson = new Gson();
        String json = gson.toJson(msgs);
        fw.write(json);
        fw.close();

    }



    public void addMsg() {

        Scanner sc = new Scanner(System.in);

        String b;
        String c;


        System.out.print("Enter your nickname:\n");
        c = sc.nextLine();
        System.out.print("Enter your message:\n");
        b = sc.nextLine();

        msgs.add(new Message(b, c));

    }

    private void sortMsgsByDate() {

        Collections.sort(msgs, new Comp());
    }

    public void delMsg() {

        int delNumber = 0;
        Scanner sc = new Scanner(System.in);
        String delId;

        delId = sc.nextLine();

        for (int i = 0; i < msgs.size(); i++) {
            if (msgs.get(i).outId().equals(delId)) {
                delNumber = i;
                break;
            }
        }

        msgs.remove(delNumber);
    }

    private void printMsg(Message m) {

        System.out.print("Message id:" + m.outId() + "\n");
        System.out.print(m.outTimestamp() + " " + m.outAuthor() + ":" + m.outMessage());

    }

    public void printSortedMsgs() {

        sortMsgsByDate();
        for (Message temp : msgs) {
            printMsg(temp);
        }

    }

    public void findMsgByAuthor() {

        Scanner sc = new Scanner(System.in);
        String auth;
        System.out.print("Enter author name:\n");
        auth = sc.nextLine();

        for (Message temp : msgs) {
            if (temp.outAuthor().equals(auth)) {
                printMsg(temp);
            }
        }
    }

    public void findMsgByWord() {

        Scanner sc = new Scanner(System.in);
        String word;
        System.out.print("Enter word:\n");
        word = sc.nextLine();
        for (Message temp : msgs) {
            if (temp.outMessage().contains(word)) {
                printMsg(temp);
            }
        }
    }

    public void findMsgByRegular() {

        System.out.print("Enter regular expression:\n");
        Scanner sc = new Scanner(System.in);
        Pattern p = Pattern.compile(sc.nextLine());
        for (Message temp : msgs) {
            Matcher m = p.matcher(temp.outMessage());
            if (m.matches()) printMsg(temp);
        }
    }

    public void findMsgByDate() throws ParseException {

        Scanner sc = new Scanner(System.in);
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm");
        System.out.print("Enter date in \"dd.MM.yyyy 'at' HH:mm\" format");
        Date date1 = format.parse(sc.nextLine());
        Date date2 = format.parse(sc.nextLine());
        Date defDate;

        for(Message temp : msgs){
            defDate = new Date(temp.outTimestamp());
            if(defDate.before(date1) && defDate.after(date2) ){
                printMsg(temp);
            }
        }
    }
}

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


public class MsgProc {

    private List<Message> msgs;

    public MsgProc() {
        msgs = new ArrayList<>();
    }

    public void getFileData(String path) throws IOException {

        FileReader fr = new FileReader(path);
        Scanner sc = new Scanner(fr);
        Gson gson = new Gson();
        Type collectionType = new TypeToken<ArrayList<Message>>() {
        }.getType();
        String str;
        if(sc.hasNext()){
            str = sc.nextLine();
            msgs = gson.fromJson(str, collectionType);
        }
        else System.out.print("File is empty!\n");
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


        Scanner sc = new Scanner(System.in);
        String delId;

        System.out.print("Enter message id:\n");

        delId = sc.nextLine();

        for (int i = 0; i < msgs.size(); i++) {
            if (msgs.get(i).outId().equals(delId)) {
                msgs.remove(i);
                break;
            }
        }


    }

    private void printMsg(Message m) {

        Date date = new Date(m.outTimestamp());
        System.out.print(date.toString() + " : " + m.outAuthor() + ": " + m.outMessage()+ "\n");

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
        boolean check = false;
        System.out.print("Enter author name:\n");

        auth = sc.nextLine();


        for (Message temp : msgs) {
            if (temp.outAuthor().equals(auth)) {
                printMsg(temp);
                check = true;
            }
        }

        if(!check){
            System.out.print("No such message\n");
        }
    }

    public void findMsgByWord() {

        Scanner sc = new Scanner(System.in);
        String word;
        boolean check = false;

        System.out.print("Enter word:\n");
        word = sc.nextLine();

        for (Message temp : msgs) {
            if (temp.outMessage().contains(word)) {
                printMsg(temp);
                check = true;
            }
        }
        if(!check){
            System.out.print("No such message\n");
        }
    }

    public void findMsgByRegular() {

        System.out.print("Enter regular expression:\n");
        Scanner sc = new Scanner(System.in);
        Pattern p = Pattern.compile(sc.nextLine());
        boolean check = false;

        for (Message temp : msgs) {
            Matcher m = p.matcher(temp.outMessage());
            if (m.matches()) {
                printMsg(temp);
                check = true;
            }
        }
        if(!check){
            System.out.print("No such message\n");
        }
    }

    public void findMsgByDate() throws ParseException {

        Scanner sc = new Scanner(System.in);
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm");
        System.out.print("Enter date in \"dd.MM.yyyy 'at' HH:mm\" format:\n");
        Date date1 = format.parse(sc.nextLine());
        Date date2 = format.parse(sc.nextLine());
        Date defDate;
        boolean check = false;

        for(Message temp : msgs){
            defDate = new Date(temp.outTimestamp());
            if(defDate.before(date1) && defDate.after(date2) ){
                printMsg(temp);
                check = true;
            }
        }
        if(!check){
            System.out.print("No such message\n");
        }
    }
}

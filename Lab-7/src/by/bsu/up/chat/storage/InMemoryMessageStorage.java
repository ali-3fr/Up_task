package by.bsu.up.chat.storage;

import by.bsu.up.chat.common.models.Message;
import by.bsu.up.chat.logging.Logger;
import by.bsu.up.chat.logging.impl.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InMemoryMessageStorage implements MessageStorage {

    private List<Message> messages;

    public InMemoryMessageStorage() {
        messages = new ArrayList<>();
        getFileData("msgHistory.txt");
    }

    public void getFileData(String file) {
        try {
            FileReader fr = new FileReader(file);
            Scanner sc = new Scanner(fr);
            Gson gson = new Gson();
            Type collectionType = new TypeToken<ArrayList<Message>>() {
            }.getType();
            String tempString;
            if (sc.hasNext()) {
                tempString = sc.nextLine();
                messages = gson.fromJson(tempString, collectionType);
            } else System.out.print("File is empty!\n");
            sc.close();
        }
        catch(FileNotFoundException e){
            System.out.println(e);
        }

    }

    public void saveToFile(String fileName) {
        try {
            File file = new File(fileName);
            FileWriter ps = new FileWriter(file);
            Gson gson = new Gson();
            String json = gson.toJson(messages);
            ps.write(json);
            ps.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized List<Message> getPortion(Portion portion) {
        int from = portion.getFromIndex();
        if (from < 0) {
            throw new IllegalArgumentException(String.format("Portion from index %d can not be less then 0", from));
        }
        int to = portion.getToIndex();
        if (to != -1 && to < portion.getFromIndex()) {
            throw new IllegalArgumentException(String.format("Porting last index %d can not be less then start index %d", to, from));
        }
        to = Math.max(to, messages.size());
        return messages.subList(from, to);
    }

    @Override
    public void addMessage(Message message) {
        messages.add(message);
        saveToFile("msgHistory.txt");
    }

    @Override
    public void updateMessage(Message message) {
        int delNumber = 0;
        String deleteId;
        String text;
        deleteId = message.getId();
        text = message.getText();

        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getId().equals(deleteId)) {
                delNumber = i;
                break;
            }
        }
        messages.get(delNumber).setText(text);
        saveToFile("msgHistory.txt");
    }


    @Override
    public void removeMessage(Message message) {
        int delNumber = 0;
        String deleteId;
        deleteId = message.getId();
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getId().equals(deleteId)) {
                delNumber = i;
                break;
            }
        }
        messages.remove(delNumber);
        saveToFile("msgHistory.txt");
    }


    @Override
    public int size() {
        return messages.size();
    }
}

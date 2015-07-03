package com.vertigo633.main;

import com.vertigo633.utils.Marshaller;
import com.vertigo633.entities.Note;
import com.vertigo633.utils.Validator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    static String XML_FILE;

    static final String SETTINGS_FILE = "resources/taskmanager.properties";

    private static List<Note> notes;

    public static void main(String[] args) {

        FileInputStream fis = null;
        try {

            Properties property = new Properties();

                fis = new FileInputStream(SETTINGS_FILE);
                property.load(fis);

            XML_FILE = property.getProperty("File_path");

            System.out.println("");
            notes = Marshaller.unmarshall(XML_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Hello! The Most Beautiful Console Notebook In The World Welcomes You!");

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        while (true) {
            showStartMenu();

            try {
                String entered = null;
                entered = br.readLine();
                int option = Integer.parseInt(entered);
                executeOption(option);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException nfe) {
                System.out.println();
                System.out.println("Please, enter the number corresponding to the option!");
            }
        }
    }

    private static void addNote() {

        Note note = new Note();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            String enteredName;
            while (true) {
                System.out.print("Enter contact name: ");
                enteredName = br.readLine();
                if (enteredName.length() == 0) {
                    System.out.println("Contact name must have at least 1 symbol");
                } else if (!Validator.validateName(enteredName)) {
                    System.out.println("This name is malformed, please try again");
                } else break;
            }
            note.setName(enteredName);
            String enteredEmail = "";
            while (true) {
                System.out.print("Enter contact e-mail: ");
                enteredEmail = br.readLine();
                if (!Validator.validateEmail(enteredEmail)) {
                    System.out.println("This e-mail is malformed, please try again");
                } else break;
            }
            note.setE_mail(enteredEmail);
            String enteredPhone = "";
            while (true) {
                System.out.print("Enter contact phone number: ");
                enteredPhone = br.readLine();
                if (!Validator.validatePhone(enteredPhone) || enteredPhone.length() > 11) {
                    System.out.println("This phone number is malformed, please try again");
                } else break;
            }
            note.setPhoneNumber(enteredPhone);
            notes.add(note);
            Marshaller.marshall(notes, XML_FILE);
            System.out.println("Contact successfully added!");
        } catch (IOException e) {
            System.out.println("The operation has failed");
            e.printStackTrace();
        }
    }

    private static void showStartMenu() {

        System.out.println();
        System.out.println("============================");
        System.out.println("|      MENU SELECTION      |");
        System.out.println("============================");
        System.out.println("| Options:                 |");
        System.out.println("|  1. Show contacts        |");
        System.out.println("|  2. Search contacts      |");
        System.out.println("|  3. Enter new contact    |");
        System.out.println("|  4. Delete contact       |");
        System.out.println("|  5. Exit                 |");
        System.out.println("============================");
        System.out.print("Please select your option: ");
    }

    private static void executeOption(int option) {
        switch (option) {
            case 1:
                showContacts(notes);
                break;
            case 2:
                searchContacts();
                break;
            case 3:
                addNote();
                break;
            case 4:
                deleteContacts();
                break;
            case 5:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println();
                System.out.println("Oh my God, you chose the wrong option!");
                break;
        }
    }

    private static void showContacts(List<Note> notesToShow) {
        try {
            notes = Marshaller.unmarshall(XML_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        int tableLength = 78;

        for (int i = 0; i < tableLength; i++) {
            System.out.print("=");
        }
        System.out.println();
        System.out.format("|%1$-34sCONTACTS%34s|", "");
        System.out.println();
        String format = "|%1$-34s|%2$-26s|%3$-14s|\n";

        System.out.format(format, "Name", "E-mail", "Phone Number");
        System.out.format("|%1$-76s|", "");
        System.out.println();
        for (Note note : notesToShow) {
            System.out.format(format, note.getName(), note.getE_mail(), note.getPhoneNumber());
        }
        for (int i = 0; i < tableLength; i++) {
            System.out.print("=");
        }
    }

    private static void searchContacts() {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String entered = "";
        System.out.print("Enter the contact name: ");
        try {
            entered = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Note> notesToShow = new ArrayList<Note>();
        for (int i = 0; i < notes.size(); i++) {
            if (entered.equals(notes.get(i).getName()) || notes.get(i).getName().contains(entered)) {
                notesToShow.add(notes.get(i));
            }
        }
        showContacts(notesToShow);
    }

    private static void deleteContacts() {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String entered = "";
        int found = 0;
        System.out.print("Enter the contact name: ");
        try {
            entered = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < notes.size(); i++) {
            if (entered.equals(notes.get(i).getName())) {

                found++;
                System.out.print("The first contact with name \"" + entered + "\" will be removed! Enter \"yes\" if you're sure: ");
                try {
                    String answer = br.readLine();
                    if ("yes".equalsIgnoreCase(answer)) {
                        notes.remove(notes.get(i));
                        Marshaller.marshall(notes, XML_FILE);

                        System.out.println("The contact has been removed!");
                        break;
                    } else {
                        System.out.println("\"" + entered + "\" has not been removed");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("The operation has failed");
                }
            }
        }
        if (found == 0) {
            System.out.println("There is no contacts with name \"" + entered + "\" in your notebook!");
        }
    }
}

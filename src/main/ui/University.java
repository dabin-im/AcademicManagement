package ui;

import java.io.FileNotFoundException;

// University
public class University {

    // EFFECTS: operates administration in university
    public static void main(String[] args) {
        try {
            new AdministrationUI();
        } catch (FileNotFoundException exception) {
            System.out.println("Unable to run application: file not found");
        }
    }
}

package ui;

import model.*;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

// University administration application
public class AdministrationConsole {
    private static final int MINIMUM_INDEX = 0;                             // minimum index of list
    private Department department;                                          // department information
    private final JsonWriter jsonWriter;                                    // Json writer object
    private final JsonReader jsonReader;                                    // Json reader object
    private final Scanner scanner;                                          // scanner instance for getting user inputs
    private static final String JSON_STORE = "./data/department.json";      // the location where Json file stored

    // EFFECTS: creates an administration and instantiates a scanner and department
    //          Regarding department, creates new department or loads existing department from a file
    //          after that, manages this department by using manageDepartment() method
    public AdministrationConsole() throws FileNotFoundException {
        scanner = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        printDepartmentMenu();
        int number = getInteger();

        while (number < 1 || number > 3) {
            System.out.println("Please try again...");
            number = getInteger();
        }

        if (number == 1) {
            createDepartment();
        } else if (number == 2) {
            loadDepartment();
        } else {
            return;
        }

        manageDepartment();
    }

    // MODIFIES: this
    // EFFECTS: creates a department based on an input of user
    private void createDepartment() {
        String departmentCode;
        String departmentName;
        String departmentAddress;
        String departmentPhone;

        System.out.print("Department Code: ");
        departmentCode = scanner.nextLine();
        System.out.print("Department Name: ");
        departmentName = scanner.nextLine();
        System.out.print("Department Address: ");
        departmentAddress = scanner.nextLine();
        System.out.print("Department Number: ");
        departmentPhone = scanner.nextLine();

        department = new Department(departmentCode, departmentName, departmentAddress, departmentPhone);
    }

    // EFFECTS: prints a menu whether creating a new department or loading an existing department from a file
    private void printDepartmentMenu() {
        System.out.println("--------------------------------------");
        System.out.println("-              Department            -");
        System.out.println("--------------------------------------");
        System.out.println("-     [1] Create                     -");
        System.out.println("-     [2] Load                       -");
        System.out.println("-     [3] End                        -");
        System.out.println("--------------------------------------");
    }

    // EFFECTS: prints a department menu
    private void printDepartmentManageMenu() {
        System.out.println("--------------------------------------");
        System.out.println("-              Department            -");
        System.out.println("--------------------------------------");
        System.out.println("-     [1] Update                     -");
        System.out.println("-     [2] Go To Other Session        -");
        System.out.println("-     [3] End                        -");
        System.out.println("--------------------------------------");
    }

    // EFFECTS: prints other sessions such as professor and student
    private void printOtherSessionMenu() {
        System.out.println("--------------------------------------");
        System.out.println("-                OTHERS              -");
        System.out.println("--------------------------------------");
        System.out.println("-     [1] Go To Professor Session    -");
        System.out.println("-     [2] Go To Student Session      -");
        System.out.println("-     [3]       Back                 -");
        System.out.println("--------------------------------------");
    }

    // EFFECTS: prints a professor menu
    private void printProfessorMenu() {
        System.out.println("--------------------------------------");
        System.out.println("-              Professor             -");
        System.out.println("--------------------------------------");
        System.out.println("-     [1] Create                     -");
        System.out.println("-     [2] Update                     -");
        System.out.println("-     [3] Delete                     -");
        System.out.println("-     [4] Total View                 -");
        System.out.println("-     [5] Professor Detail           -");
        System.out.println("-     [6] Retirement                 -");
        System.out.println("-     [7] Back                       -");
        System.out.println("--------------------------------------");
    }

    // EFFECTS: prints a student menu
    private void printStudentMenu() {
        System.out.println("--------------------------------------");
        System.out.println("-                Student             -");
        System.out.println("--------------------------------------");
        System.out.println("-     [1] Create                     -");
        System.out.println("-     [2] Update                     -");
        System.out.println("-     [3] Delete                     -");
        System.out.println("-     [4] Total View                 -");
        System.out.println("-     [5] Student Detail             -");
        System.out.println("-     [6] Graduation                 -");
        System.out.println("-     [7] Back                       -");
        System.out.println("--------------------------------------");
    }

    // EFFECTS: operates department an update function and provides other sessions
    //          to manage professor and student
    public void manageDepartment() {
        while (true) {
            printDepartmentManageMenu();
            int number = getInteger();
            switch (number) {
                case 1: updateDepartment();
                    break;
                case 2: manageOtherSession();
                    break;
                case 3: saveDepartment();
                    return;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: changes to new department information
    private void updateDepartment() {
        System.out.print("Department Code: ");
        department.setDepartmentCode(scanner.nextLine());

        System.out.print("Department Name: ");
        department.setDepartmentName(scanner.nextLine());

        System.out.print("Department Address: ");
        department.setDepartmentAddress(scanner.nextLine());

        System.out.print("Department Number: ");
        department.setDepartmentPhone(scanner.nextLine());
    }

    // EFFECTS: provides guideline with printOtherSessionMenu to manage professors or students
    //          using an integer input from a user
    private void manageOtherSession() {
        while (true) {
            printOtherSessionMenu();
            int number = getInteger();
            switch (number) {
                case 1:
                    manageProfessors();
                    break;
                case 2:
                    manageStudent();
                    break;
                case 3:
                    return;
            }
        }
    }

    // EFFECTS: operates professor management functions with printProfessorMenu through an input from a user
    private void manageProfessors() {
        while (true) {
            printProfessorMenu();
            int number = getInteger();
            switch (number) {
                case 1: addProfessor();
                    break;
                case 2: updateProfessor();
                    break;
                case 3: deleteProfessor();
                    break;
                case 4: department.viewProfessors();
                    break;
                case 5: viewProfessorDetail();
                    break;
                case 6: retireProfessor();
                    break;
                case 7:
                    return;
            }
        }
    }

    // MODIFIES: department
    // EFFECTS: creates a professor and inserts professor into a list in department by inputs from a user
    public void addProfessor() {
        String name = getString("Name");
        String email = getString("Email");
        String phone = getString("Phone");
        String address = getString("Address");
        String startDate;
        GenderType gender;
        int number = inputIntegerWithRange(1, 2, "[1] Male   [2] Female\nChoose type",
                "Error: Invalid number...");
        if (number == 1) {
            gender = GenderType.MALE;
        } else {
            gender = GenderType.FEMALE;
        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        startDate = dateFormat.format(date);

        department.addProfessor(name, email, phone, address, gender, startDate, "");
    }

    // MODIFIES: department, professor
    // EFFECTS: updates the professor a user has chosen from a list of professor in department
    private void updateProfessor() {
        Professor professor = chooseProfessor();
        if (professor == null) {
            return;
        }
        String name = getString("Name");
        String email = getString("Email");
        String phone = getString("Phone");
        String address = getString("Address");
        GenderType gender;
        int number = inputIntegerWithRange(1, 2, "[1] Male   [2] Female\nChoose type",
                "Error: Invalid number...");
        if (number == 1) {
            gender = GenderType.MALE;
        } else {
            gender = GenderType.FEMALE;
        }

        department.updateProfessorInfo(professor, name, email, phone, address, gender);
    }

    // MODIFIES: department
    // EFFECTS: delete specific professor's information by an input from a user
    private void deleteProfessor() {
        Professor professor = chooseProfessor();
        if (professor == null) {
            return;
        }
        department.removeProfessor(professor);
    }

    // EFFECTS: prints detailed information of the professor a user has chosen
    private void viewProfessorDetail() {
        Professor professor = chooseProfessor();
        if (professor == null) {
            return;
        }
        System.out.println(professor);
    }

    // MODIFIES: professor
    // EFFECTS: updates retirement date of professor
    private void retireProfessor() {
        Professor professor = chooseProfessor();
        if (professor == null) {
            return;
        }
        if (!professor.isRetired()) {
            professor.retiring();
            System.out.println("Thank you for your work!");
        } else {
            System.out.println("This professor is already retired!");
        }
    }

    // EFFECTS: returns specific professor using an input from a user
    private Professor chooseProfessor() {
        if (department.isEmptyProfessors()) {
            return null;
        }
        department.viewProfessors();
        int index = inputIntegerWithRange(MINIMUM_INDEX, department.getNumberOfProfessors() - 1,
                "Choose Professor", "Invalid Number...Try Again");

        return department.getProfessor(index);
    }

    // EFFECTS: operates student management functions with printStudentMenu through an input from a user
    private void manageStudent() {
        while (true) {
            printStudentMenu();
            int number = getInteger();
            switch (number) {
                case 1: addStudent();
                    break;
                case 2: updateStudent();
                    break;
                case 3: deleteStudent();
                    break;
                case 4: department.viewStudents();
                    break;
                case 5: viewStudentDetail();
                    break;
                case 6: requestGraduation();
                    break;
                case 7:
                    return;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a student and inserts the student into a list in department by inputs from a user
    public void addStudent() {
        String name = getString("Name");
        String email = getString("Email");
        String phone = getString("Phone");
        String address = getString("Address");
        String startDate;
        GenderType gender;
        int number = inputIntegerWithRange(1, 2, "[1] Male   [2] Female\nChoose type",
                "Error: Invalid number...");
        if (number == 1) {
            gender = GenderType.MALE;
        } else {
            gender = GenderType.FEMALE;
        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        startDate = dateFormat.format(date);

        department.addStudent(name, email, phone, address, gender, startDate, "");
    }

    // MODIFIES: department, student
    // EFFECTS: updates the student a user has chosen from a list of professor in department
    private void updateStudent() {
        Student student = chooseStudent();
        if (student == null) {
            return;
        }
        String name = getString("Name");
        String email = getString("Email");
        String phone = getString("Phone");
        String address = getString("Address");
        GenderType gender;
        int number = inputIntegerWithRange(1, 2, "[1] Male   [2] Female\nChoose type",
                "Error: Invalid number...");
        if (number == 1) {
            gender = GenderType.MALE;
        } else {
            gender = GenderType.FEMALE;
        }

        department.updateStudentInfo(student, name, email, phone, address, gender);
    }

    // EFFECTS: removes information of student in a list from department
    private void deleteStudent() {
        Student student = chooseStudent();
        if (student == null) {
            return;
        }
        department.removeStudent(student);
    }

    // EFFECTS: prints detailed information of the student a user has chosen
    private void viewStudentDetail() {
        Student student = chooseStudent();
        if (student == null) {
            return;
        }
        System.out.println(student);
    }

    // MODIFIES: student
    // EFFECTS: updates graduate date using the method named graduating from student
    private void requestGraduation() {
        Student student = chooseStudent();
        if (student == null) {
            return;
        }
        if (!student.isGraduated()) {
            student.graduating();
            System.out.println("Congratulation!");
        } else {
            System.out.println("This student is already graduated!");
        }
    }

    // EFFECTS: returns specific student using an input from a user
    private Student chooseStudent() {
        if (department.isEmptyStudents()) {
            return null;
        }
        department.viewStudents();

        int index = inputIntegerWithRange(MINIMUM_INDEX, department.getNumberOfStudents() - 1,
                "Choose Student", "Invalid Number...Try Again");

        return department.getStudent(index);
    }

    // EFFECTS: gets an integer input from a user
    private int getInteger() {
        int index = scanner.nextInt();
        scanner.nextLine();
        return index;
    }

    // EFFECTS: gets a string input from a user
    private String getString(String item) {
        System.out.print(item + ": ");
        String text = scanner.nextLine();

        while (text.equals("")) {
            System.out.println("Please input " + item + "... Try Again");
            System.out.print(item + ": ");

            text = scanner.nextLine();
        }
        return text;
    }

    // REQUIRES: min >= 0 && min <= max, item != null, errorMessage != null
    // EFFECTS: gets integer input from user within the ranges between min and max including both
    public int inputIntegerWithRange(int min, int max, String item, String errorMessage) {
        System.out.print(item + ": ");
        int number = getInteger();

        while (number > max || number < min) {
            System.out.println(errorMessage);
            System.out.print(item + ": ");
            number = getInteger();
        }
        return number;
    }

    // EFFECTS: saves the department to file if the file doesn't exist, creates new file and
    //          stores all information of department
    private void saveDepartment() {
        File file = new File(JSON_STORE);

        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("File created: " + file.getName());
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }

        try {
            jsonWriter.open();
            jsonWriter.write(department);
            jsonWriter.close();
            System.out.println("Saved " + department.getDepartmentName() + " to " + JSON_STORE);
        } catch (FileNotFoundException exception) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads department from file if the file doesn't exist, creates new department
    private void loadDepartment() {
        try {
            department = jsonReader.read();
            System.out.println("Loaded " + department.getDepartmentName() + " from " + JSON_STORE);
        } catch (IOException exception) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            System.out.println("\nCreate new department");
            createDepartment();
        }
    }

}

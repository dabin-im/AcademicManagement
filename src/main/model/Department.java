package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a department having a code, name, address, phone, professor's list, and student's list
public class Department implements Writable {
    private String departmentCode;                  // department Code
    private String departmentName;                  // department Name
    private String departmentAddress;               // department Address
    private String departmentPhone;                 // department Phone
    private final List<Professor> professors;       // list of professors
    private final List<Student> students;           // list of students

    // REQUIRES: departmentCode. != null
    //           departmentName != null
    //           departmentAddress != null
    //           departmentPhone != null
    // EFFECTS: constructs a department with given departmentCode, departmentName, departmentName,
    //          and departmentPhone. And then, creates Arraylists named Professors and Students
    public Department(String departmentCode, String departmentName, String departmentAddress, String departmentPhone) {
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
        this.departmentAddress = departmentAddress;
        this.departmentPhone = departmentPhone;

        professors = new ArrayList<>();
        students = new ArrayList<>();
    }

    // EFFECTS: gets department code
    public String getDepartmentCode() {
        return departmentCode;
    }

    // EFFECTS: gets department name
    public String getDepartmentName() {
        return departmentName;
    }

    // EFFECTS: gets department address
    public String getDepartmentAddress() {
        return departmentAddress;
    }

    // EFFECTS: gets department phone number
    public String getDepartmentPhone() {
        return departmentPhone;
    }

    // REQUIRES: departmentCode != null
    // MODIFIES: this
    // EFFECTS: changes to new departmentCode from old departmentCode
    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    // REQUIRES: departmentName != null
    // MODIFIES: this
    // EFFECTS: changes to new departmentName from old departmentName
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    // REQUIRES: departmentAddress != null
    // MODIFIES: this
    // EFFECTS: changes to new departmentAddress from old departmentAddress
    public void setDepartmentAddress(String departmentAddress) {
        this.departmentAddress = departmentAddress;
    }

    // REQUIRES: departmentPhone != null
    // MODIFIES: this
    // EFFECTS: changes to new departmentPhone from old departmentPhone
    public void setDepartmentPhone(String departmentPhone) {
        this.departmentPhone = departmentPhone;
    }

    // EFFECTS: gets number of students
    public int getNumberOfStudents() {
        return students.size();
    }

    // EFFECTS: gets number of professors
    public int getNumberOfProfessors() {
        return professors.size();
    }

    // EFFECTS: gets number of people in department
    public int getNumberOfAll() {
        return getNumberOfStudents() + getNumberOfProfessors();
    }

    // EFFECTS: provides brief department information
    public String briefInformation() {
        return "[" + departmentCode + "] " + departmentName;
    }


    // REQUIRES: name != null
    //           email != null
    //           phone != null
    //           address != null
    //           genderType != null
    //           startDate != null
    // MODIFIES: this
    // EFFECTS: adds a professor into the department
    public void addProfessor(String name, String email, String phone, String address, GenderType genderType,
                             String startDate, String endDate) {
        Professor professor = new Professor(name, email, phone, address, genderType,
                this, startDate, endDate);
        professors.add(professor);
        EventLog.getInstance().logEvent(new Event("Add a professor from the department"));
    }

    // REQUIRES: name != null
    //           email != null
    //           phone != null
    //           address != null
    //           genderType != null
    //           startDate != null
    // MODIFIES: this
    // EFFECTS: adds a student into the department
    public void addStudent(String name, String email, String phone, String address, GenderType genderType,
                           String startDate, String endDate) {
        Student student = new Student(name, email, phone, address, genderType, this, startDate, endDate);
        students.add(student);
        EventLog.getInstance().logEvent(new Event("Add a student from the department"));
    }

    // EFFECTS: returns true if number of professor is equal to zero in department
    public boolean isEmptyProfessors() {
        return professors.isEmpty();
    }

    // EFFECTS: returns true if number of student is equal to zero in department
    public boolean isEmptyStudents() {
        return students.isEmpty();
    }

    // EFFECTS: prints brief information of all professors in department
    public void viewProfessors() {
        for (int i = 0; i < professors.size(); i++) {
            System.out.println(i + ":" + professors.get(i).briefInformation());
        }
    }

    // EFFECTS: prints brief information of all students in department
    public void viewStudents() {
        for (int i = 0; i < students.size(); i++) {
            System.out.println(i + ":" + students.get(i).briefInformation());
        }
    }

    // REQUIRES: index >= 0 && index < professors.size()
    // EFFECTS: returns a certain professor information based on index from the list of professors
    public Professor getProfessor(int index) {
        return professors.get(index);
    }

    // EFFECTS: returns all professor information
    public List<Professor> getProfessors() {
        return professors;
    }

    // EFFECTS: returns all student information
    public List<Student> getStudents() {
        return students;
    }

    // REQUIRES: index >= 0 && index < professors.size()
    // EFFECTS: returns a certain student information based on index from the list of students
    public Student getStudent(int index) {
        return students.get(index);
    }

    // REQUIRES: professor != null
    //           name != null
    //           email != null
    //           phone != null
    //           address != null
    //           genderType != null
    // MODIFIES: this
    // EFFECTS: updates a specific professor from the list of professors
    public void updateProfessorInfo(Professor professor, String name, String email, String phone, String address,
                                    GenderType genderType) {
        professor.setName(name);
        professor.setEmail(email);
        professor.setPhone(phone);
        professor.setAddress(address);
        professor.setGenderType(genderType);
        EventLog.getInstance().logEvent(new Event("Modified professor from the department"));
    }

    // REQUIRES: student != null
    //           name != null
    //           email != null
    //           phone != null
    //           address != null
    //           genderType != null
    // MODIFIES: this
    // EFFECTS: updates a specific student from the list of students
    public void updateStudentInfo(Student student, String name, String email, String phone, String address,
                                  GenderType genderType) {
        student.setName(name);
        student.setEmail(email);
        student.setPhone(phone);
        student.setAddress(address);
        student.setGenderType(genderType);
        EventLog.getInstance().logEvent(new Event("Modified student from the department"));
    }


    // REQUIRES: professor != null
    // MODIFIES: this
    // EFFECTS: removes a certain professor from the list of professors
    public void removeProfessor(Professor professor) {
        professors.remove(professor);
        EventLog.getInstance().logEvent(new Event("Remove professor from the department"));
    }

    // REQUIRES: student != null
    // MODIFIES: this
    // EFFECTS: removes a certain student from the list of students
    public void removeStudent(Student student) {
        students.remove(student);
        EventLog.getInstance().logEvent(new Event("Remove student from the department"));
    }

    // EFFECTS: provides detailed department information
    @Override
    public String toString() {
        String departmentInfo = "Department Code: " + this.departmentCode;
        departmentInfo += "\nDepartment Name: " + this.departmentName;
        departmentInfo += "\nDepartment Address: " + this.departmentAddress;
        departmentInfo += "\nDepartment Phone: " + this.departmentPhone;

        return departmentInfo;
    }

    // EFFECTS: returns department information as a JSON 
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("departmentCode", departmentCode);
        json.put("departmentName", departmentName);
        json.put("departmentAddress", departmentAddress);
        json.put("departmentPhone", departmentPhone);
        json.put("professors", professorsToJson());
        json.put("students", studentsToJson());
        EventLog.getInstance().logEvent(new Event("Save department through JSON"));
        return json;
    }

    // EFFECTS: returns professors in this department as a JSON array
    private JSONArray professorsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Professor professor : professors) {
            jsonArray.put(professor.toJson());
        }
        EventLog.getInstance().logEvent(new Event("Save professors' information into the department through JSON"));
        return jsonArray;
    }

    // EFFECTS: returns students in this department as a JSON array
    private JSONArray studentsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Student student : students) {
            jsonArray.put(student.toJson());
        }
        EventLog.getInstance().logEvent(new Event("Save students' information into the department through JSON"));
        return jsonArray;
    }
}

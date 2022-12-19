package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepartmentTest {
    private Department testDepartment;
    String startDate;
    private static final String JSON_STORE = "./data/department.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    @BeforeEach
    void runBefore() {
        testDepartment = new Department("CPSC", "Computer Science",
                "Vancouver", "111-222-3333");

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        startDate = dateFormat.format(date);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    @Test
    void testConstructor() {
        assertEquals("CPSC", testDepartment.getDepartmentCode());
        assertEquals("Computer Science", testDepartment.getDepartmentName());
        assertEquals("Vancouver", testDepartment.getDepartmentAddress());
        assertEquals("111-222-3333", testDepartment.getDepartmentPhone());
        assertTrue(testDepartment.isEmptyProfessors());
        assertTrue(testDepartment.isEmptyStudents());
        assertEquals(testDepartment.getNumberOfProfessors(), 0);
        assertEquals(testDepartment.getNumberOfStudents(), 0);
    }

    @Test
    void testUpdateDepartment() {
        String departmentInfo =  "Department Code: " + testDepartment.getDepartmentCode();
        departmentInfo += "\nDepartment Name: " + testDepartment.getDepartmentName();
        departmentInfo += "\nDepartment Address: " + testDepartment.getDepartmentAddress();
        departmentInfo += "\nDepartment Phone: " + testDepartment.getDepartmentPhone();

        assertEquals(testDepartment.toString(), departmentInfo);

        departmentInfo = "[" + testDepartment.getDepartmentCode() + "] " + testDepartment.getDepartmentName();

        assertEquals(testDepartment.briefInformation(), departmentInfo);


        testDepartment.setDepartmentCode("BIOL");
        testDepartment.setDepartmentName("Biology");
        testDepartment.setDepartmentAddress("Toronto");
        testDepartment.setDepartmentPhone("222-333-4444");

        assertEquals("BIOL", testDepartment.getDepartmentCode());
        assertEquals("Biology", testDepartment.getDepartmentName());
        assertEquals("Toronto", testDepartment.getDepartmentAddress());
        assertEquals("222-333-4444", testDepartment.getDepartmentPhone());

        departmentInfo =  "Department Code: " + testDepartment.getDepartmentCode();
        departmentInfo += "\nDepartment Name: " + testDepartment.getDepartmentName();
        departmentInfo += "\nDepartment Address: " + testDepartment.getDepartmentAddress();
        departmentInfo += "\nDepartment Phone: " + testDepartment.getDepartmentPhone();
        assertEquals(testDepartment.toString(), departmentInfo);
    }

    @Test
    void testManageProfessorList() {
        testDepartment.addProfessor("Bob", "abc@gmail.com", "555-555-5555"
                , "Vancouver", GenderType.MALE, startDate, "");
        testDepartment.addProfessor("Dave", "ccc@gmail.com", "444-555-5555"
                , "LA", GenderType.MALE, startDate, "");

        assertEquals(testDepartment.getNumberOfAll(), 2);
        assertEquals(testDepartment.getNumberOfStudents(), 0);
        assertEquals(testDepartment.getNumberOfProfessors(), 2);

        testDepartment.viewProfessors();

        Professor professorDave = testDepartment.getProfessor(1);

        testDepartment.removeProfessor(professorDave);

        assertEquals(testDepartment.getNumberOfAll(), 1);
        assertEquals(testDepartment.getNumberOfAll(), testDepartment.getNumberOfProfessors());
        assertFalse(testDepartment.getNumberOfAll() == testDepartment.getNumberOfStudents());

        Professor professor = testDepartment.getProfessor(0);

        Professor bob = new Professor("Bob", "abc@gmail.com", "555-555-5555"
                , "Vancouver", GenderType.MALE, testDepartment, startDate, "");

        assertTrue(professor.getName().equals(bob.getName()));
        assertTrue(professor.getEmail().equals(bob.getEmail()));
        assertTrue(professor.getPhone().equals(bob.getPhone()));
        assertTrue(professor.getGenderType().equals(bob.getGenderType()));
        assertTrue(professor.getAddress().equals(bob.getAddress()));
        assertTrue(professor.getStartDate().equals(bob.getStartDate()));
        assertTrue(professor.getEndDate().equals(bob.getEndDate()));

        testDepartment.updateProfessorInfo(professor, "Jenna", "Jenna@gmail.com", "333-333-3333",
                "Seattle", GenderType.FEMALE);

        assertFalse(professor.getName().equals(bob.getName()));
        assertFalse(professor.getEmail().equals(bob.getEmail()));
        assertFalse(professor.getPhone().equals(bob.getPhone()));
        assertFalse(professor.getGenderType().equals(bob.getGenderType()));
        assertFalse(professor.getAddress().equals(bob.getAddress()));
    }

    @Test
    void testControlStudentList() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        String startDate = dateFormat.format(date);

        testDepartment.addStudent("June", "fff@gmail.com", "511-011-9999"
                , "Vancouver", GenderType.MALE, startDate, "");
        testDepartment.addStudent("Jason", "hhh@gmail.com", "123-444-1144"
                , "LA", GenderType.MALE, startDate, "");

        assertEquals(testDepartment.getNumberOfAll(), 2);
        assertEquals(testDepartment.getNumberOfStudents(), 2);
        assertEquals(testDepartment.getNumberOfProfessors(), 0);

        testDepartment.viewStudents();

        Student studentJason = testDepartment.getStudent(1);
        testDepartment.removeStudent(studentJason);

        assertEquals(testDepartment.getNumberOfStudents(), 1);
        assertEquals(testDepartment.getNumberOfAll(), 1);
        Student student = testDepartment.getStudent(0);

        Student june = new Student("June", "fff@gmail.com", "511-011-9999"
                , "Vancouver", GenderType.MALE, testDepartment, startDate, "");

        assertTrue(student.getName().equals(june.getName()));
        assertTrue(student.getEmail().equals(june.getEmail()));
        assertTrue(student.getPhone().equals(june.getPhone()));
        assertTrue(student.getGenderType().equals(june.getGenderType()));
        assertTrue(student.getAddress().equals(june.getAddress()));
        assertTrue(student.getStartDate().equals(june.getStartDate()));
        assertTrue(student.getEndDate().equals(june.getEndDate()));

        testDepartment.updateStudentInfo(student, "Jenna", "Jenna@gmail.com", "333-333-3333",
                "Seattle", GenderType.FEMALE);

        assertFalse(student.getName().equals(june.getName()));
        assertFalse(student.getEmail().equals(june.getEmail()));
        assertFalse(student.getPhone().equals(june.getPhone()));
        assertFalse(student.getGenderType().equals(june.getGenderType()));
        assertFalse(student.getAddress().equals(june.getAddress()));
    }

    @Test
    void testSaveDepartment() {
        testDepartment.addProfessor("Bob", "abc@gmail.com", "555-555-5555"
                , "Vancouver", GenderType.MALE, startDate, "");
        testDepartment.addStudent("June", "fff@gmail.com", "511-011-9999"
                , "Vancouver", GenderType.MALE, startDate, "");
        try {
            jsonWriter.open();
            jsonWriter.write(testDepartment);
            jsonWriter.close();
            System.out.println("Saved " + testDepartment.getDepartmentName() + " to " + JSON_STORE);
        } catch (FileNotFoundException exception) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

        Department department;
        try {
            department = jsonReader.read();
            System.out.println("Loaded " + department.getDepartmentName() + " from " + JSON_STORE);

            assertEquals(testDepartment.getDepartmentCode(), department.getDepartmentCode());
            assertEquals(testDepartment.getDepartmentName(), department.getDepartmentName());
            assertEquals(testDepartment.getDepartmentAddress(), department.getDepartmentAddress());
            assertEquals(testDepartment.getDepartmentPhone(), department.getDepartmentPhone());
            assertEquals(testDepartment.getNumberOfStudents(), department.getNumberOfStudents());
            assertEquals(testDepartment.getNumberOfProfessors(), department.getNumberOfProfessors());
            assertEquals(testDepartment.getNumberOfAll(), department.getNumberOfAll());

            Professor testProfessor = testDepartment.getProfessor(0);
            Professor professor = department.getProfessor(0);

            assertEquals(testProfessor.getName(), professor.getName());
            assertEquals(testProfessor.getEmail(), professor.getEmail());
            assertEquals(testProfessor.getAddress(), professor.getAddress());
            assertEquals(testProfessor.getPhone(), professor.getPhone());
            assertEquals(testProfessor.getGenderType(), professor.getGenderType());
            assertEquals(testProfessor.getStartDate(), professor.getStartDate());
            assertEquals(testProfessor.getEndDate(), professor.getEndDate());

            Student testStudent = testDepartment.getStudent(0);
            Student student = department.getStudent(0);

            assertEquals(testStudent.getName(), student.getName());
            assertEquals(testStudent.getEmail(), student.getEmail());
            assertEquals(testStudent.getAddress(), student.getAddress());
            assertEquals(testStudent.getPhone(), student.getPhone());
            assertEquals(testStudent.getGenderType(), student.getGenderType());
            assertEquals(testStudent.getStartDate(), student.getStartDate());
            assertEquals(testStudent.getEndDate(), student.getEndDate());

        } catch (IOException exception) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }


    }

}

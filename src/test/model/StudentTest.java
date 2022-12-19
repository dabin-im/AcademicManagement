package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentTest {
    private Department testDepartment;
    private String testDate;
    @BeforeEach
    void runBefore() {
        testDepartment = new Department("CPSC", "Computer Science",
                "Vancouver", "111-222-3333");

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        testDate = dateFormat.format(date);

        testDepartment.addStudent("June", "fff@gmail.com", "511-011-9999"
                , "Vancouver", GenderType.MALE, testDate, "");

    }

    @Test
    void testConstructor() {
        Student testStudent = testDepartment.getStudent(0);

        Department department = new Department("CPSC", "Computer Science",
                "Vancouver", "111-222-3333");

        String personInfo = "[" + testDepartment.getDepartmentCode() + "] " + testStudent.getName();

        assertEquals(testDepartment.getStudents().size(), 1);
        assertEquals("June", testStudent.getName());
        assertEquals("fff@gmail.com", testStudent.getEmail());
        assertEquals("511-011-9999", testStudent.getPhone());
        assertEquals("Vancouver", testStudent.getAddress());
        assertEquals(GenderType.MALE, testStudent.getGenderType());
        assertEquals(testDate, testStudent.getStartDate());
        assertEquals("", testStudent.getEndDate());
        assertEquals(testStudent.briefInformation(), personInfo);

        assertEquals(testStudent.getDepartment().getDepartmentCode(), department.getDepartmentCode());
        assertEquals(testStudent.getDepartment().getDepartmentName(), department.getDepartmentName());
        assertEquals(testStudent.getDepartment().getDepartmentAddress(), department.getDepartmentAddress());
        assertEquals(testStudent.getDepartment().getDepartmentPhone(), department.getDepartmentPhone());

        testDepartment.setDepartmentCode("BIOL");
        testDepartment.setDepartmentName("Biology");
        testDepartment.setDepartmentAddress("Toronto");
        testDepartment.setDepartmentPhone("222-333-4444");

        assertFalse(testStudent.getDepartment().getDepartmentCode().equals(department.getDepartmentCode()));
        assertFalse(testStudent.getDepartment().getDepartmentName().equals(department.getDepartmentName()));
        assertFalse(testStudent.getDepartment().getDepartmentAddress().equals(department.getDepartmentAddress()));
        assertFalse(testStudent.getDepartment().getDepartmentPhone().equals(department.getDepartmentPhone()));

        assertFalse(testStudent.briefInformation().equals(personInfo));
    }

    @Test
    void testUpdateStudent() {
        Student testStudent = testDepartment.getStudent(0);

        String personInfo = "User: " + testStudent.getName();
        personInfo += "\nEmail: " + testStudent.getEmail();
        personInfo += "\nPhone: " + testStudent.getPhone();
        personInfo += "\nGender: " + testStudent.getGenderType();
        personInfo += "\nDepartment: " + testStudent.getDepartment().briefInformation();
        personInfo += "\nStart Date: " + testDate;

        assertEquals(testStudent.toString(), personInfo);

        assertEquals(testDepartment.getNumberOfProfessors(), 0);
        testStudent.setName("Jenna");
        testStudent.setEmail("Jenna@gmail.com");
        testStudent.setAddress("New York");
        testStudent.setPhone("123-123-4444");
        testStudent.setGenderType(GenderType.FEMALE);

        assertTrue(testStudent.getStartDate().equals(testDate));
        assertFalse(testStudent.getEndDate().equals(testDate));

        assertFalse(testStudent.isGraduated());
        testStudent.graduating();
        assertTrue(testStudent.isGraduated());

        assertTrue(testStudent.getStartDate().equals(testDate));
        assertTrue(testStudent.getEndDate().equals(testDate));

        assertEquals("Jenna", testStudent.getName());
        assertEquals("Jenna@gmail.com", testStudent.getEmail());
        assertEquals("New York", testStudent.getAddress());
        assertEquals("123-123-4444", testStudent.getPhone());
        assertEquals(GenderType.FEMALE, testStudent.getGenderType());
        assertEquals(testDate, testStudent.getStartDate());
        assertEquals(testDate, testStudent.getEndDate());

        personInfo = "User: " + testStudent.getName();
        personInfo += "\nEmail: " + testStudent.getEmail();
        personInfo += "\nPhone: " + testStudent.getPhone();
        personInfo += "\nGender: " + testStudent.getGenderType();
        personInfo += "\nDepartment: " + testStudent.getDepartment().briefInformation();
        personInfo += "\nStart Date: " + testDate;
        personInfo += "\nEnd Date: " + testDate;

        assertEquals(testStudent.toString(), personInfo);
    }
}

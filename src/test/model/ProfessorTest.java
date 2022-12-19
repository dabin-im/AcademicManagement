package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfessorTest {
    private Department testDepartment;
    private String testDate;
    @BeforeEach
    void runBefore() {
        testDepartment = new Department("CPSC", "Computer Science",
                "Vancouver", "111-222-3333");

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        testDate = dateFormat.format(date);

        testDepartment.addProfessor("Bob", "abc@gmail.com", "555-555-5555"
                , "Vancouver", GenderType.MALE, testDate, "");
    }

    @Test
    void testConstructor() {
        Department department = new Department("CPSC", "Computer Science",
                "Vancouver", "111-222-3333");

        Professor testProfessor = testDepartment.getProfessor(0);
        String personInfo = "[" + testDepartment.getDepartmentCode() + "] " + testProfessor.getName();


        assertEquals(testDepartment.getProfessors().size(), 1);
        assertEquals("Bob", testProfessor.getName());
        assertEquals("abc@gmail.com", testProfessor.getEmail());
        assertEquals("555-555-5555", testProfessor.getPhone());
        assertEquals("Vancouver", testProfessor.getAddress());
        assertEquals(GenderType.MALE, testProfessor.getGenderType());
        assertEquals(testDate, testProfessor.getStartDate());
        assertEquals("", testProfessor.getEndDate());
        assertEquals(testProfessor.briefInformation(), personInfo);

        assertEquals(testProfessor.getDepartment().getDepartmentCode(), department.getDepartmentCode());
        assertEquals(testProfessor.getDepartment().getDepartmentName(), department.getDepartmentName());
        assertEquals(testProfessor.getDepartment().getDepartmentAddress(), department.getDepartmentAddress());
        assertEquals(testProfessor.getDepartment().getDepartmentPhone(), department.getDepartmentPhone());

        testDepartment.setDepartmentCode("BIOL");
        testDepartment.setDepartmentName("Biology");
        testDepartment.setDepartmentAddress("Toronto");
        testDepartment.setDepartmentPhone("222-333-4444");

        assertFalse(testProfessor.getDepartment().getDepartmentCode() == department.getDepartmentCode());
        assertFalse(testProfessor.getDepartment().getDepartmentName() == department.getDepartmentName());
        assertFalse(testProfessor.getDepartment().getDepartmentAddress() == department.getDepartmentAddress());
        assertFalse(testProfessor.getDepartment().getDepartmentPhone() == department.getDepartmentPhone());

        assertFalse(testProfessor.briefInformation() == personInfo);
        assertTrue(testProfessor.briefInformation().equals("[BIOL] Bob"));
    }

    @Test
    void testUpdateProfessor() {
        Professor testProfessor = testDepartment.getProfessor(0);

        String personInfo = "User: " + testProfessor.getName();
        personInfo += "\nEmail: " + testProfessor.getEmail();
        personInfo += "\nPhone: " + testProfessor.getPhone();
        personInfo += "\nGender: " + testProfessor.getGenderType();
        personInfo += "\nDepartment: " + testProfessor.getDepartment().briefInformation();
        personInfo += "\nStart Date: " + testDate;

        assertEquals(testProfessor.toString(), personInfo);

        assertEquals(testDepartment.getNumberOfProfessors(), 1);
        testProfessor.setName("Jenna");
        testProfessor.setEmail("Jenna@gmail.com");
        testProfessor.setAddress("New York");
        testProfessor.setPhone("123-123-4444");
        testProfessor.setGenderType(GenderType.FEMALE);

        assertTrue(testProfessor.getStartDate().equals(testDate));
        assertFalse(testProfessor.getEndDate().equals(testDate));

        assertFalse(testProfessor.isRetired());
        testProfessor.retiring();
        assertTrue(testProfessor.isRetired());

        assertTrue(testProfessor.getStartDate().equals(testDate));
        assertTrue(testProfessor.getEndDate().equals(testDate));

        assertEquals("Jenna", testProfessor.getName());
        assertEquals("Jenna@gmail.com", testProfessor.getEmail());
        assertEquals("New York", testProfessor.getAddress());
        assertEquals("123-123-4444", testProfessor.getPhone());
        assertEquals(GenderType.FEMALE, testProfessor.getGenderType());
        assertEquals(testDate, testProfessor.getStartDate());
        assertEquals(testDate, testProfessor.getEndDate());

        personInfo = "User: " + testProfessor.getName();
        personInfo += "\nEmail: " + testProfessor.getEmail();
        personInfo += "\nPhone: " + testProfessor.getPhone();
        personInfo += "\nGender: " + testProfessor.getGenderType();
        personInfo += "\nDepartment: " + testProfessor.getDepartment().briefInformation();
        personInfo += "\nStart Date: " + testDate;
        personInfo += "\nEnd Date: " + testDate;

        assertEquals(testProfessor.toString(), personInfo);
    }
}

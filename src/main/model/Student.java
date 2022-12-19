package model;

import org.json.JSONObject;
import persistence.Writable;

import java.text.SimpleDateFormat;
import java.util.Date;

// Represents a student having parent class named Person
public class Student extends Person implements Writable {
    // REQUIRES: name != null
    //           email != null
    //           phone != null
    //           address != null
    //           genderType != null
    //           department != null
    //           startDate != null
    // EFFECTS: constructs a student with given name, email, phone, address, gender type, department, startDate,
    //          and endDate
    public Student(String name, String email, String phone, String address, GenderType genderType,
                   Department department, String startDate, String endDate) {
        super(name, email, phone, address, genderType, department, startDate, endDate);
    }

    // EFFECTS: records student's graduation date
    public void graduating() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        endDate = dateFormat.format(date);
        EventLog.getInstance().logEvent(new Event("Made the student graduate from the department"));
    }

    // EFFECTS: returns true if the student is graduated
    public boolean isGraduated() {
        return !endDate.isEmpty();
    }

    // EFFECTS: returns student information as a JSON
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", getName());
        json.put("email", getEmail());
        json.put("phone", getPhone());
        json.put("address", getAddress());
        json.put("genderType", getGenderType());
        json.put("startDate", getStartDate());
        json.put("endDate", getEndDate());

        return json;
    }
}

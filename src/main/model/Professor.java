package model;

import org.json.JSONObject;
import persistence.Writable;

import java.text.SimpleDateFormat;
import java.util.Date;

// Represents a professor having parent class named Person
public class Professor extends Person implements Writable {
    // REQUIRES: name != null
    //           email != null
    //           phone != null
    //           address != null
    //           genderType != null
    //           department != null
    //           startDate != null
    // EFFECTS: constructs a professor with given name, email, phone, address, gender type, department, startDate,
    //          and EndDate
    public Professor(String name, String email, String phone, String address, GenderType genderType,
                     Department department, String startDate, String endDate) {
        super(name, email, phone, address, genderType, department, startDate, endDate);
    }

    // EFFECTS: records professor's retirement date
    public void retiring() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        endDate = dateFormat.format(date);
        EventLog.getInstance().logEvent(new Event("Made the professor retire from the department"));
    }

    // EFFECTS: returns true if the professor is retired
    public boolean isRetired() {
        return !endDate.isEmpty();
    }

    // EFFECTS: returns professor information as a JSON
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


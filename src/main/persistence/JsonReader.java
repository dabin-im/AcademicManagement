package persistence;

import model.Department;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Event;
import model.EventLog;
import model.GenderType;
import org.json.*;

// Represents a reader that reads department from JSON data stored in file
public class JsonReader {
    private final String source;                // Json file name

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads department from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Department read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDepartment(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(text -> contentBuilder.append(text));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses department including professors and students from JSON object and returns it
    private Department parseDepartment(JSONObject jsonObject) {
        String departmentCode = jsonObject.getString("departmentCode");
        String departmentName = jsonObject.getString("departmentName");
        String departmentAddress = jsonObject.getString("departmentAddress");
        String departmentPhone = jsonObject.getString("departmentPhone");

        EventLog.getInstance().logEvent(new Event("Load the department from JSON"));
        Department department = new Department(departmentCode, departmentName, departmentAddress, departmentPhone);
        addProfessors(department, jsonObject);
        EventLog.getInstance().logEvent(new Event("Load professors into the department from JSON"));
        addStudents(department, jsonObject);
        EventLog.getInstance().logEvent(new Event("Load students into the department from JSON"));
        return department;
    }

    // MODIFIES: department
    // EFFECTS: parses professors from JSON object and adds them to department
    private void addProfessors(Department department, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("professors");
        for (Object json : jsonArray) {
            JSONObject professorInformation = (JSONObject) json;
            addProfessor(department, professorInformation);
        }
    }

    // MODIFIES: department
    // EFFECTS: parses students from JSON object and adds them to department
    private void addStudents(Department department, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("students");
        for (Object json : jsonArray) {
            JSONObject studentInformation = (JSONObject) json;
            addStudent(department, studentInformation);
        }
    }

    // MODIFIES: department
    // EFFECTS: parses professor from JSON object and adds it to department
    private void addProfessor(Department department, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String email = jsonObject.getString("email");
        String phone = jsonObject.getString("phone");
        String address = jsonObject.getString("address");
        GenderType genderType = GenderType.valueOf(jsonObject.getString("genderType"));
        String startDate = jsonObject.getString("startDate");
        String endDate = jsonObject.getString("endDate");
        department.addProfessor(name, email, phone, address, genderType, startDate, endDate);
    }

    // MODIFIES: department
    // EFFECTS: parses student from JSON object and adds it to department
    private void addStudent(Department department, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String email = jsonObject.getString("email");
        String phone = jsonObject.getString("phone");
        String address = jsonObject.getString("address");
        GenderType genderType = GenderType.valueOf(jsonObject.getString("genderType"));
        String startDate = jsonObject.getString("startDate");
        String endDate = jsonObject.getString("endDate");
        department.addStudent(name, email, phone, address, genderType, startDate, endDate);
    }
}

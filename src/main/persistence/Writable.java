package persistence;

import org.json.JSONObject;

// Represents a writable interface for saving data from department, professor, and student
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

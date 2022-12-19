package model;

/* Represents a person having a name, email, phone, address, gender type, start date, end date,
    and department information.
*/
public abstract class Person {
    private Department department;              // department
    private GenderType genderType;              // gender type
    private String name;                        // person name
    private String email;                       // email
    private String phone;                       // phone
    private String address;                     // address
    private final String startDate;             // start date of joining at university
    protected String endDate;                   // end date of graduation or retirement at university

    /* REQUIRES: name != null
                 email != null
                 phone != null
                 address != null
                 genderType != null
                 department != null
                 startDate != null
       EFFECTS: constructs a person with given name, email, phone, address, gender type, department,
                startDate and endDate.
    */
    public Person(String name, String email, String phone, String address, GenderType genderType,
                  Department department, String startDate, String endDate) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.genderType = genderType;
        this.department = department;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // EFFECTS: gets personal name
    public String getName() {
        return name;
    }

    // EFFECTS: gets personal email
    public String getEmail() {
        return email;
    }

    // EFFECTS: gets personal phone
    public String getPhone() {
        return phone;
    }

    // EFFECTS: gets personal address
    public String getAddress() {
        return address;
    }

    // EFFECTS: gets personal gender type
    public GenderType getGenderType() {
        return genderType;
    }

    // EFFECTS: gets department information
    public Department getDepartment() {
        return department;
    }

    // EFFECTS: gets start date of joining at university
    public String getStartDate() {
        return startDate;
    }

    // EFFECTS: gets date of retirement or graduation from subclass
    public String getEndDate() {
        return endDate;
    }

    // REQUIRES: name != null
    // MODIFIES: this
    // EFFECTS: updates name from person
    public void setName(String name) {
        this.name = name;
    }

    // REQUIRES: email != null
    // MODIFIES: this
    // EFFECTS: updates email address from person
    public void setEmail(String email) {
        this.email = email;
    }

    // REQUIRES: phone != null
    // MODIFIES: this
    // EFFECTS: updates phone number of individual
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // REQUIRES: address != null
    // MODIFIES: this
    // EFFECTS: changes to new address from old address
    public void setAddress(String address) {
        this.address = address;
    }

    // REQUIRES: genderType != null
    // MODIFIES: this
    // EFFECTS: changes to new genderType from old genderType
    public void setGenderType(GenderType genderType) {
        this.genderType = genderType;
    }

    // EFFECTS: provides brief person information
    public String briefInformation() {
        return "[" + this.department.getDepartmentCode() + "] " + this.name;
    }

    // EFFECTS: provides detailed person information
    @Override
    public String toString() {
        String personInfo = "User: " + this.name;
        personInfo += "\nEmail: " + this.email;
        personInfo += "\nPhone: " + this.phone;
        personInfo += "\nGender: " + this.genderType;
        personInfo += "\nDepartment: " + this.department.briefInformation();
        personInfo += "\nStart Date: " + startDate;

        if (!endDate.isEmpty()) {
            personInfo += "\nEnd Date: " + endDate;
        }
        return personInfo;
    }
}

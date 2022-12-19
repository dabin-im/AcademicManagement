package ui;

import model.Event;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

// University administration Graphic User Interface
public class AdministrationUI extends JFrame {
    private static final String JSON_STORE = "./data/department.json";      // the location where Json file stored
    private static final String EDIT_IMAGE_1 = "./data/edit_1.png";         // the location of edit_1 image file
    private static final String EDIT_IMAGE_2 = "./data/edit_2.png";         // the location of edit_2 image file
    private static final String REFRESH_IMAGE_1 = "./data/refresh_1.png";   // the location of refresh_1 image file
    private static final String REFRESH_IMAGE_2 = "./data/refresh_2.png";   // the location of refresh_1 image file
    private static final String EXIT_IMAGE_1 = "./data/exit_1.png";         // the location of exit_1 image file
    private static final String EXIT_IMAGE_2 = "./data/exit_2.png";         // the location of exit_2 image file
    private final JsonWriter jsonWriter;                                // Json writer object
    private final JsonReader jsonReader;                                // Json reader object
    private Department department;                                      // department information
    private DepartmentUI departmentUI;                                  // Department UI to manage information
    private JComboBox<GenderType> genderTypeBox;                        // ComboBox to choose gender type
    private JLabel departmentLabel;                                     // JLabel containing dept code and name
    private JLabel departmentNumberLabel;                               // JLabel containing dept number
    private JLabel departmentAddressLabel;                              // JLabel containing dept address
    private JTextField nameField;                                       // JTextField of person name
    private JTextField emailField;                                      // JTextField of personal email
    private JTextField phoneField;                                      // JTextField of personal phone number
    private JTextField addressField;                                    // JTextField of personal address
    private JTextField startDateField;                                  // JTextField of start date of person
    private JTextField endDateField;                                    // JTextField of end date of person
    private DefaultTableModel tableModel;                               // TableModel for list of professor or student
    private JButton buttonApply;                                        // JButton for managing retirement or graduation
    private JButton buttonAdd;                                          // JButton for adding professor of student
    private JRadioButton professorButton;                               // Radiobutton to view a list of professor
    private JRadioButton studentButton;                                 // Radiobutton to view a list of students
    private JTable table;                                               // Table that expresses individual information

    // EFFECTS: creates an administration and instantiates a departmentUI, jsonWriter and jsonReader
    //          Regarding department, creates new department or loads existing department from a file
    //          after that, initializes all controls from Administration UI including JFrame
    //          besides, registers for an event to give message whether a user want to save data or not
    public AdministrationUI() throws FileNotFoundException {
        departmentUI = new DepartmentUI(this);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        if (!loadProcess()) {
            dispose();
            return;
        }
        initializingControl();
        initializingJFrame();
    }

    // EFFECTS: instantiates JFrame which has all controls and panel on Administration UI
    private void initializingJFrame() {
        getContentPane().setLayout(null);
        setTitle("Academic Management Program");
        setBounds(100, 100, 1150, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(null, "HELLO, THERE\nDO YOU WANT TO SAVE DATA?",
                        "FILE LOAD", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    saveDepartment();
                }
                printEventLog();
                System.exit(0);
            }
        });
    }

    // EFFECTS: instantiates all controls on Administration UI including JPanel
    private void initializingControl() {
        radioButtonSetting();
        labelSetting();
        textFieldSetting();
        buttonSetting();
        comboBoxSetting();

        JPanel panel = new JPanel();
        panel.setBounds(20, 90, 700, 550);
        getContentPane().add(panel);
        panel.setLayout(null);

        tableSetting(panel);

    }

    // EFFECTS: instantiates comboBox control based on gender type
    private void comboBoxSetting() {
        genderTypeBox = new JComboBox<>();
        genderTypeBox.setModel(new DefaultComboBoxModel<>(GenderType.values()));
        genderTypeBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
        genderTypeBox.setBounds(925, 235, 135, 30);
        getContentPane().add(genderTypeBox);
    }

    // MODIFIES: this
    // EFFECTS: instantiates button controls to manage department information or personal information
    private void buttonSetting() {
        departmentInfoButtonSetting();
        personalInfoButtonSetting();

        buttonApply = new JButton("Apply");
        buttonApply.setBounds(1010, 475, 80, 35);
        getContentPane().add(buttonApply);
        buttonApply.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    return;
                }
                if (professorButton.isSelected()) {
                    department.getProfessor(row).retiring();

                } else {
                    department.getStudent(row).graduating();
                }
                refreshTable();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: instantiates general management button controls of Administration UI
    private void departmentInfoButtonSetting() {
        editButtonInitializing();
        refreshButtonInitializing();
        exitButtonInitializing();
    }

    // EFFECTS: instantiates exit button control and asks user to save the data
    private void exitButtonInitializing() {
        Image image = new ImageIcon(EXIT_IMAGE_1).getImage();
        JButton button = new JButton(new ImageIcon(image.getScaledInstance(22, 22,
                java.awt.Image.SCALE_SMOOTH)));
        image = new ImageIcon(EXIT_IMAGE_2).getImage();
        button.setRolloverIcon(new ImageIcon(image.getScaledInstance(22, 22, java.awt.Image.SCALE_SMOOTH)));
        button.setBounds(481, 48, 22, 22);
        getContentPane().add(button);
        button.setBackground(new Color(this.getBackground().getRGB()));
        button.setBorderPainted(false);
        button.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent event) {
                if (JOptionPane.showConfirmDialog(null, "HELLO, THERE\nDO YOU WANT TO SAVE DATA?",
                        "FILE LOAD", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    saveDepartment();
                }
                printEventLog();
                dispose();
            }
        });
    }

    // EFFECTS: prints out event logs that users did
    private void printEventLog() {
        System.out.println("=============== EVENT LOG ===============");
        EventLog eventLog = EventLog.getInstance();
        for (Event next : eventLog) {
            System.out.println(next.toString() + "\n");
        }
        System.out.println("=========================================");
    }

    // EFFECTS: instantiates refresh button control to reload the data of professors or students in department
    private void refreshButtonInitializing() {
        Image image = new ImageIcon(REFRESH_IMAGE_1).getImage();
        JButton button = new JButton(new ImageIcon(image.getScaledInstance(22, 22,
                java.awt.Image.SCALE_SMOOTH)));
        image = new ImageIcon(REFRESH_IMAGE_2).getImage();
        button.setRolloverIcon(new ImageIcon(image.getScaledInstance(22, 22, java.awt.Image.SCALE_SMOOTH)));
        button.setBounds(430, 48, 22, 22);
        button.setBackground(new Color(this.getBackground().getRGB()));
        button.setBorderPainted(false);
        getContentPane().add(button);
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                refreshTable();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: instantiates management button control of department information
    private void editButtonInitializing() {
        Image image = new ImageIcon(EDIT_IMAGE_1).getImage();
        JButton button = new JButton(new ImageIcon(image.getScaledInstance(22, 22,
                java.awt.Image.SCALE_SMOOTH)));
        image = new ImageIcon(EDIT_IMAGE_2).getImage();
        button.setRolloverIcon(new ImageIcon(image.getScaledInstance(22, 22, java.awt.Image.SCALE_SMOOTH)));
        button.setBounds(380, 47, 22, 22);

        button.setBackground(new Color(this.getBackground().getRGB()));
        button.setBorderPainted(false);
        getContentPane().add(button);
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                departmentUI.openUI();
                departmentLabel.setText(department.briefInformation());
                departmentNumberLabel.setText("Number: " + department.getDepartmentPhone());
                departmentAddressLabel.setText("Address: " + department.getDepartmentAddress());
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: instantiates three buttons to manage personal information of professor or student
    private void personalInfoButtonSetting() {
        buttonAdd = new JButton("Add");
        buttonAdd.setBounds(700, 605, 95, 35);
        getContentPane().add(buttonAdd);
        buttonAdd.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (textFieldValidation()) {
                    addPersonalInfo();
                }
            }
        });
        JButton button = new JButton("Modify");
        button.setBounds(850, 605, 95, 35);
        getContentPane().add(button);
        modifyButtonEventInitializing(button);

        deleteButtonInitializing();
    }

    // MODIFIES: this
    // EFFECTS: instantiates button control to delete specific student or professor in department
    private void deleteButtonInitializing() {
        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(1000, 605, 95, 35);
        getContentPane().add(btnDelete);
        btnDelete.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (table.getSelectedRow() == -1) {
                    return;
                }
                if (professorButton.isSelected()) {
                    department.removeProfessor(department.getProfessor(table.getSelectedRow()));
                } else {
                    department.removeStudent(department.getStudent(table.getSelectedRow()));
                }
                refreshTable();
            }
        });
    }

    // REQUIRES: nameField.getText() != null
    //           emailField.getText() != null
    //           phoneField.getText() != null
    //           addressField.getText() != null
    // MODIFIES: this
    // EFFECTS: registers for event changing to new personal information from old information of student or professor
    private void modifyButtonEventInitializing(JButton buttonModify) {
        buttonModify.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (table.getSelectedRow() == -1) {
                    return;
                }
                if (!textFieldValidation()) {
                    return;
                }

                if (professorButton.isSelected()) {
                    department.updateProfessorInfo(department.getProfessor(table.getSelectedRow()), nameField.getText(),
                            emailField.getText(), phoneField.getText(), addressField.getText(),
                            (GenderType) genderTypeBox.getModel().getSelectedItem());
                } else {
                    department.updateStudentInfo(department.getStudent(table.getSelectedRow()), nameField.getText(),
                            emailField.getText(), phoneField.getText(), addressField.getText(),
                            (GenderType) genderTypeBox.getModel().getSelectedItem());
                }
                refreshTable();
            }
        });
    }

    // REQUIRES: nameField.getText() != null
    //           emailField.getText() != null
    //           phoneField.getText() != null
    //           addressField.getText() != null
    // MODIFIES: department
    // EFFECTS: adds personal information with respect to person type
    private void addPersonalInfo() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        String startDate;
        GenderType gender = (GenderType) genderTypeBox.getModel().getSelectedItem();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        startDate = dateFormat.format(date);

        if (professorButton.isSelected()) {
            department.addProfessor(name, email, phone, address, gender, startDate, "");
        } else {
            department.addStudent(name, email, phone, address, gender, startDate, "");
        }
        refreshTable();
    }

    // EFFECTS: initializes JTextField objects on AdministrationUI to manage personal
    //          information of professor or student
    private void textFieldSetting() {
        nameField = new JTextField();
        nameField.setBounds(700, 235, 165, 30);
        getContentPane().add(nameField);

        emailField = new JTextField();
        emailField.setBounds(700, 335, 165, 30);
        getContentPane().add(emailField);

        phoneField = new JTextField();
        phoneField.setBounds(700, 435, 165, 30);
        getContentPane().add(phoneField);

        addressField = new JTextField();
        addressField.setBounds(700, 530, 165, 30);
        getContentPane().add(addressField);

        startDateField = new JTextField();
        startDateField.setEditable(false);
        startDateField.setBounds(925, 335, 165, 30);
        getContentPane().add(startDateField);

        endDateField = new JTextField();
        endDateField.setEditable(false);
        endDateField.setBounds(925, 435, 165, 30);
        getContentPane().add(endDateField);
    }

    // EFFECTS: initializes JLabel objects on AdministrationUI
    private void labelSetting() {
        deptInfoLabelSetting();
        personalInfoLabelSetting();

        JLabel labelStartDate = new JLabel("Start Date");
        labelStartDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        labelStartDate.setBounds(925, 305, 80, 25);
        getContentPane().add(labelStartDate);

        JLabel labelEndDate = new JLabel("End Date");
        labelEndDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        labelEndDate.setBounds(925, 405, 80, 25);
        getContentPane().add(labelEndDate);
    }

    // EFFECTS: initializes JLabel objects regarding personal information
    private void personalInfoLabelSetting() {
        JLabel labelName = new JLabel("Name");
        labelName.setFont(new Font("Tahoma", Font.PLAIN, 16));
        labelName.setBounds(700, 205, 50, 25);
        getContentPane().add(labelName);

        JLabel labelEmail = new JLabel("Email");
        labelEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
        labelEmail.setBounds(700, 305, 50, 25);
        getContentPane().add(labelEmail);

        JLabel labelPhone = new JLabel("Phone");
        labelPhone.setFont(new Font("Tahoma", Font.PLAIN, 16));
        labelPhone.setBounds(700, 405, 50, 25);
        getContentPane().add(labelPhone);

        JLabel labelAddress = new JLabel("Address");
        labelAddress.setFont(new Font("Tahoma", Font.PLAIN, 16));
        labelAddress.setBounds(700, 495, 70, 25);
        getContentPane().add(labelAddress);

        JLabel labelGender = new JLabel("Gender");
        labelGender.setFont(new Font("Tahoma", Font.PLAIN, 16));
        labelGender.setBounds(925, 205, 65, 25);
        getContentPane().add(labelGender);
    }

    // EFFECTS: initializes JLabel objects regarding department information
    private void deptInfoLabelSetting() {
        departmentLabel = new JLabel(department.briefInformation());
        departmentLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        departmentLabel.setBounds(35, 40, 365, 30);
        getContentPane().add(departmentLabel);

        departmentNumberLabel = new JLabel("Number: " + department.getDepartmentPhone());
        departmentNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        departmentNumberLabel.setBounds(570, 20, 300, 25);
        getContentPane().add(departmentNumberLabel);

        departmentAddressLabel = new JLabel("Address: " + department.getDepartmentAddress());
        departmentAddressLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        departmentAddressLabel.setBounds(570, 55, 550, 25);
        getContentPane().add(departmentAddressLabel);

        JLabel personInfoLabel = new JLabel("Personal Information");
        personInfoLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        personInfoLabel.setBounds(700, 155, 206, 23);
        getContentPane().add(personInfoLabel);
    }

    // MODIFIES: this
    // EFFECTS: instantiates radiobutton for student or professor and overrides action event
    //          to get list of professor or student
    private void radioButtonSetting() {
        professorButton = new JRadioButton("Professor");
        professorButton.setSelected(true);
        professorButton.setBounds(700, 95, 95, 30);
        professorButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent exception) {
                refreshTable();
            }
        });
        getContentPane().add(professorButton);

        studentButton = new JRadioButton("Student");
        studentButton.setBounds(850, 95, 95, 30);
        studentButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent exception) {
                refreshTable();
            }
        });
        getContentPane().add(studentButton);

        ButtonGroup group = new ButtonGroup();
        group.add(professorButton);
        group.add(studentButton);
    }

    // MODIFIES: this
    // EFFECTS: refreshes table rows after managing information of student or professor to show all personal information
    private void refreshTable() {
        tableModel.setRowCount(0);
        if (professorButton.isSelected()) {
            for (Professor professor : department.getProfessors()) {
                Object[] data = {professor.getName(), professor.getEmail(), professor.getPhone(),
                        professor.getAddress()};
                tableModel.addRow(data);
            }
        } else {
            for (Student student : department.getStudents()) {
                Object[] data = {student.getName(), student.getEmail(), student.getPhone(),
                        student.getAddress()};
                tableModel.addRow(data);
            }
        }
        nameField.setText("");
        emailField.setText("");
        addressField.setText("");
        phoneField.setText("");
        startDateField.setText("");
        endDateField.setText("");
        genderTypeBox.setSelectedItem(GenderType.MALE);
        buttonApply.setVisible(true);
        buttonAdd.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: instantiates table to show general information of professors or students
    private void tableSetting(JPanel panel) {
        String[] columnNames = {"Name", "Email", "Phone", "Address"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Sansserif", Font.BOLD, 15));
        table.setDefaultEditor(Object.class, null);
        tableMouseEvent();

        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setBackground(new Color(92, 179, 255));
        header.setForeground(new Color(255, 255, 255));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 5, 640, 520);
        panel.add(scrollPane);
        refreshTable();
    }

    // MODIFIES: this
    // EFFECTS: registers for mouse event of table to get specific information of professor or student
    private void tableMouseEvent() {
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                buttonAdd.setVisible(false);
                Person person;
                if (professorButton.isSelected()) {
                    person = department.getProfessor(table.getSelectedRow());
                } else {
                    person = department.getStudent(table.getSelectedRow());
                }
                if (person.getEndDate().isEmpty()) {
                    buttonApply.setVisible(true);
                } else {
                    buttonApply.setVisible(false);
                }
                nameField.setText(person.getName());
                emailField.setText(person.getEmail());
                phoneField.setText(person.getPhone());
                addressField.setText(person.getAddress());
                genderTypeBox.setSelectedItem(person.getGenderType());
                startDateField.setText(person.getStartDate());
                endDateField.setText(person.getEndDate());
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: returns true if loadProcess successfully proceeded while it results in false
    //          During the process, gives dialog message if a user wants to load saved file.
    //          Otherwise, creates new department information through department UI.
    //          If the user choose create mode and decides not to make new department, then gets original saved data
    private boolean loadProcess() {
        File file = new File(JSON_STORE);
        if (file.exists()) {
            if (JOptionPane.showConfirmDialog(null, "HELLO, THERE\nDO YOU WANT TO LOAD THE "
                    + "SAVED FILE?", "FILE LOAD", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                loadDepartment();
                return true;
            }
        }
        departmentUI.openUI();

        if (department == null) {
            if (file.exists()) {
                loadDepartment();
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    // EFFECTS: returns department information including students and professors
    public Department getDepartment() {
        return department;
    }

    // MODIFIES: this
    // EFFECTS: creates a department based on an input of user
    public void setDepartment(String deptCode, String deptName, String deptAddress, String deptPhone) {
        if (department == null) {
            department = new Department(deptCode, deptName, deptAddress, deptPhone);
        } else {
            department.setDepartmentCode(deptCode);
            department.setDepartmentName(deptName);
            department.setDepartmentAddress(deptAddress);
            department.setDepartmentPhone(deptPhone);
        }
    }

    // EFFECTS: returns true if !nameField.getText().isEmpty() && !emailField.getText().isEmpty()
    //          && !phoneField.getText().isEmpty() && !addressField.getText().isEmpty().
    //          Otherwise, returns false, and then gives user a proper error message
    //          it plays a role to check whether each textField has value or not.
    private boolean textFieldValidation() {
        if (nameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Input Name...",
                    "TextField Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (emailField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Input Email...",
                    "TextField Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (phoneField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Input Phone Number...",
                    "TextField Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (addressField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Input Address...",
                    "TextField Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // EFFECTS: saves the department to file if the file doesn't exist, creates new file and stores
    //          all information of department
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
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE
                    + "\nCreate new department", "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

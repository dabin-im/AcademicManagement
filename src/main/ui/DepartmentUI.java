package ui;

import model.Department;
import model.Event;
import model.EventLog;

import javax.swing.*;
import java.awt.event.ActionEvent;

// Department Management Graphic User Interface
public class DepartmentUI extends JDialog {
    private AdministrationUI administrationUI;      // administration UI including department information
    private JTextField deptCodeText;                // JTextField of department code
    private JTextField deptNameText;                // JTextField of department name
    private JTextField deptAddressText;             // JTextField of department address
    private JTextField deptNumberText;              // JTextField of department number
    private JButton createButton;                   // JButton for updating department information

    // REQUIRES: administrationUI != null
    // EFFECTS: instantiates Department UI to manage department information
    public DepartmentUI(AdministrationUI administrationUI) {
        super(administrationUI, "Department Information", ModalityType.APPLICATION_MODAL);
        setSize(350, 400);
        setLayout(null);

        this.administrationUI = administrationUI;

        labelSetting();
        textFieldSetting();
        buttonSetting();

        setResizable(false);
        setLocationRelativeTo(null);
    }

    // EFFECTS: instantiates textField to edit department information
    private void textFieldSetting() {
        deptCodeText = new JTextField(10);
        deptCodeText.setBounds(180, 45, 120, 25);

        deptNameText = new JTextField(10);
        deptNameText.setBounds(180, 95, 120, 25);

        deptAddressText = new JTextField(10);
        deptAddressText.setBounds(180, 145, 120, 25);

        deptNumberText = new JTextField(10);
        deptNumberText.setBounds(180, 195, 120, 25);

        add(deptCodeText);
        add(deptNameText);
        add(deptAddressText);
        add(deptNumberText);
    }

    // REQUIRES: !deptCodeText.getText().isEmpty() && !deptNameText.getText().isEmpty() &&
    //           !deptAddressText.getText().isEmpty() && !deptNumberText.getText().isEmpty()
    // MODIFIES: administrationUI
    // EFFECTS: instantiates button control to create or update department
    private void buttonSetting() {
        submitButtonSetting();
        JButton exitButton = new JButton("EXIT");
        exitButton.setBounds(200, 280, 100, 25);
        exitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                dispose();
            }
        });
        add(createButton);
        add(exitButton);
    }

    // EFFECTS: instantiates submit button that plays roles of creating or updating department
    private void submitButtonSetting() {
        createButton = new JButton("CREATE");
        createButton.setBounds(20, 280, 100, 25);
        createButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (!textFieldValidation()) {
                    return;
                }
                administrationUI.setDepartment(deptCodeText.getText(), deptNameText.getText(),
                        deptAddressText.getText(), deptNumberText.getText());
                if (createButton.getText().equals("CREATE")) {
                    EventLog.getInstance().logEvent(new Event("Create a department"));
                } else {
                    EventLog.getInstance().logEvent(new Event("Update the department"));
                }
                dispose();
            }
        });
    }

    // EFFECTS: returns true if !deptCodeText.getText().isEmpty() && !deptNameText.getText().isEmpty()
    //          && !deptAddressText.getText().isEmpty() && !deptNumberText.getText().isEmpty()
    //          Otherwise, returns false, and then gives user a proper error message
    //          it plays a role to check whether each textField has value or not.
    private boolean textFieldValidation() {
        if (deptCodeText.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Input Code...",
                    "TextField Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (deptNameText.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Input Name...",
                    "TextField Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (deptAddressText.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Input Address...",
                    "TextField Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (deptNumberText.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Input Number...",
                    "TextField Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // EFFECTS: instantiates JLabel to guide a user to input appropriate textField
    private void labelSetting() {
        JLabel deptCodeLabel = new JLabel("Department Code:");
        deptCodeLabel.setBounds(20, 30, 200, 50);

        JLabel deptNameLabel = new JLabel("Department Name:");
        deptNameLabel.setBounds(20, 80, 200, 50);

        JLabel deptAddressLabel = new JLabel("Department Address:");
        deptAddressLabel.setBounds(20, 130, 200, 50);

        JLabel deptNumberLabel = new JLabel("Department Number:");
        deptNumberLabel.setBounds(20, 180, 200, 50);

        add(deptNameLabel);
        add(deptCodeLabel);
        add(deptAddressLabel);
        add(deptNumberLabel);
    }

    // MODIFIES: this
    // EFFECTS: opens instantiated department UI to manage department
    public void openUI() {
        if (administrationUI.getDepartment() != null) {
            createButton.setText("Update");
            Department department = administrationUI.getDepartment();
            deptCodeText.setText(department.getDepartmentCode());
            deptNameText.setText(department.getDepartmentName());
            deptAddressText.setText(department.getDepartmentAddress());
            deptNumberText.setText(department.getDepartmentPhone());
        }
        setVisible(true);
    }
}

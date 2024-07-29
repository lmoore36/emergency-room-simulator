package er.functions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ERAppSwing extends JFrame {
    private SimpleEmergencyRoom simpleER;
    private DefaultListModel<String> patientListModel;

    public ERAppSwing() {
        simpleER = new SimpleEmergencyRoom();
        patientListModel = new DefaultListModel<>();

        setTitle("Emergency Room Queue");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create UI elements
        JTextField patientNameInput = new JTextField(15);
        JTextField patientPriorityInput = new JTextField(15);
        JButton addButton = new JButton("Add Patient");
        JButton dequeueButton = new JButton("Dequeue Patient");
        JList<String> patientList = new JList<>(patientListModel);

        // Create Panels
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(patientNameInput);
        inputPanel.add(new JLabel("Priority:"));
        inputPanel.add(patientPriorityInput);
        inputPanel.add(addButton);

        JPanel controlPanel = new JPanel();
        controlPanel.add(dequeueButton);

        // Add UI elements to frame
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(patientList), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        // Add button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = patientNameInput.getText();
                int priority;
                try {
                    priority = Integer.parseInt(patientPriorityInput.getText());
                    simpleER.addPatient(name, priority);
                    updatePatientList();
                    patientNameInput.setText("");
                    patientPriorityInput.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ERAppSwing.this, "Please enter a valid priority number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dequeueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpleER.dequeue();
                updatePatientList();
            }
        });
    }

    private void updatePatientList() {
        patientListModel.clear();
        for (Patient patient : simpleER.getPatients()) {
            patientListModel.addElement(patient.getValue() + ", Priority: " + patient.getPriority());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ERAppSwing().setVisible(true);
            }
        });
    }
}
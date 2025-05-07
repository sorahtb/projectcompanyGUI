import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainMenu extends JFrame {
    private Connection connection;
    private static MainMenu instance;

    public MainMenu() {
        setTitle("Enterprise Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1));

        JButton addUserButton = new JButton("Add New User");
        addUserButton.addActionListener(new AddUserButtonListener());

        JButton addDepartmentButton = new JButton("Add New Department");
        addDepartmentButton.addActionListener(new AddDepartmentButtonListener());

        JButton showAllUsersButton = new JButton("Show All Users");
        showAllUsersButton.addActionListener(new ShowAllUsersButtonListener());

        JButton showUsersByDepartmentButton = new JButton("Show Users by Department");
        showUsersByDepartmentButton.addActionListener(new ShowUsersByDepartmentButtonListener());

        JButton deleteRecordButton = new JButton("Delete User/Department");
        deleteRecordButton.addActionListener(new DeleteRecordButtonListener());

        mainPanel.add(addUserButton);
        mainPanel.add(addDepartmentButton);
        mainPanel.add(showAllUsersButton);
        mainPanel.add(showUsersByDepartmentButton);
        mainPanel.add(deleteRecordButton);

        add(mainPanel);
        setVisible(true);


        try {
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String password = "7557";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static MainMenu getInstance() {
        if (instance == null) {
            instance = new MainMenu();
        }
        return instance;
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    private class AddUserButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String fullName = JOptionPane.showInputDialog("Enter full name:");
            String position = JOptionPane.showInputDialog("Enter position:");
            double salary = Double.parseDouble(JOptionPane.showInputDialog("Enter salary:"));
            int departmentId = Integer.parseInt(JOptionPane.showInputDialog("Enter department ID:"));

            try {
                String insertSQL = "INSERT INTO employees (full_name, position, salary, department_id) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
                preparedStatement.setString(1, fullName);
                preparedStatement.setString(2, position);
                preparedStatement.setDouble(3, salary);
                preparedStatement.setInt(4, departmentId);
                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: Failed to add user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class AddDepartmentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String departmentName = JOptionPane.showInputDialog("Enter department name:");

            try {
                String insertSQL = "INSERT INTO departments (department_name) VALUES (?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
                preparedStatement.setString(1, departmentName);
                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Department added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: Failed to add department.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class ShowAllUsersButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String selectSQL = "SELECT * FROM employees";
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
                ResultSet resultSet = preparedStatement.executeQuery();

                StringBuilder usersInfo = new StringBuilder();
                while (resultSet.next()) {
                    usersInfo.append("ID: ").append(resultSet.getInt("employee_id")).append(", ")
                            .append("Name: ").append(resultSet.getString("full_name")).append(", ")
                            .append("Position: ").append(resultSet.getString("position")).append(", ")
                            .append("Salary: ").append(resultSet.getDouble("salary")).append("\n")
                            .append("Department ID: ").append(resultSet.getInt("department_id")).append(", ");
                }

                JOptionPane.showMessageDialog(null, usersInfo.toString(), "All Users", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: Failed to retrieve users.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class ShowUsersByDepartmentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int departmentId = Integer.parseInt(JOptionPane.showInputDialog("Enter department ID:"));

            try {
                String selectSQL = "SELECT * FROM employees WHERE department_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
                preparedStatement.setInt(1, departmentId);
                ResultSet resultSet = preparedStatement.executeQuery();

                StringBuilder usersInfo = new StringBuilder();
                while (resultSet.next()) {
                    usersInfo.append("ID: ").append(resultSet.getInt("employee_id")).append(", ")
                            .append("Name: ").append(resultSet.getString("full_name")).append(", ")
                            .append("Position: ").append(resultSet.getString("position")).append(", ")
                            .append("Salary: ").append(resultSet.getDouble("salary")).append("\n")
                            .append("Department ID: ").append(resultSet.getInt("department_id")).append(", ");
                }

                JOptionPane.showMessageDialog(null, usersInfo.toString(), "Users in Department", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: Failed to retrieve users.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class DeleteRecordButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String recordType = JOptionPane.showInputDialog("Enter 'user' to delete a user, or 'department' to delete a department:");

            if (recordType.equalsIgnoreCase("user")) {
                int userId = Integer.parseInt(JOptionPane.showInputDialog("Enter user ID:"));
                try {
                    String deleteSQL = "DELETE FROM employees WHERE employee_id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
                    preparedStatement.setInt(1, userId);
                    int deletedRows = preparedStatement.executeUpdate();

                    if (deletedRows > 0) {
                        JOptionPane.showMessageDialog(null, "User deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: Failed to delete user.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (recordType.equalsIgnoreCase("department")) {
                int departmentId = Integer.parseInt(JOptionPane.showInputDialog("Enter department ID:"));
                try {
                    String deleteSQL = "DELETE FROM departments WHERE department_id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
                    preparedStatement.setInt(1, departmentId);
                    int deletedRows = preparedStatement.executeUpdate();

                    if (deletedRows > 0) {
                        JOptionPane.showMessageDialog(null, "Department deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Department not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: Failed to delete department.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter 'user' or 'department'.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}


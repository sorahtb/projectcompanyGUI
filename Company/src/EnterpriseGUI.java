import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EnterpriseGUI extends JFrame {

    private Connection connection;

    public EnterpriseGUI() {
        super("Enterprise Structure");


        try {
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String password = "7557";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database.");

            String createEmployeesTableSQL = "CREATE TABLE IF NOT EXISTS employees (" +
                    "id SERIAL PRIMARY KEY," +
                    "full_name TEXT," +
                    "position TEXT," +
                    "salary REAL)";
            connection.createStatement().executeUpdate(createEmployeesTableSQL);


            String createDepartmentsTableSQL = "CREATE TABLE IF NOT EXISTS departments (" +
                    "id SERIAL PRIMARY KEY," +
                    "department_name TEXT)";
            connection.createStatement().executeUpdate(createDepartmentsTableSQL);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(new AddUserButtonListener());

        JButton addDepartmentButton = new JButton("Add Department");
        addDepartmentButton.addActionListener(new AddDepartmentButtonListener());

        JButton deleteUserButton = new JButton("Delete User");
        deleteUserButton.addActionListener(new DeleteUserButtonListener());

        JButton deleteDepartmentButton = new JButton("Delete Department");
        deleteDepartmentButton.addActionListener(new DeleteDepartmentButtonListener());

    }

    class AddUserButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    class AddDepartmentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    class DeleteUserButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    class DeleteDepartmentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

}

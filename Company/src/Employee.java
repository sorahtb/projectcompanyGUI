import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Employee {
    private int employeeId;
    private String fullName;
    private String position;
    private BigDecimal salary;
    private int departmentId;

    public Employee() {
    }

    public Employee(String fullName, String position, BigDecimal salary, int departmentId) {
        this.fullName = fullName;
        this.position = position;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    // Метод для сохранения сотрудника в базе данных
    public void save(Connection connection) throws SQLException {
        String query = "INSERT INTO employees (full_name, position, salary, department_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, fullName);
            statement.setString(2, position);
            statement.setBigDecimal(3, salary);
            statement.setInt(4, departmentId);
            statement.executeUpdate();
        }
    }

    public void update(Connection connection) throws SQLException {
        String query = "UPDATE employees SET full_name = ?, position = ?, salary = ?, department_id = ? WHERE employee_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, fullName);
            statement.setString(2, position);
            statement.setBigDecimal(3, salary);
            statement.setInt(4, departmentId);
            statement.setInt(5, employeeId);
            statement.executeUpdate();
        }
    }

    public void delete(Connection connection) throws SQLException {
        String query = "DELETE FROM employees WHERE employee_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            statement.executeUpdate();
        }
    }

    public static Employee findById(Connection connection, int employeeId) throws SQLException {
        String query = "SELECT * FROM employees WHERE employee_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.setEmployeeId(resultSet.getInt("employee_id"));
                    employee.setFullName(resultSet.getString("full_name"));
                    employee.setPosition(resultSet.getString("position"));
                    employee.setSalary(resultSet.getBigDecimal("salary"));
                    employee.setDepartmentId(resultSet.getInt("department_id"));
                    return employee;
                }
            }
        }
        return null;
    }
}

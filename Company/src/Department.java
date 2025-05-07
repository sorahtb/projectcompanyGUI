
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Department {
    private int departmentId;
    private String name;

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void save(Connection connection) throws SQLException {
        String query = "INSERT INTO departments (department_name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.executeUpdate();
        }
    }

    public void update(Connection connection) throws SQLException {
        String query = "UPDATE departments SET department_name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setInt(2, departmentId);
            statement.executeUpdate();
        }
    }

    public void delete(Connection connection) throws SQLException {
        String query = "DELETE FROM departments WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, departmentId);
            statement.executeUpdate();
        }
    }

    public static Department getById(Connection connection, int id) throws SQLException {
        String query = "SELECT * FROM departments WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Department department = new Department();
                    department.setDepartmentId(resultSet.getInt("id"));
                    department.setName(resultSet.getString("department_name"));
                    return department;
                }
            }
        }
        return null;
    }
}


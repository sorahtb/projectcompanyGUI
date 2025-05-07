import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Company {
    private String name;
    private List<Department> departments;

    public Company() {
        departments = new ArrayList<>();
    }

    public Company(String name) {
        this.name = name;
        departments = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }

    public void removeDepartment(Department department) {
        departments.remove(department);
    }

    public void save(Connection connection) throws SQLException {

        String query = "INSERT INTO companies (company_name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.executeUpdate();
        }

        for (Department department : departments) {
            department.save(connection);
        }
    }
}

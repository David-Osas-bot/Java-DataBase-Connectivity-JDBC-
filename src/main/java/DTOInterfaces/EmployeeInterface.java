/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DTOInterfaces;

/**
 *
 * @author DELL
 */
import DTOFolders.Employee;
import java.sql.SQLException;
import java.util.List;

public interface EmployeeInterface {
    public Employee getEmployee(String id) throws SQLException;
    public void sendEmployee(Employee em) throws SQLException;
    public boolean deleteEmployee(String id) throws SQLException;
    public boolean updateEmployee(String id, Employee em) throws SQLException;
    public List<Employee> getAllEmployee();
}

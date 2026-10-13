/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOImplementation;

import DTOFolders.Employee;
import DTOInterfaces.EmployeeInterface;
import bdconnect.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class EmployeeImplementation implements EmployeeInterface {

//    DBConnect connect = new DBConnect();
//    Connection con2 = connect.getDBConnection();
    Connection con = DBConnect.getDBConnection();
    PreparedStatement sta;
    ResultSet result;
    String sqlSend = "insert into employee (empid, firstname, lastname, age) values(?,?,?,?)";
    String sqlGetEmployee = "select * from employee where empid = ?";
    String sqlUpdate = "update employee set firstname = ?, lastname = ?, age = ? where id = ?";
    String sqlDelete = "delete from employee where empid = ?";
    String sqlGetAll = "select * from employee";

    @Override
    public Employee getEmployee(String id) throws SQLException {
        Employee emp = null;
        try (PreparedStatement ps = con.prepareStatement(sqlGetEmployee)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    emp = new Employee();
                    emp.setId(rs.getString(1));
                    emp.setFirstname(rs.getString(2));
                    emp.setLastname(rs.getString(3));
                    emp.setAge(rs.getInt(4));
                }
            }
        }
        return emp;
    }

    @Override
    public void sendEmployee(Employee em) throws SQLException {
        try {
            sta = con.prepareStatement(sqlSend);
            sta.setString(1, em.getId());
            sta.setString(2, em.getFirstname());
            sta.setString(3, em.getLastname());
            sta.setInt(4, em.getAge());

            sta.executeUpdate();
        } catch (SQLException sq) {
            sq.printStackTrace();
        }

    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(sqlDelete)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateEmployee(String id, Employee em) {
        try {
            sta = con.prepareStatement(sqlUpdate);
            sta.setString(1, id);
            sta.setString(2, em.getFirstname());
            sta.setString(3, em.getLastname());
            sta.setInt(4, em.getAge());

            sta.executeUpdate();
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Employee> getAllEmployee() {
        List<Employee> le = new ArrayList();
        try {
            sta = con.prepareStatement(sqlGetAll);
            result = sta.executeQuery();
            while (result.next()) {
                Employee em = new Employee();
                em.setId(result.getString(1));
                em.setFirstname(result.getString(2));
                em.setLastname(result.getString(3));
                em.setAge(result.getInt(4));

                le.add(em);

            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }

        return le;
    }

}

package com.mycompany.frontendconnectionmaven.resources;

import DTOFolders.Employee;
import DTOImplementation.EmployeeImplementation;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author
 */
@Path("employees")
public class JakartaEE11Resource {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@PathParam("id") String id) {

        try {
            EmployeeImplementation empImpl = new EmployeeImplementation();
            Employee emp = empImpl.getEmployee(id);

            if (emp != null) {
                return Response.ok(emp).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Employee with id " + " " + id + " not found")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("Error retrieving employee: " + e.getMessage())
                    .build();
        }
    }

    @Path("/send")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendEmployee(Employee em) {
        try {
            // 1. Validate that the incoming object is not null first
//            if (em == null) {
//                return Response.status(Response.Status.BAD_REQUEST)
//                        .entity("Invalid employee data: Request body is missing or empty.")
//                        .build();
//            }

            EmployeeImplementation empImpl = new EmployeeImplementation();
            empImpl.sendEmployee(em);

            return Response.ok(em).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("Error sending employees: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteEmpoyee(@PathParam("id") String id) {

        try {
            EmployeeImplementation empImpl = new EmployeeImplementation();
            empImpl.deleteEmployee(id);
            return Response.ok("Delete request processed for employee " + id)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("Can't delete employees: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("id") String id, Employee emp) throws SQLException {
        try {
            // 1. Prevent NullPointerException if body is missing
            if (emp == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid employee data: Request body is missing.")
                        .build();
            }

            EmployeeImplementation empImpl = new EmployeeImplementation();
            emp.setId(id);

            // 2. Assume updateEmployee returns a boolean (true if updated, false if ID not found)
            boolean isUpdated = empImpl.updateEmployee(id, emp);

            if (isUpdated) {
                return Response.ok(emp).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Unable to update: Employee with ID " + id + " not found.")
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("Can't update employee: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEmployee() {
        try {
            EmployeeImplementation empImpl = new EmployeeImplementation();
            List<Employee> lisEmp = empImpl.getAllEmployee();

            if (lisEmp != null && !lisEmp.isEmpty()) {
                return Response.ok(lisEmp).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Can't get total employees")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("Can't get total employees: " + e.getMessage())
                    .build();
        }
    }

}

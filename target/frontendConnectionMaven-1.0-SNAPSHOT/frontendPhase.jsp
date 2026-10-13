<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, java.util.List"%>
<%@page import="DTOFolders.Employee, DTOImplementation.EmployeeImplementation"%>
<%!
    // Small helper: stops user text from breaking the HTML.
    private static String esc(Object o) {
        if (o == null) {
            return "";
        }
        return String.valueOf(o).replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
%>
<%
    request.setCharacterEncoding("UTF-8");

    // 1. Read what the browser sent
    String action = request.getParameter("action");   // create, update, delete, find, edit
    String id = request.getParameter("id");
    String first = request.getParameter("firstname");
    String last = request.getParameter("lastname");
    String age = request.getParameter("age");

    String message = "";
    Employee formEmployee = null;                     // used to fill the form when editing
    List<Employee> list = null;                       // rows shown in the table

    // This one object gives us every database method
    EmployeeImplementation dao = new EmployeeImplementation();

    try {
        // 2. Decide which DAO method to call

        if ("create".equals(action)) {
            Employee emp = new Employee(first, last, Integer.parseInt(age), id);
            dao.sendEmployee(emp);                     // >>> CALLS sendEmployee   (INSERT)
            message = "Employee saved.";

        } else if ("update".equals(action)) {
            Employee emp = new Employee(first, last, Integer.parseInt(age), id);
            dao.updateEmployee(id, emp);               // >>> CALLS updateEmployee (UPDATE)
            message = "Employee updated.";

        } else if ("delete".equals(action)) {
            dao.deleteEmployee(id);                    // >>> CALLS deleteEmployee (DELETE)
            message = "Employee deleted.";

        } else if ("find".equals(action)) {
            Employee found = dao.getEmployee(id);      // >>> CALLS getEmployee    (SELECT one)
            list = new ArrayList<Employee>();
            if (found != null) {
                list.add(found);
            } else {
                message = "No employee with ID " + id;
            }

        } else if ("edit".equals(action)) {
            formEmployee = dao.getEmployee(id);        // >>> CALLS getEmployee    (fills the form)
        }

        // 3. Unless we are showing one found employee, load everyone
        if (list == null) {
            list = dao.getAllEmployee();               // >>> CALLS getAllEmployee (SELECT all)
        }

    } catch (Exception e) {
        message = "Error: " + e.getMessage();
        if (list == null) {
            list = new ArrayList<Employee>();
        }
    }

    // Are we adding or editing?
    boolean editing = (formEmployee != null);
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Employees</title>
    </head>
    <body>

        <h1>Employees</h1>

        <% if (!message.isEmpty()) {%>
        <p><b><%= esc(message)%></b></p>
        <% }%>

        <!-- ========== ADD / UPDATE FORM ========== -->
        <h2><%= editing ? "Edit employee" : "Add employee"%></h2>

        <form action="EmployeeServlet" method="post">
            <!-- action = update when editing, create when adding -->
            <input type="hidden" name="action" value="<%= editing ? "update" : "create"%>">

            ID: <input type="text" name="id" required
                       value="<%= editing ? esc(formEmployee.getId()) : ""%>"
                       <%= editing ? "readonly" : ""%>><br><br>

            First name: <input type="text" name="firstname" required
                               value="<%= editing ? esc(formEmployee.getFirstname()) : ""%>"><br><br>

            Last name: <input type="text" name="lastname" required
                              value="<%= editing ? esc(formEmployee.getLastname()) : ""%>"><br><br>

            Age: <input type="number" name="age" required
                        value="<%= editing ? formEmployee.getAge() : ""%>"><br><br>

            <input type="submit" value="<%= editing ? "Update" : "Save"%>">
            <% if (editing) { %> <a href="employeeSimple.jsp">Cancel</a> <% } %>
        </form>

        <!-- ========== FIND FORM ========== -->
        <h2>Find by ID</h2>

        <form action="employeeSimple" method="get">
            <input type="hidden" name="action" value="find">
            <input type="text" name="id" required>
            <input type="submit" value="Find">
            <a href="employeeSimple.jsp">Show all</a>
        </form>

        <!-- ========== TABLE ========== -->
        <h2>All employees</h2>

        <table border="1" cellpadding="6">
            <tr>
                <th>ID</th>
                <th>First name</th>
                <th>Last name</th>
                <th>Age</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>

            <% for (Employee e : list) {%>
            <tr>
                <td><%= esc(e.getId())%></td>
                <td><%= esc(e.getFirstname())%></td>
                <td><%= esc(e.getLastname())%></td>
                <td><%= e.getAge()%></td>

                <!-- Edit: a link (GET) -->
                <td><a href="EmployeeServlet?action=edit&id=<%= esc(e.getId())%>">Edit</a></td>

                <!-- Delete: a small form (POST), because a link must never delete data -->
                <td>
                    <form action="EmployeeServlet" method="post">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="<%= esc(e.getId())%>">
                        <input type="submit" value="Delete">
                    </form>
                </td>
            </tr>
            <% }%>
        </table>

    </body>
</html>

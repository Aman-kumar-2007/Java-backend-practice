package org.example;

import java.io.FileInputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    static String url = "jdbc:mysql://localhost:3306/company";
    static String user = "root";
    static String pass = "Aman@2007";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user,pass);
    }
    static void insertEmployee() throws SQLException {
        Connection con = getConnection();
        String sql = "INSERT INTO employee(name,email,salary,department,joining_date)" +
                        "VALUES(?,?,?,?,?)";

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name : ");
        String name = sc.next();
        System.out.print("Enter email : ");
        String email = sc.next();
        System.out.print("Enter salary : ");
        Double salary = sc.nextDouble();
        System.out.print("Enter department : ");
        String depart = sc.next();
        LocalDate today = LocalDate.now();

        PreparedStatement ps = con.prepareStatement(sql);

        try {
            ps.setString(1,name);
            ps.setString(2,email);
            ps.setDouble(3,salary);
            ps.setString(4,depart);
            ps.setDate(5,java.sql.Date.valueOf(today));

            int row = ps.executeUpdate();

            if(row != 1){
                throw new SQLException();
            }
            System.out.println("Employee Details Inserted successfully !!");

        } catch (Exception e) {
            System.out.println("Employee Not inserted ( Error )");
        } finally {
            ps.close();
            con.close();
        }
    }

    static void viewAllEmployee() throws SQLException {
        Connection con = getConnection();
        String sql = "SELECT * FROM Employee";
        PreparedStatement ps = con.prepareStatement(sql);
        try {
            ResultSet employees = ps.executeQuery();

            while(employees.next()){
                System.out.println(
                                employees.getInt("id") + " " +
                                employees.getString("name") + " " +
                                employees.getString("email") + " " +
                                employees.getDouble("salary") + " " +
                                employees.getString("department")
                );
            }
        } catch (Exception e){
            System.out.println("Didn't get Employees Data");
        }
    }

    static void findEmployeeByid() throws SQLException {
        Connection con = getConnection();
        String sql = "SELECT * FROM Employee WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        Scanner sc =  new Scanner(System.in);
        System.out.print("Enter ID : ");
        int id = sc.nextInt();

        try {
            ps.setInt(1,id);
            ResultSet employees = ps.executeQuery();

            if (employees.next()) {
                System.out.println(
                        employees.getInt("id") + " " +
                                employees.getString("name") + " " +
                                employees.getString("email") + " " +
                                employees.getDouble("salary") + " " +
                                employees.getString("department")
                );
            } else {
                System.out.println("Employee not found");
            }

            System.out.println("Employee Found !!");

        } catch (Exception e){
            System.out.println("No Employee by this ID");
        }
    }

    static void findEmployeeByDepart() throws SQLException {
        Connection con = getConnection();
        String sql = "SELECT * FROM Employee WHERE department = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        Scanner sc =  new Scanner(System.in);

        System.out.print("Enter Department : ");
        String depart = sc.next();

        try {
            ps.setString(1,depart);

            ResultSet employees = ps.executeQuery();

            while(employees.next()){
                System.out.println(
                        employees.getInt("id") + " " +
                                employees.getString("name") + " " +
                                employees.getString("email") + " " +
                                employees.getDouble("salary") + " " +
                                employees.getString("department")
                );
            }

            System.out.println("Employee Found !!");

        } catch (Exception e){
            System.out.println("No Employee by this ID");
        }
    }

    static void updateEmployee() throws SQLException {
        Connection con = getConnection();
        Scanner sc = new Scanner(System.in);
        System.out.println(
                "Select through number :\n" +
                        "1. Employee name\n" +
                        "2. Employee email\n" +
                        "3. Employee department\n" +
                        "4. Employee salary\n" +
                        "5. Exit\n"
        );
        System.out.print("Enter number : ");
        int n = sc.nextInt();
        System.out.print("Enter id : ");
        int id = sc.nextInt();

        int row = 0;
        switch (n) {
            case 1:
                System.out.print("Enter name : ");
                String name = sc.next();
                String sql1 = "UPDATE Employee SET name = ? WHERE id = ?";
                PreparedStatement ps1 = con.prepareStatement(sql1);
                ps1.setString(1,name);
                ps1.setInt(2,id);
                row = ps1.executeUpdate();
                break;
            case 2:
                System.out.print("Enter Email : ");
                String email = sc.next();
                String sql2 = "UPDATE Employee SET email = ? WHERE id = ?";
                PreparedStatement ps2 = con.prepareStatement(sql2);
                ps2.setString(1,email);
                ps2.setInt(2,id);
                row = ps2.executeUpdate();
                break;
            case 3:
                System.out.print("Enter Department : ");
                String department = sc.next();
                String sql3 = "UPDATE Employee SET department = ? WHERE id = ?";
                PreparedStatement ps3 = con.prepareStatement(sql3);
                ps3.setString(1,department);
                ps3.setInt(2,id);
                row = ps3.executeUpdate();
                break;
            case 4:
                System.out.print("Enter Salary : ");
                Double salary = sc.nextDouble();
                String sql4 = "UPDATE Employee SET salary = ? WHERE id = ?";
                PreparedStatement ps4 = con.prepareStatement(sql4);
                ps4.setDouble(1,salary);
                ps4.setInt(2,id);
                row = ps4.executeUpdate();
                break;
            case 5:
                return;
            default:
                System.out.println("Didn't understand your response");
        }

        if (row == 1) {
            System.out.println("Employee updated successfully");
        } else {
            System.out.println("Employee not found");
        }


    }

    static void deleteEmployee() throws SQLException {
        Connection con = getConnection();
        String sql = "DELETE FROM Employee WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Id : ");
        int id = sc.nextInt();

        try {
            ps.setInt(1,id);;
            int row = ps.executeUpdate();

            if(row != 1) {
                throw new RuntimeException();
            }

            System.out.println("Employee Deleted successfully !!");

        } catch (Exception e){
            System.out.println("Employee doesn't exists");
        }
    }

    static void addProfileImage() throws SQLException {
        Connection con = getConnection();
        String sql = "UPDATE employee SET profile_image = ? where id = ?";

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Id : ");
        int id = sc.nextInt();

        PreparedStatement ps = con.prepareStatement(sql);
        try {
            FileInputStream pic = new FileInputStream("src/main/java/org/example/profile.png");
            ps.setBinaryStream(1,pic);
            ps.setInt(2,id);

            int row = ps.executeUpdate();

            if(row != 1){
                throw new RuntimeException();
            }

            System.out.println("Profile pic added !!");
        } catch (Exception e) {
            System.out.println("Image upload failed: " + e.getMessage());
        }
    }

    static void processSalary() throws SQLException {
        Connection con = getConnection();
        Scanner sc =  new Scanner(System.in);
        System.out.print("Enter Id : ");
        int id = sc.nextInt();

        try {
            con.setAutoCommit(false);

             String sql1 = "SELECT salary FROM Employee WHERE id = ?";
             PreparedStatement ps1 = con.prepareStatement(sql1);
             ps1.setInt(1,id);

             ResultSet rs = ps1.executeQuery();
            double empSalary = 0;
            if (rs.next()) {
                empSalary = rs.getDouble("salary");
            } else {
                throw new SQLException("Employee not found");
            }

            LocalDateTime today = LocalDateTime.now();

            String sql2 =
                    "INSERT INTO payroll(employee_id, salary, payment_date, status) " +
                            "VALUES (?, ?, ?, ?)";

            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setInt(1,id);
            ps2.setDouble(2,empSalary);
            ps2.setTimestamp(3,Timestamp.valueOf(today));
            ps2.setString(4,"Successfull");

            int rows = ps2.executeUpdate();

            if (rows != 1) {
                throw new SQLException("Salary processing failed");
            }

            con.commit();
            System.out.println("Payment done for Employee id : " + id );

        } catch (SQLException e) {
            con.rollback();
        } finally {
            con.close();
        }
    }


    static void main() throws SQLException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println(
                    "Select through number :\n" +
                            "1. Insert employee\n" +
                            "2. View All Employees\n" +
                            "3. Find Employee\n" +
                            "4. Employees By Department\n" +
                            "5. Update Employee\n" +
                            "6. Delete Employee\n" +
                            "7. Add Employee Profile Image\n" +
                            "8. Process Salary\n" +
                            "9. Exit\n"
            );

            System.out.print("Enter number : ");
            int n = sc.nextInt();

            switch (n) {
                case 1:
                    insertEmployee();
                    break;
                case 2:
                    viewAllEmployee();
                    break;
                case 3:
                    findEmployeeByid();
                    break;
                case 4:
                    findEmployeeByDepart();
                    break;
                case 5:
                    updateEmployee();
                    break;
                case 6:
                    deleteEmployee();
                    break;
                case 7:
                    addProfileImage();
                    break;
                case 8:
                    processSalary();
                    break;
                case 9:
                    System.out.println("Program terminated !!");
                    return;
                default:
                    System.out.println("Didn't understand your response");
            }
        }
    }
}

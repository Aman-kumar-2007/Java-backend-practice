package org.example;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    static String url = "jdbc:mysql://localhost:3306/college";
    static String user = "root";
    static String pass = "Aman@2007";

    public static void insertEmployee() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String name = sc.next();
        String salary = sc.next();
        String department = sc.next();
        LocalDateTime now = LocalDateTime.now();

        Connection con = DriverManager.getConnection(url,user,pass);

        String sql = "INSERT INTO employee(name, salary, department,joining_date) " +
                    "VALUES(?,?,?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, salary);
            ps.setString(3, department);
            ps.setTimestamp(4, Timestamp.valueOf(now));

            int row = ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateEmployee(Statement st){
        Scanner sc = new Scanner(System.in);
        IO.println("Enter id : ");
        String id = sc.next();
        IO.println("Enter salary : ");
        double salary = sc.nextDouble();
        String sql = "UPDATE employee SET salary = " + salary + "WHERE id = " + id;
        try {
            int row = st.executeUpdate(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteEmployee(Statement st){
        Scanner sc = new Scanner(System.in);
        IO.println("Enter id : ");
        String id = sc.next();
        String sql = "DELETE FROM employee WHERE id = " + id;
        try {
            int row = st.executeUpdate(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection con = DriverManager.getConnection(url,user,pass);
        try {
            Statement st = con.createStatement();

            insertEmployee();
//            updateEmployee(st);
//            deleteEmployee(st);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}

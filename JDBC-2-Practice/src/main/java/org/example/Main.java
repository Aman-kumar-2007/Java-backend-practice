package org.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static void insertEmployee(Statement st){
        Scanner sc = new Scanner(System.in);
        String name = sc.next();
        String salary = sc.next();
        String department = sc.next();
        String sql = "INSERT INTO employee(name, salary, department) " +
                    "VALUES('" + name + "', " + salary + ", '" + department + "')";
        try {
            int row = st.executeUpdate(sql);
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

    public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/college";
        String user = "root";
        String pass = "Aman@2007";
        try {
            Connection con = DriverManager.getConnection(url,user,pass);
            Statement st = con.createStatement();

//            insertEmployee(st);
//              updateEmployee(st);
            deleteEmployee(st);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}

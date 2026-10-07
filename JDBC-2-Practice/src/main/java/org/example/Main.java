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

    public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/college";
        String user = "root";
        String pass = "Aman@2007";
        try {
            Connection con = DriverManager.getConnection(url,user,pass);
            Statement st = con.createStatement();

            insertEmployee(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}

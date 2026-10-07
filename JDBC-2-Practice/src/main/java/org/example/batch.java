package org.example;

import java.sql.*;

public class batch {
    static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/college";
        String user = "root";
        String pass = "Aman@2007";
        try {
            Connection con = DriverManager.getConnection(url,user,pass);

            String sql = "INSERT INTO employee (name, salary, department) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "Riya");
            ps.setDouble(2, 45000);
            ps.setString(3, "HR");
            ps.addBatch();

            ps.setString(1, "Raghav");
            ps.setDouble(2, 60000);
            ps.setString(3, "Finance");
            ps.addBatch();

            int[] results = ps.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package org.example;

import java.sql.*;

public class PreparedStatementDemo {
    public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/college";
        String user = "root";
        String pass = "Aman@2007";
        try {
            Connection con = DriverManager.getConnection(url,user,pass);

            String sql = "INSERT INTO employee (name, salary, department) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            /*
             THIS SQL -->> String sql = "SELECT * FROM employee WHERE name = ?";

            ps.setString(1, "Aman");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " " +
                                rs.getString("name") + " " +
                                rs.getDouble("salary") + " " +
                                rs.getString("department")
                );
            }

             */


            /*

            THIS SQL -->   String sql = "UPDATE employee SET salary = ? WHERE id = ?";

            ps.setDouble(1, 70000);
            ps.setInt(2, 1);
            int rows = ps.executeUpdate();

             */

            /*

            THIS SQL --> String sql = "DELETE FROM employee WHERE id = ?";

            ps.setInt(1,1);
            int row = ps.executeUpdate();

             */

            /*
           THIS SQL -->>  String sql = "INSERT INTO employee (name, salary, department) VALUES (?, ?, ?)";

            ps.setString(1, "Aman");
            ps.setDouble(2, 90000);
            ps.setString(3, "IT");

            int rows = ps.executeUpdate();

            */

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

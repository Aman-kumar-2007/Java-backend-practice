package org.example;

import java.sql.*;

public class callBack {
    static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/college";
        String user = "root";
        String pass = "Aman@2007";
        try {
            Connection con = DriverManager.getConnection(url,user,pass);
            String sql = "{call getEmployee(?)}";
            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1,6);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " " +
                                rs.getString("name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionPool {
    public static void main(String[] args){

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mysql://localhost:3306/college");
        config.setUsername("root");
        config.setPassword("Aman@2007");

        config.setMaximumPoolSize(5);

        HikariDataSource dataSource = new HikariDataSource(config);

        String sql = "SELECT * FROM employee";

        try(
                Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " " +
                                rs.getString("name") + " " +
                                rs.getDouble("salary") + " " +
                                rs.getString("department")
                );
            }


            System.out.println("Database connected!");
            System.out.println("Connection obtained from HikariCP");


        } catch (Exception e){
            e.printStackTrace();
        }

        dataSource.close();

    }
}

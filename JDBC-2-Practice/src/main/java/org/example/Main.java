package org.example;

import java.io.*;
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

    public static void updateProfileImage() throws SQLException {
        Connection con = DriverManager.getConnection(url,user,pass);
        String sql = "UPDATE employee SET profile_image = ? where id = ?";

        Scanner sc = new Scanner(System.in);
        int id = sc.nextInt();

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            FileInputStream pic = new FileInputStream("src/main/java/org/example/profile-pic1.jpeg");
            ps.setBinaryStream(1,pic);
            ps.setInt(2,id);

            ps.executeUpdate();

            pic.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void readProfileImage() throws SQLException {
        Connection con = DriverManager.getConnection(url,user,pass);
        String sql = "SELECT profile_image FROM employee WHERE id = ?";

        Scanner sc = new Scanner(System.in);
        int id = sc.nextInt();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                InputStream input = rs.getBinaryStream("profile_image");

                FileOutputStream output =
                        new FileOutputStream("output.jpeg");

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                output.close();
                input.close();
                System.out.println("Image retrieved successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection con = DriverManager.getConnection(url,user,pass);
        try {
            Statement st = con.createStatement();

//            insertEmployee();
//            updateEmployee(st);
//            deleteEmployee(st);
//            updateProfileImage();
            readProfileImage();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}

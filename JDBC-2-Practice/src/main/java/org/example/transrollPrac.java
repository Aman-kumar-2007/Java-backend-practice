package org.example;

import java.sql.*;
import java.util.Scanner;

public class transrollPrac {
    public static void transferMoney(int fromId, int toId, double amount) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/college";
        String user = "root";
        String pass = "Aman@2007";
        Connection con = DriverManager.getConnection(url,user,pass);
        try{
            con.setAutoCommit(false);

            String checkSql =
                    "SELECT balance FROM account WHERE id = ?";

            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, fromId);

            ResultSet rs = checkPs.executeQuery();

            if (!rs.next()) {
                throw new SQLException("Sender account does not exist");
            }

            double balance = rs.getDouble("balance");

            if (balance < amount) {
                throw new SQLException("Insufficient balance");
            }

            String sql2 = "UPDATE account SET balance = balance - ? WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(sql2);
            ps.setDouble(1, amount);
            ps.setInt(2, fromId);

            int row1 = ps.executeUpdate();

            if (row1 != 1) {
                throw new SQLException("Sender update failed");
            }

            String addSql =
                    "UPDATE account SET balance = balance + ? WHERE id = ?";

            PreparedStatement addPs =
                    con.prepareStatement(addSql);

            addPs.setDouble(1, amount);
            addPs.setInt(2, toId);

            int row2 = addPs.executeUpdate();

            if (row2 != 1) {
                throw new SQLException("Receiver account does not exist");
            }

            con.commit();

            System.out.println("Transaction Complete");

        } catch (SQLException e){
            con.rollback();
            e.printStackTrace();
            System.out.println("Transaction failed");
        } finally {
            con.close();
        }
    }
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int fromId = sc.nextInt();
        int toId = sc.nextInt();
        double amount = sc.nextDouble();
        transferMoney(fromId,toId,amount);
    }
}

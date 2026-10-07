package org.example;

import java.sql.*;

public class TransactionRollback {
    public static void main() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/college";
        String user = "root";
        String pass = "Aman@2007";
        Connection con = DriverManager.getConnection(url,user,pass);
        try {
            con.setAutoCommit(false);

            String sql1 =
                    "UPDATE employee SET salary = salary - 500 WHERE id = 4";

            String sql2 =
                    "UPDATE employee SET salary = salary + 500 WHERE id = 5";

            Statement st = con.createStatement();

            st.executeUpdate(sql1);
            st.executeUpdate(sql2);

            con.commit();

            System.out.println("Transaction successful");


        } catch (SQLException e) {
            con.rollback();
            System.out.println("Transaction failed");
            e.printStackTrace();
        }
    }

}

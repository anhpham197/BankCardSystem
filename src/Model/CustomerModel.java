package Model;

import ConnectUtil.ConnectToDB;
import Entity.Customer;

import java.sql.*;
import java.util.Scanner;

public class CustomerModel {
    Connection con = ConnectToDB.getConnect();
    Statement sqlFile = null;
    ResultSet rs = null;
    Scanner inp = new Scanner(System.in);

    public Customer SearchFromDB(String pin) throws SQLException {
        sqlFile = con.createStatement();
        rs = sqlFile.executeQuery(String.format("SELECT * FROM bidv_services WHERE PIN = %s", pin));
        Customer newObj = null;
        while (rs.next()) {
            newObj = new Customer();
            newObj.setPIN(rs.getString("PIN"));
            newObj.setName(rs.getString("NAME"));
            newObj.setNumOfAcc(rs.getString("NumOfAcc"));
            newObj.setSurplus(rs.getLong("Surplus"));
        }
        return newObj;
    }

    public Customer SearchAccFromDB(String numOfAcc) throws SQLException {
        sqlFile = con.createStatement();
        rs = sqlFile.executeQuery(String.format("SELECT * FROM bidv_services WHERE NumOfAcc = %s", numOfAcc));
        Customer newObj = null;
        while (rs.next()) {
            newObj = new Customer();
            newObj.setPIN(rs.getString("PIN"));
            newObj.setName(rs.getString("NAME"));
            newObj.setNumOfAcc(rs.getString("NumOfAcc"));
            newObj.setSurplus(rs.getLong("Surplus"));
        }
        return newObj;
    }

    public void UpdateData(Customer user, String newPIN) throws SQLException {
        String updateQuerySe = String.format("UPDATE bidv_services SET PIN ='%s' WHERE PIN = %s",
                                              newPIN, user.getPIN());
        sqlFile = con.createStatement();
        sqlFile.executeUpdate(updateQuerySe);
    }

    public void SendMoneyToAcc(Customer sender, Customer receiver, float sendMoney) throws SQLException {
        float senderSurplus = sender.getSurplus() - sendMoney;
        float receiverSurplus = receiver.getSurplus() + sendMoney;

        String updateQuerySe = "UPDATE bidv_services SET Surplus = ? WHERE PIN = ?";
        PreparedStatement psSe = con.prepareStatement(updateQuerySe);
        psSe.setString(2, sender.getPIN());
        psSe.setFloat(1, senderSurplus);


        String updateQueryRe = "UPDATE bidv_services SET Surplus = ? WHERE NumOfAcc = ?";
        PreparedStatement psRe = con.prepareStatement(updateQueryRe);
        psRe.setString(2, receiver.getNumOfAcc());
        psRe.setFloat(1, receiverSurplus);

        psSe.execute();
        psRe.execute();
    }

    public void WithdrawMoneyFromAcc(Customer withdrawer, float withdrawMoney) throws SQLException {
        float surplus = withdrawer.getSurplus() - withdrawMoney;
        String updateQuery = "UPDATE bidv_services SET Surplus = ? WHERE PIN = ?";
        PreparedStatement ps = con.prepareStatement(updateQuery);
        ps.setFloat(1,surplus);
        ps.setString(2,withdrawer.getPIN());
        ps.execute();
    }
}

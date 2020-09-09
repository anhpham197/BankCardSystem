package Controller;

import Entity.Customer;
import Model.CustomerModel;

import java.sql.SQLException;

public class CustomerController {
    CustomerModel mod = new CustomerModel();

    public Customer SearchByPIN(String PIN) throws SQLException {
        return mod.SearchFromDB(PIN);
    }

    public Customer SearchByAcc (String numOfAcc) throws SQLException {
        return mod.SearchAccFromDB(numOfAcc);
    }

    public void ChangePIN(Customer user, String newPIN) throws SQLException {
        mod.UpdateData(user, newPIN);
    }

    public void SendMoneyToAcc(Customer sender, Customer receiver, float sendMoney) throws SQLException {
        mod.SendMoneyToAcc(sender, receiver, sendMoney);
    }

    public void WithdrawMoney(Customer withdrawer, float withdrawMoney) throws SQLException {
        mod.WithdrawMoneyFromAcc (withdrawer,withdrawMoney);
    }
}

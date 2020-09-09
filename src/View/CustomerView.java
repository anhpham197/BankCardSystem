package View;

import Controller.CustomerController;
import Entity.Customer;
import com.cuongtm.table.CVTable;

import java.sql.SQLException;
import java.util.Scanner;

public class CustomerView {
    static final float MAINTENANCE_FEE = 50000;
    static Scanner inp = new Scanner(System.in);
    static CustomerController controller = new CustomerController();

    public static void main(String[] args) throws SQLException {
        boolean isContinue = true;
        while (isContinue) {

            // Show the menu
            CVTable.menu("===== WELCOME TO BIDV'S SERVICES =====", "");

            // Select option
            int option = inp.nextInt();
            inp.nextLine();

            switch (option) {
                case 1:  // Show customer info
                    System.out.print("Please enter your PIN : ");
                    String PIN = inp.nextLine();
                    try {
                        Customer res = controller.SearchByPIN(PIN);
                        if (res != null) {
                            System.out.println("Name : " + res.getName());
                            System.out.println("NumOfAcc : " + res.getNumOfAcc());
                            System.out.println("Surplus : " + res.getSurplus());
                        } else System.out.println("Can't find your account! Please try again!");
                    } catch (SQLException throwables) {
                        System.out.println("=> ERROR : " + throwables.getMessage());
                    }
                    break;

                case 2:  // Change PIN
                    System.out.print("Please enter your old pin : ");
                    String oldPIN = inp.nextLine();
                    System.out.print("Please enter your new PIN with 6 digits: ");
                    String newPIN = inp.nextLine();

                    Customer user = controller.SearchByPIN(oldPIN);

                    try {
                        if (user != null) {
                            controller.ChangePIN(user, newPIN);
                            System.out.println("Change PIN successfully!");
                        } else System.out.println("Can't find your account! Please try again!");
                    } catch (SQLException throwables) {
                        System.out.println("=> ERROR : " + throwables.getMessage());
                        throwables.printStackTrace();
                    }
                    break;

                case 3:  // Send money
                    System.out.print("Please enter your PIN : ");
                    String senderPIN = inp.nextLine();
                    System.out.print("Please enter receiver's account : ");
                    String receiverNumOfAcc = inp.nextLine();

                    Customer sender = controller.SearchByPIN(senderPIN);
                    Customer receiver = controller.SearchByAcc(receiverNumOfAcc);
                    System.out.print("Please enter money : ");
                    float sendMoney = inp.nextFloat();

                    if ((sender.getSurplus() >= (MAINTENANCE_FEE + sendMoney)) && sender != null && receiver != null) {
                        controller.SendMoneyToAcc(sender, receiver, sendMoney);
                        System.out.println("Successful transaction!\nYour surplus : " + (sender.getSurplus() - sendMoney));
                    } else if (receiver == null)
                        System.out.println("Can't find receiver's account! Please try again!");
                    else if (sender == null)
                        System.out.println("Can't find your account! Please try again!");
                    else System.out.println("Incomplete transaction!\nPlease check your surplus!");

                    break;

                case 4:  // Withdraw money
                    System.out.print("Please enter your PIN : ");
                    String withdrawerPIN = inp.nextLine();

                    System.out.print("Please enter money : ");
                    float withdrawMoney = inp.nextFloat();

                    Customer withdrawer = controller.SearchByPIN(withdrawerPIN);

                    if (withdrawer.getSurplus() >= (MAINTENANCE_FEE + withdrawMoney) && withdrawer != null) {
                        controller.WithdrawMoney(withdrawer, withdrawMoney);
                        System.out.println("Successful transaction!\nYour surplus : " +
                                (withdrawer.getSurplus() - withdrawMoney));
                    } else if (withdrawer == null)
                        System.out.println("Can't find your account! Please try again!");
                    else System.out.println("Incomplete transaction!\nPlease check your surplus!");

                    break;

                case 5:  // Exit
                    System.out.println("Thanks for using our services !");
                    isContinue = false;
                    break;
            }
        }
    }
}

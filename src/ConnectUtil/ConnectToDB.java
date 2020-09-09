package ConnectUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ConnectToDB extends LoginDB_Info {
    static final String DB_Info = "jdbc:mysql://127.0.0.1:3306/customer";
    static final String USER_NAME = "root";
    static final String PASS = "anh@1972001";
    public static Scanner inp = new Scanner(System.in);

    public static Connection getConnect() {
       /* LoginDB_Info login = new LoginDB_Info();
        login.setUSER_NAME(inp.nextLine());
        login.setPASS(inp.nextLine());*/
        try {
            Connection bridge = DriverManager.getConnection(DB_Info, USER_NAME, PASS);
            System.out.println("Connected Successfully");
            return bridge;
        } catch (SQLException throwables) {
            System.out.println("Can't connect to this schema");
            throwables.printStackTrace();
            return null;
        }
    }
}

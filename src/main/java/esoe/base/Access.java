package esoe.base;

import javax.swing.*;

public class Access{
    static String userName = "root";
    static String password = null;
    static String url = "jdbc:mysql://localhost:3306/" 	+
            "mysql?zeroDateTimeBehavior=convertToNull" + "&useSSL=false";
    static String driver = "com.mysql.jdbc.Driver";

    public static String getUserName(){
        return userName;
    }

    public static String getPassword(){
        if (password == null) {password = JOptionPane.showInputDialog("password", "root");}
        return password;
    }

    public static void setUrl(String Name){
        if (Name == "") url = "jdbc:mysql://localhost:3306/" +
                "mysql?zeroDateTimeBehavior=convertToNull" +
                "&useSSL=false"
                ;
        else url = "jdbc:mysql://localhost:3306/" + Name + "?useSSL=false";
    }


    public static String getUrl(){
        return url;
    }

    public static String getDriver(){
        return driver;
    }
}
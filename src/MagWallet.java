
import DB.ConnectionConfiguration;
import Wallet.Wallet;

import java.rmi.server.ExportException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MagWallet {

	private static List<Wallet> wallets;

    public static void main(String[] args) {
		int index = 0;
        List<Pair<String,String>> download_usernames_passwords = new ArrayList<>();
		Scanner scanner;
		String prompt;
		String userName;
		String password;
		Wallet w; //new wallet 
		boolean new_wallet_flag;
		wallets = new ArrayList<Wallet>();
        Connection connection = ConnectionConfiguration.getConnection();

		while(true){
			new_wallet_flag = true;
			System.out.println("Welcome to MagWallet - please enter your user name:");
			userName = (new Scanner(System.in)).next();
            System.out.println("Now please enter your corresponding password: ");
            password = (new Scanner(System.in)).next();
            download_usernames_passwords = load_usernames_and_pass();
                //go through each loaded usernames
                for ( Pair<String, String> uname_pass : download_usernames_passwords) {
                    //Case 1 - if the name is already in data base
                    if(uname_pass.getKey().equals(userName) && uname_pass.getValue().equals(password)) {
                        System.out.println(userName + " is already in our data base would you like to see your wallet (y/n)");
                        if ((new Scanner(System.in)).next().equals("y")) {
                            w = new Wallet(userName, password);
                            wallets.add(index++, w);
                            w.look_inside();
                        }
                        new_wallet_flag = false;
                    }
                }//for
                //For no user name in our database
                if(new_wallet_flag == true){
                    System.out.println("Seems like you are not in our data base would you like to register(y/n)?");
                    if((prompt = (scanner = new Scanner(System.in)).next()).equals("y")){
                        w = new Wallet(userName, password);
                        wallets.add(index++, w);
                        //Create new user and password
                        if(ConnectionConfiguration.create_new_user(w)) {
                            System.out.println("Account with username " + w.getUser_name() + " created");
                            w.look_inside();
                        }
                    }
                }
		}//while
    }//main
    private static List<Pair<String,String>> load_usernames_and_pass(){
        List<Pair<String, String>> usernames_passwords = new ArrayList<>();
        String query = "SELECT user_name, password FROM Wallet";
        ResultSet rs;
        final int USERNAME_COLUMN = 1;
        final int PASSWORD_COLUMN = 2;
       try {
           rs = ConnectionConfiguration.getConnection().createStatement().executeQuery(query);
           //Go through each username and add to usernames
           while(rs.next()){
               usernames_passwords.add(new Pair<>(rs.getString(USERNAME_COLUMN), rs.getString(PASSWORD_COLUMN)));
           }
       }catch(Exception e){
           e.printStackTrace();
           return null;
       }

       return usernames_passwords;
    }

}




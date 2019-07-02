
import Wallet.Cards.Card;
import Wallet.Cards.CreditCard;
import Wallet.Cards.Student_IDCard;
import Wallet.Parsers.Parser;
import sun.tools.jar.CommandLine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MagWallet {

	private static List<Wallet> wallets;


    public static void main(String[] args) {
		int index = 0;
		Scanner scanner;
		String prompt;
		String name;
		Wallet w; //new wallet 
		boolean new_wallet_flag;

		while(true){
			new_wallet_flag = true;
			System.out.println("Welcome to MagWallet - please enter your name");
			name = (scanner = new Scanner(System.in)).next();
			for (Wallet wall : wallets){
				//Case 1 - if the name is already in data base
				if(wall.getName().equals(name){
					System.out.println(name + "is already in our data base would you like to see your wallet (y/n)"); 
					if((prompt = (scanner = new Scanner(System.in)).next()).equals("y")){
						wall.look_inside();
					}
					new_wallet_flag = false;
				}
			}//for
			if(new_wallet_flag == true){
				System.out.println("Seems like you are not in our data base would you like to register(y/n)?");	
				if((prompt = (scanner = new Scanner(System.in)).next()).equals("y")){
					w = new Wallet(name);
					wallets.add(index++, w);
					w.look_inside();
				}
			}
		}//while
    }//main

}




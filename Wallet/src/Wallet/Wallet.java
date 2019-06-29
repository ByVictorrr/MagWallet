package Wallet;

import Wallet.Cards.Card;
import Wallet.Parsers.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//https://netjs.blogspot.com/2016/10/how-to-run-shell-script-from-java-program.html

public class Wallet{
    private static List<Card> cards;

    public static void main(String[] args) {
		int i = 0;
		final int unparsed_card_data_track1 = 2;
		final int unparsed_card_data_track2 = 3;
	    String [] cmd = {"sh", "/home/victor/VictorsWallet/Wallet/src/upload_mag_spoof.sh", "track1 string", "track2 string"};
        Scanner scanner;
        Process proc;
		cards = new ArrayList<>();
		Card c;
		//endless loop program
        while(true){
           try{
              c =  Parser.swipeString();
              System.out.println(c.toString());
               cards.add(i,c);
               System.out.println("Would you like to upload this card to the magspoof? (y/n)");
               scanner = new Scanner(System.in);
               //Check to see if user wants to upload to the magspoof
               if(scanner.next().equals("y")){
                   cmd[unparsed_card_data_track1] = getTrack1(c.getUnParsed());
                   cmd[unparsed_card_data_track2] = getTrack2(c.getUnParsed());
                   proc = Runtime.getRuntime().exec(cmd);
                   proc.waitFor();
               }

           }catch (Exception e){
               e.printStackTrace();
               System.out.println("prob null ptr");
               break;
           }//catch
            i++;
        }//while
    }//main

   private static String getTrack1(String unparsed){
       return unparsed.split(";")[0];
   }
   private static String getTrack2(String unparsed){
       String track2 = unparsed.split(";")[1];
       return ";" + track2;
   }


}


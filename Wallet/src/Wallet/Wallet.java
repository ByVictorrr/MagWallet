package Wallet;

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

//https://netjs.blogspot.com/2016/10/how-to-run-shell-script-from-java-program.html

public class Wallet {
    private static List<Card> cards;

    public static void main(String[] args) {
        int i = 0;
        final int shell_parmeter = 1;
        int exit_code;
        String[] cmd = {"src/upload_mag_spoof.sh", "track"};
        Scanner scanner;



        cards = new ArrayList<>();
        Card c;
        //endless loop program
        while (true) {
            try {
                c = Parser.swipeString();
                System.out.println(c.toString());
                    if (!cards.contains(c)) {
                        cards.add(i, c);
                    }
                System.out.println("Would you like to upload this card to the magspoof? (y/n)");
                scanner = new Scanner(System.in);
                //Check to see if user wants to upload to the magspoof
                if (scanner.next().equals("y")) {
                    cmd[shell_parmeter] = shell_parm(c.getUnParsed());
                    exit_code = run_shell(cmd);
                    System.out.println("Exited with " + exit_code);
                }
                //Check to see if user wants to print all his/her cards
                print_list_of_cards(cards);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("prob null ptr");
                break;
            }//catch
            i++;
        }//while
    }//main


    private static void print_list_of_cards(List<Card> cards) {
        Scanner scanner;
        String option;
        System.out.println("Would you like to view your cards?(y/n)");
        scanner = new Scanner(System.in);
        while (true) {
            if ((option = scanner.next()).equals("y")) {
                for (Card c : cards) {
                    System.out.println("============================");
                    System.out.println(c.toString());
                    System.out.println("============================");
                }
                return;
            } else if (option.equals("n")) {
                return;
            } else {
                System.out.println("Please enter (y/n)");
            }
        }
    }

    private static String getTrack1(String unparsed) {
        // replace all / to \/
        String s = unparsed.split(";")[0].replaceAll("/", "\\\\/");
        return s;
    }

    private static String getTrack2(String unparsed) {
        String track2 = unparsed.split(";")[1];
        return track2;
    }

    private static String shell_parm(String unparsed) {
        String track1 = getTrack1(unparsed);
        String track2 = getTrack2(unparsed);
        String format_parm = "\\\"" + track1 + "\\\\0\"" + ", \"" + ";" + track2 + "\\\\0\\\"";
        return format_parm;
    }

    private static int run_shell(String[] cmd) {
        int exit_code = -1;
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            exit_code = proc.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return exit_code;
    }
}




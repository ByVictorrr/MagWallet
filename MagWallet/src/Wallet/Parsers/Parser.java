package Wallet.Parsers;

import Wallet.Cards.Card;
import Wallet.Cards.CreditCard;
import Wallet.Cards.Student_IDCard;
import Wallet.Wallet;

import java.util.*;
import java.util.regex.Pattern;

public class Parser {
    protected final static int NOT_CARD = -1;
    protected final static int CREDIT_CARD = 0;
    protected final static int STUDENT_ID = 1; //Note : cal poly id only work

    public final static String CREDIT_TYPE = "credit card";
    public final static String STUDENT_TYPE = "student id";

    private final static List<String> regexCardFormat = Arrays.asList(".*4[0-9]{12}(?:[0-9]{3})?.*", "^.*[0-9]+\\^STUDENT\\?\\;.*"); //regex


    public static int isCard(String swipe) {
        for (int i = 0; i < regexCardFormat.size(); i++)
            if (Pattern.matches(regexCardFormat.get(i), swipe))
                return i;
        return NOT_CARD;
    }

    public List<String> getRegexCardFormat() {
        return this.regexCardFormat;
    }

    public static String getType(String swipe) {
        String type = null;
        switch (isCard(swipe)) {
            case CREDIT_CARD:
                type = CREDIT_TYPE;
                break;
            case STUDENT_ID:
                type = STUDENT_TYPE;
                break;
            default:
                System.out.println("not a student id or credit card");
                return null;
        }
        return type;
    }

    //Returns the card or null if not a valid card or isnt in our db
    public static Card swipeString() {
        String data;
        Card card = null;
        int type;
        System.out.println("==================================\nSwipe your card:\n1.)Credit card\n2.)Student ID (Cal poly card)\n==============================");
        Scanner scanner = new Scanner(System.in);
        try { //Try to get input
            if ((type = isCard(data = (scanner.next() + scanner.nextLine()))) != NOT_CARD) {
                //Step 1 - is a card
                //Step 2 - get the type of card
                switch (type) {
                    //Case 1 - just a reg credit card
                    case CREDIT_CARD:
                        card = new CreditCard(ParserCreditCard.getName(data),
                                ParserCreditCard.getCardNumber(data),
                                Parser.getType(data),
                                data,
                                ParserCreditCard.getCreditType(data)
                        );
                        System.out.println("Credit card");
                        break;
                    case STUDENT_ID:
                        card = new Student_IDCard(ParserCardID.getName(data),
                                ParserCardID.getCardNumber(data),
                                Parser.getType(data),
                                data
                        );
                        System.out.println("Cal poly id");
                        break;
                    default:
                        System.out.println("Not a card in our database");
                }
            } else {
                //not a valid card
                System.out.println("Not a valid card");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return card;
    }


}


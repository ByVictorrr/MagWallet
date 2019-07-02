package Wallet.Parsers;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserCreditCard extends Parser {
    private static final int VISA = 0;
    private static final int MASTERCARD = 1;
    private static final int AMERICAN_EXPRESS = 2;
    private static final int DINERS_CLUB = 3;
    private static final int DISCOVER = 4;
    private static final int JCB = 5;

    private final static List<String> regex_credit_types = Arrays.asList(".*4[0-9]{12}(?:[0-9]{3})?.*",
            ".*5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720[0-9]{12}.*",
            ".*3[47][0-9]{13}.*",
            ".*3(?:0[0-5]|[68][0-9])[0-9]{11}.*",
            ".*6(?:011|5[0-9]{2})[0-9]{12}.*",
            ".*2131f|1800|35\\d{3}\\d{11}.*"

    );

    private final static List<String> regex_credit_nums = Arrays.asList("4[0-9]{12}(?:[0-9]{3})?",
            "5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720[0-9]{12}",
            "3[47][0-9]{13}",
            "3(?:0[0-5]|[68][0-9])[0-9]{11}",
            "6(?:011|5[0-9]{2})[0-9]{12}",
            "2131f|1800|35\\d{3}\\d{11}"

    );

    //Returns null if credit type doesnt exist or actual type
    private static String getCreditTypeUtility(int type) {
        String str_type = null;

        if (type < 0 || type > 5)
            return null;
        switch (type) {
            case VISA:
                str_type = "visa";
                break;
            case MASTERCARD:
                str_type = "master card";
                break;
            case AMERICAN_EXPRESS:
                str_type = "american express";
                break;
            case DINERS_CLUB:
                str_type = "diners club";
                break;
            case DISCOVER:
                str_type = "discover";
                break;
            case JCB:
                str_type = "jcb";
                break;
            default:
                System.out.println("not a valid type");
        }
        return str_type;
    }


    //Returns name or null if match not found
    public static String getName(String swipe) {
        String name = null;
        Pattern p = Pattern.compile("\\b\\^.*\\^\\b");
        Matcher m = p.matcher(swipe);
        //if we fina a match
        if (m.find()) {
            name = m.group(0);
            name = name.replaceAll("\\^", "");
            name = name.replace("/", " ");
        } else {
            //if no match return null
            return null;
        }
        return name;
    }

    public static String getCardNumber(String swipe) {
        String number = null;
        Matcher m;
        //go through and extract the credit numbers
        for (int i = 0; i < regex_credit_nums.size(); i++) {
            m = Pattern.compile(regex_credit_nums.get(i)).matcher(swipe);
            //if the pattern is found
            if (m.find()) {
                number = m.group(0);
            }
        }
        return number;
    }


    public static String getCreditType(String swipe) {
        String type = null;
        //use regex_credit_types from above
        for (int i = 0; i < regex_credit_types.size(); i++) {
            //Case 1 - if the swipe data matches one of the regex above
            if (Pattern.compile(regex_credit_types.get(i)).matcher(swipe).matches()) {
                type = getCreditTypeUtility(i);
                break;
            }

        }//for
        return type;
    }//end


}


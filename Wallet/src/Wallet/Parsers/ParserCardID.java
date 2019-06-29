package Wallet.Parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserCardID extends Parser{
    final static String regex_card_number = ";\\b[0-9]+";
    final static String regex_name = "\\b\\^[A-Z]+";

    public static String getName(String swipe) {
        String name;
        Pattern p = Pattern.compile(regex_name);
        Matcher m = p.matcher(swipe);
        //if we fina a match
        if(m.find()){
            name = m.group(0);
            name = name.replaceAll("\\^", "");
        }else{
            //if no match return null
            return null;
        }
        return name;
    }
    public static String getCardNumber(String swipe){
        String num;
        Pattern p = Pattern.compile(regex_card_number);
        Matcher m = p.matcher(swipe);
        //if we fina a match
        if(m.find()){
            num = m.group(0);
            num = num.replaceAll(";", "");
        }else{
            //if no match return null
            return null;
        }
        return num;
    }

}

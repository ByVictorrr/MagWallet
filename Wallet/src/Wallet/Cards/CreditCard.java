package Wallet.Cards;

import Wallet.Wallet;

public class CreditCard extends Card{
    private String credit_type;
    public final String VISA = "visa";
    public final String MASTERCARD = "master card";
    public final String AMERICAN_EXPRESS = "american express";
    public final String DINERS_CLUB = "diners club";
    public final String DISCOVER = "discover";
    public final String JCB = "jcb";


    public CreditCard(String name, String cardNumber, String type, String unParsed, String credit_type){
        super(name,cardNumber,type,unParsed);
        this.credit_type= credit_type;
    }
    public String getCreditType(){ return this.credit_type;}
    public String toString(){
        return super.toString() + "\nCredit Type: " + this.credit_type + "\n";
    }
}

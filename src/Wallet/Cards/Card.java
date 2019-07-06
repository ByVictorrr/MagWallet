package Wallet.Cards;

public class Card {
    private String name;
    private String cardNumber;
    private String type;
    private String unParsed;

    public Card(String name, String cardNumber, String type, String unParsed) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.type = type;
        this.unParsed = unParsed;
    }
    public Card(){
       this.name = "";
       this.cardNumber = "";
       this.type = "";
       this.unParsed = "";
    }

    public boolean equals(Object c) {
        //Step 1 - check if its an object of tyep card first
        Card card;
        if (c instanceof Card) {
            //Step 2 - check if each member is equal to
            card = (Card) c;
            return card.getUnParsed().equals(this.unParsed) && card.getName().equals(this.name) && card.getCardNumber().equals(this.cardNumber) && card.getType().equals(this.type);
        }
        return false;
    }

    public void setCardNumber(String num) {
        this.cardNumber = num;
    }

    public void setName(String name) { this.name = name; }
    public String getName() {
        return this.name;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUnParsed(String unParsed) {
        this.unParsed = unParsed;
    }

    public String getType() {
        return type;
    }

    public String getUnParsed() {
        return unParsed;
    }

    public String toString() {
        return "name: " + this.name + "\ncard number: " + this.cardNumber + "\ntype: " + this.type + "\nunparsed: " + this.unParsed;
    }
}

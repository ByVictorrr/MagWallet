package Wallet;
import DB.ConnectionConfiguration;
import Wallet.Cards.*;
import Wallet.Parsers.Parser;
import com.google.gson.Gson;
import jdk.nashorn.internal.parser.JSONParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.transform.Result;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.*;

//https://netjs.blogspot.com/2016/10/how-to-run-shell-script-from-java-program.html

public class Wallet {
    private List<Card> cards;
    private String[] cmd = {"src/upload_mag_spoof.sh", "track"};
    private String user_name;
    private String password;
    private final int shell_parmeter = 1;
    private static final int CARD_COLUMN = 3;

    public Wallet(String name, String password) {
        this.user_name = name;
        this.password = password;
        cards = new ArrayList<Card>();
    }

    public void look_inside() {
        int i = 0;
        int exit_code;
        Scanner scanner;
        Card c;
        //While loop - is the endless swipe card upload magspoof stuff
        while (exitWallet()) {
            try {
                //Step 1 - swipe card
                c = Parser.swipeString();
                //Step 2 - add card if not already there
                if (!cards.contains(c)) {
                    cards.add(i, c);
                }
                //Step 3 - options for swiped card
                while (wallet_options(c))
                    ;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("prob null ptr");
                break;
            }//catch
            i++;
        }//while
    }

    private static JSONArray convertTOJSON(ResultSet rs) {
        JSONArray ja = new JSONArray();
        try{
            while(rs.next()){
                JSONObject o = new JSONObject();
                o.put(rs.getMetaData().getColumnLabel(CARD_COLUMN), rs.getString(CARD_COLUMN));
            }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ja;
    }
    private List<JSONObject> string_to_JSON_list(String json){
        try {
            JSONObject jo = new JSONObject(json);
            JSONArray ja = jo.getJSONArray("Cards");
            List<JSONObject> jo_list = new ArrayList<>();
            for(int i = 0; i< ja.length(); i++){
               jo_list.add(ja.getJSONObject(i));
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return jo_list;
    }
    public List<Card> download_cards_from_mysql(){
        String query = "SELECT cards FROM Wallet WHERE user_name = '" + this.user_name + "'";
        List<Card> extracted_cards = new ArrayList<>();
        ResultSet rs;
        List<JSONObject> jo_list = new ArrayList<>();
        String cards;
        ObjectMapper object_mapper = new ObjectMapper();
        try{
            //Step 1 - get the data from cards
            rs = ConnectionConfiguration.getConnection().createStatement().executeQuery(query);
            cards = rs.getString("cards");
            jo_list = string_to_JSON_list(cards);
            //Step 2 - parse list of json object to list of card objects
           for(int i = 0; i<jo_list.size(); i++) {
                extracted_cards.add(object_mapper.read()-);
           }
            //TODO: add to extracted cards
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return extracted_cards;
    }
    private void upload_magspoof(Card c) {
        int exit_code;
        cmd[shell_parmeter] = shell_parm(c.getUnParsed());
        exit_code = run_shell(cmd);
        System.out.println("Exited with " + exit_code);
    }
    private boolean wallet_options(Card c){
	    boolean flag = false;
	    System.out.println("=========" + user_name + "'s Wallet ============\n" +
                "1. Upload a card to your database\n" +
                "2. Upload a card to the Magspoof\n" +
                "3. View all the cards saved\n" +
                "4. Download saved cards\n" +
                "Type anything else to exit");
	    Scanner in = new Scanner(System.in);
	    switch(in.nextLine()){
            case "1": // upload to db
                flag = upload_cards_mysql();
                break;
            case "2": //upload to magspoof
                upload_magspoof(c);
                flag = true;
            case "3": //view all cards
                print_list_of_cards(this.cards);
                flag = true;
            case "4": //download cards to this wallet.
               this.cards = download_cards_from_mysql();
               flag = true;
            default:
                return flag;
        }
        return flag;
    }
	//exitWallet - exits the look_inside method for specfific wallet
	private static boolean exitWallet(){
		System.out.println("Would you like to exit your Wallet(y/n)");
		if((new Scanner(System.in)).next().equals("n"))
			return true;
		return false;
	}
	public boolean equals(Object o){
		Wallet wall;
		if(o instanceof Wallet){
			wall = (Wallet)o;
			return wall.getUser_name().equals(this.user_name) && wall.getCards().equals(this.cards);
		}
		return false;
	}

	public String getUser_name(){return this.user_name;}
	public String getPassword(){return this.password;}
	public List<Card> getCards(){return this.cards;}

	public JSONObject getJSON_Cards(){
	    int index = 0;
        JSONArray ja = new JSONArray();
        JSONObject jo = new JSONObject();
        List<Map<String, String>> list_of_maps = new ArrayList<>();
        try{
            for(Card c: this.cards){
               list_of_maps.add(new HashMap<>(4));
               list_of_maps.get(index).put("name", c.getName());
               list_of_maps.get(index).put("cardNumber", c.getCardNumber());
               list_of_maps.get(index).put("type", c.getType());
               list_of_maps.get(index).put("unParsed", c.getUnParsed());
               ja.put(list_of_maps.get(index));
               index++;
            }
            jo.put("Cards", ja);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return jo;
    }
    public boolean upload_cards_mysql(){
	    JSONObject jo =  getJSON_Cards();
	    String query = "UPDATE Wallet SET cards='" + jo.toString() + "' WHERE user_name='" + this.user_name + "'";
        try{
            ConnectionConfiguration.getConnection().createStatement().executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void print_JSON(Wallet wallet){
	    try {
            PrintWriter pw = new PrintWriter("JSON_FORMAT.json");
            pw.write(getJSON_Cards().toString());
        }catch(Exception e){
	        e.printStackTrace();
        }
    }
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




import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Blackjack {

	
	ArrayList<Integer> cards = new ArrayList<Integer>(Arrays.asList(1,1,1,1,2,2,2,2,3,3,3,3,4,4,4,4,5,5,5,5,6,6,6,6,7,7,7,7,8,8,8,8,9,9,9,9,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10));
	ArrayList<Integer> deck;
	int card1, card2;
	int dCard, hdCard;
	
	public Blackjack(){
		deck = new ArrayList<Integer>(cards);
	}
	
	public void shuffle(){
		Collections.shuffle(deck);
	}
	
	public void deal(){
		card1 = deck.remove(0);
		card2 = deck.remove(0);
		dCard = deck.remove(0);
		hdCard = deck.remove(0);
	}
	
	public int playerLowHand(){
		return card1+card2;
	}
	
	public int playerHighHand(){
		int hc1 = (card1 == 1)? 11 : card1;
		int hc2 = (card2 == 1)? 11 : card2;
		if(hc1+hc2 == 22){
			return 12;
		}
		return hc1+hc2;
	}
	
	public int opponentCard(){
		return (dCard == 1)? 11 : dCard;
	}
	
	public int hiddenHighHand(){
		int hc1 = (dCard == 1)? 11 : dCard;
		int hc2 = (hdCard == 1)? 11 : hdCard;
		if(hc1+hc2 == 22){
			return 12;
		}
		return hc1+hc2;
	}
	
	public double cheaterDecide(){
		if(playerHighHand()==21){
			return 1.0;
		}
		if(playerHighHand()<hiddenHighHand()){
			return 0.0;
		}
		if(playerHighHand()>hiddenHighHand() && playerHighHand() < 17){
			return 0.0;
		} else{
			return 1.0;
		}
		
	}
	
	public void reset(){
		deck = new ArrayList<Integer>(cards);
	}
	
}

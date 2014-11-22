
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Card> cardDeck;
	
	public Deck () {
		cardDeck = new ArrayList<Card> ();
		initializeCards();
		shuffleDeck();
	}
	
	private void shuffleDeck() {
		Collections.shuffle (cardDeck);
	}
	
	public int numCardsLeft() {
		return cardDeck.size();
	}
	
	public Card drawCard() {
		Card c = cardDeck.get(0);
		cardDeck.remove(0);
		return c;
	}
	
	//DEBUG
	public void printDeck () {
		for (Card c : cardDeck) {
			c.printName();
		}
	}
	
	private void initializeCards () {
		cardDeck.add (new Card ("January0_20pts_Ro", Card.Month.January, Card.Yaku.Ro, null, 20));
		cardDeck.add (new Card ("January1_10pts_Ha", Card.Month.January, Card.Yaku.Ha, null, 10));
		cardDeck.add (new Card ("January2_0pts", Card.Month.January, null, null, 0));
		cardDeck.add (new Card ("January3_0pts", Card.Month.January, null, null, 0));
		cardDeck.add (new Card ("February0_10pts_Ha", Card.Month.February, Card.Yaku.Ha, null, 10));
		cardDeck.add (new Card ("February1_5pts_Ro", Card.Month.February, Card.Yaku.Ro, null, 5));
		cardDeck.add (new Card ("February2_0pts", Card.Month.February, null, null, 0));
		cardDeck.add (new Card ("February3_0pts", Card.Month.February, null, null, 0));
		cardDeck.add (new Card ("March0_20pts_I", Card.Month.March, Card.Yaku.I, null, 20)); //Also needs to be a part of the Ro yaku
		cardDeck.add (new Card ("March1_10pts_Ha", Card.Month.March, Card.Yaku.Ha, null, 10));
		cardDeck.add (new Card ("March2_0pts", Card.Month.March, null, null, 0));
		cardDeck.add (new Card ("March3_0pts", Card.Month.March, null, null, 0));
		cardDeck.add (new Card ("April0_10pts_He", Card.Month.April, Card.Yaku.He, null, 10));
		cardDeck.add (new Card ("April1_5pts_To", Card.Month.April, Card.Yaku.To, null, 5));
		cardDeck.add (new Card ("April2_0pts", Card.Month.April, null, null, 0));
		cardDeck.add (new Card ("April3_0pts", Card.Month.April, null, null, 0));
		cardDeck.add (new Card ("May0_10pts_He", Card.Month.May, Card.Yaku.He, null, 10));
		cardDeck.add (new Card ("May1_5pts_To", Card.Month.May, Card.Yaku.To, null, 5));
		cardDeck.add (new Card ("May2_0pts", Card.Month.May, null, null, 0));
		cardDeck.add (new Card ("May3_0pts", Card.Month.May, null, null, 0));
		cardDeck.add (new Card ("June0_10pts_Ni", Card.Month.June, Card.Yaku.Ni, null, 10));
		cardDeck.add (new Card ("June1_5pts_Ho", Card.Month.June, Card.Yaku.Ho, null, 5));
		cardDeck.add (new Card ("June2_0pts", Card.Month.June, null, null, 0));
		cardDeck.add (new Card ("June3_0pts", Card.Month.June, null, null, 0));
		cardDeck.add (new Card ("July0_10pts_He", Card.Month.July, Card.Yaku.He, null, 10));
		cardDeck.add (new Card ("July1_5pts_To", Card.Month.July, Card.Yaku.To, null, 5)); //Also needs to be a part of the Chi yaku
		cardDeck.add (new Card ("July2_0pts", Card.Month.July, null, null, 0));
		cardDeck.add (new Card ("July3_0pts", Card.Month.July, null, null, 0));
		cardDeck.add (new Card ("August0_20pts_I", Card.Month.August, Card.Yaku.I, null, 20));
		cardDeck.add (new Card ("August1_5pts_Chi", Card.Month.August, Card.Yaku.Chi, null, 5));
		cardDeck.add (new Card ("August2_0pts", Card.Month.August, null, null, 0));
		cardDeck.add (new Card ("August3_0pts", Card.Month.August, null, null, 0));
		cardDeck.add (new Card ("September0_10pts_Ni", Card.Month.September, Card.Yaku.Ni, null, 10));
		cardDeck.add (new Card ("September1_5pts_I", Card.Month.September, Card.Yaku.I, null, 5)); //Also needs to be a part of the Ho yaku
		cardDeck.add (new Card ("September2_0pts", Card.Month.September, null, null, 0));
		cardDeck.add (new Card ("September3_0pts", Card.Month.September, null, null, 0));
		cardDeck.add (new Card ("October0_10pts_Ni", Card.Month.October, Card.Yaku.Ni, null, 10));
		cardDeck.add (new Card ("October1_5pts_Ho", Card.Month.October, Card.Yaku.Ho, null, 5)); //Also needs to be a part of the Chi yaku
		cardDeck.add (new Card ("October2_0pts", Card.Month.October, null, null, 0));
		cardDeck.add (new Card ("October3_0pts", Card.Month.October, null, null, 0));
		cardDeck.add (new Card ("November0_10pts", Card.Month.November, null, null, 10));
		cardDeck.add (new Card ("November1_5pts", Card.Month.November, null, null, 5));
		cardDeck.add (new Card ("November2_5pts", Card.Month.November, null, null, 5));
		cardDeck.add (new Card ("November3_Gaji", Card.Month.November, null, null, 0));
		cardDeck.add (new Card ("December0_20pts", Card.Month.December, null, null, 20));
		cardDeck.add (new Card ("December1_10pts", Card.Month.December, null, null, 10));
		cardDeck.add (new Card ("December2_0pts", Card.Month.December, null, null, 0));
		cardDeck.add (new Card ("December3_0pts", Card.Month.December, null, null, 0));
		
		//TODO: Make second constructor for when a card has two yaku groups
	}
	
}
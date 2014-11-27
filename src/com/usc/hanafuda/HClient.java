package com.usc.hanafuda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import com.usc.hanafuda.entities.Card;
import com.usc.hanafuda.entities.Card.Yaku;
import com.usc.hanafuda.entities.FieldPanel;
import com.usc.hanafuda.entities.HandPanel;
import com.usc.hanafuda.handlers.MyAssetHandler;

//TODO: How do we notify the GUI of when to change things?
		// For example, when the server sends a card
		// Maybe use enum to represent state
		// Change the state depending on what we want it to do


//TODO: store gui inside client 

public class HClient extends Thread {
	private PrintWriter pw;
	private BufferedReader br;
	private ObjectOutputStream os;
	private ObjectInputStream is;
	private ArrayList<Card> Hand = new ArrayList<Card>();
	private ArrayList<Card> Field = new ArrayList<Card>();
	private ArrayList<Card> Collection = new ArrayList<Card>();
	private ArrayList<Card> OpponentCollection = new ArrayList<Card>();
	private boolean Host = false;
	private boolean MyTurn = false;
	private int score=0;
	private int AnotherScore=0;
	private Card anotherSelectedCard;
	private Card receivedDeckCard;
	private Socket s;
	private String userName;
	private Scanner scan;
	
	
	public HClient (String hostname, int port, String userName) {	
		
		try {
			//DEBUG
			this.scan = new Scanner (System.in);
			
			this.userName = userName;
			Socket s = new Socket (hostname, port);
			this.pw = new PrintWriter (s.getOutputStream());
			this.os = new ObjectOutputStream (s.getOutputStream());
			this.br = new BufferedReader (new InputStreamReader(s.getInputStream()));
			this.is = new ObjectInputStream (s.getInputStream());
			this.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} // End of constructor
	
	public String getUserName() {
		
		return userName;
		
	}
	
	public ArrayList<Card> getHand(){
		
		return Hand;
		
	}
	
	public void sendScore(){

		sendMessage("Signal:SendScore");
		
		
		if(Host) {
			sendMessage("h"+Integer.toString(score));
		}
		
		else {
			sendMessage(Integer.toString(score));
		}
		
	}
	
	public void updateScore(){
		int tempScore=0;

		for (int i=0;  i<Collection.size(); i++){
			
			tempScore+=Collection.get(i).getValue();
				
		}
		
		score=tempScore;
		
	}
	
	public void sendMessage (String msg) {
		pw.println (msg);
		pw.flush();
	}
	
	public void sendCardToServer (Card cd) {
		try {
			// Inform server that client will be sending card
			sendMessage("Signal:SendCard");
			
			// Make sure card and message are not sent at the same time
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Send the card
			os.reset();
			os.writeObject (cd);
			os.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}		
	
	// This method checks for matches with the given card and all cards from the field 
	public ArrayList<Card> getMatchingCards (Card currentCard) { 
		ArrayList<Card> potentialMatch = new ArrayList<Card>();
		if(currentCard!=null){
		// Iterate through field
		for (int i = 0; i < Field.size(); i++) {
			// Check if card from the field is a match
			//System.out.println("Match "+currentCard.getName()+" with "+Field.get(i).getName());
			if (currentCard.isMatch (Field.get(i))) {
				potentialMatch.add (Field.get(i));
			}
		}
		}
		return potentialMatch;
	}
	
	//TODO: Need method to process turn when there is no match
	
	// This method is called when a player wants to match a card from the hand with one from the field
	// They will collect the card from the field; discard any cards whose value is 0
	public void processMatchAndRemoveCards (Card cardFromHand, Card cardFromField) {
		// Add card to collection if value > 0
		if (cardFromHand.getValue() > 0 || cardFromHand.isGaji()) {
			if (cardFromHand.isGaji()) {
				cardFromHand.setGajiMonth (cardFromField.getMonth());
			}
			Collection.add(cardFromHand);
			//TODO: Update score 
		}
		
		// Add card to collection if value > 0
		if (cardFromField.getValue() > 0) {		
			Collection.add (cardFromField);
		}
		//test
		for(int i=0;i<Collection.size();i++){
			
			
			System.out.println("Process: "+Collection.get(i).getName());
			
			
		}
		
		
		// Remove matched card from hand
		for(int i=0; i < Hand.size(); i++) {			
			if (Hand.get(i).equals(cardFromHand)) {
				Hand.remove (Hand.get(i));
			}
		}
		
		// Remove matched card from field
		for (int i = 0; i < Field.size(); i++) {			
			if (Field.get(i).equals(cardFromField)) {
				Field.remove (Field.get(i));
			}
		}
		// Send new card and field to server
		
		
		updateScore();
		
		sendScore();
		
		
		
		sendField();
		
		sendCollection();
		
	}
	
	public void sendSelectedCard(Card cd){
		
		
		sendMessage("Signal:SendSelectedCard");
		
		sendCardToServer(cd);
		
		
		
		
	}
	
	
	public void endTurn(){

		sendMessage("Signal:EndTurn");
		
		MyTurn=false;
		
		
	}
	
	public void addDrawnCardToField(Card cd){
		
	
		Field.add(cd);
		
		sendField();
		sendMessage ("Signal:UpdateFieldFinished");
		
		
	}
	
	
	
	public void addHandCardToField(Card cd) {//for no match
		Field.add(cd);
		
		for(int i=0;i<Hand.size();i++){
			
			if(Hand.get(i).equals(cd))
			Hand.remove(cd);
			
		}
		
		sendField();
		FieldPanel.refreshField(); // added to refresh GUI
		sendMessage ("Signal:UpdateFieldFinished");
		
	}
	
	public void getCardFromDeck(){
		
		
		sendMessage("Signal:GetCardFromDeck");
		
		
		

		
	}
	public ArrayList<Card> getField(){
		
		
		return Field;
		
	}
	public synchronized void sendField () {
		sendMessage("Signal:UpdateField");
		
		for (int i = 0; i < Field.size(); i++) {
			System.out.println("send field "+i);
			sendMessage ("Signal:SendField");
			sendCardToServer (Field.get (i));
		}
		
		
		// What happens in the server when the first and last messages are sent?
	}
	
	public synchronized void sendCollection () {
		sendMessage ("Signal:UpdateCollection");
		for (int i=0; i < Collection.size(); i++) {
			System.out.println("sending "+i+" collection");
			sendMessage ("Signal:SendCollection");
			sendCardToServer (Collection.get (i));
		}
		
		sendMessage ("Signal:UpdateCollectionFinished");

		// What happens in the server when the first and last messages are sent?
	}
	public void waitForResponse(){
		
		try {
			this.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public int calculateFinalScore () {
		int finalScore = 0;
		
		// Check for gaji in collection
		for (Card cc : Collection) {
			if (cc.isGaji()) {
				// Check if field has any cards from gaji month
				for (Card fc : Field) {
					//TODO: May want to show cards being added to hand
					if (fc.getMonth() == cc.getGajiMonth()) {
						Collection.add(fc);
					}
				}
			}
		}
		
		// Count value of all cards in collection
		for (Card cd : Collection) {
			finalScore += cd.getValue();
		}
		
		// Check for yakus
		int I = 0;
		int Ro = 0;
		int Ha = 0;
		int Ni = 0;
		int Ho = 0;
		int He = 0;
		int To = 0;
		int Chi = 0;
		
		for (Card cd : Collection) {
			if (cd.getYaku1() != null && cd.getYaku1() == Yaku.I) {
				I++;
			}
			if (cd.getYaku2() != null && cd.getYaku2() == Yaku.I) {
				I++;
			}
			if (cd.getYaku1() != null && cd.getYaku1() == Yaku.Ro) {
				Ro++;
			}
			if (cd.getYaku2() != null && cd.getYaku2() == Yaku.Ro) {
				Ro++;
			}
			if (cd.getYaku1() != null && cd.getYaku1() == Yaku.Ha) {
				Ha++;
			}
			if (cd.getYaku2() != null && cd.getYaku2() == Yaku.Ha) {
				Ha++;
			}
			if (cd.getYaku1() != null && cd.getYaku1() == Yaku.Ni) {
				Ni++;
			}
			if (cd.getYaku2() != null && cd.getYaku2() == Yaku.Ni) {
				Ni++;
			}
			if (cd.getYaku1() != null && cd.getYaku1() == Yaku.Ho) {
				Ho++;
			}
			if (cd.getYaku2() != null && cd.getYaku2() == Yaku.Ho) {
				Ho++;
			}
			if (cd.getYaku1() != null && cd.getYaku1() == Yaku.To) {
				To++;
			}
			if (cd.getYaku2() != null && cd.getYaku2() == Yaku.To) {
				To++;
			}
			if (cd.getYaku1() != null && cd.getYaku1() == Yaku.Chi) {
				Chi++;
			}
			if (cd.getYaku2() != null && cd.getYaku2() == Yaku.Chi) {
				Chi++;
			}
		}
		
		if (I == 3) {
			finalScore += 50;
		}
		if (Ro == 3) {
			finalScore += 50;
		}
		if (Ha == 3) {
			finalScore += 50;
		}
		if (Ni == 3) {
			finalScore += 50;
		}
		if (Ho == 3) {
			finalScore += 50;
		}
		if (He == 3) {
			finalScore += 50;
		}
		if (To == 3) {
			finalScore += 50;
		}
		if (Chi == 3) {
			finalScore += 50;
		}
		
		return finalScore;
	}
	
	
	public void run() {
		try {	
			while (true) {
				//System.out.println(1);
				String line = br.readLine();
				//System.out.println(2);
				System.out.println ("Client: Received message from Server: "+ line);
				
				// Host has the "start game" button while others have "prepared" button
				// NOTE: What does prepared do?
				
				if (line.equals ("Signal:Host")) {
					Host = true;
				}
				
				// Receive hand
				if (line.equals ("Signal:SendHand")) {
					// Read one more line message to confirm the receiving card process
					String nextMessage=br.readLine();
						
					if (nextMessage.equals ("Signal:SendCard")) {
						try {
							Card received = (Card) is.readObject();
							
							// Add to hand
							Hand.add(received);
							
							//DEBUG
							System.out.println ("I am the host: " + Host);
							System.out.println ("Hand Card received: " + received.getName());
							System.out.println ("I have <" + Hand.size() + "> cards in Hand.");
						} catch (ClassNotFoundException e) {
							System.out.println(e.getMessage());
							e.printStackTrace();
						}
					}
						
				} // End of receive hand block
				
				// Receive field
				if (line.equals ("Signal:SendField")) {
					// Read one more line message to confirm the receiving card process
					String nextMessage = br.readLine();
					
					if (nextMessage.equals ("Signal:SendCard")) {
						try {
							Card received = (Card) is.readObject();
							
							// Add to field
							Field.add(received);
						
							//DEBUG
							System.out.println ("Field Card received: " + received.getName());
							System.out.println ("I have <" + Field.size() + "> cards in Field");
						
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				} // End of receive field block
				
				//TODO: Collections that are sent should be from other players
				if (line.equals ("Signal:SendCollection")) {
					// Read one more line message to confirm the receiving card process
						String nextMessage = br.readLine();
						
						if (nextMessage.equals ("Signal:SendCard")) {		
							try {
								Card received = (Card) is.readObject();
	
								//TODO: Add received card to the collection of the correct opponent
								
								//DEBUG
								System.out.println ("Collection Card received: " + received.getName());
								
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
						}
				}
				//update opponent collection
				
				if (line.equals ("Signal:SendOpponentCollection")) {
					String nextMessage = br.readLine();
					
					if (nextMessage.equals ("Signal:SendCard")) {		
						try {
							Card received = (Card) is.readObject();
							OpponentCollection.add(received);
							//TODO: Add received card to the collection of the correct opponent
							
							//DEBUG
							System.out.println ("Collection Card received: " + received.getName());
							
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
					
				}
				// Update field
				if (line.equals ("Signal:UpdateField")) {
					Field.clear();
					
				}
				
				// Update collection of opponent
				if (line.equals ("Signal:UpdateOpponentCollection")) {
					OpponentCollection.clear();
					
					//TODO: Clear collection of correct opponent
				}
				
				// Determine the player's ability to act
				if (line.equals ("Signal:Turn")) {
						MyTurn = true;
						System.out.println("my turn");
						System.out.println("My Hand cards now:");
						for(int i=0;i<Hand.size();i++){
						System.out.println("<"+i+">"+Hand.get(i).getName());		
						}
						System.out.println();
						System.out.println("Field cards now:");
						for(int i=0;i<Field.size();i++){
						System.out.println("<"+i+">"+Field.get(i).getName());		
						}
						
						
						System.out.println("Select a hand card to play");
						//
//						Scanner scan=new Scanner(System.in);
//						int choice=scan.nextInt();
//						Card playing=Hand.get(choice);
						
						while(HandPanel.returnCurrentSelectedHandCard() ==null){
							System.out.println("waiting for a hand card to be selected");
						}
						
						Card playing = HandPanel.returnCurrentSelectedHandCard();
						//

						ArrayList<Card> temp=getMatchingCards(playing);
						System.out.println("Choose "+playing.getName());

						
//						if(temp.size()==0){//no match need put the card onto the field and draw new one
//							
//							addHandCardToField(playing);
//							this.waitForResponse();				
//							
//							
//						}
						while(HandPanel.returnNumMatchingCards()==-1){
							System.out.println("waiting to receive number of matching cards");
						}
						if(HandPanel.returnNumMatchingCards()==0){//no match need put the card onto the field and draw new one
							
							addHandCardToField(playing); // changed this function a little bit to resend field to GUI - Xiaohan
							this.waitForResponse();				
							
							
						}
						
						else{//match! need process
							System.out.println("possible matches for this card:");
							for(int i=0;i<temp.size();i++){
								
								System.out.println("<"+i+">"+temp.get(i).getName());
								
							}
							
							
							System.out.println("Select a card from deck to match");
							Scanner scan2=new Scanner(System.in);
							
							int selectMatchedCard=scan2.nextInt();

							this.processMatchAndRemoveCards(playing, temp.get(selectMatchedCard));
							
							this.waitForResponse();	
						}
						

						getCardFromDeck();
						
						line=br.readLine();
						if (line.equals ("Signal:SendCardFromDeck")) {
							String nextMessage = br.readLine();
							
							if (nextMessage.equals ("Signal:SendCard")) {
								try {
									Card received = (Card) is.readObject();				
									
									//TODO: Notify GUI
									
									this.receivedDeckCard=received;
									
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
							}
						}
						
						System.out.println("I draw "+receivedDeckCard.getName()+" from deck");
						
						this.waitForResponse();
						ArrayList<Card> temp2=getMatchingCards(receivedDeckCard);
						
						if(temp2.size()==0){//still no match
							
							
							this.addDrawnCardToField(receivedDeckCard);
							
							this.waitForResponse();
							
							
						}
						
						else{
							System.out.println("possible matches for this card:");
							for(int i=0;i<temp2.size();i++){
								
								System.out.println("<"+i+">"+temp2.get(i).getName());
								
							}
							
							
							System.out.println("Select a card from deck to match");
							Scanner scan2=new Scanner(System.in);
							
							int selectMatchedCard=scan2.nextInt();

							this.processMatchAndRemoveCards(receivedDeckCard, temp2.get(selectMatchedCard));
							
							this.waitForResponse();
							
						}
						
						System.out.println("My turn is over!!!!!!!!!!");
						System.out.println();
						System.out.println("My Hand cards now:");
						for(int k=0;k<Hand.size();k++){
						System.out.println("<"+k+">"+Hand.get(k).getName());		
						}
						System.out.println();
						System.out.println("Field cards now:");
						for(int k=0;k<Field.size();k++){
						System.out.println("<"+k+">"+Field.get(k).getName());		
						}
						System.out.println();
						System.out.println("Collection cards now:");
						for(int k=0;k<Collection.size();k++){
						System.out.println("<"+k+">"+Collection.get(k).getName());		
						}
						
						System.out.println();
						System.out.println("My Score now:"+score);
						endTurn();
						System.out.println("My turn is ended");	
						
						HandPanel.resetNumMatchingCards(); // added by X
				}
				
				
				
				// Receive card from deck
				if (line.equals ("Signal:SendCardFromDeck")) {
					String nextMessage = br.readLine();
					
					if (nextMessage.equals ("Signal:SendCard")) {
						try {
							Card received = (Card) is.readObject();				
							
							//TODO: Notify GUI
							
							this.receivedDeckCard = received;
							
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
				
				if(line.equals("Signal:ScoreOfAnother")){
					
						String nextMessage=br.readLine();
						
						AnotherScore=Integer.valueOf(nextMessage);
						
						System.out.println("Opponent Score is: "+AnotherScore);
					
					
				}
				
				if(line.equals("Signal:ReceiveSelectedCard")){
					String nextMessage = br.readLine();
					
					if (nextMessage.equals ("Signal:SendCard")) {		
						try {
							Card received = (Card) is.readObject();

							//TODO: Add received card to the collection of the correct opponent
							
							this.anotherSelectedCard=received;
							System.out.println("Opponent is currently playing card :"+anotherSelectedCard.getName());
							
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
					
				}
				
				
				if (line.equals ("Signal:GameEnded")) {  
					//TODO: print to GUI that the game ended and show final score
				}
				
				if (line.equals ("Signal:YourTurn")) {
					// JENNY-NOV24
					MyTurn = true;
					
					// What does this do? Is it different from Signal:Turn
					
					//TODO: Enable player's access in GUI
					// player chooses a card and system checks if there are any matching cards in the field
					//1. if no match, then put the card in the field
					//2. if there is one match, add both of these cards to collection
					//3. if there is more than one match, let the player choose which card, along with the card chosen, should be added to collection
						// if there is a match at all, then player should draw another card from a deck and system compares that card with the cards on field
						//repeat steps 1,2,3 once
				}
				
				
			} // End of while(true) block
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // End of run() block
	
	public static void main (String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("what is your username: ");
		String playerName = scan.nextLine();
		HClient h = new HClient("localhost",6789, playerName);
		MyAssetHandler.load();
		MyGame g = new MyGame(h);
	}
}

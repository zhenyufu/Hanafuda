package com.usc.hanafuda;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import com.usc.hanafuda.entities.Card;
import com.usc.hanafuda.entities.Deck;

//TODO: Check if all four cards from a month are in the field at the beginning

public class HServer {
	public  Vector<ServerThread> vServerThread = new Vector<ServerThread> ();
	private Deck deck;
	public ArrayList<Card> Field;
	public ArrayList<Card> Collection;
	public ServerThread currentPlayer;
	
	public Card currentPlayedCard;
	
	public int hostScore=0;
	public int guestScore=0;
	
	
	public HServer (int port) {
		try {
			ServerSocket ss = new ServerSocket(port);

			while (true) {
				//DEBUG
				if (vServerThread.size() == 2) {
					onStartOfGame();
				}
				
				Socket s = ss.accept();
				ServerThread st = new ServerThread (s, this);
				vServerThread.add (st);
				st.start();	
				
				if (vServerThread.size() == 1) {
					//The first one connected is default to be the host
					sendMessage ("Signal:Host", st);
				}
				
				//DEBUG
				System.out.println ("Client has connected to the server.");
				
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	
	// Send a message to a specific client
	public void sendMessage (String msg, ServerThread current) {
		//DEBUG
		//System.out.println ("Server: Sending message to client: " + msg);
		
		current.send (msg);
	}
	
	
	// Send a card to a specific client 
	public void sendCard (Card cd, ServerThread current) {
		//DEBUG
		//System.out.println ("Server: Sending card to client: " + cd.getName());
		
		current.sendCard (cd);
	}
	
	
	// Remove disconnected client
	public void removeDisconnection (ServerThread disconnected) {
		vServerThread.remove (disconnected);
	}

	
	// This method is called when server receives message to start the game
	// NOTE: The player will send the message "Signal:StartGame"
	public void onStartOfGame () {
		// Initialize Deck object
		deck = new Deck();
		
		//DEBUG - checked
		//System.out.println ("The initial deck is: ");
		//deck.printDeck();
		//System.out.println();
		//System.out.println();
		
		// Initialize field
		int numInitialFieldCard = 0;
		int numInitialHandCard = 0;
		
		if (vServerThread.size() == 2) {
			numInitialFieldCard = 8;
			numInitialHandCard = 8;
		}
		
		Field = new ArrayList (numInitialFieldCard);
		
		for (int i = 0; i < numInitialFieldCard; i++) {
			Field.add (deck.drawCard());
			// Deck should now be 40
		}
		
		//DEBUG - checked
		//System.out.println ("Initial field is: ");
		//for (Card c : Field) {
		//	c.printName();
		//}
		//System.out.println();
		//System.out.println();
		
		// Send correct amount of cards to each client
		for(int i = 0; i < vServerThread.size(); i++) { // For each client
			ServerThread current = vServerThread.get (i);
			
			for (int j = 0; j < numInitialHandCard; j++) {
				sendMessage ("Signal:SendHand", current);
			
				// Make sure the messages are not sent to clients at the same time as the cards 
				try {
					Thread.sleep (100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
				sendCard (deck.drawCard(), current);
			}
		} // Deck should be 24
		
		// Send field to each client		
		for (int i = 0; i < vServerThread.size(); i++) { // For each client
			ServerThread current = vServerThread.get (i);
			
			for (int j = 0; j < numInitialFieldCard; j++) {
				// Signal clients that the next card will be for the field
				sendMessage ("Signal:SendField", current);
				
				// Make sure the messages are not sent to clients at the same time as the cards 
				try {
					Thread.sleep (100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				sendCard (Field.get(j), current);
			}
		}
		
		//DEBUG
		System.out.println ("Deck after giving out cards: ");
		deck.printDeck();
		System.out.println();
		System.out.println();
		
		// Notify first player of his/her turn; as default, the host is the first one to act
		ServerThread host = vServerThread.get (0);
		sendMessage ("Signal:Turn", host);
		currentPlayer = host;
		
	} //End of OnStartOfGame() block
	
	// This method will update the field based on the card sent by the client 
	public void updateField () {
		//TODO: Update field
		
		// Send the updated field to each client
		for (int i = 0; i < vServerThread.size(); i++) {
			ServerThread current = vServerThread.get (i);
			
			sendMessage ("Signal:UpdateField", current);
		
			// Make sure the messages are not sent to clients at the same time as the cards 
			for(int j=0; j < Field.size(); j++) {
				// Signal clients that the next few cards will be the field.
				sendMessage ("Signal:SendField", current);
				
				// Make sure the messages are not sent to clients at the same time as the cards 
				try {
					Thread.sleep (100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				sendCard (Field.get (j), current);
			}			
		}
		
	} // End of updateField() block
	
	//UpdateCollection: When will this be called? Is this called when a player's collections is updated?
			// We need to keep track of the current player's collection
			// Maybe use a variable that is a list of cards; we should clear it each time it is a new player's turn
			// Do not send the field in this method
			// Also need a way to identify players from each other when we send it
			// When we send the updated collection, the client receiving it needs to know it is the collection of the specific player
			// For example, if it is Player 2's turn, when we send updated collections, we need to inform the other players that it belongs to Player 2

	
	// This method will send card from the deck to the client
	public void sendCardFromDeck () {
		// Draw card from deck
		Card drawnCard = deck.drawCard();
		
		// Inform the player that a card will be sent
		sendMessage ("Signal:SendCardFromDeck", currentPlayer); 
		
		sendCard(drawnCard, currentPlayer);		
	}
	
	
	public void nextPlayer(){
		
		if(currentPlayer.equals(vServerThread.get(0))){
			
			currentPlayer=vServerThread.get(1);
			
		}
		
		else if(currentPlayer.equals(vServerThread.get(1))){
			
			currentPlayer=vServerThread.get(0);
			
		}
		
		
		
		
	}
	
	public Boolean GameIsOver(){
		
		
		if(deck.numCardsLeft()==8) return true;
		
		
		
		return false;
		
		
		
	}

	
	public static void main(String [] args){
		new HServer(6789);
	}	
}

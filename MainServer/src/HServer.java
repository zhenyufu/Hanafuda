import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;


public class HServer {
	private Vector<ServerThread> vServerThread=new Vector<ServerThread>();
	//Field variable
	private Deck deck;
	private ArrayList<Card> Field;
	public HServer(int port){
		try{
			ServerSocket ss=new ServerSocket(port);
			int Randomincrement=0;
			while(true){
				Socket s=ss.accept();
				ServerThread st=new ServerThread(s, this);
				vServerThread.add(st);
				st.start();	
				
				if(vServerThread.size()==1){//The first one connected is default to be the host
					sendMessage("Signal:Host", st);
				}
				
				//Server sends message to all clients example
				//sendMessage("yolo",st);
				//Server sends card to all clients example
				//test: send a random card to each client
				//Card foo=new Card ("January0_20pts_Ro", Card.Month.January, Card.Yaku.Ro, null, 20);
				//sendCard(foo, st);
				//Randomincrement++;
			}
		}
	
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	//send message to specific client
	public void sendMessage(String msg, ServerThread current){
		current.send(msg);
	}
	//send card to specific client 
	public void sendCard(Card cd, ServerThread current){
		current.sendCard(cd);
	}
	//remove disconnected client
	public void removeDisconnection(ServerThread disconnected){
		vServerThread.remove(disconnected);
	}
	

	//TODO: This method is called when server receives message to start the game
	//NOTE: The player will send the message "Signal:StartGame"
	public void onStartOfGame () {
		//TODO: Initialize Deck object
		deck=new Deck();
		//TODO: Initialize whatever is being used to store field
		Field=new ArrayList(8);
		
		for(int i=0;i<8;i++)
		Field.add(deck.drawCard());//deck should be 48-8=40 now
		
		
		//TODO: Use sendCard() and Deck to send correct amount of cards to each client
		for(int i=0;i<vServerThread.size();i++){//every person
			ServerThread current=vServerThread.get(i);
			
			for(int j=0;j<8;j++){
			sendMessage("Signal:SendHands",current);
			
			//make sure the messages are not send to clients at the same time as the cards 
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				sendCard(deck.drawCard(),current);
			
			}
			
		}//deck should be 40-number of player*8 now
		
		//TODO: Send field to each client
		
		for(int i=0;i<vServerThread.size();i++){//every person
			ServerThread current=vServerThread.get(i);
			//signal clients that the next few cards will be the field.
			
			for(int j=0;j<Field.size();j++){
				
				//signal clients that the next few cards will be the field.
				sendMessage("Signal:SendField",current);
				
				//make sure the messages are not send to clients at the same time as the cards 
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				sendCard(Field.get(j),current);
			
			}
			
		}
		
		//TODO: Notify first player of his/her turn
		
		//As default, the host is the first one to act
		ServerThread host=vServerThread.get(0);
		sendMessage("Signal:Turn", host);
		
	}//OnStartOfGame block
	
	//This method will update the field based on the card sent by the client 
	public void updateField () {
		//TODO: Update field; send updated field to each client
		//send the field again to each client
		for(int i=0;i<vServerThread.size();i++){
			
		ServerThread current=vServerThread.get(i);
			
		sendMessage("Signal:UpdateField",current);
		
		//make sure the messages are not send to clients at the same time as the cards 
		for(int j=0;j<Field.size();j++){
			
			//signal clients that the next few cards will be the field.
			sendMessage("Signal:SendField",current);
			
			//make sure the messages are not send to clients at the same time as the cards 
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			sendCard(Field.get(j),current);
		
		}
		
		
	}
		
	}//updateField block
	
	
	//This method will send card from deck to the client
	public void sendCardFromDeck () {
		//TODO: Draw card from deck
		//TODO: If the card has a match in the field, send this card and its match to the client;
				//If not, add this card to the field
		//TODO: Send updated field to each client
	}
	
	//This method is called when the client notifies the server of the end of turn
	public void onEndOfTurn () {
		//TODO: Check for end of game
		//TODO: If the game hasn't ended, notify next player of turn
	}
	
	public static void main(String [] args){
		new HServer(6789);
	}
	
}

//example card

/*class Card implements Serializable{
	private int value;
	public Card(int value ){
		this.value=value;
	}
	public void print(){
		System.out.println("this card has value of "+ value);
	}
}*/

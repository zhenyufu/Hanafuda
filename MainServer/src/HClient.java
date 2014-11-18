import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class HClient extends Thread{
	private PrintWriter pw;
	private BufferedReader br;
	private ObjectOutputStream os;
	private ObjectInputStream is;
	private ArrayList<Card> Hands=new ArrayList<Card>();
	private ArrayList<Card> Field=new ArrayList<Card>();
	private ArrayList<Card> Collection=new ArrayList<Card>();
	private boolean Host=false;
	private boolean MyTurn=false;
	
	public HClient(String hostname, int port){
		
		try {
			
			Socket s=new Socket(hostname, port);
			this.pw=new PrintWriter(s.getOutputStream());
			this.os=new ObjectOutputStream(s.getOutputStream());
			this.br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.is = new ObjectInputStream(s.getInputStream());
			this.start();
			Scanner scan=new Scanner(System.in);
			while(true){
				String line=scan.nextLine();
				System.out.println(line);
				pw.println(line);
				pw.flush();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void sendMessage(String msg){
		pw.println(msg);
		pw.flush();
	}
	
	public void sendCardToServer(Card cd){
		try {
			
			pw.println("Signal:SendCard");
			pw.flush();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			os.reset();
			os.writeObject(cd);
			os.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
		
	
	public ArrayList<Card> getMatchingCards(Card currentCard){//check all cards from field based on the current card you selected 
		
		ArrayList<Card> potentialMatch=new ArrayList<Card>();
		
		for(int i=0; i<Field.size();i++){
			
			if(currentCard.isMatch(Field.get(i))){
				potentialMatch.add(Field.get(i));
			}
			
		}
		
		return potentialMatch;
	}
	
	public void processMatchRemoveCards(Card cardFromHand, Card cardFromField){
		
		if(cardFromHand.getValue()>0 ){
			
			
			Collection.add(cardFromHand);//TODO:need to update score 
			
			
		}
		if(cardFromField.getValue()>0){
			
			
			Collection.add(cardFromField);
			
		}
		
		//remove matched card from hands
		for(int i=0;i<Hands.size();i++){
			
			
			if(Hands.get(i).equals(cardFromHand)){
				
				Hands.remove(Hands.get(i));
				
			}	
			
		}
		
		//remove matched card from fields
		for(int i=0;i<Field.size();i++){
			
			
			if(Field.get(i).equals(cardFromField)){
				
				Field.remove(Field.get(i));
				
			}
			
		}
		
		sendField();
		
		sendCollection();
	}
	
	public void sendField(){
		
		sendMessage("Signal:UpdateField");
		
		for(int i=0;i<Field.size();i++){
			
			sendMessage("Signal:SendField");
			sendCardToServer(Field.get(i));
		
		}
		
		sendMessage("Signal:UpdateFieldFinished");
	}
	
	public void sendCollection(){
		
		sendMessage("Signal:UpdateCollection");
		
		for(int i=0;i<Collection.size();i++){
			
			sendMessage("Signal:SendCollection");
			sendCardToServer(Collection.get(i));
		
		}
		
		sendMessage("Signal:UpdateCollectionFinished");
				
	}
	
	
	public void run(){
		try {
			
		while(true){
				
		String line = br.readLine();
		System.out.println("Client received: "+line);
		
		//Host has the "start game" button while others have "prepared" button
		
		if(line.equals("Signal:Host")){
			Host=true;
		}
		
		//receive hands
		if(line.equals("Signal:SendHands")){
			
			//read one more line message to confirm the receiving card process
			
				String nextMessage=br.readLine();
				
				if(nextMessage.equals("Signal:SendCard")){
				try {
					
					Card received = (Card) is.readObject();
					//add to hand
					Hands.add(received);
					//for test
					System.out.println("Hands Card received");
					received.print();
					System.out.println("I have <"+Hands.size()+"> cards in hand");
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
				
		}//receive hands block
		
		//receive field
		if(line.equals("Signal:SendField")){
		
		//read one more line message to confirm the receiving card process
		
			String nextMessage=br.readLine();
			
			if(nextMessage.equals("Signal:SendCard")){
			
			try {
				
				Card received = (Card) is.readObject();
				//add to field
				Field.add(received);
				//for test
				System.out.println("Field Card received");
				received.print();
				System.out.println("I have <"+Field.size()+"> cards in Field");
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}//receive field block
		
		if(line.equals("Signal:SendCollection")){
			
			//read one more line message to confirm the receiving card process
			
				String nextMessage=br.readLine();
				
				if(nextMessage.equals("Signal:SendCard")){
				
				try {
					
					Card received = (Card) is.readObject();
					//add to field
					Collection.add(received);
					//for test
					System.out.println("Collection Card received");
					received.print();
					System.out.println("I have <"+Collection.size()+"> cards in Collection");
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		//update field
		if(line.equals("Signal:UpdateField")){
			Field.clear();
		}//update field block
		
		if(line.equals("Signal:UpdateCollection")){
			Field.clear();
		}
		
		//Determine the player's ability to act
		if(line.equals("Signal:Turn")){
			
				MyTurn=true;
				
		}
		
		if(line.equals("Signal:SendCardFromDeck")){
			String nextMessage=br.readLine();
			
			if(nextMessage.equals("Signal:SendCard")){
			try {
				
				Card received = (Card) is.readObject();
				//TODOS Handle card from deck
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		}
			
	}//while(true) block
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void main(String[] args){
		/*Scanner scan=new Scanner (System.in);
		System.out.println("What is the name of the server?");
		String hostname=scan.nextLine();
		System.out.println("port?");
		int port=scan.nextInt();*/
		new HClient("localhost",6789);
	}
}
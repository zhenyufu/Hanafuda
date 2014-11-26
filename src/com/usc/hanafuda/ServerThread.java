package com.usc.hanafuda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.usc.hanafuda.entities.Card;
import com.usc.hanafuda.entities.Deck;

public class ServerThread extends Thread{
	private Socket s;
	private HServer hs;
	private PrintWriter pw;
	private ObjectOutputStream os;
	private ObjectInputStream is;
	private int Pendingscore;
	private ServerThread PendingTarget;
	public ServerThread(Socket s, HServer hs){
		this.s=s;
		this.hs=hs;
		try {
			
			this.pw=new PrintWriter(s.getOutputStream());
			this.os=new ObjectOutputStream(s.getOutputStream());
			this.is=new ObjectInputStream(s.getInputStream());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void sendCard(Card cd){
		try {
			
			pw.println("Signal:SendCard");
			pw.flush();
			
			try {
				this.sleep(10);
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
	
	
	public void send(String message){
		pw.println(message);
		pw.flush();
		
	}
	
	public void run (){
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			while(true){
				String line=br.readLine();
				//nothing more to read, close the socket
				if(line==null){
					s.close();
					hs.removeDisconnection(this);
					break;
				}
				//
				if(line.equals("Signal:StartGame")){
					
					hs.onStartOfGame();
					
				}
				
				
				if(line.equals("Signal:SendField")){
					
					//read one more line message to confirm the receiving card process
					
						String nextMessage=br.readLine();
						if(nextMessage.equals("Signal:SendCard")){
						
						try {
							
							Card received = (Card) is.readObject();
							//add to field
							hs.Field.add(received);
							//for test
							//System.out.println("Field Card received");
							received.printName();
							System.out.println("I have <"+hs.Field.size()+"> cards in Field");
							
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
				
				if(line.equals("Signal:SendCollection")){
					
					//read one more line message to confirm the receiving card process
					
						String nextMessage=br.readLine();
						System.out.println("next Message: "+nextMessage);
						
						if(nextMessage.equals("Signal:SendCard")){
						
						try {
							
							Card received = (Card) is.readObject();
							//add to field
							hs.Collection.add(received);
							//for test
							//System.out.println("Field Card received");
							received.printName();
							System.out.println("I have <"+hs.Collection.size()+"> cards in Collection");
							
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
				
				
				if(line.equals("Signal:UpdateField")){
					
					hs.Field.clear();
					
				}
				
				if(line.equals("Signal:UpdateCollection")){
					
					hs.Collection.clear();
					
				}
				
				if(line.equals("Signal:UpdateFieldFinished")){
					ServerThread oppo=null;
					for(int i=0;i<hs.vServerThread.size();i++){
						if(!hs.vServerThread.get(i).equals(this)){
							
							oppo=hs.vServerThread.get(i);
							
							
						}
						
						
					}
					hs.updateField(oppo);
					
					/*try {
						this.sleep(400);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					*/
				}
				
				if(line.equals("Signal:UpdateCollectionFinished")){
					//find opponent 
					ServerThread oppo=null;
					for(int i=0;i<hs.vServerThread.size();i++){
						if(!hs.vServerThread.get(i).equals(this)){
							
							oppo=hs.vServerThread.get(i);
							
							
						}
						
						
					}
					hs.updateField(oppo);
					try {
						this.sleep(400);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					hs.updateOpponentCollection(oppo);
					try {
						this.sleep(400);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					hs.sendMessage("Signal:ScoreOfAnother", PendingTarget);
					try {
						this.sleep(400);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					hs.sendMessage(Integer.toString(Pendingscore), PendingTarget);
					
				}
				if(line.equals("Signal:GetCardFromDeck")){
					
					hs.sendCardFromDeck();
					
				}
				
				if(line.equals("Signal:EndTurn")){
					
					if(!hs.GameIsOver()){
						hs.nextPlayer();
						hs.sendMessage("Signal:Turn", hs.currentPlayer);
						}
					
					else{
						
						
						//TODO: Final scoring
						//TODO: End of game
						
						
						
					}
					
					
				}
				
				if(line.equals("Signal:SendScore")){
					System.out.println("score!");
					String nextMessage=br.readLine();
					
					if(nextMessage.charAt(0)=='h'){
						
						
						
						int score=Integer.valueOf(nextMessage.substring(1, nextMessage.length()));
						
						hs.hostScore=score;
						
						Pendingscore=score;
						
						System.out.println("set host score to: "+score);
						ServerThread another=hs.vServerThread.get(1);
						
						PendingTarget=another;
						//hs.sendMessage("Signal:ScoreOfAnother", another);
						
						//hs.sendMessage(Integer.toString(score), another);
					}
					
					else{
						
						
						int score=Integer.valueOf(nextMessage);
						
						hs.guestScore=score;
						
						Pendingscore=score;
						System.out.println("set other score to: "+score);
						ServerThread another=hs.vServerThread.get(0);
						
						PendingTarget=another;
						//hs.sendMessage("Signal:ScoreOfAnother", another);
						
						//hs.sendMessage(Integer.toString(score), another);
						
					}
					
				
					
				}
				
				if(line.equals("Signal:SendSelectedCard")){
					
					
					String nextMessage=br.readLine();
					
					if(nextMessage.equals("Signal:SendCard")){
					
					try {
						
						Card received = (Card) is.readObject();
						
						hs.currentPlayedCard=received;
						
						ServerThread another=null;
						
						for(int i=0 ;i<hs.vServerThread.size();i++){
							
							if(!hs.vServerThread.get(i).equals(hs.currentPlayer)){
								
								another=hs.vServerThread.get(i);
							}
						
						}
						hs.sendMessage("Signal:ReceiveSelectedCard", another);
						hs.sendCard(hs.currentPlayedCard, another);
						
						
						
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
					
					
					
				}
				
				
				
				System.out.println("received message: "+line);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

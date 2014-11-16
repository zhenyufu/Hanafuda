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
	private ObjectInputStream is;
	private ArrayList<Card> Hands=new ArrayList<Card>(8);
	private ArrayList<Card> Field=new ArrayList<Card>(8);
	private boolean Host=false;
	private boolean MyTurn=false;
	
	public HClient(String hostname, int port){
		
		try {
			
			Socket s=new Socket(hostname, port);
			this.pw=new PrintWriter(s.getOutputStream());
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
		
		//update field
		if(line.equals("Signal:UpdateField")){
			Field.clear();
		}//update field block
		
		//Determine the player's ability to act
		if(line.equals("Signal:Turn")){
			
				MyTurn=true;
				
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
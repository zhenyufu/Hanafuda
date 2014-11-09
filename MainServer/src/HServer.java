import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class HServer {
	private Vector<ServerThread> vServerThread=new Vector<ServerThread>();
	
	public HServer(int port){
		try{
			ServerSocket ss=new ServerSocket(port);
			int Randomincrement=0;
			while(true){
				Socket s=ss.accept();
				ServerThread st=new ServerThread(s, this);
				vServerThread.add(st);
				st.start();	
				
				//Server sends message to all clients example
				sendMessage("yolo",st);
				//Server sends card to all clients example
				//test: send a random card to each client
				Card foo=new Card(Randomincrement);
				sendCard(foo, st);
				Randomincrement++;
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
	
	
	public static void main(String [] args){
		new HServer(6789);
	}
	
}

//example card

class Card implements Serializable{
	private int value;
	public Card(int value ){
		this.value=value;
	}
	public void print(){
		System.out.println("this card has value of "+ value);
	}
}

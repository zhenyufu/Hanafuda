import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class ServerThread extends Thread{
	private Socket s;
	private HServer hs;
	private PrintWriter pw;
	private ObjectOutputStream os;
	
	public ServerThread(Socket s, HServer hs){
		this.s=s;
		this.hs=hs;
		try {
			
			this.pw=new PrintWriter(s.getOutputStream());
			this.os=new ObjectOutputStream(s.getOutputStream());

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
				
				System.out.println("received message: "+line);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

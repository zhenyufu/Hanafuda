import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class HClient extends Thread{
	private PrintWriter pw;
	private BufferedReader br;
	private ObjectInputStream is;
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
			
			if(line.equals("Signal:SendCard")){
				
				try {
					
					Card received = (Card) is.readObject();
					System.out.println("Card received");
					received.print();
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			
			}
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
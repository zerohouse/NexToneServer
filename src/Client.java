import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	
	
	

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("192.168.0.17",13333);
			Receiver receiver = new Receiver(socket);
			Thread thread = new Thread(receiver);
			thread.start();

			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);

			Sender sender = new Sender(socket);
			while (true) {
				System.out.println("말하셈");
				sender.sendMessage(scan.nextLine());
				System.out.println("메시지 보냄");
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}

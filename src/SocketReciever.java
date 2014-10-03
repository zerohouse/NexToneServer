import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketReciever implements Runnable{
	int port = 13333;
	

	Socket socket;
	
	@SuppressWarnings("resource")
	@Override
	public void run() {
		try {
			ServerSocket serversocket = new ServerSocket(port);
			ConnectedSocket connectedSocket;
			Thread thread;
			while(true){
				System.out.println("클라이언트 받음");
				socket = serversocket.accept();
				connectedSocket = new ConnectedSocket(socket);
				thread = new Thread(connectedSocket);
				ConnectedSocket.add(connectedSocket);
				thread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}
}

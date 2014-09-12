import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private static final int STATUS_START = 0;
	private static final int FIRST = 1;
	private static final int SECOND = 2;

	public static void main(String[] args) {

		// 포트를 지정한다.
		int port = 13333;
		
		try {
			ServerSocket serversocket = new ServerSocket(port);
			System.out.println("기다림...ver2");

			while (true) {
				makeConnection(serversocket, null);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void makeConnection(ServerSocket serversocket, Socket socket)
			throws IOException {
		Socket socket1;
		Socket socket2;
		Thread thread1;
		Thread thread2;
		if (socket == null)
			socket1 = serversocket.accept();
		else
			socket1 = socket;

		System.out.println("하나받음" + socket1.getInetAddress().toString());

		socket2 = serversocket.accept();
		if (!isAvailable(socket1)) {
			makeConnection(serversocket, socket2);
			return;
		}
		ServerReceiver reciever = new ServerReceiver(socket1, socket2);

		thread1 = new Thread(reciever);
		thread1.start();

		ServerReceiver reciever2 = new ServerReceiver(socket2, socket1);
		thread2 = new Thread(reciever2);
		thread2.start();
		System.out.println("둘받음 : 연결함");

		String firstmessage = STATUS_START + " " + FIRST;
		String seccondmessage = STATUS_START + " " + SECOND;
		System.out.println(firstmessage);
		System.out.println(seccondmessage);
		reciever.sendMessage(firstmessage);
		reciever2.sendMessage(seccondmessage);
	}

	private static boolean isAvailable(Socket socket) {
		return true; // 소켓 유효성 판단 논리 필요.
		/*
		 * OutputStream out; InputStream in; try { in = socket.getInputStream();
		 * 
		 * if (in.read() == -1) { System.out.println("리턴 펄스"); return false; }
		 * return true; } catch (IOException e) { System.out.println("죽었다.");
		 * e.printStackTrace(); return false; }
		 */
	}
}

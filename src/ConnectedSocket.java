import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class ConnectedSocket implements Runnable {

	static int no = 0;

	int id;
	Socket socket;
	public static ArrayList<ConnectedSocket> sockets = new ArrayList<ConnectedSocket>();
	private boolean stop = false;
	DataOutputStream dataout;
	static int game = 0;

	ConnectedSocket(Socket socket) {
		this.socket = socket;
		this.id = no;
		no++;
	}

	public static void add(ConnectedSocket connectedSocket) {
		sockets.add(connectedSocket);
	}

	@Override
	public void run() {
		//InputStream in;
		System.out.println("start");
		try {
		//	in = socket.getInputStream();
			//DataInputStream datain = new DataInputStream(in);
			OutputStream out = socket.getOutputStream();
			dataout = new DataOutputStream(out);
			while (!stop) {
				try {
					// System.out.println(datain.read());
					System.out.println("플레이어 고르는 중");
					dataout.writeUTF("9000&게임중인 플레이어:" + game + "명.\n대기자:"
							+ sockets.size() + "명\n 게임 상대를 고르는중!!");
					connectTry();
					Thread.sleep(1000);
				} catch (IOException e) {
					System.out.println("소켓클로즈");
					sockets.remove(this);
					socket.close();
					stop = true;
				}
			}
		} catch (IOException e) {
			System.out.println("소켓연결실패");
			sockets.remove(this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void connectTry() {
		System.out.println("아이디:" + id + "접속 소켓:" + sockets.size() + "연결시도중");
		if (sockets.size() < 2) {
			return;
		}
		// 전적 비슷한 상대 고르는 논리 필요.
		ConnectedSocket selected = notId(id);
		stop = true;
		selected.stop = true;
		makeConnection(socket, selected.socket);
		System.out.println(id + "와 " + selected.id + "를 매칭하여 게임을 시작하였습니다.");

		sockets.remove(selected);
		sockets.remove(this);

		game += 2;
	}

	private void makeConnection(Socket socket1, Socket socket2) {
		Connecter reciever = new Connecter(socket1, socket2);

		Thread thread1 = new Thread(reciever);
		thread1.start();

		Connecter reciever2 = new Connecter(socket2, socket1);
		Thread thread2 = new Thread(reciever2);
		thread2.start();

		String messageforSocket1;
		String messageforSocket2;

		Random r = new Random();
		if (r.nextInt(1) == 0) {
			messageforSocket1 = "0&1";
			messageforSocket2 = "0&2";
		} else {
			messageforSocket1 = "0&2";
			messageforSocket2 = "0&1";
		}

		reciever.sendMessage(messageforSocket1);
		reciever2.sendMessage(messageforSocket2);

	}

	public static void printSockets() {
		for (ConnectedSocket socket : sockets) {
			System.out.println(String.format("%d: 아이피:%s", socket.id,
					socket.socket.getInetAddress().toString()));
		}
	}

	public static ConnectedSocket id(int require) {
		for (ConnectedSocket socket : sockets) {
			if (socket.id == require) {
				return socket;
			}
		}
		return null;
	}

	public static ConnectedSocket notId(int require) {
		for (ConnectedSocket socket : sockets) {
			if (socket.id != require) {
				return socket;
			}
		}
		return null;
	}

	public void sendMessage(String string) {
		try {
			dataout.writeUTF(string);
			System.out.println("send:" + string);
		} catch (IOException e) {
			System.out.println("소켓이 닫혔습니다.");
			e.printStackTrace();
		}
	}

}

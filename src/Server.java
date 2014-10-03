import java.util.Scanner;

public class Server {

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		// 포트를 지정한다.
		SocketReciever receiver = new SocketReciever();
		Thread receivethread = new Thread(receiver);
		receivethread.start();

		while (true) {
			System.out.print("command> ");
			Do(scan.nextLine());
		}

	}

	private static void Do(String input) {

		String doing[] = null;
		String type = null;
		if (input.contains(" ")) {
			doing = input.split(" ");
			if(doing.length==0)
				return;
			type = doing[0];
		} else {
			type = input;
		}

		switch (type) {
		case "?":
			System.out.println("sockets : 연결된 소켓 출력");
			break;

		case "sockets":
			ConnectedSocket.printSockets();
			break;

		case "send":
			try {
				ConnectedSocket.id(Integer.parseInt(doing[1])).sendMessage(
						doing[2]);

			} catch (Exception e) {
				System.out
						.println("커맨드 입력형식이 잘못되었습니다.\n형식 : send [socketid] [message]");
			}
			break;

		default:
			System.out.println("해당 커맨드 없음.");

		}

	}
}

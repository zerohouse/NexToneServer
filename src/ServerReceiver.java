import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerReceiver implements Runnable {

	DataInputStream datain;
	DataOutputStream dataout;
	Socket socket;
	boolean end = false;
	


	ServerReceiver(Socket socket, Socket socketout) {
		try {
			this.socket = socket;
			InputStream in = socket.getInputStream();
			OutputStream out = socketout.getOutputStream();
			datain = new DataInputStream(in);
			dataout = new DataOutputStream(out);
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String message) {
		try {
			dataout.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String message = "";
		String[] messageSplit;
		while (datain != null) {
			try {
				message = datain.readUTF();
				messageSplit = message.split("&");
				doServerJob(Integer.parseInt(messageSplit[0]));
				System.out.println(message);
				if (message != "")
					dataout.writeUTF(message);

			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
		try {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!end)
				dataout.writeUTF("103&");
			datain.close();
			dataout.close();
			socket.close();
			System.out.println("소켓이 닫혔습니다.");
			System.out.println("에러 : 트루");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void doServerJob(int i) {
		switch (i) {
		case 550:
			end = true;
			break;
		}
	}

}

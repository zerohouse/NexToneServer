import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Receiver implements Runnable {

	Socket socket;
	InputStream in;
	DataInputStream datain;

	Receiver(Socket socket) {
		this.socket = socket;
		try {
			in = socket.getInputStream();
			datain = new DataInputStream(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (datain != null) {
			try {
				System.out.println(datain.readUTF());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}

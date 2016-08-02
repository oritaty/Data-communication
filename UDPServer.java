/**
 * Part1 of the assignment; return current time (of the server side) to the client.
 * @author: yusuke
 */
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

public class UDPServer {
	public static void main(String args[]) {
		DatagramSocket aSocket = null;
		if (args.length < 1) {
			System.out.println("Usage: java UDPServer <PortNumber>");
			System.exit(1);
		}
		try {
			int socket_no = Integer.valueOf(args[0]).intValue();
			aSocket = new DatagramSocket(socket_no);
			byte[] buffer = new byte[1000];
			while (true) {
				DatagramPacket request = new DatagramPacket(buffer,
						buffer.length);
				aSocket.receive(request);
				String time = getCurrentTime();
				byte[] sd = time.getBytes();
				int length = sd.length;
				InetAddress ip = request.getAddress();
				int port = request.getPort();
				DatagramPacket reply = new DatagramPacket(sd, length, ip, port);
				aSocket.send(reply);
			}
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (aSocket != null)
				aSocket.close();
		}
	}
	
	public static String getCurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
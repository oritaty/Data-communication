import java.net.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.io.*;

public class UDPClient {
	public static void main(String args[]) {
		// args give message contents and server hostname
		DatagramSocket aSocket = null;
		if (args.length < 2) {
			System.out
					.println("Usage: java UDPClient <Host name> <Port number>");
			System.exit(1);
		}
		try {
			aSocket = new DatagramSocket();

			Calendar calendar = new GregorianCalendar();
			TimeZone timeZone = calendar.getTimeZone();
			String id = timeZone.getID();

			byte[] m = (id).getBytes();
			InetAddress aHost = InetAddress.getByName(args[0]);
			int serverPort = Integer.valueOf(args[1]).intValue();
			DatagramPacket request = new DatagramPacket(m, m.length, aHost,
					serverPort);
			aSocket.send(request);
			byte[] buffer = new byte[1000];
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			aSocket.receive(reply);
			System.out.println("Reply: " + new String(reply.getData()));
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (aSocket != null)
				aSocket.close();
		}
	}
}
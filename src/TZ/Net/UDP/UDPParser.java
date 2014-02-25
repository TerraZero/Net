package TZ.Net.UDP;

import java.net.DatagramPacket;

public class UDPParser {

	public static String getString(DatagramPacket data) {
		return new String(data.getData(), 0, data.getLength());
	}

}

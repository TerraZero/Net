package TZ.Net.UDP.Packet;

import java.net.DatagramPacket;

public class SimpleUDPPacket extends StdUDPPacket {

	public SimpleUDPPacket(String address) {
		super(address);
	}

	public SimpleUDPPacket(String ip, int port) {
		super(ip, port);
	}

	public SimpleUDPPacket(byte[] data, String ip, int port) {
		super(data, ip, port);
	}

	public SimpleUDPPacket(DatagramPacket packet, boolean sending) {
		super(packet, sending);
	}
	
	public SimpleUDPPacket(byte[] data, int length, String ip, int port) {
		super(data, length, ip, port);
	}
	
	public String getString() {
		return new String(this.data, 0, this.length);
	}

}

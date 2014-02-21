package TZ.Net.UDP.Packet;

import java.net.DatagramPacket;

import TZ.Net.IP;
import TZ.Net.UDP.UDPPacket;

public abstract class StdUDPPacket implements UDPPacket {
	
	protected String protocol;
	protected String ip;
	protected int port;
	protected byte[] data;
	protected int length;
	protected boolean sending;
	
	public StdUDPPacket(String address) {
		this.sending = true;
		this.setAddress(address);
	}

	public StdUDPPacket(String ip, int port) {
		this.sending = true;
		this.ip = ip;
		this.port = port;
	}
	
	public StdUDPPacket(byte[] data, String ip, int port) {
		this.sending = true;
		this.data = data;
		this.ip = ip;
		this.port = port;
	}
	
	public StdUDPPacket(byte[] data, int length, String ip, int port) {
		this.sending = true;
		this.data = data;
		this.length = length;
		this.ip = ip;
		this.port = port;
	}
	
	public StdUDPPacket(DatagramPacket packet, boolean sending) {
		this.sending = sending;
		this.data = packet.getData();
		this.ip = IP.getIPFromInetAddress(packet.getAddress());
		this.port = packet.getPort();
		this.length = packet.getLength();
	}

	public String getProtocol() {
		return this.protocol;
	}

	public void setAddress(String address) {
		String[] s = address.split(":");
		this.setAddress(s[0], Integer.parseInt(s[1]));
	}

	public String getIP() {
		return this.ip;
	}

	public int getPort() {
		return this.port;
	}

	public byte[] getData() {
		return this.data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public void setAddress(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public String getAddress() {
		return this.ip + ":" + this.port;
	}
	
	public boolean isSending() {
		return this.sending;
	}
	
	public void setSending(boolean sending) {
		this.sending = sending;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public void setData(byte[] data, int length) {
		this.data = data;
		this.length = length;
	}
	
	public int getLength() {
		return this.length;
	}

}

package TZ.Net.UDP.Anchor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import TZ.Net.IP;
import TZ.Net.UDP.UDPAnchor;

public class StdUDPAnchor implements UDPAnchor<DatagramSocket> {
	
	private DatagramSocket socket;
	private DatagramPacket input;
	private DatagramPacket output;
	
	private String ip;
	private int port;
	private byte[] buffer;

	public StdUDPAnchor() {
		this(0, 1024);
	}
	
	public StdUDPAnchor(int port) {
		this(port, 1024);
	}
	
	public StdUDPAnchor(int port, int buffer) {
		this.init(port, buffer);
	}
	
	protected void init(int port, int buffer) {
		try {
			this.socket = new DatagramSocket(port);
			this.setBuffer(buffer);
		} catch (SocketException e) {
			System.out.println(e);
			return;
		}
		this.refreshData();
	}
	
	public void setBuffer(int buffer) {
		this.buffer = new byte[buffer];
		this.input = new DatagramPacket(this.buffer, buffer);
		this.output = new DatagramPacket(this.buffer, buffer);
	}
	
	public int getPort() {
		return this.port;
	}
	
	public String getIP() {
		return this.ip;
	}
	
	public void refreshData() {
		this.ip = IP.getIPFromInetAddress(this.socket.getLocalAddress());
		this.port = this.socket.getLocalPort();
	}
	
	public int send(byte[] data, String ip, int port) {
		this.output.setData(data);
		this.output.setLength(data.length);
		this.output.setSocketAddress(new InetSocketAddress(ip, port));
		try {
			this.socket.send(this.output);
			return 1;
		} catch (IOException e) {
			System.out.println(e);
			return -1;
		}
	}
	
	public DatagramPacket listen() {
		try {
			this.socket.receive(this.input);
			return this.input;
		} catch (IOException e) {
			return null;
		}
	}
	
	public void unbind() {
		this.socket.close();
	}

	public void coreRegistry() {
		
	}

	public void coreDelete() {
		this.unbind();
	}

	public int getBuffer() {
		return this.buffer.length;
	}

	public DatagramSocket getSocket() {
		return this.socket;
	}

}

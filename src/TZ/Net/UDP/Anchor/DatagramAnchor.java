package TZ.Net.UDP.Anchor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import TZ.Listen.Liste.LNode;
import TZ.Listen.V5.L;
import TZ.Net.IP;
import TZ.Net.UDP.UDPProtocol;

public class DatagramAnchor extends StdAnchor<DatagramSocket, DatagramPacket> implements Runnable {
	
	protected DatagramPacket input;
	protected DatagramPacket output;
	
	public DatagramAnchor() throws SocketException {
		this(0, 1024, 0);
	}

	public DatagramAnchor(int port) throws SocketException {
		this(port, 1024, 0);
	}	
	
	public DatagramAnchor(int port, int buffer) throws SocketException {
		this(port, buffer, 0);
	}
	
	public DatagramAnchor(int port, int buffer, int timeout) throws SocketException {
		this.socket = new DatagramSocket(port);
		this.setBuffer(buffer);
		this.socket.setSoTimeout(timeout);
		this.thread = new Thread(this);
		this.protocols = L.create();
		this.refreshData();
	}
	
	public void setBuffer(int buffer) {
		this.buffer = new byte[buffer];
		this.input = new DatagramPacket(this.buffer, this.buffer.length);
		this.output = new DatagramPacket(this.buffer, this.buffer.length);
	}

	public int getBuffer() {
		return this.buffer.length;
	}

	public void refreshData() {
		this.port = this.socket.getLocalPort();
		this.ip = IP.getIPFromInetAddress(this.socket.getLocalAddress());
	}
	
	public void send(byte[] data, String ip, int port) throws IOException {
		this.output.setData(data, 0, data.length);
		this.output.setAddress(IP.getInetAddressFromIP(ip));
		this.output.setPort(port);
		this.send(this.output);
	}

	public void send(DatagramPacket data) throws IOException {
		this.socket.send(data);
	}

	public DatagramPacket listening() throws IOException {
		this.socket.receive(this.input);
		return this.input;
	}
	
	public DatagramPacket getPacket() {
		return this.output;
	}

	public void run() {
		try {
			do {
				try {
					this.socket.receive(this.input);
					this.listen(this.input, false);
				} catch (SocketTimeoutException e) {
					this.listen(this.input, true);
				}
			} while (true);
		} catch (Exception e) {
			this.exception(e);
		}
	}
	
	public void listen(DatagramPacket input, boolean timeout) {
		for (LNode<UDPProtocol<DatagramPacket>> n = this.protocols.root(); n != null; n = n.next()) {
			if (n.content().listen(input, timeout)) break;
		}
	}

	public void setTimeout(int timeout) throws SocketException {
		this.socket.setSoTimeout(timeout);
	}

	public int getTimeout() throws SocketException {
		return this.socket.getSoTimeout();
	}

}

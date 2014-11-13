package TZ.Net.UDP.Anchor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import TZ.Base.Listen.Liste.LN;
import TZ.Base.Listen.V5.Liste;
import TZ.Net.IP;
import TZ.Net.UDP.UDPProtocol;

/**
 * Base class for using DatagramSocket and DatagramPacket
 * @author TerraZero
 *
 */
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
		this.protocols = Liste.create();
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
	
	/**
	 * Get a predefined Packet for sending
	 * @return
	 *   DatagramPacket - Packet for output
	 */
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
	
	/**
	 * Execute the Listening for protocols
	 * @param input
	 *   The DatagramPacket which is receives
	 * @param timeout
	 *   If is occurred cause a timeout
	 */
	public void listen(DatagramPacket input, boolean timeout) {
		for (LN<UDPProtocol<DatagramPacket>> n = this.protocols.root(); n != null; n = n.next()) {
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

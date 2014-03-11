package TZ.Net.wo.UDP.Anchor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import TZ.Core.Core;
import TZ.Listen.Reference.VL;
import TZ.Listen.Reference.VarInit;
import TZ.Net.IP;
import TZ.V5.Strings;

public class DatagramAnchor extends StdUDPAnchor<DatagramSocket, DatagramPacket, DatagramPacket> {
	
	protected DatagramPacket input;
	protected DatagramPacket output;
	
	protected byte[] buffer;
	
	public DatagramAnchor(int port) {
		this(port, 0, null, 0);
	}
	
	public DatagramAnchor(int port, int buffer) {
		this(port, buffer, null, 0);
	}

	public DatagramAnchor(int port, int buffer, String ip) {
		this(port, buffer, ip, 0);
	}

	public DatagramAnchor(int port, int buffer, String ip, int timeout) {
		try {
			if (ip == null) {
				this.socket = new DatagramSocket(port);
			} else {
				this.socket = new DatagramSocket(port, IP.getInetAddressFromIP(ip));
			}
			this.setBuffer(buffer);
			this.setTimeout(timeout);
		} catch (SocketException e) {
			Core.exception(e, Strings.implode(":", "init", "socket"), "DatagramAnchor", VL.create(VarInit.create(port, buffer, ip, timeout).add("port", "buffer", "ip", "timeout")));
		}
	}

	/**
	 * Set the buffer of the socket<br />
	 * <i>default: 1024</i><br />
	 * <i>when: buffer < 1</i>
	 */
	public void setBuffer(int buffer) {
		if (buffer < 1) buffer = 1024;
		this.buffer = new byte[buffer];
		this.input = new DatagramPacket(this.buffer, buffer);
		this.output = new DatagramPacket(this.buffer, buffer);
	}

	public int getBuffer() {
		return 0;
	}

	public void refreshData() {
		
	}

	public void setTimeout(int timeout) {
		
	}

	public void getTimeout() {
		
	}

	public DatagramPacket createPacket() {
		return null;
	}

	public DatagramPacket listen() {
		return null;
	}

	public DatagramPacket listen(int timeout) {
		return null;
	}

	public void send(DatagramPacket packet) {
		
	}

	public boolean disconnect() {
		return false;
	}

	public boolean isConnected() {
		return false;
	}

}

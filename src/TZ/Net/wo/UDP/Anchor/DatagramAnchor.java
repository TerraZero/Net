package TZ.Net.wo.UDP.Anchor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import TZ.Listen.Reference.VL;
import TZ.Listen.Reference.VarInit;
import TZ.Net.IP;
import TZ.Net.UDP.UDPParser;
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
			this.refreshData();
		} catch (SocketException e) {
			this.exception(e, Strings.implode(":", "init", "socket"), this.toString(), VL.create(VarInit.create(port, buffer, ip, timeout).add("port", "buffer", "ip", "timeout")));
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
		this.input = this.createPacket();
		this.output = this.createPacket();
	}

	public int getBuffer() {
		return this.buffer.length;
	}

	public void refreshData() {
		this.ip = IP.getIPFromInetAddress(this.socket.getLocalAddress());
		this.port = this.socket.getLocalPort();
	}

	public void setTimeout(int timeout) {
		try {
			this.socket.setSoTimeout(timeout);
		} catch (SocketException e) {
			this.exception(e, Strings.implode(":", "set", "timeout"), this.toString(), VL.create(VarInit.create(this, timeout).add("anchor", "timeout")));
		}
	}

	public int getTimeout() {
		try {
			return this.socket.getSoTimeout();
		} catch (SocketException e) {
			this.exception(e, Strings.implode(":", "get", "timeout"), this.toString(), VL.create(VarInit.create(this).add("anchor")));
			return -1;
		}
	}

	public DatagramPacket createPacket() {
		return new DatagramPacket(this.buffer, this.buffer.length);
	}

	public int listen() {
		try {
			try {
				this.socket.receive(this.input);
			} catch (SocketTimeoutException e) {
				return 0;
			}
			return 1;
		} catch (IOException e) {
			this.exception(e, Strings.implode(":", "function", "listen"), this.toString(), VL.create(VarInit.create(this, this.input).add("anchor", "input")));
			return -1;
		}
	}

	public int listen(int timeout) {
		int tmpTimeout = this.getTimeout();
		this.setTimeout(timeout);
		int listen = this.listen();
		this.setTimeout(tmpTimeout);
		return listen;
	}
	
	public DatagramPacket getInput() {
		return this.input;
	}
	
	public String getInputString() {
		return UDPParser.getString(this.input);
	}
	
	public String getInputAddress() {
		return UDPParser.getAddress(this.input);
	}
	
	public int getInputLength() {
		return this.input.getLength();
	}
	
	public String getInputIP() {
		return IP.getIPFromInetAddress(this.input.getAddress());
	}
	
	public int getInputPort() {
		return this.input.getPort();
	}

	public boolean send(DatagramPacket packet) {
		try {
			this.socket.send(packet);
			return true;
		} catch (IOException e) {
			this.exception(e, Strings.implode(":", "function", "send"), this.toString(), VL.create(VarInit.create(this, packet).add("anchor", "packet")));
			return false;
		}
	}
	
	/**
	 * Send the data 
	 * @param data
	 *   The data of the packet
	 * @param address
	 *   The address of the target<br />
	 *   <i>format: ip:port - 127.0.0.1:55555</i>
	 * @return
	 *   true - successful
	 *	 false - exception
	 */
	public boolean send(byte[] data, String address) {
		return this.send(data, -1, IP.getIP(address), IP.getPort(address), -1);
	}
	
	/**
	 * Send the data 
	 * @param data
	 *   The data of the packet
	 * @param ip
	 *   The ip of the target
	 * @param port
	 *   The port of the target
	 * @return
	 *   true - successful
	 *	 false - exception
	 */
	public boolean send(byte[] data, String ip, int port) {
		return this.send(data, -1, ip, port, -1);
	}
	
	/**
	 * Send the data 
	 * @param data
	 *   The data of the packet
	 * @param length
	 *   The length of data<br />
	 *   <i>default: -1</i>
	 * @param ip
	 *   The ip of the target
	 * @param port
	 *   The port of the target
	 * @return
	 *   true - successful
	 *	 false - exception
	 */
	public boolean send(byte[] data, int length, String ip, int port) {
		return this.send(data, length, ip, port, -1);
	}
	
	/**
	 * Send the data 
	 * @param data
	 *   The data of the packet
	 * @param length
	 *   The length of data<br />
	 *   <i>default: -1</i>
	 * @param ip
	 *   The ip of the target
	 * @param port
	 *   The port of the target
	 * @param offset
	 *   The offset of the data<br />
	 *   <i>default: -1</i>
	 * @return
	 *   true - successful
	 *	 false - exception
	 */
	public boolean send(byte[] data, int length, String ip, int port, int offset) {
		this.output.setData(data, (offset == -1 ? 0 : offset), (length == -1 ? data.length : length));
		this.output.setAddress(IP.getInetAddressFromIP(ip));
		this.output.setPort(port);
		return this.send(this.output);
	}

	public boolean disconnect() {
		if (this.isConnected()) {
			this.socket.disconnect();
			this.socket.close();
			return true;
		} else {
			return false;
		}
	}

	public boolean isConnected() {
		return this.socket.isConnected() && !this.socket.isClosed();
	}
	
	public String toString() {
		return "DatagramAnchor[" + this.ip + ":" + this.port + "]";
	}
	
	/**
	 * Get the packet that is usually used to send
	 * @return
	 *   DatagramPacket this.output
	 */
	public DatagramPacket getPacket() {
		return this.output;
	}
	
	/**
	 * Get the packet that is usually used to send
	 * @param refresh
	 *   generates the packet new
	 * @return
	 *   DatagramPacket this.output
	 */
	public DatagramPacket getPacket(boolean refresh) {
		if (refresh) this.output = this.createPacket();
		return this.output;
	}

}

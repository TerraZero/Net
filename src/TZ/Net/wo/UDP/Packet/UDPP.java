package TZ.Net.wo.UDP.Packet;

import TZ.Net.IP;
import TZ.Net.wo.UDP.UDPPacket;

public class UDPP implements UDPPacket {
	
	protected byte[] data;
	protected int length;
	protected String ip;
	protected int port;

	public void setIP(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setAddress(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public void setAddress(String address) {
		this.ip = IP.getIP(address);
		this.port = IP.getPort(address);
	}

	public String getIP() {
		return this.ip;
	}
	
	public int getPort() {
		return this.port;
	}

	public String getAddress() {
		return this.ip + ":" + this.port;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getLength() {
		return this.length;
	}

	public void setData(byte[] data, int offset, int length) {
		this.data = data;
		this.length = data.length;
	}

	public void setData(byte[] data) {
		this.setData(data, 0, data.length);
	}

	public byte[] getData() {
		return this.data;
	}
	
	public void setData(String data) {
		this.data = data.getBytes();
	}
	
	protected String getDataString() {
		return new String(this.data, 0, this.length);
	}
	
	public String toString() {
		return "UDPP[" + this.getDataString() + "]";
	}

	public UDPPacket clear() {
		this.data = null;
		this.length = -1;
		this.ip = null;
		this.port = -1;
		return this;
	}

}

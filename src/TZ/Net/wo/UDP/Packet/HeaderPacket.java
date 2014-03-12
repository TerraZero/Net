package TZ.Net.wo.UDP.Packet;

import java.net.DatagramPacket;

import TZ.Listen.Alias.AliasListe;
import TZ.Listen.Alias.AliasNode;
import TZ.Listen.V5.AL;
import TZ.Net.IP;
import TZ.Net.wo.UDP.UDPPacket;

public class HeaderPacket extends UDPP implements UDPPacket {

	protected AliasListe<String, String> header;
	
	public HeaderPacket(DatagramPacket packet) {
		this(packet.getData(), packet.getLength(), IP.getIPFromInetAddress(packet.getAddress()), packet.getPort());
	}
	
	public HeaderPacket(byte[] data, int length, String ip, int port) {
		this.data = data;
		this.length = length;
		this.ip = ip;
		this.port = port;
	}
	
	public void header() {
		String[] content = new String(this.data, 0, this.length).split("§§");
		if (content.length == 2) {
			this.setHeader(content[0]);
		}
	}
	
	public String header(String key) {
		if (this.header == null) this.header();
		return this.header.get(key);
	}
	
	public void header(String key, String value) {
		if (this.header == null) this.header = AL.create();
		this.header.add(key, value);
	}
	
	public boolean isHeader(String key) {
		if (this.header == null) this.header();
		return this.header.isKey(key);
	}
	
	protected void setHeader(String header) {
		String[] items = header.split(";");
		String[] fields = null;
		for (int i = 0; i < items.length; i++) {
			if (items[i].length() != 0) {
				fields = items[i].split(":");
				this.header.as(fields[0], (fields[1].length() == 0 ? null : fields[1]));
			}
		}
	}
	
	protected String getHeader() {
		StringBuilder string = new StringBuilder();
		for (AliasNode<String, String> n = this.header.root(); n != null; n = n.next()) {
			string.append(n.alias()).append(":").append(n.content()).append(";");
		}
		return string.toString();
	}
	
	protected String getDataString() {
		return this.getHeader() + "§§" + this.data;
	}
	
	public DatagramPacket getPacket(DatagramPacket packet) {
		byte[] packetData = this.getDataString().getBytes();
		packet.setData(packetData);
		packet.setLength(packetData.length);
		packet.setAddress(IP.getInetAddressFromIP(this.ip));
		packet.setPort(this.port);
		return packet;
	}
	
	public UDPPacket clear() {
		this.header.clear();
		this.header = null;
		return super.clear();
	}

}

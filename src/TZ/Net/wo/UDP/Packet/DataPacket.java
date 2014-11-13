package TZ.Net.wo.UDP.Packet;

import java.net.DatagramPacket;

import TZ.Base.Listen.Alias.AL;
import TZ.Base.Listen.Alias.AN;
import TZ.Base.Listen.V5.AliasListe;
import TZ.Net.wo.UDP.UDPPacket;

public class DataPacket extends HeaderPacket {
	
	public static final String TYPE = "UDPDP";
	
	protected AL<String, String> content;
	
	public DataPacket() {
		super(DataPacket.TYPE);
	}

	public DataPacket(DatagramPacket packet) {
		super(packet);
	}

	public DataPacket(byte[] data, int length, String ip, int port) {
		super(data, length, ip, port);
	}
	
	public DataPacket(HeaderPacket packet) {
		super(packet.data, packet.length, packet.ip, packet.port);
	}
	
	public void generateParse() {
		this.header = AliasListe.create();
		this.content = AliasListe.create();
		if (this.data == null) return;
		String s = new String(this.data, 0, this.length);
		String[] content = s.split("��");
		if (content.length == 2) {
			this.setHeader(content[0]);
			this.setContent(content[1]);
		} else if (content.length == 1 && s.endsWith("��")) {
			this.setHeader(s.substring(0, s.length() - 2));
		} else {
			this.header("type", DataPacket.TYPE);
		}
	}
	
	protected void setContent(String content) {
		String[] items = content.split(";");
		String[] fields = null;
		for (int i = 0; i < items.length; i++) {
			if (items[i].length() != 0) {
				fields = items[i].split(":");
				this.content.as(fields[0], (fields[1].length() == 0 ? null : fields[1]));
			}
		}
	}
	
	public String content(String key) {
		if (this.content == null) this.generate();
		return this.content.get(key);
	}
	
	public void content(String key, String value) {
		if (this.content == null) this.generate();
		this.content.as(key, value);
	}
	
	public boolean isContent(String key) {
		if (this.content == null) this.generate();
		return this.content.isKey(key) && this.content.get(key) != null;
	}
	
	protected String getContent() {
		if (this.content == null) this.generate();
		StringBuilder string = new StringBuilder();
		for (AN<String, String> n = this.content.root(); n != null; n = n.next()) {
			string.append(n.alias()).append(":").append(n.content()).append(";");
		}
		return string.toString();
	}
	
	protected String getDataString() {
		return this.getHeader() + "��" + this.getContent();
	}
	
	public UDPPacket clear() {
		this.header.clear();
		return super.clear();
	}

}

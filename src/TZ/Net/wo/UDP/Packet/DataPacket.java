package TZ.Net.wo.UDP.Packet;

import java.net.DatagramPacket;

import TZ.Listen.Alias.AliasListe;
import TZ.Listen.Alias.AliasNode;
import TZ.Listen.V5.AL;

public class DataPacket extends HeaderPacket {
	
	protected AliasListe<String, String> content;
	
	public DataPacket() {
		super();
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
	
	public void generate() {
		this.header = AL.create();
		this.content = AL.create();
		if (this.data == null) return;
		String[] content = new String(this.data, 0, this.length).split("§§");
		if (content.length == 2) {
			this.setHeader(content[0]);
			this.setContent(content[1]);
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
		return this.content.isKey(key);
	}
	
	protected String getContent() {
		if (this.content == null) this.generate();
		StringBuilder string = new StringBuilder();
		for (AliasNode<String, String> n = this.content.root(); n != null; n = n.next()) {
			string.append(n.alias()).append(":").append(n.content()).append(";");
		}
		return string.toString();
	}
	
	protected String getDataString() {
		return this.getHeader() + "§§" + this.getContent();
	}

}

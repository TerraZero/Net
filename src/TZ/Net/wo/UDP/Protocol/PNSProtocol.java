package TZ.Net.wo.UDP.Protocol;

import java.net.DatagramPacket;

import TZ.Net.wo.UDP.Anchor.DatagramAnchor;
import TZ.Net.wo.UDP.Packet.DataPacket;
import TZ.Net.wo.UDP.Packet.HeaderPacket;
import TZ.Net.wo.UDP.Service.PNSService;

public class PNSProtocol extends StdUDPProtocol<DatagramPacket, DatagramPacket> {
	
	public static final String NO_SEND = "!no-send!";
	public static final String TIMEOUT = "!timeout!";
	
	public static void main(String[] args) {
		new PNSService().start();
		PNSProtocol prot = PNSProtocol.protocol();
		System.out.println(prot.query("buffer"));
		System.out.println(prot.set("buffer", "3555"));
		System.out.println(prot.query("buffer"));
		System.out.println(prot.pns("login", "hallo", "51234", null));
		System.out.println(prot.pns("port", "hallo", null, null));
		System.out.println(prot.pns("port", "dshfjsf", null, null));
		System.out.println(prot.pns("login", "hallo", "85479", null));
		System.out.println(prot.pns("clear", null, null, null));
		System.out.println(prot.pns("port", "hallo", null, null));
		System.out.println(prot.pns("login", "hallo", "55555", "test"));
		System.out.println(prot.pns("rename", "hallo", null, "huhu"));
		System.out.println(prot.pns("port", "huhu", null, null));
		System.out.println(prot.pns("port", "hallo", null, null));
		System.out.println("Exit: " + prot.exit());
	}
	
	public static final int PORT = 53197;
	
	private static PNSProtocol protocol;
	
	public static PNSProtocol protocol() {
		if (PNSProtocol.protocol == null) PNSProtocol.protocol = new PNSProtocol();
		return PNSProtocol.protocol;
	}

	private PNSProtocol() {
		super("PNS");
		this.connectAnchor(new DatagramAnchor(0, 2048));
		this.anchor.setTimeout(30);
	}
	
	protected DataPacket createDataPacket(String cmd) {
		DataPacket packet = new DataPacket();
		packet.setAddress("127.0.0.1:" + PNSProtocol.PORT);
		packet.header("cmd", cmd);
		return packet;
	}
	
	public DataPacket getInputData() {
		return new DataPacket(this.alterInput(this.anchor.getInput()));
	}
	
	public String query(String query) {
		DataPacket output = this.createDataPacket("query");
		output.content("query", query);
		if (!this.sending(output)) {
			return PNSProtocol.NO_SEND;
		}
		return this.getReturn(this.anchor.listen());
	}
	
	public String exit() {
		DataPacket output = new DataPacket();
		output.setAddress("127.0.0.1:" + PNSProtocol.PORT);
		output.header("cmd", "exit");
		if (!this.sending(output)) {
			return PNSProtocol.NO_SEND;
		}
		return this.getReturn(this.anchor.listen());
	}
	
	public String set(String set, String value) {
		DataPacket output = this.createDataPacket("set");
		output.content("set", set);
		output.content("value", value);
		if (!this.sending(output)) {
			return PNSProtocol.NO_SEND;
		}
		return this.getReturn(this.anchor.listen());
	}
	
	public String pns(String pns, String name, String port, String rename) {
		DataPacket output = this.createDataPacket("pns");
		output.content("pns", pns);
		if (name != null) output.content("name", name);
		if (port != null) output.content("port", port);
		if (rename != null) output.content("rename", rename);
		if (!this.sending(output)) {
			return PNSProtocol.NO_SEND;
		}
		return this.getReturn(this.anchor.listen());
	}
	
	protected boolean sending(HeaderPacket hp) {
		return this.anchor.send(this.alterOutput(hp.getPacket(this.anchor.getPacket())));
	}
	
	protected String getReturn(int state) {
		if (state == 1) {
			DataPacket data = this.getInputData();
			if (data.isHeader("action") && !data.header("action").equals("executed") && !data.header("action").equals("auto")) {
				return data.header("exception");
			} else {
				return data.content("respond");
			}
		} else if (state == 0) {
			return PNSProtocol.TIMEOUT;
		} else {
			return null;
		}
	}
	
	public boolean isStateOK(String respond) {
		return respond != null && !respond.equals(PNSProtocol.NO_SEND) && !respond.equals(PNSProtocol.TIMEOUT);
	}
	
}

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
		prot.pns("login", "hallo", "51234");
		prot.pns("port", "hallo", null);
		prot.pns("port", "dshfjsf", null);
		prot.pns("login", "hallo", "85479");
		prot.pns("clear", null, null);
		prot.pns("port", "hallo", null);
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
	
	protected DataPacket getInputData() {
		return new DataPacket(this.alterInput(this.anchor.getInput()));
	}
	
	public String query(String query) {
		DataPacket output = this.createDataPacket("query");
		output.content("query", query);
		if (!this.sending(output)) {
			return PNSProtocol.NO_SEND;
		}
		int state = this.anchor.listen();
		if (state == 1) {
			return this.getInputData().content("value");
		} else if (state == 0) {
			return PNSProtocol.TIMEOUT;
		} else {
			return null;
		}
	}
	
	public String exit() {
		DataPacket output = new DataPacket();
		output.setAddress("127.0.0.1:" + PNSProtocol.PORT);
		output.header("cmd", "exit");
		if (!this.sending(output)) {
			return PNSProtocol.NO_SEND;
		}
		int state = this.anchor.listen();
		if (state == 1) {
			DataPacket input = this.getInputData();
			if (input.header("action").equals("abort")) {
				if (input.isHeader("exception")) {
					return input.header("exception");
				} else {
					return "abort";
				}
			}
			return "exit";
		} else if (state == 0) {
			return PNSProtocol.TIMEOUT;
		} else {
			return null;
		}
	}
	
	public String set(String set, String value) {
		DataPacket output = this.createDataPacket("set");
		output.content("set", set);
		output.content("value", value);
		if (!this.sending(output)) {
			return PNSProtocol.NO_SEND;
		}
		int state = this.anchor.listen();
		if (state == 1) {
			return this.getInputData().content("value");
		} else if (state == 0) {
			return PNSProtocol.NO_SEND;
		} else {
			return null;
		}
	}
	
	public String pns(String pns, String name, String port) {
		DataPacket output = this.createDataPacket("pns");
		output.content("pns", pns);
		if (name != null) output.content("name", name);
		if (port != null) output.content("port", port);
		if (!this.sending(output)) {
			return PNSProtocol.NO_SEND;
		}
		int state = this.anchor.listen();
		System.out.println(this.getInputData());
		return null;
	}
	
	/*
	public int check() {
		
	}
	
	public int regist(string name, int port) {
		
	}
	
	public int deregist(string name) {
		
	}
	
	public string[] getregists() {
		
	}
	
	public int[] getports() {
		
	}
	
	public int sendover() {
		
	}
	*/
	
	protected boolean sending(HeaderPacket hp) {
		return this.anchor.send(this.alterOutput(hp.getPacket(this.anchor.getPacket())));
	}
	
}

package TZ.Net.wo.UDP.Protocol;

import java.net.DatagramPacket;

import TZ.Net.wo.UDP.Anchor.DatagramAnchor;
import TZ.Net.wo.UDP.Packet.DataPacket;
import TZ.Net.wo.UDP.Service.PNSService;

public class PNSProtocol extends StdUDPProtocol<DatagramPacket, DatagramPacket> {
	
	public static void main(String[] args) {
		new PNSService().start();
		PNSProtocol prot = PNSProtocol.service();
		prot.query("buffer");
		prot.query("timeout");
		prot.exit();
	}
	
	public static final int PORT = 53197;
	
	private static PNSProtocol protocol;
	
	public static PNSProtocol service() {
		if (PNSProtocol.protocol == null) PNSProtocol.protocol = new PNSProtocol();
		return PNSProtocol.protocol;
	}

	private PNSProtocol() {
		super("PNS");
		this.connectAnchor(new DatagramAnchor(0, 2048));
		this.anchor.setTimeout(30);
	}
	
	public void query(String query) {
		DataPacket output = new DataPacket();
		output.setAddress("127.0.0.1:" + PNSProtocol.PORT);
		output.header("cmd", "query");
		output.content("query", query);
		this.anchor.send(output.getPacket(this.anchor.getPacket()));
		if (this.anchor.listen() == 1) {
			DataPacket d = new DataPacket(this.anchor.getInput());
			System.out.println(d);
		}
	}
	
	public void exit() {
		DataPacket output = new DataPacket();
		output.setAddress("127.0.0.1:" + PNSProtocol.PORT);
		output.header("cmd", "exit");
		this.anchor.send(output.getPacket(this.anchor.getPacket()));
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
	
}

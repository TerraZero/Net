package TZ.Net.wo.UDP.Protocol;

import java.net.DatagramPacket;

import TZ.Net.wo.UDP.Anchor.DatagramAnchor;

public class PNSService extends StdUDPProtocol<DatagramPacket, DatagramPacket> {
	
	private static PNSService service;
	
	public static PNSService service() {
		if (PNSService.service == null) PNSService.service = new PNSService();
		return PNSService.service;
	}

	private PNSService() {
		super("PNS");
		this.connectAnchor(new DatagramAnchor(0, 1024));
		this.anchor.setTimeout(30);
	}
	
	public int check() {
		
	}
	
	public int regist(String name, int port) {
		
	}
	
	public int deregist(String name) {
		
	}
	
	public String[] getRegists() {
		
	}
	
	public int[] getPorts() {
		
	}
	
	public int sendOver() {
		
	}
	
}

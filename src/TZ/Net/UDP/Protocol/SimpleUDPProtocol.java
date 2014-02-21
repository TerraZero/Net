package TZ.Net.UDP.Protocol;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import TZ.Listen.V5.L;
import TZ.Net.UDP.Anchor.StdUDPAnchor;
import TZ.Net.UDP.Packet.SimpleUDPPacket;
import TZ.V5.Listen;

public class SimpleUDPProtocol extends StdUDPProtocol<StdUDPAnchor, SimpleUDPPacket, DatagramSocket> implements Runnable {
	
	public static void main(String[] args) {
		L<String> s = L.create();
		s.add("hdfs");
		Listen.print(s);
//		SimpleUDPProtocol prot = new SimpleUDPProtocol();
//		prot.addInputListener(new UDPListener<SimpleUDPPacket>() {
//
//			public boolean listen(SimpleUDPPacket packet) {
//				System.out.println("Input:");
//				System.out.println(packet.getString());
//				return false;
//			}
//			
//		});
//		prot.send("hallo", "127.0.0.1", prot.getAnchor().getPort());
	}
	
	public SimpleUDPProtocol() {
		super(new StdUDPAnchor());
		this.init();
	}

	public SimpleUDPProtocol(StdUDPAnchor anchor) {
		super(anchor);
		this.init();
	}
	
	protected void init() {
		this.thread = new Thread(this);
	}
	
	public void send(String data, String ip, int port) {
		this.send(data.getBytes(), data.length(), ip, port);
	}
	
	public void send(byte[] data, int length, String ip, int port) {
		SimpleUDPPacket udp = new SimpleUDPPacket(data, length, ip, port);
		this.send(udp);
	}
	
	public void send(byte[] data, String ip, int port) {
		this.send(data, data.length, ip, port);
	}
	
	public void send(byte[] data, int length, String address) {
		SimpleUDPPacket udp = new SimpleUDPPacket(address);
		udp.setData(data, length);
		this.send(udp);
	}
	
	public void send(byte[] data, String address) {
		this.send(data, data.length, address);
	}
	
	public void send(String data, String address) {
		this.send(data.getBytes(), data.length(), address);
	}
	
	public void send(SimpleUDPPacket packet) {
		packet.setSending(true);
		this.outputListener.fire(packet);
		this.anchor.send(packet.getData(), packet.getIP(), packet.getPort());
	}
	
	public void watchOut() {
		this.thread.start();
	}

	public void run() {
		DatagramPacket input;
		SimpleUDPPacket udp;
		do {
			input = this.anchor.listen();
			udp = new SimpleUDPPacket(input, false);
			this.inputListener.fire(udp);
		} while (true);
	}

}

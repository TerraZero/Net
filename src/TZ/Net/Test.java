package TZ.Net;

import TZ.Net.UDP.UDPListener;
import TZ.Net.UDP.Anchor.StdUDPAnchor;
import TZ.Net.UDP.Packet.SimpleUDPPacket;
import TZ.Net.UDP.Protocol.SimpleUDPProtocol;
import TZ.V5.Input;

public class Test {

	public static void main(String[] args) {
		boolean b = Input.getBoolean("System: ");
		if (b) {
			StdUDPAnchor anchor = new StdUDPAnchor(53197);
			final SimpleUDPProtocol p = new SimpleUDPProtocol(anchor);
			p.addInputListener(new UDPListener<SimpleUDPPacket>() {
				
				public boolean listen(SimpleUDPPacket packet) {
					if (packet.getString().equals("exit")) {
						System.exit(0);
					}
					packet.setData(("echo: " + packet.getString()).getBytes());
					p.send(packet);
					return false;
				}
				
			});
			p.watchOut();
		} else {
			SimpleUDPProtocol pp = new SimpleUDPProtocol();
			pp.addInputListener(new UDPListener<SimpleUDPPacket>() {
				
				public boolean listen(SimpleUDPPacket packet) {
					System.out.println("Listen: " + packet.getString());
					return false;
				}
				
			});
			pp.watchOut();
			String s = "";
			do {
				s = Input.getString("Befehl: ");
				pp.send(s, "127.0.0.1:53197");
			} while (!s.equals("exit"));
			System.out.println("exit!");
		}
	}

}

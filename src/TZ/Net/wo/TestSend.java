package TZ.Net.wo;

import java.net.DatagramPacket;

import TZ.Core.Core;
import TZ.Net.IP;
import TZ.Net.UDP.Anchor.DatagramAnchor;
import TZ.Net.UDP.Protocol.SimpleProtocol;
import TZ.V5.Input;

public class TestSend {

	public static void main(String[] args) {
		try { 
			DatagramAnchor anchor = new DatagramAnchor();
			SimpleProtocol simple = new SimpleProtocol(anchor);
			anchor.addProtocol(simple);
			do {
				String s = Input.getString("Send: ");
				DatagramPacket output = anchor.getPacket();
				output.setAddress(IP.getInetAddressFromIP("127.0.0.1"));
				output.setData(s.getBytes(), 0, s.length());
				output.setPort(53197);
				simple.send(output);
				if (s.equals("exit")) {
					Core.exit();
				}
			} while (true);
		} catch (Exception e) {
			Core.exception(e);
		}
		System.out.println("end");
	}

}

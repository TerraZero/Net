package TZ.Net.wo;

import TZ.Core.Core;
import TZ.Net.UDP.Anchor.DatagramAnchor;
import TZ.Net.UDP.Protocol.SimpleProtocol;

public class TestListin {

	public static void main(String[] args) {
		try {
			DatagramAnchor anchor = new DatagramAnchor(53197);
			anchor.addProtocol(new SimpleProtocol(anchor));
			anchor.start();
		} catch (Exception e) {
			Core.exception(e);
		}
	}

}

package TZ.Net.UDP.Protocol;

import java.io.IOException;
import java.net.DatagramPacket;

import TZ.Core.Core;
import TZ.Net.UDP.UDPParser;
import TZ.Net.UDP.Anchor.DatagramAnchor;

/**
 * A simple protocol more to test 
 * @author TerraZero
 *
 */
public class SimpleProtocol extends StdProtocol<DatagramPacket> {
	
	/**
	 * The Same anchor <br />
	 * this.anchor == this.dataAnchor
	 */
	protected DatagramAnchor dataAnchor;
	
	public SimpleProtocol(DatagramAnchor anchor) {
		super(anchor);
		this.dataAnchor = anchor;
	}

	public boolean listen(DatagramPacket data, boolean timeout) {
		if (timeout) return false;
		String s = UDPParser.getString(data);
		System.out.println(s);
		if (s.equals("exit")) {
			Core.exit();
		}
		return false;
	}

	public void send(DatagramPacket data) {
		try {
			this.dataAnchor.send(data);
		} catch (IOException e) {
			Core.exception(e);
		}
	}

}

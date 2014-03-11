package TZ.Net.wo.UDP.Anchor;

import TZ.Core.Core;
import TZ.Listen.Reference.VL;
import TZ.Listen.Reference.VarInit;
import TZ.Net.wo.UDP.UDPAnchor;

public abstract class StdUDPAnchor<socket, input, output> implements UDPAnchor<socket, input, output> {

	protected String ip;
	protected int port;
	
	protected socket socket;
	
	public String getIP() {
		return this.ip;
	}

	public int getPort() {
		return this.port;
	}

	public String getAddress() {
		return this.ip + ":" + port;
	}

	public socket getSocket() {
		return this.socket;
	}
	
	public void exception(Exception e, String id) {
		Core.exception(e, id, "UDPAnchor", VL.create(VarInit.create(this).add("anchor")));
	}
	
	public input alterInput(input input) {
		return input;
	}
	
	public output alterOutput(output output) {
		return output;
	}

}

package TZ.Net.wo.UDP.Protocol;

import TZ.Net.wo.UDP.UDPAnchor;
import TZ.Net.wo.UDP.UDPProtocol;

public class StdUDPProtocol<input, output> implements UDPProtocol<input, output> {

	protected String name;
	protected UDPAnchor<?, input, output> anchor;
	
	public StdUDPProtocol(String name) {
		this.name = name;
	}
	
	public UDPAnchor<?, input, output> getAnchor() {
		return this.anchor;
	}

	public String getName() {
		return this.name;
	}

	public void connectAnchor(UDPAnchor<?, input, output> anchor) {
		this.anchor = anchor;
	}
	
	public input alterInput(input input) {
		return input;
	}
	
	public output alterOutput(output output) {
		return output;
	}

}

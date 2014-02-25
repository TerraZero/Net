package TZ.Net.UDP.Protocol;

import TZ.Net.UDP.UDPAnchor;
import TZ.Net.UDP.UDPProtocol;

public abstract class StdProtocol<data> implements UDPProtocol<data> {

	protected UDPAnchor<?, ?> anchor;
	
	public StdProtocol(UDPAnchor<?, ?> anchor) {
		this.anchor = anchor;
	}
	
	public UDPAnchor<?, ?> getAnchor() {
		return this.anchor;
	}

}

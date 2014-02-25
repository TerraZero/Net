package TZ.Net.UDP.Protocol;

import TZ.Net.UDP.UDPAnchor;
import TZ.Net.UDP.UDPProtocol;

/**
 * The default base class for UDPProtocol
 * @author TerraZero
 *
 * @param <data> The connection data to UDPAnchor
 */
public abstract class StdProtocol<data> implements UDPProtocol<data> {

	protected UDPAnchor<?, ?> anchor;
	
	public StdProtocol(UDPAnchor<?, ?> anchor) {
		this.anchor = anchor;
	}
	
	public UDPAnchor<?, ?> getAnchor() {
		return this.anchor;
	}

}

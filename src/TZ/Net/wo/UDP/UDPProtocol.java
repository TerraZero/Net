package TZ.Net.wo.UDP;

public interface UDPProtocol<input, output> {
	
	/**
	 * The referenced Anchor
	 * @return
	 *   UDPAnchor - this.anchor
	 */
	public UDPAnchor<?, input, output> getAnchor();

	/**
	 * The name of the protocol
	 * @return
	 *   String - this.name
	 */
	public String getName();
	
}

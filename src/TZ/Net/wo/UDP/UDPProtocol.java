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
	
	/**
	 * Connect the protocol to the anchor
	 * @param anchor
	 *   The given anchor
	 */
	public void connectAnchor(UDPAnchor<?, input, output> anchor);
	
	public input alterInput(input input);
	
	public output alterOutput(output output);
	
}

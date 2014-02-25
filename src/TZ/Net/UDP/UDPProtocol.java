package TZ.Net.UDP;

/**
 * The base class for UDP Protocols
 * @author TerraZero
 *
 * @param <data> The connection data to UDPAnchor
 */
public interface UDPProtocol<data> {

	/**
	 * The referenced Anchor
	 * @return
	 *   UDPAnchor - this.anchor
	 */
	public UDPAnchor<?, ?> getAnchor();
	
	/**
	 * Is call whenever the anchor receives something
	 * @param data
	 *   The data which is receives 
	 * @param timeout
	 *   If it was cause of timeout
	 * @return
	 *   true - if the receives data was for this Protocol
	 *   false - unless
	 */
	public boolean listen(data data, boolean timeout);
	
	/**
	 * Sending data
	 * @param data
	 *   data to be sent
	 */
	public void send(data data);
	
}

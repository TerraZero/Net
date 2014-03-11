package TZ.Net.wo.UDP;

/**
 * The base class for UDP Anchor
 * @author TerraZero
 *
 * @param <socket> The socket to connection with the Net
 * @param <input> The input data to the UDPProtocol
 * @param <output> The output data from the UDPProtocol
 */
public interface UDPAnchor<socket, input, output> {

	/**
	 * Set the buffer of the socket
	 * @param buffer
	 *   The buffer size
	 */
	public void setBuffer(int buffer);
	
	/**
	 * Get the buffer size
	 * @return
	 *   int - the buffer size
	 */
	public int getBuffer();
	
	/**
	 * Get the String IP of the socket
	 * @return
	 *   String - String IP 
	 */
	public String getIP();
	
	/**
	 * Get the Port of the socket
	 * @return
	 *   int - The port of the socket
	 */
	public int getPort();
	
	/**
	 * Get the Address String<br />
	 * <i>format: ip:port - 127.0.0.1:55555</i>
	 * @return
	 *   String - Address String
	 */
	public String getAddress();
	
	/**
	 * Get the socket which connected to the Net
	 * @return
	 *   <socket> - The socket
	 */
	public socket getSocket();
	
	/**
	 * Refreshed data to this Anchor <br />
	 * <i>example: ip, port</i>
	 */
	public void refreshData();
	
	/**
	 * The default exception handling for the running Thread
	 * @param e
	 *   The exception which is occurred
	 * @param id
	 *   A String which the the exception defined
	 */
	public void exception(Exception e, String id);
	
	/**
	 * Set the timeout of this Anchor
	 * @param timeout
	 *   The time in milliseconds
	 */
	public void setTimeout(int timeout);
	
	/**
	 * Get the set Timeout
	 * @return
	 *   int - The timeout of the socket
	 */
	public void getTimeout();
	
	/**
	 * Create a new output packet
	 * @return
	 *   a new output packet
	 */
	public output createPacket();
	
	/**
	 * 
	 * @return
	 * 	 <output> - the receive data<br />
	 *   null - timeout
	 */
	public output listen();
	
	/**
	 * 
	 * @param timeout
	 *   Temporary timeout time
	 * @return
	 *   <output> - the receive data<br />
	 *   null - timeout
	 */
	public output listen(int timeout);
	
	/**
	 * Send the data over the socket
	 * @param input
	 *   The data to send
	 */
	public void send(input packet);
	
	/**
	 * Disconnect the socket
	 * @return
	 *   true - successful
	 */
	public boolean disconnect();
	
	/**
	 * Is the socket connected to a port
	 * @return
	 *   true - the socket is connected<br />
	 *   false - the socket is disconnected
	 */
	public boolean isConnected();
	
	public input alterInput(input input);
	
	public output alterOutput(output output);
	
}

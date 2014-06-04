package TZ.Net.UDP;

import java.io.IOException;
import java.net.SocketException;

import TZ.Listen.Liste.L;

/**
 * The base class for UDP Anchor
 * @author TerraZero
 *
 * @param <socket> The socket to connection with the Net
 * @param <data> The connection data to UDPProtocols
 */
public interface UDPAnchor<socket, data> {

	/**
	 * Add a listing Protocol to this Anchor
	 * @param protocol
	 *   The additional UDPProtocol
	 */
	public void addProtocol(UDPProtocol<data> protocol);
	
	/**
	 * Get a List of all referenced UDPProtocols
	 * @return
	 *   List(UDPProtocol)
	 */
	public L<UDPProtocol<data>> getProtocols();
	
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
	 * Get the Address String<br />
	 * <i>format: ip:port - 127.0.0.1:55555</i>
	 * @return
	 *   String - Address String
	 */
	public String getAddress();
	
	/**
	 * Get the Port of the socket
	 * @return
	 *   int - The port of the socket
	 */
	public int getPort();
	
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
	 * Send the data over the socket
	 * @param data
	 *   The data to send
	 * @throws IOException
	 *   exception during the process
	 */
	public void send(data data) throws IOException;
	
	/**
	 * Get the Thread which is used for listening
	 * @return
	 *   Thread - The Thread of this Anchor
	 */
	public Thread getThread();
	
	/**
	 * The default exception handling for the running Thread
	 * @param e
	 *   The exception which is occurred
	 */
	public void exception(Exception e);
	
	/**
	 * Set the timeout of this Anchor
	 * @param timeout
	 *   The time in milliseconds
	 * @throws SocketException
	 *   exception during the process
	 */
	public void setTimeout(int timeout) throws SocketException;
	
	/**
	 * Get the set Timeout
	 * @return
	 *   int - The timeout of the socket
	 * @throws SocketException
	 *   exception during the process
	 */
	public int getTimeout() throws SocketException;
	
	/**
	 * Start the Thread to listening of the port
	 */
	public void start();
	
}

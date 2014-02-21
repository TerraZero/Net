package TZ.Net.UDP;

import java.net.DatagramPacket;

/**
 * <strong>
 *  TODO CoreObject einbauen
 * </strong><br />
 * 
 * @author TerraZero
 *
 * @param <socket>
 */
public interface UDPAnchor<socket> {

	public int send(byte[] data, String ip, int port);
	
	public DatagramPacket listen();
	
	public int getPort();
	
	public String getIP();
	
	public void setBuffer(int buffer);
	
	public int getBuffer();
	
	public void unbind();
	
	public void refreshData();
	
	public socket getSocket();
	
}

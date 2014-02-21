package TZ.Net.UDP;

import java.net.DatagramPacket;

import TZ.Core.CoreObject;

public interface UDPAnchor<socket> extends CoreObject {

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

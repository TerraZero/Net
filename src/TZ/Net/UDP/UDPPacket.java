package TZ.Net.UDP;

public interface UDPPacket {

	public String getProtocol();
	
	public void setAddress(String address);
	
	public void setAddress(String ip, int port);
	
	public String getAddress();
	
	public String getIP();
	
	public int getPort();
	
	public byte[] getData();
	
	public void setData(byte[] data);
	
	public void setData(byte[] data, int length);
	
	public boolean isSending();
	
	public void setSending(boolean sending);
	
	public int getLength();
	
	public void setLength(int length);
	
}

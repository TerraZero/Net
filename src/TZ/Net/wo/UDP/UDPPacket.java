package TZ.Net.wo.UDP;

public interface UDPPacket {

	public void setIP(String ip);
	
	public void setPort(int port);
	
	public void setAddress(String ip, int port);
	
	public void setAddress(String address);
	
	public String getIP();
	
	public int getPort();
	
	public String getAddress();
	
	public void setLength(int length);
	
	public int getLength();
	
	public void setData(byte[] data, int offset, int length);
	
	public void setData(byte[] data);
	
	public byte[] getData();
	
	public UDPPacket clear();
	
}

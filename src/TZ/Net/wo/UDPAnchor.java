package TZ.Net.wo;

import TZ.Listen.Liste.Liste;

public interface UDPAnchor<socket, data> {

	public void addProtocol(UDPProtocol<data> protocol);
	
	public Liste<UDPProtocol<data>> getProtocols();
	
	public String getIP();
	
	public String getAddress();
	
	public int getPort();
	
	public socket getSocket();
	
	public void refreshData();
	
	public void send(data data);
	
}

package TZ.Net.UDP;

import java.io.IOException;
import java.net.SocketException;

import TZ.Listen.Liste.Liste;

public interface UDPAnchor<socket, data> {

	public void addProtocol(UDPProtocol<data> protocol);
	
	public Liste<UDPProtocol<data>> getProtocols();
	
	public void setBuffer(int buffer);
	
	public int getBuffer();
	
	public String getIP();
	
	public String getAddress();
	
	public int getPort();
	
	public socket getSocket();
	
	public void refreshData();
	
	public void send(data data) throws IOException;
	
	public data listening() throws IOException;
	
	public Thread getThread();
	
	public void exception(Exception e);
	
	public void setTimeout(int timeout) throws SocketException;
	
	public int getTimeout() throws SocketException;
	
	public void start();
	
}

package TZ.Net.UDP;

import TZ.Listen.Listener.Handler;

public interface UDPProtocol<anchor extends UDPAnchor<socket>, packet extends UDPPacket, socket> {
	
	public String getName();
	
	public Thread getThread();
	
	public anchor getAnchor();
	
	public void addInputListener(UDPListener<packet> add);
	
	public void addOutputListener(UDPListener<packet> add);
	
	public void removeInputListener(UDPListener<packet> remove);
	
	public void removeOutputListener(UDPListener<packet> remove);
	
	public Handler<UDPListener<packet>, packet> getInputHandler();
	
	public Handler<UDPListener<packet>, packet> getOutputHandler();
	
}

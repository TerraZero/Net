package TZ.Net.UDP;


public interface UDPProtocol<data> {

	public UDPAnchor<?, ?> getAnchor();
	
	public boolean listen(data data, boolean timeout);
	
	public void send(data data);
	
}

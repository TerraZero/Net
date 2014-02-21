package TZ.Net.wo;

public interface UDPProtocol<data> {

	public UDPAnchor<?, ?> getAnchor();
	
	public void listen(data data);
	
	public void send(data data);
	
}

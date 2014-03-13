package TZ.Net.wo.UDP;

public interface UDPService<input, output> extends Runnable {
	
	public String getName();
	
	public String getProtocol();
	
	public UDPAnchor<?, input, output> getAnchor();
	
	public void connectAnchor(UDPAnchor<?, input, output> anchor);
	
	public Thread getThread();
	
	public void start();
	
	public void daemon();

}

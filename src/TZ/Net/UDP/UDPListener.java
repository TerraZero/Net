package TZ.Net.UDP;

public interface UDPListener<packet extends UDPPacket> {

	public boolean listen(packet packet);
	
}

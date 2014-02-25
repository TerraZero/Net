package TZ.Net.UDP.Anchor;

import TZ.Core.Core;
import TZ.Listen.Liste.Liste;
import TZ.Net.UDP.UDPAnchor;
import TZ.Net.UDP.UDPProtocol;

/**
 * The default base class for UDPAnchor
 * @author TerraZero
 *
 * @param <socket> The socket to connection with the Net
 * @param <data> The connection data to UDPProtocols
 */
public abstract class StdAnchor<socket, data> implements UDPAnchor<socket, data> {
	
	protected socket socket;
	
	protected Liste<UDPProtocol<data>> protocols;
	protected Thread thread;
	
	protected byte[] buffer;
	protected String ip;
	protected int port;

	public void addProtocol(UDPProtocol<data> protocol) {
		this.protocols.add(protocol);
	}

	public Liste<UDPProtocol<data>> getProtocols() {
		return this.protocols;
	}

	public void setBuffer(int buffer) {
		this.buffer = new byte[buffer];
	}

	public int getBuffer() {
		return this.buffer.length;
	}

	public String getIP() {
		return this.ip;
	}

	public String getAddress() {
		return this.ip + ":" + this.port;
	}

	public int getPort() {
		return this.port;
	}

	public socket getSocket() {
		return this.socket;
	}

	public Thread getThread() {
		return this.thread;
	}
	
	public void exception(Exception e) {
		Core.exception(e);		
	}

	public void start() {
		this.thread.start();
	}

}

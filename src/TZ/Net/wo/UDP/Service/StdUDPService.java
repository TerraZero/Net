package TZ.Net.wo.UDP.Service;

import TZ.Net.wo.UDP.UDPAnchor;
import TZ.Net.wo.UDP.UDPService;

public abstract class StdUDPService<input, output> implements UDPService<input, output> {
	
	protected String name;
	protected String protocol;
	protected UDPAnchor<?, input, output> anchor;
	protected Thread daemon;
	
	public StdUDPService() {
		this.daemon = new Thread(this);
	}
	
	public String getName() {
		return this.name;
	}

	public String getProtocol() {
		return this.protocol;
	}

	public UDPAnchor<?, input, output> getAnchor() {
		return this.anchor;
	}

	public void connectAnchor(UDPAnchor<?, input, output> anchor) {
		this.anchor = anchor;
	}

	public Thread getThread() {
		return this.daemon;
	}

	public void start() {
		this.daemon.start();
	}

	public void run() {
		while (true) {
			this.daemon();
		}
	}

}

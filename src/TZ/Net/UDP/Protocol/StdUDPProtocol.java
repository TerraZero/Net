package TZ.Net.UDP.Protocol;

import TZ.Listen.Listener.Handler;
import TZ.Listen.Listener.LHandler;
import TZ.Listen.V5.LN;
import TZ.Net.UDP.UDPAnchor;
import TZ.Net.UDP.UDPListener;
import TZ.Net.UDP.UDPPacket;
import TZ.Net.UDP.UDPProtocol;

public abstract class StdUDPProtocol<anchor extends UDPAnchor<socket>, packet extends UDPPacket, socket> implements UDPProtocol<anchor, packet, socket> {

	protected String name;
	protected Thread thread;
	protected anchor anchor;
	protected Handler<UDPListener<packet>, packet> inputListener;
	protected Handler<UDPListener<packet>, packet> outputListener;
	
	public StdUDPProtocol(anchor anchor) {
		this(anchor, "StdUDP");
	}
	
	public StdUDPProtocol(anchor anchor, String name) {
		this.name = name;
		this.anchor = anchor;
		this.inputListener = new StdHandler<packet>();
		this.outputListener = new StdHandler<packet>();
	}
	
	public String getName() {
		return this.name;
	}

	public Thread getThread() {
		return this.thread;
	}

	public anchor getAnchor() {
		return this.anchor;
	}

	public void addInputListener(UDPListener<packet> add) {
		this.inputListener.add(add);
	}

	public void addOutputListener(UDPListener<packet> add) {
		this.outputListener.add(add);
	}

	public void removeInputListener(UDPListener<packet> remove) {
		this.inputListener.removeAll(remove);
	}

	public void removeOutputListener(UDPListener<packet> remove) {
		this.outputListener.removeAll(remove);
	}

	public Handler<UDPListener<packet>, packet> getInputHandler() {
		return this.inputListener;
	}

	public Handler<UDPListener<packet>, packet> getOutputHandler() {
		return this.outputListener;
	}

}
class StdHandler<packet extends UDPPacket> extends LHandler<UDPListener<packet>, packet> {
	
	public void fire(packet event) {
		for (LN<UDPListener<packet>> n = this.root; n != null; n = n.next) {
			if (this.fire(n.content, event)) break;
		}
	}

	public boolean fire(UDPListener<packet> listener, packet event) {
		return listener.listen(event);
	}
	
}

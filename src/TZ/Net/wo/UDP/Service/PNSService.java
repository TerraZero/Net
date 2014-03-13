package TZ.Net.wo.UDP.Service;

import java.net.DatagramPacket;

import TZ.Net.wo.UDP.Anchor.DatagramAnchor;
import TZ.Net.wo.UDP.Packet.DataPacket;
import TZ.Net.wo.UDP.Packet.HeaderPacket;
import TZ.V5.Strings.VarString;

public class PNSService extends StdUDPService<DatagramPacket, DatagramPacket> {
	
	public static final int PORT = 53197;
	
	public PNSService() {
		this.name = "PNS - Service";
		this.protocol = "PNS";
		this.connectAnchor(new DatagramAnchor(PNSService.PORT, 2048));
		this.anchor.setTimeout(30);
	}

	public void daemon() {
		int state = this.anchor.listen();
		switch (state) {
			case 1 : //ok
				this.successful();
			case 0 : //timeout
			case -1 : //exception
			default: 
		}
	}
	
	protected void successful() {
		HeaderPacket hp = new HeaderPacket(this.anchor.getInput());
		String command = hp.header("cmd");
		System.out.println(command);
		if (command != null) {
			switch (command) {
				case "exit" :
					if (hp.getIP().equals("127.0.0.1")) {
						this.daemon.interrupt();
						System.out.println("exit");
						System.exit(0);
					}
					break;
				case "set" :
					if (hp.getIP().equals("127.0.0.1")) {
						this.set(hp);
					}
					break;
				case "query" :
					this.query(hp);
					break;
				default :
					break;
			}
		}
	}
	
	protected void query(HeaderPacket hp) {
		DataPacket output = new DataPacket();
		DataPacket data = new DataPacket(hp);
		String query = data.content("query");
		switch (query) {
			case "buffer" :
				output.setAddress(data.getAddress());
				output.content("query", query);
				output.content("buffer", this.anchor.getBuffer() + "");
				break;
			case "timeout" :
				output.setAddress(data.getAddress());
				output.content("query", query);
				output.content("timeout", this.anchor.getTimeout() + "");
				break;
			default :
				break;
		}
		if (output.getIP() != null) {
			output.header("reply", "y");
			if (!this.anchor.send(output.getPacket(this.anchor.getPacket()))) {
				this.trigger("exception", "query", output.getIP(), output.getPort() + "", "sending", "Failure query send!");
			}
		}
	}
	
	protected void set(HeaderPacket hp) {
		DataPacket output = new DataPacket();
		DataPacket data = new DataPacket(hp);
		String set = data.content("set");
		switch (set) {
			case "buffer" :
				try {
					int buffer = Integer.parseInt(data.content("value"));
					this.anchor.setBuffer(buffer);
					output.setAddress(data.getAddress());
					output.content("set", set);
					output.content("value", buffer + "");
				} catch (NumberFormatException e) {
					this.trigger("exception", "set", "null", "-1", "parseInt", e.toString());
				}
				break;
			case "timeout" :
				try {
					int timeout = Integer.parseInt(data.content("value"));
					this.anchor.setTimeout(timeout);
				} catch (NumberFormatException e) {
					//trigger
				}
				break;
		}
		if (output.getIP() != null) {
			output.header("reply", "y");
			if (!this.anchor.send(output.getPacket(this.anchor.getPacket()))) {
				this.trigger("exception", "set", output.getIP(), output.getPort() + "", "sending", "Failure set send!");
			}
		}
	}
	
	protected void trigger(String type, String function, String ip, String port, String action, String message, String... args) {
		this.trigger(type, function, ip, port, action, message, new VarString(args));
	}
	
	protected void trigger(String type, String function, String ip, String port, String action, String message, VarString args) {
		
	}

}

package TZ.Net.wo.UDP.Service;

import java.net.DatagramPacket;

import TZ.Base.Listen.Alias.AL;
import TZ.Base.Listen.V5.AliasListe;
import TZ.Base.V5.Strings.VarString;
import TZ.Net.wo.UDP.Anchor.DatagramAnchor;
import TZ.Net.wo.UDP.Packet.DataPacket;

public class PNSService extends StdUDPService<DatagramPacket, DatagramPacket> {
	
	public static final int PORT = 53197;
	
	protected AL<String, String> system;
	
	public PNSService() {
		this.name = "PNS - Service";
		this.protocol = "PNS";
		this.connectAnchor(new DatagramAnchor(PNSService.PORT, 2048));
		this.anchor.setTimeout(30);
		this.system = AliasListe.create();
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
		DataPacket data = new DataPacket(this.anchor.getInput());
		DataPacket output = new DataPacket();
		String command = data.header("cmd");
		String reply = null;
		if (command != null) {
			output.header("function", command);
			switch (command) {
				case "exit" :
					if (this.authorized(data)) {
						this.daemon.interrupt();
						output.setAddress(data.getAddress());
						output.content(command, "ok");
						this.respond(output, "exit");
						this.prepareHeader(output, null, "executed");
						this.sending(output, "y");
						System.exit(0);
					} else {
						output.content(command, "no");
						output.setAddress(data.getAddress());
						this.prepareHeader(output, "rights", "abort");
						this.sending(output, "y");
					}
					break;
				case "set" :
					if (this.authorized(data)) reply = this.set(data, output);
					break;
				case "query" :
					reply = this.query(data, output);
					break;
				case "pns" :
					reply = this.pns(data, output);
					break;
				case "trigger" :
					reply = this.trigger(data, output);
					break;
				case "check" :
					output.content(command, "ok");
					output.setAddress(data.getAddress());
					reply = "y";
					break;
				default :
					break;
			}
		}
		if (reply != null && output.getIP() != null) {
			if (this.sending(output, reply)) {
				this.trigger("exception", command, output.getIP(), output.getPort() + "", "sending", "Failure '" + command + "' send!");
			}
		}
	}
	
	protected boolean authorized(DataPacket packet) {
		return packet.getIP().equals("127.0.0.1");
	}
	
	protected String query(DataPacket data, DataPacket output) {
		String query = data.content("query");
		switch (query) {
			case "buffer" :
				this.prepareQuery(output, data.getAddress(), query, this.anchor.getBuffer() + "");
				break;
			case "timeout" :
				this.prepareQuery(output, data.getAddress(), query, this.anchor.getTimeout() + "");
				break;
			default :
				return null;
		}
		return "y";
	}
	
	protected String set(DataPacket data, DataPacket output) {
		String set = data.content("set");
		switch (set) {
			case "buffer" :
				try {
					int buffer = Integer.parseInt(data.content("value"));
					this.anchor.setBuffer(buffer);
					this.prepareSet(output, data.getAddress(), set, buffer + "");
				} catch (NumberFormatException e) {
					this.trigger("exception", "set", "null", "-1", "parseInt", e.toString());
				}
				break;
			case "timeout" :
				try {
					int timeout = Integer.parseInt(data.content("value"));
					this.anchor.setTimeout(timeout);
					this.prepareSet(output, data.getAddress(), set, timeout + "");
				} catch (NumberFormatException e) {
					this.trigger("exception", "set", "null", "-1", "parseInt", e.toString());
				}
				break;
			default :
				return null;
		}
		return "y";
	}
	
	protected String pns(DataPacket data, DataPacket output) {
		String pns = data.content("pns");
		String name = data.content("name");
		String port = data.content("port");
		String rename = data.content("rename");
		switch (pns) {
			case "login" :
				if (data.getIP().equals("127.0.0.1")) {
					output.setAddress(data.getAddress());
					this.respond(output, name + ":" + port);
					if (name == null || port == null) {
						this.prepareHeader(output, "null", "abort");
					} else if (this.system.isKey(name)) {
						this.prepareHeader(output, "already", "abort");
					} else {
						this.system.add(name, port);
						this.prepareHeader(output, null, "executed");
					}
				}
				break;
			case "logout" :
				if (data.getIP().equals("127.0.0.1")) {
					output.setAddress(data.getAddress());
					this.respond(output, name + ":" + port);
					if (name == null) {
						this.prepareHeader(output, "null", "abort");
					} else {
						port = this.system.get(name);
						if (port == null) {
							this.prepareHeader(output, "null", "abort");
						} else {
							this.prepareHeader(output, null, "executed");
						}
					}
				}
				break;
			case "clear" :
				if (data.getIP().equals("127.0.0.1")) {
					output.setAddress(data.getAddress());
					this.respond(output, "clear");
					this.prepareHeader(output, null, "executed");
					this.system.clear();
				}
				break;
			case "port" :
				output.setAddress(data.getAddress());
				this.respond(output, port);
				if (name == null) {
					this.prepareHeader(output, "null", "abort");
				} else {
					port = this.system.get(name);
					if (port == null) {
						this.prepareHeader(output, "exist", "abort");
					} else {
						this.prepareHeader(output, null, "executed");
					}
				}
				break;
			case "rename" :
				if (data.getIP().equals("127.0.0.1")) {
					output.setAddress(data.getAddress());
					this.respond(output, rename);
					if (rename == null || name == null) {
						this.prepareHeader(output, "null", "abort");
					} else if (this.system.isKey(rename)) {
						this.prepareHeader(output, "already", "abort");
					} else if (!this.system.isKey(name)) {
						this.prepareHeader(output, "exist", "abort");
					} else {
						if (this.system.rename(name, rename)) {
							this.prepareHeader(output, null, "executed");
						} else {
							this.prepareHeader(output, "exception", "abort");
						}
					}
				}
			default :
				return null;
		}
		this.preparePNS(output, pns, name, port, rename);
		return "y";
	}
	
	protected String trigger(DataPacket data, DataPacket output) {
		return "";
	}
	
	protected DataPacket prepareHeader(DataPacket packet, String exception, String action) {
		if (exception != null) packet.header("exception", exception);
		if (action != null) packet.header("action", action);
		return packet;
	}
	
	protected DataPacket prepareSet(DataPacket packet, String address, String set, String respond) {
		packet.setAddress(address);
		packet.content("set", set);
		this.respond(packet, respond);
		return packet;
	}
	
	protected DataPacket prepareQuery(DataPacket packet, String address, String query, String respond) {
		packet.setAddress(address);
		packet.content("query", query);
		this.respond(packet, respond);
		return packet;
	}
	
	protected DataPacket preparePNS(DataPacket packet, String pns, String name, String port, String rename) {
		packet.content("pns", pns);
		if (name != null) packet.content("name", name);
		if (port != null) packet.content("port", port);
		if (rename != null) packet.content("rename", rename);
		return packet;
	}
	
	protected boolean sending(DataPacket packet, String reply) {
		packet.header("reply", reply);
		if (!packet.isHeader("action")) packet.header("action", "auto");
		return this.anchor.send(packet.getPacket(this.anchor.getPacket()));
	}
	
	protected DataPacket respond(DataPacket output, String respond) {
		output.content("respond", respond);
		return output;
	}
	
	protected void trigger(String type, String function, String ip, String port, String action, String message, String... args) {
		this.trigger(type, function, ip, port, action, message, new VarString(args));
	}
	
	protected void trigger(String type, String function, String ip, String port, String action, String message, VarString args) {
		
	}

}

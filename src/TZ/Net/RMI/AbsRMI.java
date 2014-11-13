package TZ.Net.RMI;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import TZ.Base.Listen.Alias.AL;
import TZ.Base.Listen.Alias.AN;
import TZ.Base.Listen.V5.AliasListe;
import TZ.Core.Core;
import TZ.Net.IP;

public abstract class AbsRMI extends UnicastRemoteObject implements RMI {

	private static final long serialVersionUID = 1L;
	
	protected AL<String, RMI> connects;
	protected String id;

	public AbsRMI(String id) throws RemoteException {
		super();
		this.id = id;
	}
	
	public void bind() {
		try {
			RMISystem.getRegis().bind(this.id, this);
			this.connects = AliasListe.create();
		} catch (RemoteException | AlreadyBoundException e) {
			Core.exception(e, "Cannot bind " + this.toString(), this.toString());
		}
	}
	
	public void unbind() {
		this.unbind(RMI.DISCON);
	}
	
	public void unbind(int option) {
		switch (option) {
			case RMI.FORCE :
				try {
					RMISystem.getRegis().unbind(this.id);
				} catch (RemoteException | NotBoundException e) {
					Core.exception(e, "Cannot unbind " + this.toString() + " mode: FORCE", this.toString());
				}
				break;
			case RMI.DISCON :
				for (AN<String, RMI> n = this.connects.root(); n != null; n = n.next()) {
					this.discon(n.content());
				}
				this.connects.clear();
				try {
					RMISystem.getRegis().unbind(this.id);
				} catch (RemoteException | NotBoundException e) {
					Core.exception(e, "Cannot unbind " + this.toString() + " mode: default", this.toString());
				}
				break;
			default :
				if (this.connects.length() == 0) {
					try {
						RMISystem.getRegis().unbind(this.id);
					} catch (RemoteException | NotBoundException e) {
						Core.exception(e, "Cannot unbind " + this.toString() + " mode: default", this.toString());
					}
				}
		}
	}
	
	public void connect(String ip, String id) {
		
	}
	
	public void disconnect(String ip, String id) {
		this.discon(this.connects.get(this.getName(ip, id)));
		this.connects.remove(this.getName(ip, id));
	}

	public String toString() {
		return "RMI-Object [" + this.id + "]";
	}
	
	public String getID() {
		return this.id;
	}
	
	public String getIP() {
		return IP.getIP();
	}
	
	public Registry getRegis() {
		return RMISystem.getRegis();
	}
	
	protected void discon(RMI rmi) {
		
	}
	
	public String getName(String ip, String id) {
		return ip + "-" + id;
	}
	
}

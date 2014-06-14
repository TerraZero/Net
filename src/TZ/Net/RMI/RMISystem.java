package TZ.Net.RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import TZ.Core.Core;

public class RMISystem {

	private static Registry regis;
	
	public static Registry getRegis() {
		if (RMISystem.regis == null) {
			try {
				LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
				RMISystem.regis = LocateRegistry.getRegistry();
			} catch (RemoteException e) {
				Core.exception(e, "Cannot create RMI Registry", "RMI-System");
			}
		}
		return RMISystem.regis;
	}
	
	public static void setRegis(Registry regis) {
		RMISystem.regis = regis;
	}
	
}

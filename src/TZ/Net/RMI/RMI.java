package TZ.Net.RMI;

import java.rmi.Remote;
import java.rmi.registry.Registry;

public interface RMI extends Remote {
	
	public static final int FORCE = 0;
	public static final int DISCON = 1;
	
	public void bind();
	
	public void unbind();
	
	public void unbind(int option);
	
	public void connect(String ip, String id);
	
	public void disconnect(String ip, String id);
	
	//############################################
	
	public String getIP();
	
	public String getID();
	
	public Registry getRegis();
	
	public String getName(String ip, String id);

}

package TZ.Net;

import java.net.InetAddress;
import java.net.UnknownHostException;

import TZ.Core.Core;

/**
 * Includes common IP functions
 * @author TerraZero
 *
 */
public class IP {
	
	/**
	 * Convert a InetAddress in a String IP
	 * @param address
	 *   The InetAddress to convert 
	 * @return
	 *   String - The converted InetAddress
	 */
	public static String getIPFromInetAddress(InetAddress address) {
		return (address.toString() + "/").split("/")[1];
	}
	
	/**
	 * Convert a String IP in a InetAddress
	 * @param ip
	 *   The String IP to convert
	 * @return
	 *   InetAddress - The converted InetAddress
	 * @log
	 *   UnknownHostException<br />
	 *   <i>args: ip</i>
	 */
	public static InetAddress getInetAddressFromIP(String ip) {
		try {
			return InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			Core.exception(e, "IP: " + ip);
			return null;
		}
	}
	
	/**
	 * Get String IP from address String
	 * @param address
	 *   The address String<br />
	 *   <i>format: ip:port - 127.0.0.1:55555</i>
	 * @return
	 *   String - The String IP
	 */
	public static String getIP(String address) {
		return address.split(":")[0];
	}
	
	/**
	 * Get the port of the address String
	 * @param address
	 *   The address String<br />
	 *   <i>format: ip:port - 127.0.0.1:55555</i>
	 * @return
	 *   int - The port of the address String
	 */
	public static int getPort(String address) {
		return Integer.parseInt(address.split(":")[1]);
	}
	
	/**
	 * Get a address String
	 * @param ip
	 *   The given ip
	 * @param port
	 *   The given port
	 * @return
	 *   The address String<br />
	 *   <i>format: ip:port - 127.0.0.1:55555</i>
	 */
	public static String getAddress(String ip, int port) {
		return ip + ":" + port;
	}
	
}

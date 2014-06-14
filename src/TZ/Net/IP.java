package TZ.Net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import TZ.Core.Core;
import TZ.V5.Bytes;

/**
 * Includes common IP functions
 * @author TerraZero
 *
 */
public class IP {
	
	private static String ip;
	private static String mac;
	private static String mask;
	private static String broadcast;
	
	public static String getIP() {
		if (IP.ip == null) {
			try {
				IP.ip = InetAddress.getLocalHost().toString().split("/")[1];
			} catch (UnknownHostException e) {
				Core.exception(e, "Cannot create ip", "IP");
			}
		}
		return IP.ip;
	}
	
	public static String getMAC() {
		if (IP.mac == null) {
			try {
				NetworkInterface network = NetworkInterface.getByInetAddress(Inet4Address.getLocalHost());
				byte[] mac = network.getHardwareAddress();
				String macaddress = "";
				for (int i = 0; i < mac.length; i++) {
					macaddress += String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : "");
				}
				IP.mac = macaddress;
			} catch (SocketException | UnknownHostException e) {
				Core.exception(e, "Cannot create MAC", "IP");
			}
		}
		return IP.mac;
	}
	
	public static String getMask() {
		if (IP.mask == null) {
			try {
				NetworkInterface network = NetworkInterface.getByInetAddress(Inet4Address.getLocalHost());
				InterfaceAddress address = null;
				for (InterfaceAddress add : network.getInterfaceAddresses()) {
					if (add != null) {
						address = add;
						break;
					}
				}
				IP.broadcast = address.getBroadcast().toString().split("/")[1];
				IP.mask = IP.parsePrefix(address.getNetworkPrefixLength());
			} catch (SocketException | UnknownHostException e) {
				Core.exception(e, "Cannot create Mask", "IP");
			}
		}
		return IP.mask;
	}
	
	public static String getBroadcast() {
		if (IP.broadcast == null) {
			try {
				NetworkInterface network = NetworkInterface.getByInetAddress(Inet4Address.getLocalHost());
				InterfaceAddress address = null;
				for (InterfaceAddress add : network.getInterfaceAddresses()) {
					if (add != null) {
						address = add;
						break;
					}
				}
				IP.broadcast = address.getBroadcast().toString().split("/")[1];
				IP.mask = IP.parsePrefix(address.getNetworkPrefixLength());
			} catch (SocketException | UnknownHostException e) {
				Core.exception(e, "Cannot create Broadcast", "IP");
			}
		}
		return IP.broadcast;
	}
	
	public static String parsePrefix(int prefix) {
		int bytes = prefix / 8;
		int endbyte = prefix % 8;
		StringBuilder ip = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			if (bytes > i) {
				ip.append("255");
			} else if (bytes == i && endbyte != 0) {
				ip.append(Integer.parseInt(Bytes.getByte(endbyte), 2));
			} else {
				ip.append("0");
			}
			if (i != 3) {
				ip.append(".");
			}
		}
		return ip.toString();
	}
	
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
	 *   <i>format: ip:port - 127.0.0.1:55555</i>ssssssss
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

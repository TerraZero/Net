package TZ.Net;

import java.net.InetAddress;
import java.net.UnknownHostException;

import TZ.Core.Core;

public class IP {
	
//	public static String parsePrefix(int prefix) {
//		int bytes = prefix / 8;
//		int endbyte = prefix % 8;
//		StringBuilder ip = new StringBuilder();
//		for (int i = 0; i < 4; i++) {
//			if (bytes > i) {
//				ip.append("255");
//			} else if (bytes == i && endbyte != 0) {
//				ip.append(Integer.parseInt(Bytes.getByte(endbyte), 2));
//			} else {
//				ip.append("0");
//			}
//			if (i != 3) {
//				ip.append(".");
//			}
//		}
//		return ip.toString();
//	}
	
	public static String getIPFromInetAddress(InetAddress address) {
		return (address.toString() + "/").split("/")[1];
	}
	
	public static InetAddress getInetAddressFromIP(String ip) {
		try {
			return InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			Core.exception(e, "IP: " + ip);
			return null;
		}
	}
	
}

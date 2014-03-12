package TZ.Net.UDP;

import java.net.DatagramPacket;

import TZ.Net.IP;

/**
 * Includes common Datagram functions
 * @author TerraZero
 *
 */
public class UDPParser {

	/**
	 * Extract the data as String
	 * @param data
	 *   The DatagramPacket from which extract the data
	 * @return
	 *   String data.getData()
	 */
	public static String getString(DatagramPacket data) {
		return new String(data.getData(), 0, data.getLength());
	}
	
	/**
	 * Extract the data as String
	 * @param data
	 * 	The DatagramPacket from which extract the data
	 * @param length
	 * 	The length of the extraction
	 * @return
	 *  String data.getData() 
	 */
	public static String getString(DatagramPacket data, int length) {
		return new String(data.getData(), 0, length);
	}
	
	/**
	 * Extract the address from a packet
	 * @param data
	 *   The DatagramPacket from which extract the address
	 * @return
	 *   IP.getAddress(String, int)
	 */
	public static String getAddress(DatagramPacket data) {
		return IP.getIPFromInetAddress(data.getAddress()) + ":" + data.getPort();
	}

}

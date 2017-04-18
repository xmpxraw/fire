package org.yuantai.common.util;

import java.net.InetAddress;

public class NetUtil {

	public static boolean ping(String ip) {
		try {
			InetAddress address = InetAddress.getByName(ip);
			if (address.isReachable(5000)) {
				return true;
			}
			/*Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				if (address.isReachable(ni, 0, 5000)) {
					return true;
				}
			}*/
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}

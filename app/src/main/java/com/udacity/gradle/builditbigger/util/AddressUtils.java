package com.udacity.gradle.builditbigger.util;

import com.udacity.gradle.builditbigger.helper.Constants;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class AddressUtils {
    public static String getIPAddress() {
        boolean isEmulator = DeviceUtils.isEmulator();

        if (isEmulator) {
            return Constants.EMULATOR_IP_ADDRESS;
        } else {
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                            return inetAddress.getHostAddress() + ":8080/_ah/api/";
                        }
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
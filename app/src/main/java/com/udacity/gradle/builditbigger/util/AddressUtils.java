package com.udacity.gradle.builditbigger.util;

import com.udacity.gradle.builditbigger.BuildItBiggerApp;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.helper.Constants;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import timber.log.Timber;

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
                            Timber.d(String.format(BuildItBiggerApp.getInstance()
                                    .getString(R.string.real_device_ip_address), inetAddress.getHostAddress()));

                            return String.format(BuildItBiggerApp.getInstance()
                                    .getString(R.string.real_device_ip_address), inetAddress.getHostAddress());
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
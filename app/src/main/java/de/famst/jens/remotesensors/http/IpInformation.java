package de.famst.jens.remotesensors.http;

import android.net.wifi.WifiManager;

/**
 * Created by jens on 30/08/14.
 */
public class IpInformation
{
    private WifiManager wifiManager = null;

    public IpInformation(WifiManager wifiManager)
    {
        this.wifiManager = wifiManager;
    }

    public String getCurrentIp()
    {
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        final String formattedIpAddress =
                String.format("%d.%d.%d.%d",
                        (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                        (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));

        return formattedIpAddress;
    }
}

package com.morty.nt.util;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import org.apache.log4j.Logger;

/**
 * Just list the network interfaces.
 * @author amorton
 */
public class InterfaceLister
{

    public static final Logger m_logger = Logger.getLogger(InterfaceLister.class);


    public static String[] getDevices()
    {
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
        String[] names = new String[devices.length];

        for(int i=0; i<devices.length;i++)
        {
            NetworkInterface current = devices[i];
            names[i] = current.name;
        }

        return names;

    }

    public static NetworkInterface getInterface(String name) throws Exception
    {
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
        NetworkInterface returnDevice = null;

        for(int i=0; i<devices.length;i++)
        {
            NetworkInterface current = devices[i];
            if(current.name.equals(name))
            {
                returnDevice = current;
                break;
            }
        }

        if(returnDevice == null)
            throw new Exception("Unknown device ["+name+"]");
        else return returnDevice;

    }



    public static void main(String[] args)
    {
        m_logger.info("Starting InterfaceLister");
        try
        {
            NetworkInterface[] devices = JpcapCaptor.getDeviceList();
            for(int i=0; i<devices.length;i++)
            {
                NetworkInterface current = devices[i];
                m_logger.info("Network Device: Index["+i+"]  Name["+current.name+"] MAC Address ["+new String(current.mac_address)+"]  Description ["+current.description+"]");
            }
        }
        catch(Exception e)
        {
            m_logger.error("Error occurred",e);
        }
        finally
        {
            m_logger.info("Finished InterfaceLister");
        }
    }

}

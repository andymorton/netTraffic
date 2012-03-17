package com.morty.nt;

import com.morty.nt.util.InterfaceLister;
import jpcap.JpcapCaptor;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import org.apache.log4j.Logger;

/**
 * Monitors the network traffic....
 * @author amorton
 */
public class NetworkMonitor
{
    private final Logger m_logger = Logger.getLogger(getClass());
    private String m_Interface;
    PacketReceiver m_receiver = new PacketDumper();

    public void setInterface(String s)
    {
        m_Interface = s;
    }

    public String getInterface()
    {
        return m_Interface;
    }

    public void setReceiver(PacketDumper receiver)
    {
        m_receiver = receiver;
    }


    public void monitor() throws Exception
    {
        //get the network list
        m_logger.info("Starting to monitor ["+m_Interface+"]");
        
        
        //if not there bail...
        JpcapCaptor jpcap = JpcapCaptor.openDevice(InterfaceLister.getInterface(m_Interface), 2000, false, 20);


        //then pass off to retriever (PacketDumper).
        jpcap.loopPacket(-1, m_receiver);

    }


}

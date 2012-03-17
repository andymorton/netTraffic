package com.morty.nt;

import java.util.HashSet;
import javax.annotation.PostConstruct;
import jpcap.PacketReceiver;
import jpcap.packet.ARPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import org.apache.log4j.Logger;

/**
 * Accepts the packets and does stuff with them.
 * @author amorton
 */
public class PacketDumper implements PacketReceiver
{
    public final Logger m_logger = Logger.getLogger(getClass());


    private HashSet m_excludedPorts = new HashSet();

    @PostConstruct
    public void doStuff()
    {
        m_excludedPorts.add(1900);
    }


    public void receivePacket(Packet packet)
    {
        //I cant be bothered with ARP packets
        if(!(packet instanceof ARPPacket))
        {
            if(packet instanceof TCPPacket)
            {
                String data = new String(packet.data);
                String[] lines  = data.split("\n");
                //m_logger.info("Lines ["+lines.length+"]");

                String firstline = lines[0];
                String[] words = firstline.split(" ");
                String secondWord = null;
                if(words.length >1)
                    secondWord = words[1];


                //Now get the host...generally on the second line!
                String host = null;
                if(lines.length > 1 && lines[1].startsWith("Host"))
                    host = lines[1];

                //SSL URL decrypter...
                if(((TCPPacket)packet).dst_port == 443)
                {
                    m_logger.info("SSL Packet: ["+packet.toString()+"]");
                    m_logger.info("SSL Packet Data: ["+packet.data+"]");
                    m_logger.info("SSL Packet Header: ["+packet.header+"]");
                }


                if(secondWord != null && host != null)
                {
                    //We want to write to a log file - configuration through log4j.
                    //m_logger.info("Packet ["+packet.toString()+"]");
                    m_logger.info("Packet Info Source ["+((TCPPacket)packet).src_ip+"] Dest ["+((TCPPacket)packet).dst_ip+"] Host["+host+"] Request ["+secondWord+"]");
                }
                
                //m_logger.info("Packet Data["+new String(packet.data)+"]");
            }
        }


        //Optionally save it??
    }

}

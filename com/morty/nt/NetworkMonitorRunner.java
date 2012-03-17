package com.morty.nt;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Looks up the network runner and runs it...
 * @author amorton
 */
public class NetworkMonitorRunner
{

    private static final Logger m_logger = Logger.getLogger(NetworkMonitorRunner.class);

    public static void main(String[] args)
    {
        try
        {
            //Get the spring bean, and run
            m_logger.info("Starting Network Monitor ");
            String springFile = args[0];
            m_logger.info("Using Spring File ["+springFile+"]");

            final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(springFile);
            final NetworkMonitor monitor = (NetworkMonitor) ctx.getBean(NetworkMonitorConstants.NM_BEAN);

            monitor.monitor();
        }
        catch(Exception e)
        {
            m_logger.error("Unable to get bean from spring context. Please confirm file and bean details.",e);
        }
    }


}

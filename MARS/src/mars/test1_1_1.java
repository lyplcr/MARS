/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mars;

import org.ros.*;
import java.net.InetAddress;
import org.ros.address.InetAddressFactory;
import org.ros.node.NodeConfiguration;
//import org.ros.tutorials.pubsub.Talker;

/**
 * 
 * @author Thomas Tosik <tosik at iti.uni-luebeck.de>
 */
public class test1_1_1{

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
       //String master_uri = "http://Tosik-PC-Ubuntu:11311/"; 
       String master_uri = "http://141.83.88.157:11311/"; 
       java.net.URI muri = java.net.URI.create(master_uri);
       InetAddress host = InetAddressFactory.newNonLoopback();
       //NodeConfiguration nodeConf = NodeConfiguration.newPublic(host.getHostName(), muri);
       NodeConfiguration nodeConf = NodeConfiguration.newPublic("141.83.88.166", muri);
       //DefaultNodeFactory factory= new DefaultNodeFactory();
       //org.ros.node.Node node = factory.newNode("AAAAAAAAAA", nodeConf);
       /*Listener ros_listener = new Listener();
       ros_listener.main(nodeConf);*/
    }

}

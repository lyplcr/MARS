/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.sensors;

import com.jme3.scene.Node;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import mars.PhysicalEnvironment;
import mars.ros.MARSNodeMain;
import mars.states.SimState;
import org.jboss.netty.buffer.ChannelBuffers;
import org.ros.message.Time;
import org.ros.node.topic.Publisher;

/**
 *
 * @author Thomas Tosik <tosik at iti.uni-luebeck.de>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Hakuyo extends LaserScanner{

    ///ROS stuff
    /**
     * 
     */
    protected Publisher<sensor_msgs.LaserScan> publisher = null;
    /**
     * 
     */
    protected sensor_msgs.LaserScan fl;
    /**
     * 
     */
    protected std_msgs.Header header; 
    
    /**
     * 
     */
    public Hakuyo(){
        super();
    }

    @Override
    protected float calculateAverageNoiseFunction(float x){
        return 14.22898616f*((float)Math.pow(1.03339750f, (float)Math.abs(x)) );
    }

    @Override
    protected float calculateStandardDeviationNoiseFunction(float x){
        return 7.50837174f*((float)Math.pow(1.02266704f, (float)Math.abs(x)) );
    }
    
        /**
     * 
     * @param ros_node
     * @param auv_name
     */
    @Override
    public void initROS(MARSNodeMain ros_node, String auv_name) {
        super.initROS(ros_node, auv_name);
        publisher = ros_node.newPublisher(auv_name + "/" + this.getPhysicalExchangerName(),sensor_msgs.LaserScan._TYPE);  
        fl = this.mars_node.getMessageFactory().newFromType(sensor_msgs.LaserScan._TYPE);
        header = this.mars_node.getMessageFactory().newFromType(std_msgs.Header._TYPE);
        this.rosinit = true;
    }
    
    /**
     * 
     */
    @Override
    public void publish() {
        super.publish();
        header.setSeq(rosSequenceNumber++);
        header.setFrameId(this.getRos_frame_id());
        header.setStamp(Time.fromMillis(System.currentTimeMillis()));
        fl.setHeader(header);
        
        float[] instantData = getInstantData();
        float lastHeadPosition = getLastHeadPosition();
        this.mars.getTreeTopComp().initRayBasedData(instantData,lastHeadPosition,this);
        fl.setAngleIncrement(getScanning_resolution());
        fl.setRangeMax(getMaxRange());
        fl.setRangeMin(getMinRange());
        fl.setScanTime(getRos_publish_rate()/1000f);
        //fl.setTimeIncrement();
        fl.setAngleMax(getScanningAngleMax());
        fl.setAngleMin(getScanningAngleMin());
        
        fl.setRanges(instantData);
        
        if( publisher != null ){
            publisher.publish(fl);
        }
    }    
}

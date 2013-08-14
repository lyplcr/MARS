/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mars.sensors;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import mars.ChartValue;
import org.ros.node.topic.Publisher;
import mars.NoiseType;
import mars.PhysicalExchanger;
import mars.states.SimState;
import mars.ros.MARSNodeMain;

/**
 *  This a basis Velocimeter class. It gives you the linear velocity.
 * @author Thomas Tosik
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Velocimeter extends Sensor implements ChartValue{

   ///ROS stuff
    private Publisher<std_msgs.Float32> publisher = null;
    private std_msgs.Float32 fl;
    
    /**
     * 
     */
    public Velocimeter(){
        super();
    }
    
    /**
     *
     * @param simstate 
     */
    public Velocimeter(SimState simstate){
        super(simstate);
    }
    
    public Velocimeter(Velocimeter sensor){
        super(sensor);
    }

    @Override
    public PhysicalExchanger copy() {
        Velocimeter sensor = new Velocimeter(this);
        sensor.initAfterJAXB();
        return sensor;
    }

    public void update(float tpf){

    }

    /**
     *
     */
    public void init(Node auv_node){
        this.auv_node = auv_node;
    }

    /**
     * 
     * @return
     */
    public Vector3f getLinearVelocity(){
        if(getNoise_type() == NoiseType.NO_NOISE){
            return getLinearVelocityRaw();
        }else if(getNoise_type() == NoiseType.UNIFORM_DISTRIBUTION){
            float noise = getUnifromDistributionNoise(getNoise_value());
            Vector3f noised = new Vector3f(getLinearVelocityRaw().x+((float)((1f/100f)*noise)),getLinearVelocityRaw().y+((float)((1f/100f)*noise)),getLinearVelocityRaw().z+((float)((1f/100f)*noise)));
            return noised;
        }else if(getNoise_type() == NoiseType.GAUSSIAN_NOISE_FUNCTION){
            float noise = getGaussianDistributionNoise(getNoise_value());
            Vector3f noised = new Vector3f(getLinearVelocityRaw().x+((float)((1f/100f)*noise)),getLinearVelocityRaw().y+((float)((1f/100f)*noise)),getLinearVelocityRaw().z+((float)((1f/100f)*noise)));
            return noised;
        }else{
            return getLinearVelocityRaw();
        }
    }

    /**
     *
     * @return
     */
    private Vector3f getLinearVelocityRaw(){
        return physics_control.getLinearVelocity();
    }

    /**
     *
     * @return
     */
    public float getLinearVelocityXAxis(){
        return physics_control.getLinearVelocity().x;
    }

    /**
     *
     * @return
     */
    public float getLinearVelocityYAxis(){
        return physics_control.getLinearVelocity().y;
    }

    /**
     *
     * @return
     */
    public float getLinearVelocityZAxis(){
        return physics_control.getLinearVelocity().z;
    }

    /**
     *
     */
    public void reset(){

    }
    
    /**
     * 
     * @param ros_node
     * @param auv_name
     */
    @Override
    public void initROS(MARSNodeMain ros_node, String auv_name) {
        super.initROS(ros_node, auv_name);
        publisher = ros_node.newPublisher(auv_name + "/" + this.getPhysicalExchangerName(),std_msgs.Float32._TYPE);  
        fl = this.mars_node.getMessageFactory().newFromType(std_msgs.Float32._TYPE);
        this.rosinit = true;
    }

    /**
     * 
     */
    @Override
    public void publish() {
        fl.setData(getLinearVelocity().length());
        if( publisher != null ){
            publisher.publish(fl);
        }
    }
    
    @Override
    public Object getChartValue() {
        return getLinearVelocity().length();
    }

    @Override
    public long getSleepTime() {
        return getRos_publish_rate();
    }
}
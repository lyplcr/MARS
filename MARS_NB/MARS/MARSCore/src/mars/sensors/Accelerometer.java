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
import mars.PhysicalEnvironment;
import mars.PhysicalExchanger;
import mars.states.SimState;
import mars.ros.MARSNodeMain;

/**
 * An basic Acclerometer class. Measures the accleration for all 3 axis.
 * @author Thomas Tosik
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Accelerometer extends Sensor implements ChartValue{

    private Vector3f old_velocity = new Vector3f(0f,0f,0f);
    private Vector3f new_velocity = new Vector3f(0f,0f,0f);
    private Vector3f acceleration = new Vector3f(0f,0f,0f);
    
    ///ROS stuff
    private Publisher<std_msgs.Float32> publisher = null;
    private std_msgs.Float32 fl;
    
    /**
     * 
     */
    public Accelerometer(){
        super();
    }
        
    /**
     *
     * @param simstate 
     * @param pe
     */
    public Accelerometer(SimState simstate,PhysicalEnvironment pe){
        super(simstate);
        this.pe = pe;
    }

    /**
     * 
     * @param simstate 
     */
    public Accelerometer(SimState simstate){
        super(simstate);
    }
    
    public Accelerometer(Accelerometer accelerometer){
        super(accelerometer);
    }

    @Override
    public PhysicalExchanger copy() {
        Accelerometer sensor = new Accelerometer(this);
        sensor.initAfterJAXB();
        return sensor;
    }

    /**
     *
     */
    public void init(Node auv_node){
        this.auv_node = auv_node;
    }

    /**
     *
     * @param tpf
     */
    public void update(float tpf){
        new_velocity = physics_control.getLinearVelocity();//get the new velocity
        Vector3f difference_velocity = new_velocity.subtract(old_velocity);
        acceleration = difference_velocity.divide(tpf);
        acceleration = acceleration.add(pe.getGravitational_acceleration_vector());//dont forget the gravitational accleration
        old_velocity = new_velocity.clone();
    }

    /**
     *
     * @return
     */
    public float getAccelerationXAxis(){
        return acceleration.x;
    }

    /**
     *
     * @return
     */
    public float getAccelerationYAxis(){
        return acceleration.y;
    }

    /**
     * 
     * @return
     */
    public float getAccelerationZAxis(){
        return acceleration.z;
    }

    /**
     * 
     * @return
     */
    public Vector3f getAcceleration(){
        if(getNoise_type() == NoiseType.NO_NOISE){
            return getAccelerationRaw();
        }else if(getNoise_type() == NoiseType.UNIFORM_DISTRIBUTION){
            float noise = getUnifromDistributionNoise(getNoise_value());
            Vector3f noised = new Vector3f(getAccelerationRaw().x+((float)((1f/100f)*noise)),getAccelerationRaw().y+((float)((1f/100f)*noise)),getAccelerationRaw().z+((float)((1f/100f)*noise)));
            return noised;
        }else if(getNoise_type() == NoiseType.GAUSSIAN_NOISE_FUNCTION){
            float noise = getGaussianDistributionNoise(getNoise_value());
            Vector3f noised = new Vector3f(getAccelerationRaw().x+((float)((1f/100f)*noise)),getAccelerationRaw().y+((float)((1f/100f)*noise)),getAccelerationRaw().z+((float)((1f/100f)*noise)));
            return noised;
        }else{
            return getAccelerationRaw();
        }
    }

    /**
     *
     * @return
     */
    private Vector3f getAccelerationRaw(){
        return acceleration;
    }

    /**
     *
     */
    public void reset(){
        old_velocity = new Vector3f(0f,0f,0f);
        new_velocity = new Vector3f(0f,0f,0f);
        acceleration = new Vector3f(0f,0f,0f);
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
        fl.setData((getAcceleration().length()));
        if( publisher != null ){
            publisher.publish(fl);
        }
    }
    
    @Override
    public Object getChartValue() {
        return getAcceleration().length();
    }

    @Override
    public long getSleepTime() {
        return getRos_publish_rate();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.sensors;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import mars.Initializer;
import org.ros.node.topic.Publisher;
import mars.NoiseType;
import mars.PhysicalEnvironment;
import mars.PhysicalExchanger;
import mars.states.SimState;
import mars.ros.MARSNodeMain;
import org.ros.message.Time;

/**
 * Returns the force of the water current. NOT FINISHED! DONT USE!
 * @author Thomas Tosik
 */
@XmlAccessorType(XmlAccessType.NONE)
public class FlowMeter extends Sensor{

    private Geometry FlowMeterStart;
    
    private Initializer initer;

    ///ROS stuff
    private Publisher<geometry_msgs.Vector3Stamped> publisher = null;
    private geometry_msgs.Vector3Stamped fl;
    private std_msgs.Header header; 
    
    /**
     * 
     */
    public FlowMeter(){
        super();
    }
        
     /**
     *
     * @param simstate 
      * @param pe
     */
    public FlowMeter(SimState simstate, PhysicalEnvironment pe){
        super(simstate);
        this.pe = pe;
    }

    /**
     *
     * @param simstate 
     */
    public FlowMeter(SimState simstate){
        super(simstate);
    }
    
    public FlowMeter(FlowMeter sensor){
        super(sensor);
    }

    @Override
    public PhysicalExchanger copy() {
        FlowMeter sensor = new FlowMeter(this);
        sensor.initAfterJAXB();
        return sensor;
    }

    /**
     *
     */
    public void init(Node auv_node){

        Sphere sphere7 = new Sphere(16, 16, 0.025f);
        FlowMeterStart = new Geometry("FlowMeterStart", sphere7);
        Material mark_mat7 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mark_mat7.setColor("Color", ColorRGBA.White);
        FlowMeterStart.setMaterial(mark_mat7);
        //FlowMeterStart.setLocalTranslation(getFlowMeterStartVector());
        FlowMeterStart.updateGeometricState();
        PhysicalExchanger_Node.attachChild(FlowMeterStart);
        PhysicalExchanger_Node.setLocalTranslation(getPosition());
        Quaternion quat = new Quaternion();
        quat.fromAngles(getRotation().getX(),getRotation().getY(),getRotation().getZ());
        PhysicalExchanger_Node.setLocalRotation(quat);
        auv_node.attachChild(PhysicalExchanger_Node);
        this.auv_node = auv_node;
    }

    public void update(float tpf){

    }

     /**
     *
     * @return The exact depth of the current auv
     */
    public Vector3f getFlowForce(){
        if(getNoise_type() == NoiseType.NO_NOISE){
            return getRawFlowForce();
        }else if(getNoise_type() == NoiseType.UNIFORM_DISTRIBUTION){
            float noise = getUnifromDistributionNoise(getNoise_value());
            Vector3f noised = new Vector3f(getRawFlowForce().x+((float)((1f/100f)*noise)),getRawFlowForce().y+((float)((1f/100f)*noise)),getRawFlowForce().z+((float)((1f/100f)*noise)));
            return noised;
        }else if(getNoise_type() == NoiseType.GAUSSIAN_NOISE_FUNCTION){
            float noise = getGaussianDistributionNoise(getNoise_value());
            Vector3f noised = new Vector3f(getRawFlowForce().x+((float)((1f/100f)*noise)),getRawFlowForce().y+((float)((1f/100f)*noise)),getRawFlowForce().z+((float)((1f/100f)*noise)));
            return noised;
        }else{
            return getRawFlowForce();
        }
    }

     /**
     *
     * @return The depth of the current auv 
     */
    private Vector3f getRawFlowForce(){
        return initer.getFlowVector();
    }

    /**
     *
     * @return
     */
    public PhysicalEnvironment getPe() {
        return pe;
    }

    /**
     *
     * @param pe
     */
    public void setPe(PhysicalEnvironment pe) {
        this.pe = pe;
    }
    
    /**
     *
     * @return
     */
    public Initializer getIniter() {
        return initer;
    }

    /**
     *
     * @param initer
     */
    public void setIniter(Initializer initer) {
        this.initer = initer;
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
        publisher = ros_node.newPublisher(auv_name + "/" + this.getPhysicalExchangerName(),geometry_msgs.Vector3Stamped._TYPE);  
        fl = this.mars_node.getMessageFactory().newFromType(geometry_msgs.Vector3Stamped._TYPE);
        header = this.mars_node.getMessageFactory().newFromType(std_msgs.Header._TYPE);
    }

    /**
     * 
     */
    @Override
    public void publish() {
        header.setSeq(rosSequenceNumber++);
        header.setFrameId(this.getRos_frame_id());
        header.setStamp(Time.fromMillis(System.currentTimeMillis()));
        fl.setHeader(header);
        
        geometry_msgs.Vector3 vec = this.mars_node.getMessageFactory().newFromType(geometry_msgs.Vector3._TYPE);
        vec.setX(getFlowForce().x);
        vec.setY(getFlowForce().z);
        vec.setZ(getFlowForce().y);

        fl.setVector(vec);
        if( publisher != null ){
            publisher.publish(fl);
        }
    }
}
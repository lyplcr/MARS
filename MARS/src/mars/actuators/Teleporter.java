/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.actuators;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import geometry_msgs.Point;
import geometry_msgs.Quaternion;
import geometry_msgs.Vector3;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import mars.PhysicalExchanger;
import mars.ros.MARSNodeMain;
import mars.states.SimState;
import org.ros.message.MessageListener;
import org.ros.node.topic.Subscriber;

/**
 *
 * @author Thomas Tosik <tosik at iti.uni-luebeck.de>
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Teleporter extends Actuator{
    
    /**
     * 
     */
    public Teleporter(){
        super();
    }
    
    /**
     *
     * @param simstate 
     * @param MassCenterGeom
     */
    public Teleporter(SimState simstate,Geometry MassCenterGeom) {
        super(simstate,MassCenterGeom);
    }

    /**
     *
     * @param simstate 
     */
    public Teleporter(SimState simstate) {
        super(simstate);
    }
    
    public Teleporter(Teleporter teleporter){
        super(teleporter);
    }

    @Override
    public PhysicalExchanger copy() {
        Teleporter actuator = new Teleporter(this);
        actuator.initAfterJAXB();
        return actuator;
    }

    /**
     * DON'T CALL THIS METHOD!
     * In this method all the initialiasing for the motor will be done and it will be attached to the physicsNode.
     */
    public void init(Node auv_node){
    }

    public void update(){
    }

   /**
     *
     * @param tpf
     */
    @Override
    public void update(float tpf){
    }

    public void reset(){
        
    }
    
    /**
     * 
     * @param vector
     */
    public void teleport(final Vector3f vector, final com.jme3.math.Quaternion quat){
        Future simStateFuture = this.simauv.enqueue(new Callable() {
            public Void call() throws Exception {
                getPhysicsControl().setPhysicsLocation(vector);
                getPhysicsControl().setPhysicsRotation(quat);
                return null;
            }
        }); 
    }
    
    /**
     * 
     * @param ros_node
     * @param auv_name
     */
    @Override
    public void initROS(MARSNodeMain ros_node, String auv_name) {
        super.initROS(ros_node, auv_name);
        final Teleporter self = this;
        Subscriber<geometry_msgs.PoseStamped> subscriber = ros_node.newSubscriber(auv_name + "/" + getPhysicalExchangerName(), geometry_msgs.PoseStamped._TYPE);
        subscriber.addMessageListener(new MessageListener<geometry_msgs.PoseStamped>() {
                @Override
                public void onNewMessage(geometry_msgs.PoseStamped message) {
                    System.out.println("I (" + getPhysicalExchangerName()+ ") heard: \"" + message.getPose().getPosition() + "\"");
                    
                    Point pos = (Point)message.getPose().getPosition();
                    Vector3f v_pos = new Vector3f((float)pos.getX(), (float)pos.getZ(), (float)pos.getY());
                    
                    //getting from ROS Co-S to MARS Co-S
                    Quaternion ori = (Quaternion)message.getPose().getOrientation();
                    com.jme3.math.Quaternion quat = new com.jme3.math.Quaternion((float)ori.getX(), (float)ori.getZ(), (float)ori.getY(), -(float)ori.getW());
                    com.jme3.math.Quaternion qrot = new com.jme3.math.Quaternion();
                    qrot.fromAngles(0f, FastMath.HALF_PI, 0);
                    quat.multLocal(qrot);

                    self.teleport(v_pos,quat);
                }
        });
    }
}

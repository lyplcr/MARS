/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.actuators.servos;

import com.jme3.scene.Geometry;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import mars.states.SimState;
import mars.ros.MARSNodeMain;
import org.ros.message.MessageListener;

/**
 *
 * @author Thomas Tosik
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Modelcraft_ES07 extends Servo{
    
    public Modelcraft_ES07(){
        super();
        OperatingAngle = 5.235987f;
        Resolution = 0.005061f;
        SpeedPerDegree = 0.003266f;
    }
    
    public Modelcraft_ES07(SimState simstate,Geometry MassCenterGeom) {
        super(simstate,MassCenterGeom);
        OperatingAngle = 5.235987f;
        Resolution = 0.005061f;
        SpeedPerDegree = 0.003266f;
    }

    public Modelcraft_ES07(SimState simstate) {
        super(simstate);
        OperatingAngle = 5.235987f;
        Resolution = 0.005061f;
        SpeedPerDegree = 0.003266f;
    }
    
        /**
     * 
     * @param ros_node
     * @param auv_name
     */
    @Override
    public void initROS(MARSNodeMain ros_node, String auv_name) {
        super.initROS(ros_node, auv_name);
        final Servo self = this;
        ros_node.newSubscriber(auv_name + "/" + getPhysicalExchangerName(), "smart_e_msgs/servoCam",
          new MessageListener<org.ros.message.smart_e_msgs.servoCam>() {
            @Override
            public void onNewMessage(org.ros.message.smart_e_msgs.servoCam message) {
              self.setDesiredAnglePosition((int)message.data);
            }
          });
    }
}
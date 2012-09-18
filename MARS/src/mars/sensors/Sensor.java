/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mars.sensors;

import mars.sensors.sonar.Sonar;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import java.util.HashMap;
import javax.swing.tree.TreePath;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import org.ros.node.topic.Publisher;
import mars.PhysicalEnvironment;
import mars.PhysicalExchanger;
import mars.MARS_Main;
import mars.ros.ROS_Publisher;
import mars.states.SimState;

/**
 * This is a basic sensors interface. Extend from here to make you
 * own sensors like an pressure sensor or light sensors.
 * @author Thomas Tosik
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso( {Accelerometer.class,Compass.class,Gyroscope.class,Sonar.class,InfraRedSensor.class,PingDetector.class,PressureSensor.class,SalinitySensor.class,Compass.class,TemperatureSensor.class,UnderwaterModem.class,Velocimeter.class,VideoCamera.class,IMU.class,Positionmeter.class,Orientationmeter.class,Posemeter.class,TerrainSender.class,GPSReceiver.class} )
public abstract class Sensor extends PhysicalExchanger implements ROS_Publisher{
    /*
     * 
     */
    /**
     * 
     */
    protected SimState simState;
    /**
     *
     */
    protected MARS_Main simauv;
    /**
     *
     */
    protected AssetManager assetManager;
    /**
     *
     */
    protected Node rootNode;
    /*
     * 
     */
    /**
     * 
     */
    protected long time = 0;
    
    /**
     * 
     */
    protected Sensor(){
    }
    
    /**
     * 
     * @param simstate 
     */
    protected Sensor(SimState simstate){
        setSimState(simstate);
        variables = new HashMap<String,Object> ();
    }

    /**
     *
     * @param simauv
     * @param pe
     */
    protected Sensor(MARS_Main simauv, PhysicalEnvironment pe){
        this.simauv = simauv;
        this.pe = pe;
        this.assetManager = simauv.getAssetManager();
        this.rootNode = simauv.getRootNode();
        variables = new HashMap<String,Object> ();
    }
    
    /**
     * 
     * @param simState
     */
    @Override
    public void setSimState(SimState simState) {
        this.simState = simState;
        this.simauv = this.simState.getMARS();
        this.assetManager = this.simauv.getAssetManager();
        this.rootNode = this.simState.getRootNode();
    }

    /**
     *
     */
    public abstract void init(Node auv_node);
    
    @Override
    public void updateState(TreePath path) {
    }
    
    /**
     * 
     */
    public void publish() {
    }

    /**
     * 
     */
    public void publishUpdate() {
        long curtime = System.currentTimeMillis();
        if( ((curtime-time) < getRos_publish_rate()) || (getRos_publish_rate() == 0) ){
            
        }else{
            time = curtime;
            if(mars_node != null && mars_node.isExisting()){
                if(mars_node.isRunning()){
                    publish();
                }
            }
        }
    }
}

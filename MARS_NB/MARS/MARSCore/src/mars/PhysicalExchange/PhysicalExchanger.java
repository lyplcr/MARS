/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.PhysicalExchange;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.rits.cloning.Cloner;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.swing.tree.TreePath;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import mars.Helper.Noise;
import mars.PhysicalEnvironment;
import mars.misc.PropertyChangeListenerSupport;
import mars.actuators.Actuator;
import mars.auv.AUV;
import mars.ros.MARSNodeMain;
import mars.ros.ROS;
import mars.ros.TF_ROS_Publisher;
import mars.sensors.Sensor;
import mars.states.SimState;
import mars.xml.HashMapAdapter;

/**
 * This is the basic interface for all sensors/actuators.
 *
 * @author Thomas Tosik
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({Actuator.class, Sensor.class})
public abstract class PhysicalExchanger extends Noise implements AUVObject, ROS, PropertyChangeListenerSupport {

    @SuppressWarnings("FieldMayBeFinal")
    private List listeners = Collections.synchronizedList(new LinkedList());

    /**
     *
     * @param pcl
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        listeners.add(pcl);
    }

    /**
     *
     * @param pcl
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        listeners.remove(pcl);
    }

    private void fire(String propertyName, Object old, Object nue) {
        //Passing 0 below on purpose, so you only synchronize for one atomic call:
        PropertyChangeListener[] pcls = (PropertyChangeListener[]) listeners.toArray(new PropertyChangeListener[0]);
        for (PropertyChangeListener pcl : pcls) {
            pcl.propertyChange(new PropertyChangeEvent(this, propertyName, old, nue));
        }
    }

    /**
     *
     * @param auv_node
     */
    public void init(Node auv_node) {
        this.auv_node = auv_node;
    }

    /**
     *
     */
    @XmlJavaTypeAdapter(HashMapAdapter.class)
    protected HashMap<String, Object> variables;

    /**
     *
     */
    protected Node PhysicalExchanger_Node = new Node();
    /**
     *
     */
    protected Node auv_node;
    /**
     *
     */
    protected AUV auv;
    /**
     *
     */
    protected String PhysicalExchangerName = "";
    /**
     *
     */
    protected RigidBodyControl physics_control;
    /**
     *
     */
    protected PhysicalEnvironment pe;
    /*
     * 
     */
    /**
     *
     */
    protected boolean enabled = true;
    /*
     * 
     */
    /**
     *
     */
    protected int ros_publish_rate = 1000;
    /**
     *
     */
    protected String ros_frame_id = "/map";
    /*
     * 
     */
    /**
     *
     */
    protected String ros_msg_type = "";
    /*
     * 
     */
    /**
     *
     */
    protected MARSNodeMain mars_node = null;

    /**
     *
     */
    protected int rosSequenceNumber = 0;

    /*
     * 
     */
    /**
     *
     */
    public TF_ROS_Publisher tf_pub = null;
    /**
     *
     */
    protected SimState simState = null;
    /**
     *
     */
    protected boolean rosinit = false;

    private long oldtime = 0;

    /**
     *
     * @param simState
     */
    public void setSimState(SimState simState) {
        this.simState = simState;
    }

    /**
     *
     * @return
     */
    public RigidBodyControl getPhysicsControl() {
        return physics_control;
    }

    /**
     *
     * @param physics_control
     */
    public void setPhysicsControl(RigidBodyControl physics_control) {
        this.physics_control = physics_control;
    }

    /**
     *
     * @param visible
     */
    public void setNodeVisibility(boolean visible) {
        if (visible) {
            PhysicalExchanger_Node.setCullHint(CullHint.Never);
        } else {
            PhysicalExchanger_Node.setCullHint(CullHint.Always);
        }
    }

    /**
     * Set the unique name.
     * 
     * @param name
     */
    @Override
    public void setName(String name) {
        String old = getName();
        variables.put("name", name);
        PhysicalExchanger_Node.setName(name);
        fire("name", old, name);
    }

    /**
     *
     * @return The unique name of the sensor/actuator.
     */
    @Override
    public String getName() {
        return (String) variables.get("name");
    }

    /**
     * Reset the sensors/actuator to the default settings.
     */
    public abstract void reset();

    /**
     *
     * @return A no-depth clone copy.
     */
    public abstract PhysicalExchanger copy();

    /**
     *
     * @param pe
     */
    public void copyValuesFromPhysicalExchanger(PhysicalExchanger pe) {
        HashMap<String, Object> variablesOriginal = pe.getAllVariables();
        Cloner cloner = new Cloner();
        variables = cloner.deepClone(variablesOriginal);

        HashMap<String, Object> noisevariablesOriginal = pe.getAllNoiseVariables();
        noises = cloner.deepClone(noisevariablesOriginal);
    }

    /**
     * Deattach from all nodes, filters.....
     */
    public void cleanup() {
        auv_node.detachChild(PhysicalExchanger_Node);
    }

    /**
     *
     * @return
     */
    public Boolean isEnabled() {
        return (Boolean) variables.get("enabled");
    }

    /**
     *
     * @return
     */
    @Override
    public Boolean getEnabled() {
        return (Boolean) variables.get("enabled");
    }

    /**
     *
     * @param enabled
     */
    @Override
    public void setEnabled(Boolean enabled) {
        boolean old = getEnabled();
        variables.put("enabled", enabled);
        fire("enabled", old, enabled);
    }

    /**
     *
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     *
     * @return
     */
    @Override
    public String getROS_MSG_Type() {
        return ros_msg_type;
    }

    /**
     *
     */
    @Override
    public void initROS() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param ros_node
     * @param auv_name
     */
    @Override
    public void initROS(MARSNodeMain ros_node, String auv_name) {
        setROS_Node(ros_node);
        tf_pub.initROS(ros_node, auv_name);
    }

    /**
     *
     * @param ros_msg_type
     */
    @Override
    public void setROS_MSG_Type(String ros_msg_type) {
        this.ros_msg_type = ros_msg_type;
    }

    /**
     *
     * @return
     */
    @Override
    public MARSNodeMain getMARS_Node() {
        return mars_node;
    }

    /**
     *
     * @param ros_node
     */
    @Override
    public void setROS_Node(MARSNodeMain ros_node) {
        this.mars_node = ros_node;
    }

    /**
     *
     * @return
     */
    public Integer getRos_publish_rate() {
        return (Integer) variables.get("ros_publish_rate");
    }

    /**
     *
     * @param ros_publish_rate
     */
    public void setRos_publish_rate(Integer ros_publish_rate) {
        int old = getRos_publish_rate();
        variables.put("ros_publish_rate", ros_publish_rate);
        fire("ros_publish_rate", old, ros_publish_rate);
    }

    /**
     *
     * @return
     */
    public Integer getTFRos_publish_rate() {
        if ((Integer) variables.get("tf_ros_publish_rate") == null) {
            return 1000;
        } else {
            return (Integer) variables.get("tf_ros_publish_rate");
        }
    }

    /**
     *
     * @param tf_ros_publish_rate
     */
    public void setTFRos_publish_rate(Integer tf_ros_publish_rate) {
        int old = getTFRos_publish_rate();
        variables.put("tf_ros_publish_rate", tf_ros_publish_rate);
        fire("tf_ros_publish_rate", old, tf_ros_publish_rate);
    }

    /**
     *
     * @return
     */
    public String getRos_frame_id() {
        return (String) variables.get("ros_frame_id");
    }

    /**
     *
     * @param ros_frame_id
     */
    public void setRos_frame_id(String ros_frame_id) {
        String old = getRos_frame_id();
        //this.ros_frame_id = ros_frame_id;
        variables.put("ros_frame_id", ros_frame_id);
        fire("ros_frame_id", old, ros_frame_id);
    }

    /**
     *
     * @return
     */
    public Integer getRos_queue_listener_size() {
        Integer ros_queue_listener_size = (Integer) variables.get("ros_queue_listener_size");
        if (ros_queue_listener_size != null) {
            return ros_queue_listener_size;
        } else {
            return 1;
        }
    }

    /**
     *
     * @param ros_queue_listener_size
     */
    public void setRos_queue_listener_size(Integer ros_queue_listener_size) {
        int old = getRos_queue_listener_size();
        //this.ros_frame_id = ros_frame_id;
        variables.put("ros_queue_listener_size", ros_queue_listener_size);
        fire("ros_queue_listener_size", old, ros_queue_listener_size);
    }

    /**
     * The update method that will be called by the auvManager, hence by the
     * main mars update loop. Should be node safe
     *
     * @param tpf
     */
    public abstract void update(float tpf);

    /**
     *
     * @return
     */
    public PhysicalEnvironment getPhysical_environment() {
        return pe;
    }

    /**
     *
     * @param pe
     */
    public void setPhysical_environment(PhysicalEnvironment pe) {
        this.pe = pe;
    }

    /**
     *
     * @return
     */
    public HashMap<String, Object> getAllVariables() {
        return variables;
    }

    /**
     *
     * @return
     */
    public HashMap<String, String> getAllActions() {
        return null;
    }

    /**
     *
     */
    public void initAfterJAXB() {
        tf_pub = new TF_ROS_Publisher(this);
        /* variables.put("noise_type", getNoiseType());
         variables.put("noise_value", getNoiseValue());
         variables.put("name",getName());
         variables.put("enabled", isEnabled());
         variables.put("ros_publish_rate", getRos_publish_rate());
         variables.put("ros_frame_id", getRos_frame_id());*/
    }

    ;
    
    /**
     * 
     * @return
     */
    public String getIcon() {
        return (String) variables.get("icon");
    }

    /**
     *
     * @return
     */
    public String getdnd_icon() {
        return (String) variables.get("dndIcon");
    }

    /**
     *
     * @param icon
     */
    public void setIcon(String icon) {
        String old = getIcon();
        variables.put("icon", icon);
        fire("icon", old, icon);
    }

    /**
     *
     * @param dnd_icon
     */
    public void setdnd_icon(String dnd_icon) {
        String old = getdnd_icon();
        variables.put("dndIcon", dnd_icon);
        fire("dndIcon", old, dnd_icon);
    }

    /**
     *
     * @return
     */
    public String getAccumulator() {
        return (String) variables.get("accumulator");
    }

    /**
     *
     * @param accumulator
     */
    public void setAccumulator(String accumulator) {
        String old = getAccumulator();
        variables.put("accumulator", accumulator);
        fire("accumulator", old, accumulator);
    }

    /**
     *
     * @return
     */
    public Vector3f getPosition() {
        return (Vector3f) variables.get("Position");
    }

    /**
     *
     * @param Position
     */
    public void setPosition(Vector3f Position) {
        Vector3f old = getPosition();
        variables.put("Position", Position);
        fire("Position", old, Position);
    }

    /**
     *
     * @return
     */
    public Vector3f getRotation() {
        return (Vector3f) variables.get("Rotation");
    }

    /**
     *
     * @param Rotation
     */
    public void setRotation(Vector3f Rotation) {
        Vector3f old = getRotation();
        variables.put("Rotation", Rotation);
        fire("Rotation", old, Rotation);
    }

    /**
     *
     * @return
     */
    public Float getCurrentConsumption() {
        return (Float) variables.get("currentConsumption");
    }

    /**
     *
     * @param currentConsumption
     */
    public void setCurrentConsumption(Float currentConsumption) {
        Float old = getCurrentConsumption();
        variables.put("currentConsumption", currentConsumption);
        fire("currentConsumption", old, currentConsumption);
    }

    /**
     *
     * @param auv
     */
    public void setAuv(AUV auv) {
        this.auv = auv;
        this.auv_node = auv.getAUVNode();
    }

    /**
     *
     * @return
     */
    public AUV getAuv() {
        return auv;
    }

    /**
     *
     * @return
     */
    public Vector3f getTFPosition() {
        return PhysicalExchanger_Node.getLocalTranslation();
    }

    /**
     *
     * @return
     */
    public Quaternion getTFOrientation() {
        return PhysicalExchanger_Node.getLocalRotation();
    }

    /**
     *
     * @return
     */
    public Node getPhysicalExchanger_Node() {
        return PhysicalExchanger_Node;
    }

    /**
     *
     * @param path
     */
    @Deprecated
    public abstract void updateState(TreePath path);

    /**
     * Make a periodic action on call. Called by publishDataUpdate.
     */
    public void publishData() {
    }

    /**
     * The update loop for publishing of data from a sensor.
     */
    public void publishDataUpdate() {
        long curtime = System.currentTimeMillis();
        if (((curtime - oldtime) < getRos_publish_rate()) || (getRos_publish_rate() == 0)) {

        } else {
            oldtime = curtime;
            publishData();
        }
    }
}
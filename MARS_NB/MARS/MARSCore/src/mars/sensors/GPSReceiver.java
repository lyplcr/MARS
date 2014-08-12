/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.sensors;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import mars.PhysicalEnvironment;
import mars.PhysicalExchanger;
import mars.geodesy.Ellipsoid;
import mars.geodesy.GeodeticCalculator;
import mars.geodesy.GlobalPosition;
import mars.ros.MARSNodeMain;
import mars.states.SimState;
import org.ros.message.Time;
import org.ros.node.topic.Publisher;
import sensor_msgs.NavSatFix;

/**
 *
 * @author Thomas Tosik
 */
@XmlAccessorType(XmlAccessType.NONE)
public class GPSReceiver extends Sensor{
    
    @XmlElement(name="Positionmeter")
    Positionmeter pos = new Positionmeter();
    
    ///ROS stuff
    private Publisher<sensor_msgs.NavSatFix> publisher = null;
    private sensor_msgs.NavSatFix fl;
    private sensor_msgs.NavSatStatus NavSatStatus; 
    private std_msgs.Header header; 
    
    private Geometry GPSReceiverGeom;
    
    private GeodeticCalculator geoCalc = new GeodeticCalculator();
    private Ellipsoid reference = Ellipsoid.WGS84;  
    
    /**
     * 
     */
    public GPSReceiver(){
        super();
    }
        
    /**
     *
     * @param simstate 
     * @param pe
     */
    public GPSReceiver(SimState simstate,PhysicalEnvironment pe){
        super(simstate);
        this.pe = pe;
        pos.setPhysical_environment(pe);
        pos.setSimState(simState);
    }

    /**
     * 
     * @param simstate 
     */
    public GPSReceiver(SimState simstate){
        super(simstate);
        pos.setSimState(simState);
    }
    
    /**
     *
     * @param sensor
     */
    public GPSReceiver(GPSReceiver sensor){
        super(sensor);
        pos = (Positionmeter)sensor.getPositionMeter().copy();
    }

    /**
     *
     * @return
     */
    @Override
    public PhysicalExchanger copy() {
        GPSReceiver sensor = new GPSReceiver(this);
        sensor.initAfterJAXB();
        return sensor;
    }

    /**
     *
     */
    public void init(Node auv_node){
        super.init(auv_node);
        pos.init(auv_node);
        
        Sphere sphere7 = new Sphere(16, 16, 0.04f);
        GPSReceiverGeom = new Geometry("PressureStart", sphere7);
        Material mark_mat7 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mark_mat7.setColor("Color", ColorRGBA.White);
        GPSReceiverGeom.setMaterial(mark_mat7);
        GPSReceiverGeom.updateGeometricState();
        PhysicalExchanger_Node.attachChild(GPSReceiverGeom);
        PhysicalExchanger_Node.setLocalTranslation(getReferencePointWorld());
        rootNode.attachChild(PhysicalExchanger_Node);
    }

    /**
     *
     * @param tpf
     */
    public void update(float tpf){
        pos.update(tpf);
    }
    
    /**
     *
     */
    public void reset(){
        pos.reset();
    }

    /**
     *
     * @return
     */
    public Positionmeter getPositionMeter() {
        return pos;
    }

    /**
     *
     * @return
     */
    public Vector3f getReferencePointGPS() {
        return (Vector3f)variables.get("ReferencePointGPS");
    }

    /**
     *
     * @param ReferencePointGPS 
     */
    public void setReferencePointGPS(Vector3f ReferencePointGPS) {
        variables.put("ReferencePointGPS", ReferencePointGPS);
    }
    
    /**
     *
     * @return
     */
    public Vector3f getReferencePointWorld() {
        return (Vector3f)variables.get("ReferencePointWorld");
    }

    /**
     *
     * @param ReferencePointWorld 
     */
    public void setReferencePointWorld(Vector3f ReferencePointWorld) {
        variables.put("ReferencePointWorld", ReferencePointWorld);
    }
    
    /**
     *
     * @return
     */
    public Float getLatitudeFactor() {
        return (Float)variables.get("LatitudeFactor");
    }

    /**
     *
     * @param LatitudeFactor 
     */
    public void setLatitudeFactor(Float LatitudeFactor) {
        variables.put("LatitudeFactor", LatitudeFactor);
    }
    
    @Override
    public void setPhysical_environment(PhysicalEnvironment pe) {
        super.setPhysical_environment(pe);
        pos.setPhysical_environment(pe);
    }
    
    /**
     * 
     * @param simState
     */
    @Override
    public void setSimState(SimState simState) {
        super.setSimState(simState);
        pos.setSimState(simState);
    }
    
    @Override
    public void setPhysicsControl(RigidBodyControl physics_control) {
        super.setPhysicsControl(physics_control);
        pos.setPhysicsControl(physics_control);
    }
    
        /**
     *
     * @param visible
     */
    @Override
    public void setNodeVisibility(boolean visible){
        super.setNodeVisibility(visible);
        pos.setNodeVisibility(visible);
    }

    /**
     *
     * @param name
     */
    @Override
    public void setName(String name){
        super.setName(name);
        pos.setName(name + "_positionmeter");
    }
    
    /**
     * 
     * @param enabled
     */
    @Override
    public void setEnabled(Boolean enabled) {
        super.setEnabled(enabled);
        pos.setEnabled(enabled);
    }
    
    /**
     * 
     * @param ros_node
     * @param auv_name
     */
    @Override
    public void initROS(MARSNodeMain ros_node, String auv_name) {
        super.initROS(ros_node, auv_name);
        publisher = ros_node.newPublisher(auv_name + "/" + this.getName(),sensor_msgs.NavSatFix._TYPE);  
        fl = this.mars_node.getMessageFactory().newFromType(sensor_msgs.NavSatFix._TYPE);
        NavSatStatus = this.mars_node.getMessageFactory().newFromType(sensor_msgs.NavSatStatus._TYPE);
        header = this.mars_node.getMessageFactory().newFromType(std_msgs.Header._TYPE);
        this.rosinit = true;
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
        
        NavSatStatus.setService((short)1);
        NavSatStatus.setStatus((byte)0);
        fl.setStatus(NavSatStatus);
        
        GlobalPosition pointA = new GlobalPosition(getReferencePointGPS().z, getReferencePointGPS().x, 0.0); // Point A

        GlobalPosition userPos = new GlobalPosition(getReferencePointGPS().z+0.01f, getReferencePointGPS().x, 0.0); // Point B
        GlobalPosition userPos2 = new GlobalPosition(getReferencePointGPS().z, getReferencePointGPS().x+0.01f, 0.0); // Point B

        double distanceLat = geoCalc.calculateGeodeticCurve(reference, userPos, pointA).getEllipsoidalDistance(); // Distance between Point A and Point B
        double distanceLon = geoCalc.calculateGeodeticCurve(reference, userPos2, pointA).getEllipsoidalDistance(); // Distance between Point A and Point B
        
        Vector3f diffPosition = pos.getWorldPosition().subtract(getReferencePointWorld());
        double metLat = (1d/distanceLat)*(Math.abs(pointA.getLatitude()-userPos.getLatitude()));
        double latitude = diffPosition.z * metLat;
        
        double metLon = (1d/distanceLon)*(Math.abs(pointA.getLongitude()-userPos2.getLongitude()));
        double longitude = diffPosition.x * metLon;
        
        fl.setAltitude((double)pos.getPositionY());
        fl.setLatitude(((double)getReferencePointGPS().z) - latitude);
        fl.setLongitude(((double)getReferencePointGPS().x) + longitude);
        
        
        //old style
        /*double longitudeFactor = (double)getLatitudeFactor() * (double)Math.cos(((double)getReferencePointGPS().y)*(Math.PI/180d));
        Vector3f diffPosition = pos.getPosition().subtract(getReferencePointWorld());
        double latitude = (diffPosition.z/getLatitudeFactor())*(180d/Math.PI);
        double longitude = (diffPosition.x/longitudeFactor)*(180d/Math.PI);
        
        fl.setAltitude((double)pos.getPositionY());
        fl.setLatitude(((double)getReferencePointGPS().z) - latitude);
        fl.setLongitude(((double)getReferencePointGPS().x) + longitude);*/
        
        if( publisher != null ){
            publisher.publish(fl);
        }
    }
}

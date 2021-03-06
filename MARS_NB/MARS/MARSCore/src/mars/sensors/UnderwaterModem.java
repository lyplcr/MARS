/*
 * Copyright (c) 2015, Institute of Computer Engineering, University of Lübeck
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * 
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * 
 * * Neither the name of the copyright holder nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package mars.sensors;

import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Line;
import com.jme3.scene.shape.Sphere;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import mars.PhysicalExchange.PhysicalExchanger;
import mars.events.CommunicationDeviceEvent;
import mars.events.CommunicationDeviceEventType;
import mars.events.CommunicationType;
import mars.states.SimState;

/**
 * A underwater modem class for communication between the AUVs.
 *
 * @author Thomas Tosik
 */
@XmlAccessorType(XmlAccessType.NONE)
public class UnderwaterModem extends CommunicationDevice {

    private Geometry UnderwaterModemStart;
    private Geometry UnderwaterModemEnd;
    private Geometry DebugDistance;
    private Sphere debugDistanceSphere;
    private Material debugDistanceMat;
    private Node comNet = new Node("comNet");

    /**
     *
     */
    public UnderwaterModem() {
        super();
    }

    /**
     *
     * @param simstate
     */
    public UnderwaterModem(SimState simstate) {
        super(simstate);
    }

    /**
     *
     * @param sensor
     */
    public UnderwaterModem(UnderwaterModem sensor) {
        super(sensor);
    }

    /**
     *
     * @return
     */
    @Override
    public PhysicalExchanger copy() {
        UnderwaterModem sensor = new UnderwaterModem(this);
        sensor.initAfterJAXB();
        return sensor;
    }

    /**
     *
     * @return
     */
    @Override
    public Vector3f getWorldPosition() {
        return UnderwaterModemStart.getWorldTranslation();
    }

    /**
     *
     * @return
     */
    @Override
    public Float getPropagationDistance() {
        return (Float) variables.get("propagationDistance");
    }

    /**
     *
     * @param propagationDistance
     */
    public void setPropagationDistance(Float propagationDistance) {
        variables.put("propagationDistance", propagationDistance);
    }

    /**
     *
     * @return
     */
    public boolean getDebug() {
        return (Boolean) variables.get("debug");
    }

    /**
     *
     * @param debug
     */
    public void setDebug(boolean debug) {
        variables.put("debug", debug);
    }

    /**
     *
     * @return
     */
    public ColorRGBA getDebugColor() {
        return (ColorRGBA) variables.get("debugColor");
    }

    /**
     *
     * @param debugColor
     */
    public void setDebugColor(ColorRGBA debugColor) {
        variables.put("debugColor", debugColor);
    }

    @Override
    public void update(float tpf) {

    }

    @Override
    public void init(Node auv_node) {
        Sphere sphere7 = new Sphere(8, 8, 0.05f);
        UnderwaterModemStart = new Geometry("UnderwaterModemStart", sphere7);
        Material mark_mat7 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mark_mat7.setColor("Color", ColorRGBA.Blue);
        UnderwaterModemStart.setMaterial(mark_mat7);
        UnderwaterModemStart.updateGeometricState();
        PhysicalExchanger_Node.attachChild(UnderwaterModemStart);

        Sphere sphere9 = new Sphere(8, 8, 0.05f);
        UnderwaterModemEnd = new Geometry("UnderwaterModemEnd", sphere9);
        Material mark_mat9 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mark_mat9.setColor("Color", ColorRGBA.Blue);
        UnderwaterModemEnd.setMaterial(mark_mat9);
        UnderwaterModemEnd.setLocalTranslation(Vector3f.UNIT_X);
        UnderwaterModemEnd.updateGeometricState();
        PhysicalExchanger_Node.attachChild(UnderwaterModemEnd);

        //create debug stuff
        debugDistanceSphere = new Sphere(16, 16, getPropagationDistance());
        DebugDistance = new Geometry("DebugDistance", debugDistanceSphere);
        debugDistanceMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        debugDistanceMat.setColor("Color", getDebugColor());
        //mark_mat10.getAdditionalRenderState().setBlendMode(BlendMode.AlphaAdditive);
        debugDistanceMat.getAdditionalRenderState().setWireframe(true);
        //DebugDistance.setQueueBucket(Bucket.Transparent);
        DebugDistance.setMaterial(debugDistanceMat);
        DebugDistance.setLocalTranslation(Vector3f.ZERO);
        DebugDistance.updateGeometricState();
        PhysicalExchanger_Node.attachChild(DebugDistance);
        setDebugVisible(getDebug());

        PhysicalExchanger_Node.attachChild(comNet);

        PhysicalExchanger_Node.setLocalTranslation(getPosition());
        Quaternion quat = new Quaternion();
        quat.fromAngles(getRotation().getX(), getRotation().getY(), getRotation().getZ());
        PhysicalExchanger_Node.setLocalRotation(quat);
        this.auv_node = auv_node;
        this.auv_node.attachChild(PhysicalExchanger_Node);
    }

    private void setDebugVisible(boolean visible) {
        /*if(!visible){
         DebugDistance.setCullHint(CullHint.Always);
         }else{
         DebugDistance.setCullHint(CullHint.Never);
         }*/
        //forgot future?
        if (!visible) {
            DebugDistance.removeFromParent();
        } else {
            PhysicalExchanger_Node.attachChild(DebugDistance);
        }
    }

    private void updateState(String target, String hashmapname) {
        if (target.equals("debug") && hashmapname.equals("")) {
            setDebugVisible(getDebug());
        } else if (target.equals("debug_color") && hashmapname.equals("")) {
            debugDistanceMat.setColor("Color", getDebugColor());
        } else if (target.equals("propagation_distance") && hashmapname.equals("")) {
            debugDistanceSphere.updateGeometry(16, 16, getPropagationDistance());
        }
    }

    /**
     *
     */
    @Override
    public void reset() {

    }

    /**
     * some eye candy, you can see the communication net
     *
     * @param uws
     */
    public void updateComNet(HashMap<String, UnderwaterModem> uws) {
        Future<Void> fut2 = simState.getMARS().enqueue(new Callable<Void>() {
            public Void call() throws Exception {
                comNet.detachAllChildren();
                return null;
            }
        });
        final Vector3f modPos = this.getWorldPosition();
        for (String elem : uws.keySet()) {
            final UnderwaterModem uw = uws.get(elem);
            if (uw != this) {//ignore myself
                Vector3f distance = modPos.subtract(uw.getWorldPosition());
                final float proDist = this.getPropagationDistance();
                final float dis = Math.abs(distance.length());
                if (dis <= proDist) {//ignore uws far away
                    final Vector3f newVec = new Vector3f();
                    comNet.worldToLocal(uw.getWorldPosition(), newVec);
                    Future<Void> fut = simState.getMARS().enqueue(new Callable<Void>() {
                        public Void call() throws Exception {
                            Geometry x_axis = new Geometry("x_axis!", new Line(Vector3f.ZERO, newVec.mult(0.5f)));
                            Material x_axis_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                            ColorRGBA color = getDebugColor();
                            color.a = 1f - (dis / proDist);
                            x_axis_mat.setColor("Color", color);
                            x_axis_mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
                            x_axis.setQueueBucket(Bucket.Transparent);
                            x_axis.setMaterial(x_axis_mat);
                            x_axis.setLocalTranslation(new Vector3f(0f, 0f, 0f));
                            x_axis.updateGeometricState();
                            comNet.attachChild(x_axis);
                            return null;
                        }
                    });
                }
            }
        }
    }

    @Override
    public SimState getSimState() {
        return simState;
    }

    /**
     *
     * @param msg
     */
    @Override
    public void sendToCommDevice(String msg) {
        notifyAdvertisementAUVObject(new CommunicationDeviceEvent(this, msg, System.currentTimeMillis(), CommunicationDeviceEventType.OUT));
    }
    
    @Override
    public void sendIntoNetwork(String msg){
        notifyAdvertisementAUVObject(new CommunicationDeviceEvent(this, msg, System.currentTimeMillis(), CommunicationDeviceEventType.IN));                  
        com_manager.putMsg(getAuv().getName(), msg, CommunicationType.UNDERWATERSOUND);
    }
}

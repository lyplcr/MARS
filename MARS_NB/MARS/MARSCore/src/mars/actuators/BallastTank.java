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
package mars.actuators;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.rits.cloning.Cloner;
import java.util.HashMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import mars.KeyConfig;
import mars.Keys;
import mars.PhysicalExchange.PhysicalExchanger;
import mars.states.SimState;
import mars.xml.HashMapAdapter;

/**
 * A ballast tank that can be filled with water. This results than in a change of
 * the upper/lower force. The AUV sinks appropriatly.
 *
 * @author Tosik
 */
@XmlAccessorType(XmlAccessType.NONE)
public class BallastTank extends Actuator implements Keys {

    //motor
    private Geometry BallastStart;
    /**
     *
     */
    protected float volumePerSecond = 0.1f;
    /**
     *
     */
    protected float maxVolume = 1.0f;
    private float desiredVolume = 0f;
    private float currentVolume = 0f;
    private float time = 0f;

    private Node Rotation_Node = new Node();

    //JAXB KEYS
    @XmlJavaTypeAdapter(HashMapAdapter.class)
    @XmlElement(name = "Actions")
    private HashMap<String, String> action_mapping = new HashMap<String, String>();

    /**
     *
     */
    public BallastTank() {
        super();
    }

    /**
     *
     * @param simstate
     * @param MassCenterGeom
     */
    public BallastTank(SimState simstate, Geometry MassCenterGeom) {
        super(simstate, MassCenterGeom);
    }

    /**
     *
     * @param simstate
     */
    public BallastTank(SimState simstate) {
        super(simstate);
    }

    /**
     *
     * @param ballastTank
     */
    public BallastTank(BallastTank ballastTank) {
        super(ballastTank);
        HashMap<String, String> actionsOriginal = ballastTank.getAllActions();
        Cloner cloner = new Cloner();
        action_mapping = cloner.deepClone(actionsOriginal);
    }

    /**
     *
     * @return
     */
    @Override
    public PhysicalExchanger copy() {
        BallastTank actuator = new BallastTank(this);
        actuator.initAfterJAXB();
        return actuator;
    }

    /**
     *
     * @param pe
     */
    @Override
    public void copyValuesFromPhysicalExchanger(PhysicalExchanger pe) {
        super.copyValuesFromPhysicalExchanger(pe);
        if (pe instanceof BallastTank) {
            HashMap<String, String> actionsOriginal = ((BallastTank) pe).getAllActions();
            Cloner cloner = new Cloner();
            action_mapping = cloner.deepClone(actionsOriginal);
        }
    }

    /**
     *
     * @return
     */
    public float getCurrentVolume() {
        return currentVolume;
    }

    /**
     *
     * @return
     */
    public float getDesiredVolume() {
        return desiredVolume;
    }

    /**
     *
     * @param desiredVolume
     */
    public void setDesiredVolume(float desiredVolume) {
        if (getMaxVolume() < desiredVolume) {
            this.desiredVolume = getMaxVolume();
        } else if (desiredVolume <= 0f) {
            this.desiredVolume = 0f;
        } else {
            this.desiredVolume = desiredVolume;
        }
    }

    /**
     *
     * @param percent
     */
    public void setDesiredVolumePrecent(float percent) {
        setDesiredVolume((percent) * getMaxVolume());
    }

    /**
     *
     * @return
     */
    public Float getMaxVolume() {
        return (Float) variables.get("MaxVolume");
    }

    /**
     *
     * @param MaxVolume
     */
    public void setMaxVolume(Float MaxVolume) {
        variables.put("MaxVolume", MaxVolume);
    }

    /**
     *
     * @return
     */
    public Float getVolumePerSecond() {
        return (Float) variables.get("VolumePerSecond");
    }

    /**
     *
     * @param VolumePerSecond
     */
    public void setVolumePerSecond(Float VolumePerSecond) {
        variables.put("VolumePerSecond", VolumePerSecond);
    }

    /**
     * DON'T CALL THIS METHOD! In this method all the initialiasing for the
     * motor will be done and it will be attached to the physicsNode.
     */
    @Override
    public void init(Node auv_node) {
        super.init(auv_node);
        Sphere sphere7 = new Sphere(8, 8, 0.025f);
        BallastStart = new Geometry("BallastStart", sphere7);
        Material mark_mat7 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mark_mat7.setColor("Color", ColorRGBA.LightGray);
        BallastStart.setMaterial(mark_mat7);
        BallastStart.updateGeometricState();
        Rotation_Node.attachChild(BallastStart);

        PhysicalExchanger_Node.setLocalTranslation(getPosition());
        Quaternion quat = new Quaternion();
        quat.fromAngles(getRotation().getX(), getRotation().getY(), getRotation().getZ());
        PhysicalExchanger_Node.setLocalRotation(quat);
        PhysicalExchanger_Node.attachChild(Rotation_Node);
        auv_node.attachChild(PhysicalExchanger_Node);
    }

    /*
     * See updateForces(float tpf)
     */
    public void updateForces() {
    }

    /**
     *
     * @param tpf
     */
    @Override
    public void update(float tpf) {
        //check if ballasttank is under or over water, because we get different forces depending on the density of the fluid.
        if (getDesiredVolume() != getCurrentVolume()) {
            float diff = getDesiredVolume() - getCurrentVolume();
            float VolumePerTPF = getVolumePerSecond() * tpf;
            if (VolumePerTPF >= Math.abs(diff)) {//not enough space, set to max/min

                currentVolume = getDesiredVolume();
            } else {//enough space, add/sub normal
                currentVolume = currentVolume + (Math.signum(diff) * VolumePerTPF);
            }
        }
        float buoyancy_force = getPhysicalEnvironment().getFluid_density() * getPhysicalEnvironment().getGravitational_acceleration() * getCurrentVolume();
        physics_control.applyImpulse(Vector3f.UNIT_Y.negate().mult(buoyancy_force / ((float) mars_settings.getPhysicsFramerate())), this.getMassCenterGeom().getWorldTranslation().subtract(BallastStart.getWorldTranslation()));
    }

    public void reset() {
        currentVolume = 0f;
        setDesiredVolume(0f);
    }

    /**
     *
     * @return
     */
    @Override
    public HashMap<String, String> getAllActions() {
        return action_mapping;
    }

    /**
     *
     * @param inputManager
     * @param keyconfig
     */
    @Override
    public void addKeys(InputManager inputManager, KeyConfig keyconfig) {
        for (String elem : action_mapping.keySet()) {
            String action = action_mapping.get(elem);
            final String mapping = elem;
            final BallastTank self = this;
            if (action.equals("setDesiredVolumePrecent")) {
                inputManager.addMapping(mapping, new KeyTrigger(keyconfig.getKeyNumberForMapping(mapping)));
                ActionListener actionListener = new ActionListener() {
                    public void onAction(String name, boolean keyPressed, float tpf) {
                        if (name.equals(mapping) && !keyPressed && self.getAuv().getAuv_param().getManualControl()) {
                            self.setDesiredVolumePrecent(1.0f);
                        }
                    }
                };
                inputManager.addListener(actionListener, elem);
            }
        }
    }
    
    @Override
    public void publishData() {
        super.publishData();
    }
}

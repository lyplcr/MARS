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
package mars.simobjects;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.ConeCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import mars.object.CollisionType;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.rits.cloning.Cloner;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.event.EventListenerList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import mars.Helper.Helper;
import mars.MARS_Main;
import mars.MARS_Settings;
import mars.control.GuiControl;
import mars.events.MARSObjectEvent;
import mars.events.MARSObjectListener;
import mars.misc.PickHint;
import mars.misc.PropertyChangeListenerSupport;
import mars.object.MARSObject;
import mars.xml.HashMapAdapter;

/**
 * A basic simauv object that shall be loaded. For example a pipeline or another
 * custom made model.
 *
 * @author Thomas Tosik
 */
@XmlRootElement(name = "SimObject")
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({OilBurst.class})
public class SimObject implements MARSObject,PropertyChangeListenerSupport{

    /**
     *
     */
    @XmlJavaTypeAdapter(HashMapAdapter.class)
    @XmlElement(name = "")
    protected HashMap<String, Object> simob_variables;
    private HashMap<String, Object> collision;
    
    private EventListenerList eventlisteners = new EventListenerList();

    //selection stuff aka highlightening
    private boolean selected = false;
    AmbientLight ambient_light = new AmbientLight();
    private Spatial ghost_simob_spatial;
    /**
     *
     */
    protected Node simObNode = new Node("simObNode");
    /**
     *
     */
    protected Node debugNode = new Node("debugNode");
    /**
     *
     */
    protected Node renderNode = new Node("renderNode");

    private MARS_Main simauv;
    /**
     *
     */
    protected AssetManager assetManager;
    private Spatial spatial;
    private Material spatialMaterial;
    private RigidBodyControl physics_control;
    private MARS_Settings mars_settings;
    private Spatial debugShape;
    
    private List<PropertyChangeListener> listeners = Collections.synchronizedList(new LinkedList<PropertyChangeListener>());

    /**
     *
     */
    public SimObject() {

    }

    /**
     *
     * @param simob
     */
    public SimObject(SimObject simob) {
        HashMap<String, Object> variablesOriginal = simob.getAllVariables();
        Cloner cloner = new Cloner();
        simob_variables = cloner.deepClone(variablesOriginal);
    }

    @Override
    public void cleanup() {
    }

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
    
    /**
     *
     */
    @Override
    public void removeAllPropertyChangeListeners() {
        listeners.clear();
    }

    private void fire(String propertyName, Object old, Object nue) {
        //Passing 0 below on purpose, so you only synchronize for one atomic call:
        PropertyChangeListener[] pcls = listeners.toArray(new PropertyChangeListener[0]);
        for (PropertyChangeListener pcl : pcls) {
            pcl.propertyChange(new PropertyChangeEvent(this, propertyName, old, nue));
        }
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<updateVariable(propertyName);
    }

    /**
     *
     * @return
     */
    public SimObject copy() {
        SimObject simob = new SimObject(this);
        simob.initAfterJAXB();
        return simob;
    }

    /**
     *
     * @param target
     * @param hashmapname
     */
    public void updateState(String target, String hashmapname) {
        if (target.equals("position") && hashmapname.equals("")) {
            if (physics_control != null) {
                physics_control.setPhysicsLocation(getPosition());
            }
        } else if (target.equals("rotation") && hashmapname.equals("")) {
            if (physics_control != null) {
                Matrix3f m_rot = new Matrix3f();
                Quaternion q_rot = new Quaternion();
                q_rot.fromAngles(getRotation().x, getRotation().y, getRotation().z);
                m_rot.set(q_rot);
                physics_control.setPhysicsRotation(m_rot);
            }
        } else if (target.equals("scale") && hashmapname.equals("")) {
            getSpatial().setLocalScale(getScale());
        } else if (target.equals("debug_collision") && hashmapname.equals("Collision")) {
            setCollisionVisible(getCollisionDebug());
        } else if (target.equals("color") && hashmapname.equals("")) {

            spatialMaterial.setColor("Color", getColor());
        } else if (target.equals("light") && hashmapname.equals("")) {
            if (!getLight()) {
                spatialMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                spatialMaterial.setColor("Color", getColor());
                spatial.setMaterial(spatialMaterial);
            } else {

            }
        }
    }

    /**
     *
     * @return
     */
    public HashMap<String, Object> getAllVariables() {
        return simob_variables;
    }

    private void loadModel() {
        //assetManager.registerLocator("./Assets/Models/", FileLocator.class);
        spatialMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        spatial = assetManager.loadModel(getFilepath());

        spatial.setLocalScale(getScale());
        spatial.setUserData("simob_name", getName());
        spatial.setLocalTranslation(getPosition());
        spatial.rotate(getRotation().x, getRotation().y, getRotation().z);
        if (!getLight()) {
            spatialMaterial.setColor("Color", getColor());
            spatial.setMaterial(spatialMaterial);
        }
        spatial.setShadowMode(ShadowMode.CastAndReceive);
        spatial.updateGeometricState();

        spatial.updateModelBound();

        spatial.setName(getName());
        renderNode.attachChild(spatial);
    }

    /*
     * When we have the spatial for the auv we create the physics node out of it. Needed for all the physics and collisions.
     */
    private void createPhysicsNode() {
        CollisionShape collisionShape;
        if (getCollisionType() == CollisionType.BOXCOLLISIONSHAPE) {
            collisionShape = new BoxCollisionShape(getCollisionDimensions());
        } else if (getCollisionType() == CollisionType.SPHERECOLLISIONSHAPE) {
            collisionShape = new SphereCollisionShape(getCollisionDimensions().x);
        } else if (getCollisionType() == CollisionType.CONECOLLISIONSHAPE) {
            collisionShape = new ConeCollisionShape(getCollisionDimensions().x, getCollisionDimensions().y);
        } else if (getCollisionType() == CollisionType.CYLINDERCOLLISIONSHAPE) {
            //collisionShape = new CylinderCollisionShape(auv_param.getCollisionDimensions().x,auv_param.getCollisionDimensions().y);
            collisionShape = new BoxCollisionShape(getCollisionDimensions());
        } else if (getCollisionType() == CollisionType.MESHACCURATE) {
            collisionShape = CollisionShapeFactory.createMeshShape(spatial);
        } else {
            collisionShape = new BoxCollisionShape(getCollisionDimensions());
        }
        physics_control = new RigidBodyControl(collisionShape, 0f);
        physics_control.setCollisionGroup(PhysicsCollisionObject.COLLISION_GROUP_03);
        physics_control.setCollideWithGroups(PhysicsCollisionObject.COLLISION_GROUP_01);
        physics_control.addCollideWithGroup(PhysicsCollisionObject.COLLISION_GROUP_02);
        physics_control.addCollideWithGroup(PhysicsCollisionObject.COLLISION_GROUP_03);
        physics_control.addCollideWithGroup(PhysicsCollisionObject.COLLISION_GROUP_04);

        //debug
        Material debug_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        debug_mat.setColor("Color", ColorRGBA.Red);
        /*debugShape = physics_control.createDebugShape(assetManager);
         debugNode.attachChild(debugShape);
         if(getCollisionDebug()){
         debugShape.setCullHint(CullHint.Inherit);
         }else{
         debugShape.setCullHint(CullHint.Always);
         }*/

        spatial.addControl(physics_control);
        spatial.updateGeometricState();
    }

    /**
     *
     */
    @SuppressWarnings("unchecked")
    public void initAfterJAXB() {
        collision = (HashMap<String, Object>) simob_variables.get("Collision");
    }

    /**
     *
     */
    public void init() {
        loadModel();
        createPhysicsNode();
        createGhostSpatial();
        
        //a control for controling the auv from the gui
        GuiControl guicontrol = new GuiControl(this);
        simObNode.addControl(guicontrol);
        
        Helper.setNodePickUserData(debugNode, PickHint.NoPick);
        simObNode.attachChild(renderNode);
        simObNode.attachChild(debugNode);
        spatial.updateGeometricState();
        simObNode.updateGeometricState();
    }

    /**
     *
     * @return
     */
    public MARS_Settings getMARSSettings() {
        return mars_settings;
    }

    /**
     *
     * @param mars_settings
     */
    public void setMARSSettings(MARS_Settings mars_settings) {
        this.mars_settings = mars_settings;
    }

    /**
     *
     * @param selected
     */
    @Override
    public void setSelected(boolean selected) {
        if (selected && this.selected == false) {
            ambient_light.setColor(mars_settings.getGuiSelectionColor());
            simObNode.addLight(ambient_light);
        } else if (selected == false) {
            simObNode.removeLight(ambient_light);
        }
        this.selected = selected;
    }

    /**
     *
     * @return
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     *
     * @return
     */
    public Node getSimObNode() {
        return simObNode;
    }

    /**
     *
     * @return
     */
    public Node getRenderNode() {
        return renderNode;
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
     * @return
     */
    public String getIcon() {
        return (String) simob_variables.get("icon");
    }

    /**
     *
     * @param icon
     */
    public void setIcon(String icon) {
        simob_variables.put("icon", icon);
    }

    /**
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getDndIcon() {
        return (String) simob_variables.get("dndIcon");
    }

    /**
     *
     * @param dndIcon
     */
    public void setDndIcon(String dndIcon) {
        simob_variables.put("dndIcon", dndIcon);
    }

    /**
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public Vector3f getCollisionDimensions() {
        return (Vector3f) ((HashMap<String, Object>) simob_variables.get("Collision")).get("dimensions");
    }

    /**
     *
     * @param dimensions
     */
    @SuppressWarnings("unchecked")
    public void setCollisionDimensions(Vector3f dimensions) {
        ((HashMap<String, Object>) simob_variables.get("Collision")).put("dimensions", dimensions);
    }

    /**
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public Vector3f getCollisionPosition() {
        return (Vector3f) ((HashMap<String, Object>) simob_variables.get("Collision")).get("position");
    }

    /**
     *
     * @param position
     */
    @SuppressWarnings("unchecked")
    public void setCollisionPosition(Vector3f position) {
        ((HashMap<String, Object>) simob_variables.get("Collision")).put("position", position);
    }

    /**
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public Integer getCollisionType() {
        return (Integer) ((HashMap<String, Object>) simob_variables.get("Collision")).get("type");
    }

    /**
     *
     * @param type
     */
    @SuppressWarnings("unchecked")
    public void setCollisionType(Integer type) {
        ((HashMap<String, Object>) simob_variables.get("Collision")).put("type", type);
    }

    /**
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public Boolean getCollisionCollidable() {
        return (Boolean) ((HashMap<String, Object>) simob_variables.get("Collision")).get("collidable");
    }

    /**
     *
     * @param collidable
     */
    @SuppressWarnings("unchecked")
    public void setCollisionCollidable(Boolean collidable) {
        ((HashMap<String, Object>) simob_variables.get("Collision")).put("collidable", collidable);
    }

    /**
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public Boolean getRayDetectable() {
        return (Boolean) simob_variables.get("rayDetectable");
    }

    /**
     *
     * @param rayDetectable
     */
    public void setRayDetectable(Boolean rayDetectable) {
        simob_variables.put("rayDetectable", rayDetectable);
    }

    /**
     *
     * @return
     */
    public Boolean getPinger() {
        return (Boolean) simob_variables.get("pinger");
    }

    /**
     *
     * @param pinger
     */
    public void setPinger(Boolean pinger) {
        simob_variables.put("pinger", pinger);
    }

    /**
     *
     * @return
     */
    public Boolean getLight() {
        return (Boolean) simob_variables.get("light");
    }

    /**
     *
     * @param light
     */
    public void setLight(Boolean light) {
        simob_variables.put("light", light);
    }

    /**
     *
     * @param color
     */
    public void setColor(ColorRGBA color) {
        simob_variables.put("color", color);
    }

    /**
     *
     * @return
     */
    public ColorRGBA getColor() {
        return (ColorRGBA) simob_variables.get("color");
    }

    /**
     *
     * @param simobject_spatial
     */
    private void setSpatial(Spatial spatial) {
        this.spatial = spatial;
    }

    /**
     *
     * @return
     */
    public Spatial getSpatial() {
        return spatial;
    }

    /**
     *
     * @return
     */
    public Boolean isEnabled() {
        return (Boolean) simob_variables.get("enabled");
    }
    
    /**
     *
     * @return
     */
    public Boolean getEnabled() {
        return (Boolean) simob_variables.get("enabled");
    }

    /**
     *
     * @param enabled
     */
    public void setEnabled(Boolean enabled) {
        simob_variables.put("enabled", enabled);
    }

    /**
     *
     * @return
     */
    public String getFilepath() {
        return (String) simob_variables.get("filepath");
    }

    /**
     *
     * @param filepath
     */
    public void setFilepath(String filepath) {
        simob_variables.put("filepath", filepath);
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return (String) simob_variables.get("name");
    }

    /**
     *
     * @param name
     */
    @Override
    public void setName(String name) {
        simob_variables.put("name", name);
    }

    /**
     *
     * @return
     */
    public Vector3f getPosition() {
        return (Vector3f) simob_variables.get("position");
    }

    /**
     *
     * @param position
     */
    public void setPosition(Vector3f position) {
        simob_variables.put("position", position);
    }

    /**
     *
     * @return
     */
    public Vector3f getRotation() {
        return (Vector3f) simob_variables.get("rotation");
    }

    /**
     *
     * @param rotation
     */
    public void setRotation(Vector3f rotation) {
        simob_variables.put("rotation", rotation);
    }

    /**
     *
     * @return
     */
    public Vector3f getScale() {
        return (Vector3f) simob_variables.get("scale");
    }

    /**
     *
     * @param scale
     */
    public void setScale(Vector3f scale) {
        simob_variables.put("scale", scale);
    }

    /**
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public Boolean getCollisionDebug() {
        return (Boolean) ((HashMap<String, Object>) simob_variables.get("Collision")).get("debug");
    }

    /**
     *
     * @param debug_collision
     */
    @SuppressWarnings("unchecked")
    public void setCollisionDebug(Boolean debug) {
        ((HashMap<String, Object>) simob_variables.get("Collision")).put("debug", debug);
    }

    /**
     *
     * @param value
     * @param hashmapname
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object getValue(String value, String hashmapname) {
        if (hashmapname.equals("") || hashmapname == null) {
            return simob_variables.get(value);
        } else {
            HashMap<String, Object> hashmap = (HashMap<String, Object>) simob_variables.get(hashmapname);
            return hashmap.get(value);
        }
    }

    /**
     *
     * @param value
     * @param object
     * @param hashmapname
     */
    @SuppressWarnings("unchecked")
    public void setValue(String value, Object object, String hashmapname) {
        if (hashmapname.equals("") || hashmapname == null) {
            simob_variables.put(value, object);
        } else {
            HashMap<String, Object> hashmap = (HashMap<String, Object>) simob_variables.get(hashmapname);
            hashmap.put(value, object);
        }
    }

    /**
     *
     * @return
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     *
     * @param assetManager
     */
    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    /**
     *
     * @return
     */
    public MARS_Main getSimauv() {
        return simauv;
    }

    /**
     *
     * @param simauv
     */
    public void setSimauv(MARS_Main simauv) {
        this.simauv = simauv;
    }

    private void createGhostSpatial() {
        //assetManager.registerLocator("Assets/Models", FileLocator.class);
        ghost_simob_spatial = assetManager.loadModel(getFilepath());
        ghost_simob_spatial.setLocalScale(getScale());
        ghost_simob_spatial.setLocalTranslation(getPosition());
        ghost_simob_spatial.rotate(getRotation().x, getRotation().y, getRotation().z);
        ghost_simob_spatial.updateGeometricState();
        ghost_simob_spatial.updateModelBound();
        ghost_simob_spatial.setName(getName() + "_ghost");
        ghost_simob_spatial.setUserData("simob_name", getName());
        ghost_simob_spatial.setCullHint(CullHint.Always);
        debugNode.attachChild(ghost_simob_spatial);
    }

    /**
     *
     * @return
     */
    public Spatial getGhostSpatial() {
        return ghost_simob_spatial;
    }

    /**
     *
     * @param hide
     */
    public void hideGhostSpatial(boolean hide) {
        if (hide) {
            ghost_simob_spatial.setCullHint(CullHint.Always);
        } else {
            ghost_simob_spatial.setCullHint(CullHint.Never);
        }
    }

    /**
     *
     * @param visible
     */
    public void setCollisionVisible(boolean visible) {
        if (visible) {
            debugShape.setCullHint(CullHint.Inherit);
        } else {
            debugShape.setCullHint(CullHint.Always);
        }
    }

    /**
     *
     * @param visible
     */
    public void setWireframeVisible(boolean visible) {
        if (visible) {
            Node nodes = (Node) spatial;
            List<Spatial> children = nodes.getChildren();
            for (Iterator<Spatial> it = children.iterator(); it.hasNext();) {
                Spatial spatial2 = it.next();
                if (spatial2 instanceof Geometry) {
                    Geometry geom = (Geometry) spatial2;
                    geom.getMaterial().getAdditionalRenderState().setWireframe(true);
                }
            }
        } else {
            Node nodes = (Node) spatial;
            List<Spatial> children = nodes.getChildren();
            for (Iterator<Spatial> it = children.iterator(); it.hasNext();) {
                Spatial spatial2 = it.next();
                if (spatial2 instanceof Geometry) {
                    Geometry geom = (Geometry) spatial2;
                    geom.getMaterial().getAdditionalRenderState().setWireframe(false);
                }
            }
        }
    }

    @Override
    public String toString() {
        return getName();
    }
    
        /**
     *
     * @param listener
     */
    @Override
    public void addMARSObjectListener(MARSObjectListener listener) {
        eventlisteners.add(MARSObjectListener.class, listener);
    }

    /**
     *
     * @param listener
     */
    @Override
    public void removeMARSObjectListener(MARSObjectListener listener) {
        eventlisteners.remove(MARSObjectListener.class, listener);
    }

    /**
     *
     */
    @Override
    public void removeAllMARSObjectListener() {
        //eventlisteners.remove(MARSObjectListener.class, null);
    }

    /**
     *
     * @param event
     */
    @Override
    public void notifyAdvertisementMARSObject(MARSObjectEvent event) {
        for (MARSObjectListener l : eventlisteners.getListeners(MARSObjectListener.class)) {
            l.onNewData(event);
        }
    }

    /**
     *
     * @param event
     */
    protected synchronized void notifySafeAdvertisementMARSObject(MARSObjectEvent event) {
        notifyAdvertisementMARSObject(event);
    }
}

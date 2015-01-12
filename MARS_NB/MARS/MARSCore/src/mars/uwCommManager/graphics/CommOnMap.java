/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.uwCommManager.graphics;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Line;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import mars.MARS_Main;
import mars.MARS_Settings;
import mars.auv.AUV;
import mars.auv.AUV_Manager;
import mars.sensors.UnderwaterModem;
import mars.states.MapState;
import mars.uwCommManager.helpers.DistanceTrigger;

/**
 * This class is used for all visual effects generated by communications on the minimap.
 * @version 0.2
 * @author Jasper Schwinghammer
 */
public class CommOnMap {
    
    
    private MapState mapState = null;
    private AUV_Manager auvManager = null;
    private MARS_Main app;
    private MARS_Settings marsSettings = null;
    private AssetManager assetManager = null;
    private boolean active;
    private boolean borders;
    
    private Map<String,List<DistanceTrigger>> distances;
    private List<Spatial> inactivePaths;
    
    /**
     * @since 0.1
     * @param active if this class starts active or not
     */
    public CommOnMap(boolean active, boolean borders) {
        this.active = active;
        this.borders = borders;
        distances = new HashMap();
        inactivePaths = new LinkedList();
    }
    
    /**
     * Init all stuff that can fail
     * @since 0.1
     * @param mapState
     * @param auvManager
     * @return if init failed or not
     */
    public boolean init(MapState mapState, AUV_Manager auvManager, MARS_Settings marsSettings, AssetManager assetManager, MARS_Main app) {
        if(mapState == null || auvManager == null || marsSettings == null) return false;
        this.mapState = mapState;
        this.auvManager = auvManager;
        this.marsSettings = marsSettings;
        this.assetManager = assetManager;
        this.app = app;
        return true;
        
    }
    
    /**
     * jMonkeyEngine update method
     * @since 0.1
     * @param tpf 
     */
    public  void update(float tpf) {
        Map<String, Node> auvNodes = mapState.getAUVNodes();
        
        
        for (String elem : auvNodes.keySet()) {
            Node node = (Node) auvNodes.get(elem);
            AUV auv = auvManager.getAUV(elem);
            if (auv != null && auv.getAuv_param().isEnabled()) {

                Vector3f ter_pos = marsSettings.getTerrainPosition();
                //float tile_length = mars_settings.getTileLength();
                float tile_length = marsSettings.getTerrainScale().getX();
                int terx_px = mapState.getTexMl().getImage().getWidth();
                int tery_px = mapState.getTexMl().getImage().getHeight();

                //update propagation distance
                ArrayList uws = auv.getSensorsOfClass(UnderwaterModem.class.getName());
                Iterator it = uws.iterator();
                while (it.hasNext()) {
                    UnderwaterModem uw = (UnderwaterModem) it.next();
                    Geometry uwgeom = (Geometry) node.getChild(auv.getName() + "-" + uw.getName() + "-geom");
                    Geometry uwgeom_border = (Geometry) node.getChild(auv.getName() + "-" + uw.getName() + "-geom-border");
                    if (active) {
                        if(!borders) {
                        uwgeom.setCullHint(Spatial.CullHint.Never);
                        uwgeom_border.setCullHint(Spatial.CullHint.Always);
                        Cylinder cyl = (Cylinder) uwgeom.getMesh();
                        cyl.updateGeometry(16, 16, uw.getPropagationDistance() * (2f / (terx_px * tile_length)), uw.getPropagationDistance() * (2f / (terx_px * tile_length)), 0.1f, true, false);
                        } else {
                            uwgeom.setCullHint(Spatial.CullHint.Always);
                            uwgeom_border.setCullHint(Spatial.CullHint.Never);
                            Cylinder cyl = (Cylinder) uwgeom_border.getMesh();
                            cyl.updateGeometry(16, 16, uw.getPropagationDistance() * (2f / (terx_px * tile_length))+0.01f, uw.getPropagationDistance() * (2f / (terx_px * tile_length)), 0.1f, false, true);
                        }
                    } else {
                        uwgeom.setCullHint(Spatial.CullHint.Always);
                        uwgeom_border.setCullHint(Spatial.CullHint.Always);
                    }
                }
                
                
                //DRAW CONNECTIONS
                if(distances.containsKey(auv.getName())) {
                    String distanceNodeName = auv.getName()+"distances";
                    Node distanceNode = (Node) node.getChild(distanceNodeName);
                    if(distanceNode == null) {
                        attachDistanceNode(node,distanceNodeName);
                    }
                    else {
                        inactivePaths = new LinkedList(distanceNode.getChildren());
                        List<DistanceTrigger> targets = distances.get(auv.getName());
                        for(DistanceTrigger i : targets) {
                            String connectionName = auv.getName()+"->"+i.getAUVName();
                            Geometry uwgeom = (Geometry) distanceNode.getChild(connectionName);
                            if(uwgeom == null) {
                                attachLine(connectionName, distanceNode, new Vector3f(0,0,0), auvNodes.get(i.getAUVName()).getWorldTranslation().subtract(node.getWorldTranslation()));
                            } else {
                                inactivePaths.remove(uwgeom);
                                Line line = (Line) uwgeom.getMesh();
                                line.updatePoints(new Vector3f(0,0,0), auvNodes.get(i.getAUVName()).getWorldTranslation().subtract(node.getWorldTranslation()));
                                uwgeom.setCullHint(Spatial.CullHint.Inherit);
                            }
                        }
                        for(Spatial i : inactivePaths) {
                            i.setCullHint(Spatial.CullHint.Always);
                        }
                    }
                }
            }
        }
    }
    /**
     * TODO DOCUMENTATION
     * @param node
     * @param name 
     */
    private void attachDistanceNode(final Node node, final String name) {
        app.enqueue(new Callable<Object>(){
            @Override
            public Object call() {
                Node distanceNode = new Node(name);
                node.attachChild(distanceNode);
                return null;
            }  
        });
    }
    /**
     * TODO DOCUMENTATION
     * @param name
     * @param distanceNode
     * @param start
     * @param end 
     */
    private void attachLine(final String name, final Node distanceNode, final Vector3f start, final Vector3f end) {
        app.enqueue(new Callable<Object>(){
            @Override
            public Object call() {
                Line line = new Line(start, end);
                line.setLineWidth(0.1f);
                Geometry uwgeom = new Geometry(name,line);
                Material lineMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                lineMat.setColor("Color", ColorRGBA.Blue);
                uwgeom.setMaterial(lineMat);
                uwgeom.setCullHint(Spatial.CullHint.Inherit);
                distanceNode.attachChild(uwgeom);
                return null;
            }
        });
    }
    
    /**
     * Set if active
     * @since 0.1
     * @param active 
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    
    /**
     * Set if borders or plain is drawn
     * @since 0.1
     * @param borders if borders should be drawn
     */
    public void setBorders(boolean borders) {
        this.borders = borders;
    }
    
    /**
     * @since 0.1
     * @return if active
     */
    public boolean isActive() {
        return active;
    }
    
    /**
     * @since 0.2
     * @param distances 
     */
    public void setDistances(Map<String,List<DistanceTrigger>> distances) {
        this.distances = distances;
    }
    
    
}

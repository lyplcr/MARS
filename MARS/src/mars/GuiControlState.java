/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.debug.Arrow;
import mars.auv.AUV;
import mars.simobjects.SimObject;

/**
 * This class is used for storing in which state the gui controls are currently. 
 * For example: When you are pressing left_ctrl you get into a new state where
 * you can move the auv. But you have to deactivate ther commands that could be 
 * harming like the contextmenue on right mouse button.
 * @author Thomas Tosik
 */
public class GuiControlState {
    private boolean move_auv = false;
    private boolean rotate_auv = false;
    private boolean move_simob = false;
    private boolean rotate_simob = false;
    private boolean auv_context = false;
    private boolean free = true;
    private Vector3f intersection = Vector3f.ZERO;
    private Spatial ghost_object;
    private Quaternion rotation = new Quaternion();
    private int depth_iteration = 0;
    private float depth_factor = 0.25f;
    private Vector3f contact_point = Vector3f.ZERO;
    private Vector3f contact_direction = Vector3f.ZERO;
    private AUV latestSelectedAUV = null;
    private SimObject latestSelectedSimOb = null;
    private Arrow arrow;
    private Geometry rotateArrow;
    private AssetManager assetManager;
    private Node GUINode = new Node("GUI_Node");
    private Vector3f rotateArrowVectorStart = Vector3f.ZERO;
    private Vector3f rotateArrowVectorEnd = Vector3f.UNIT_X;
    
    /**
     * 
     * @param assetManager
     */
    public GuiControlState(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
    
    /**
     * 
     */
    public void init(){
        arrow = new Arrow(getRotateArrowVectorEnd());
        Vector3f ray_start = getRotateArrowVectorStart();
        rotateArrow = new Geometry("RotateArrow", arrow);
        Material mark_mat4 = new Material(this.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mark_mat4.setColor("Color", ColorRGBA.White);
        rotateArrow.setMaterial(mark_mat4);
        rotateArrow.setLocalTranslation(ray_start);
        rotateArrow.updateGeometricState();
        setRotateArrowVisible(false);
        GUINode.attachChild(rotateArrow);
    }
    
    /**
     * 
     */
    public void updateRotateArrow(){
        rotateArrow.setLocalTranslation(getRotateArrowVectorStart());
        arrow.setArrowExtent(getRotateArrowVectorEnd().subtract(getRotateArrowVectorStart()));
        rotateArrow.updateGeometricState();
    }
    
    /**
     * 
     * @param visible
     */
    public void setRotateArrowVisible(boolean visible){
        if(visible){
            rotateArrow.setCullHint(CullHint.Inherit);
        }else{
            rotateArrow.setCullHint(CullHint.Always);
        }
    }

    /**
     * 
     * @return
     */
    public Vector3f getRotateArrowVectorEnd() {
        return rotateArrowVectorEnd;
    }

    /**
     * 
     * @param rotateArrowVectorEnd
     */
    public void setRotateArrowVectorEnd(Vector3f rotateArrowVectorEnd) {
        this.rotateArrowVectorEnd = rotateArrowVectorEnd;
    }

    /**
     * 
     * @return
     */
    public Vector3f getRotateArrowVectorStart() {
        return rotateArrowVectorStart;
    }

    /**
     * 
     * @param rotateArrowVectorStart
     */
    public void setRotateArrowVectorStart(Vector3f rotateArrowVectorStart) {
        this.rotateArrowVectorStart = rotateArrowVectorStart;
    }

    /**
     * 
     * @return
     */
    public Node getGUINode() {
        return GUINode;
    }

    /**
     * 
     * @return
     */
    public AUV getLatestSelectedAUV() {
        return latestSelectedAUV;
    }

    /**
     * 
     * @param latestSelectedAUV
     */
    public void setLatestSelectedAUV(AUV latestSelectedAUV) {
        this.latestSelectedAUV = latestSelectedAUV;
    }
    
    /**
     * 
     * @return
     */
    public SimObject getLatestSelectedSimOb() {
        return latestSelectedSimOb;
    }

    /**
     * 
     * @param latestSelectedSimOb
     */
    public void setLatestSelectedSimOb(SimObject latestSelectedSimOb) {
        this.latestSelectedSimOb = latestSelectedSimOb;
    }

    /**
     * 
     * @return
     */
    public boolean isMove_auv() {
        return move_auv;
    }

    /**
     * 
     * @param move_auv
     */
    public void setMove_auv(boolean move_auv) {
        this.move_auv = move_auv;
        if(move_auv == true){
            setFree(false);  
            setRotate_auv(false);
        }else{
            setFree(true);
        }
    }

    /**
     * 
     * @return
     */
    public boolean isRotate_auv() {
        return rotate_auv;
    }

    /**
     * 
     * @param rotate_auv
     */
    public void setRotate_auv(boolean rotate_auv) {
        this.rotate_auv = rotate_auv;
        if(rotate_auv == true){
            setFree(false);   
            setMove_auv(false);
        }else{
            setFree(true);
        }
    }
    
    /**
     * 
     * @return
     */
    public boolean isMove_simob() {
        return move_simob;
    }

    /**
     * 
     * @param move_simob
     */
    public void setMove_simob(boolean move_simob) {
        this.move_simob = move_simob;
        if(move_simob == true){
            setFree(false);  
            setRotate_simob(false);
        }else{
            setFree(true);
        }
    }

    /**
     * 
     * @return
     */
    public boolean isRotate_simob() {
        return rotate_simob;
    }

    /**
     * 
     * @param rotate_simob
     */
    public void setRotate_simob(boolean rotate_simob) {
        this.rotate_simob = rotate_simob;
        if(rotate_simob == true){
            setFree(false);   
            setMove_simob(false);
        }else{
            setFree(true);
        }
    }

    /**
     * 
     * @return
     */
    public boolean isAuv_context() {
        return auv_context;
    }

    /**
     * 
     * @param auv_context
     */
    public void setAuv_context(boolean auv_context) {
        this.auv_context = auv_context;
    }

    /**
     * 
     * @return
     */
    public boolean isFree() {
        return free;
    }

    /**
     * 
     * @param free
     */
    public void setFree(boolean free) {
        this.free = free;
    }

    /**
     * 
     * @return
     */
    public Vector3f getIntersection() {
        return intersection;
    }

    /**
     * 
     * @param intersection
     */
    public void setIntersection(Vector3f intersection) {
        this.intersection = intersection;
    }

    /**
     * 
     * @return
     */
    public Spatial getGhostObject() {
        return ghost_object;
    }

    /**
     * 
     * @param ghost_object
     */
    public void setGhostObject(Spatial ghost_object) {
        this.ghost_object = ghost_object;
    }

    /**
     * 
     * @return
     */
    public Quaternion getRotation() {
        return rotation;
    }

    /**
     * 
     * @param rotation
     */
    public void setRotation(Quaternion rotation) {
        this.rotation = rotation;
    }

    /**
     * 
     * @return
     */
    public float getDepth_factor() {
        return depth_factor;
    }

    /**
     * 
     * @param depth_factor
     */
    public void setDepth_factor(float depth_factor) {
        this.depth_factor = depth_factor;
    }

    /**
     * 
     * @return
     */
    public int getDepth_iteration() {
        return depth_iteration;
    }

    /**
     * 
     * @param depth_iteration
     */
    public void setDepth_iteration(int depth_iteration) {
        this.depth_iteration = depth_iteration;
    }
    
    /**
     * 
     */
    public void incrementDepthIteration(){
        depth_iteration = depth_iteration + 1;
    }
    
    /**
     * 
     */
    public void decrementDepthIteration(){
        depth_iteration = depth_iteration - 1;
    }
    
    /**
     * 
     * @param contact_point
     */
    public void setAuvContactPoint(Vector3f contact_point){
        this.contact_point = contact_point;
    }
    
    /**
     * 
     * @return
     */
    public Vector3f getAuvContactPoint(){
        return contact_point;
    }
    
    /**
     * 
     * @param contact_direction
     */
    public void setAuvContactDirection(Vector3f contact_direction){
        this.contact_direction = contact_direction;
    }
    
    /**
     * 
     * @return
     */
    public Vector3f getAuvContactDirection(){
        return contact_direction;
    }
}

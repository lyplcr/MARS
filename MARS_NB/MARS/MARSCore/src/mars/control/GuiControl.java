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
package mars.control;

import com.jme3.app.state.AppStateManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import mars.Helper.Helper;
import mars.auv.AUV;
import mars.object.MARSObject;
import mars.simobjects.SimObject;
import mars.states.GuiState;

/**
 * This control is used to steer vehicles manually.
 * 
 * @author Thomas Tosik <tosik at iti.uni-luebeck.de>
 */
public class GuiControl extends AbstractControl{

    private MARSObject marsObj;
    private Vector3f contact_point = Vector3f.ZERO;
    private Vector3f contact_direction = Vector3f.ZERO;
    private boolean move = false;
    private boolean rotate = false;
    private int depth_iteration = 0;
    private float depth_factor = 0.25f;
    private Vector3f intersection = Vector3f.ZERO;
    private AppStateManager stm;
    private GuiState guiState;
    private Quaternion rotation = new Quaternion();
    
    public GuiControl(MARSObject marsObj) {
        super();
        this.marsObj = marsObj;
    }
    
    public GuiControl(MARSObject marsObj, AppStateManager stm) {
        this(marsObj);
        this.stm = stm;
        if (stm.getState(GuiState.class) != null) {
            guiState = stm.getState(GuiState.class);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    @Override
    protected void controlUpdate(float f) {
        
    }
    
    public void select(){
        marsObj.setSelected(true);
    }
    
    public void deselect(){
        marsObj.setSelected(false);
        setMove(false);
        //dont forget the ghost
        if(marsObj instanceof AUV){
            AUV auv = (AUV)marsObj;
            auv.hideGhostAUV(true);
        }else if(marsObj instanceof SimObject){
            SimObject simob = (SimObject)marsObj;
            simob.hideGhostSpatial(true);
        }
    }

    public MARSObject getMarsObj() {
        return marsObj;
    }
    
    /**
     *
     * @param contact_point
     */
    public void setContactPoint(Vector3f contact_point) {
        this.contact_point = contact_point;
    }

    /**
     *
     * @return
     */
    public Vector3f getContactPoint() {
        return contact_point;
    }

    /**
     *
     * @param contact_direction
     */
    public void setContactDirection(Vector3f contact_direction) {
        this.contact_direction = contact_direction;
    }

    /**
     *
     * @return
     */
    public Vector3f getContactDirection() {
        return contact_direction;
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
    public void incrementDepthIteration() {
        depth_iteration = depth_iteration + 1;
    }

    /**
     *
     */
    public void decrementDepthIteration() {
        depth_iteration = depth_iteration - 1;
    }
    
    /**
     *
     */
    public void resetDepthIteration() {
        depth_iteration = 0;
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
    
    public boolean getMove(){
        return move;
    }
    
    public void setMove(boolean move){
        this.move = move;
        if(marsObj instanceof AUV){
            AUV auv = (AUV)marsObj;
            if (auv.getMARS_Settings().getGuiMouseUpdateFollow()) {
                auv.hideGhostAUV(true);
            } else {
                auv.hideGhostAUV(false);
            }
        }else if(marsObj instanceof SimObject){
            SimObject simob = (SimObject)marsObj;
            simob.hideGhostSpatial(false);
        }
    }
    
    public boolean getRotate(){
        return rotate;
    }
    
    public void setRotate(boolean rotate){
        if (guiState == null && stm.getState(GuiState.class) != null) {
            guiState = stm.getState(GuiState.class);
        }
        this.rotate = rotate;
        if(marsObj instanceof AUV){
            AUV auv = (AUV)marsObj;
            guiState.setRotateArrowVisible(rotate);
            if (auv.getMARS_Settings().getGuiMouseUpdateFollow()) {
                auv.hideGhostAUV(true);
            } else {
                auv.hideGhostAUV(false);
            }
        }else if(marsObj instanceof SimObject){
            SimObject simob = (SimObject)marsObj;
            simob.hideGhostSpatial(false);
        }
    }
    
    public void move(Vector3f click3d, Vector3f dir){
        if (getMove()) {
            System.out.println("actual movwing");
            if(marsObj instanceof AUV){
                AUV auv = (AUV)marsObj;
                intersection = Helper.getIntersectionWithPlaneCorrect(auv.getAUVNode().getWorldTranslation(), Vector3f.UNIT_Y, click3d, dir);
                if (auv.getGhostAUV() != null) {
                    Vector3f diff = auv.getAUVSpatial().getWorldTranslation().subtract(auv.getAUVNode().getWorldTranslation());//dont forget to add the difference beetwen the intersection planes
                    auv.getGhostAUV().setLocalTranslation((auv.getAUVNode().worldToLocal(intersection, null).add(new Vector3f(0f, getDepth_factor() * getDepth_iteration(), 0f))).add(diff));
                    //auv.getGhostAUV().setLocalTranslation(auv.getAUVNode().worldToLocal(intersection, null).add(new Vector3f(0f, getDepth_factor() * getDepth_iteration(), 0f)));
                    auv.getGhostAUV().setLocalRotation(auv.getAUVSpatial().getLocalRotation());
                }
                if (auv.getMARS_Settings().getGuiMouseUpdateFollow()) {
                    auv.getPhysicsControl().setPhysicsLocation(intersection.add(new Vector3f(0f, getDepth_factor() * getDepth_iteration(), 0f)));//set end postion
                }
            }else if(marsObj instanceof SimObject){
                SimObject simob = (SimObject)marsObj;
                intersection = Helper.getIntersectionWithPlaneCorrect(simob.getSpatial().getWorldTranslation(), Vector3f.UNIT_Y, click3d, dir);
                if (simob.getGhostSpatial() != null) {
                    simob.getGhostSpatial().setLocalTranslation(simob.getSimObNode().worldToLocal(intersection, null));
                }
            }
        }
    }
    
    public void rotate(Vector3f click3d, Vector3f dir) {
        if (getRotate()) {
            System.out.println("actual movwing");
            if(marsObj instanceof AUV){
                AUV auv = (AUV)marsObj;
                intersection = Helper.getIntersectionWithPlaneCorrect(auv.getAUVNode().getWorldTranslation(), Vector3f.UNIT_Y, click3d, dir);
                Vector3f diff = intersection.subtract(auv.getAUVNode().getWorldTranslation());
                diff.y = 0f;
                diff.normalizeLocal();
                float angle = 0f;
                if (diff.z < 0f) {
                    angle = diff.angleBetween(Vector3f.UNIT_X);
                } else {
                    angle = diff.angleBetween(Vector3f.UNIT_X) * (-1);
                }

                if (auv.getGhostAUV() != null) {
                    Quaternion quat = new Quaternion();
                    Quaternion gquat = new Quaternion();

                    Quaternion wQuat = auv.getAUVSpatial().getWorldRotation();
                    float[] ff = wQuat.toAngles(null);
                    float newAng = ff[1] - angle;

                    quat.fromAngleNormalAxis(angle, Vector3f.UNIT_Y);
                    gquat.fromAngleNormalAxis(-newAng, Vector3f.UNIT_Y);
                    setRotation(quat);
                    auv.getGhostAUV().setLocalRotation(gquat);
                    guiState.setRotateArrowVectorStart(auv.getGhostAUV().getWorldTranslation());
                    guiState.setRotateArrowVectorEnd(intersection);
                    guiState.updateRotateArrow();
                }
            }
        }
    }
    
    public void drop(){
        if(marsObj instanceof AUV){
            AUV auv = (AUV)marsObj;
            auv.getPhysicsControl().setPhysicsLocation(intersection.add(new Vector3f(0f, getDepth_factor() * getDepth_iteration(), 0f)));//set end postion
            Spatial ghostObject = auv.getGhostAUV();
            if(ghostObject !=null){
               Vector3f diff = auv.getAUVSpatial().getWorldTranslation().subtract(auv.getAUVNode().getWorldTranslation());//dont forget to add the difference beetwen the intersection planes
               ghostObject.setLocalTranslation((auv.getAUVNode().worldToLocal(auv.getAUVNode().getWorldTranslation(), null)).add(diff));//reset ghost auv for rotation
            }
            auv.hideGhostAUV(true);
            setDepth_iteration(0);
        }else if(marsObj instanceof SimObject){
            SimObject simob = (SimObject)marsObj;
            simob.getPhysicsControl().setPhysicsLocation(intersection.add(new Vector3f(0f, getDepth_factor() * getDepth_iteration(), 0f)));//set end postion
            /*Spatial ghostObject = simob.getGhostSpatial();
            if(ghostObject !=null){
               ghostObject.setLocalTranslation(auv.getAUVNode().worldToLocal(auv.getAUVNode().getWorldTranslation(), null));//reset ghost auv for rotation
            }*/
            simob.hideGhostSpatial(true);
            setDepth_iteration(0);
        }
    }
    
    public void dropRotate(){
        if(marsObj instanceof AUV){
            AUV auv = (AUV)marsObj;
            auv.getPhysicsControl().setPhysicsRotation(getRotation());//set end roation
            auv.hideGhostAUV(true);
            guiState.setRotateArrowVisible(false);
        }else if(marsObj instanceof SimObject){
            SimObject simob = (SimObject)marsObj;
            simob.getPhysicsControl().setPhysicsRotation(getRotation());//set end roation
            simob.hideGhostSpatial(true);
            guiState.setRotateArrowVisible(false);
        }
    }
    
    public void poke() {
        if(marsObj instanceof AUV){
            AUV auv = (AUV)marsObj;
            Vector3f rel_pos = auv.getMassCenterGeom().getWorldTranslation().subtract(getContactPoint());
            Vector3f direction = getContactDirection().negate().normalize();
            Vector3f mult = direction.mult(auv.getAuv_param().getMass() * auv.getMARS_Settings().getPhysicsPoke() / ((float) auv.getMARS_Settings().getPhysicsFramerate()));
            auv.getPhysicsControl().applyImpulse(mult, rel_pos);
        }
    }
    
    public void reset(){
        if(marsObj instanceof AUV){
            AUV auv = (AUV)marsObj;
            auv.reset();
        }
    }
}

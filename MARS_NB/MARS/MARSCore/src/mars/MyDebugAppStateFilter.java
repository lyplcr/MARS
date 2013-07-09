/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.debug.BulletDebugAppState.DebugAppStateFilter;
import com.jme3.bullet.debug.BulletRigidBodyDebugControl;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import mars.auv.AUV_Manager;

/**
 *
 * @author Thomas Tosik <tosik at iti.uni-luebeck.de>
 */
public class MyDebugAppStateFilter implements DebugAppStateFilter{

    private MARS_Settings mars_settings;
    private AUV_Manager auvManager;
    private int i = 0;
    
    public MyDebugAppStateFilter(MARS_Settings mars_settings, AUV_Manager auvManager) {
        this.mars_settings = mars_settings;
        this.auvManager =  auvManager;
    }

    @Override
    public boolean displayObject(Object o) {
        if(mars_settings.isPhysicsDebug()){
            return true;
        }else{
            if(o instanceof MyCustomGhostControl){//we dont want to see them..ever
                return false;
            }else{
                if(o instanceof RigidBodyControl){
                    RigidBodyControl control = (RigidBodyControl)o;
                    Object userObject = control.getUserObject();
                    if(userObject != null && userObject instanceof Spatial){
                        Spatial spatial = (Spatial)userObject;
                        if(spatial.getUserData(DebugHint.DebugName) != null){
                            int debugHint = (Integer)spatial.getUserData(DebugHint.DebugName);
                            if(debugHint == DebugHint.Debug){
                                return true;
                            }else{
                                return true;
                            }
                        }else{
                            return false;
                        }
                    }else{
                        return false;
                    }
                }else{// other than RigidBodyControl dont interest us, maybe terrain
                    return false;
                }
            }
        }
    }
}

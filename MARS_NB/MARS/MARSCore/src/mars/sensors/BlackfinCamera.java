/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.sensors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import mars.states.SimState;

/**
 *
 * @author Thomas Tosik
 * @deprecated 
 */
@XmlAccessorType(XmlAccessType.NONE)
@Deprecated
public class BlackfinCamera extends VideoCamera{

    /**
     * 
     */
    public BlackfinCamera() {
        super();
    }
        
    /**
     * 
     * @param simstate
     */
    public BlackfinCamera(SimState simstate) {
        super(simstate);
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mars.auv.example;

import com.jme3.math.Vector3f;
import javax.xml.bind.annotation.XmlRootElement;
import mars.states.SimState;
import mars.auv.BasicAUV;

/**
 * The Hanse class for the Hanse AUV. Because i developed mainly for Hanse it is the same as the BasicAUV class.
 * @author Thomas Tosik
 */
@XmlRootElement
public class Hanse extends BasicAUV{

    /**
     * 
     * @param simstate 
     */
    public Hanse(SimState simstate){
        super(simstate);
    }

    /**
     *
     */
    public Hanse(){
        super();
    }

    @Override
    protected Vector3f updateMyForces(){
        return new Vector3f(0f,0f,0f);
    }
    
    @Override
    protected Vector3f updateMyTorque(){
        return new Vector3f(0f,0f,0f);
    }
}
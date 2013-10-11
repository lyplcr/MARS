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
 *
 * @author Thomas Tosik
 */
@XmlRootElement
public class Monsun3 extends BasicAUV{
    
    /**
     * 
     * @param simstate 
     */
    public Monsun3(SimState simstate){
        super(simstate);
    }

    /**
     *
     */
    public Monsun3(){
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

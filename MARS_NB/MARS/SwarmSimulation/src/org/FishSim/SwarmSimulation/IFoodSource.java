/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.FishSim.SwarmSimulation;

import com.jme3.math.Vector3f;

/**
 *
 * @author Acer
 */
public interface IFoodSource {
     public Vector3f getLocalTranslation();
     
     public void feed();
     
     
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.uwCommManager.helpers;

/**
 *
 * @author Jasper Schwinghammer
 */
public class AmbientNoiseHelper {
    
    
    
    private AmbientNoiseHelper(){
        
    }
    
    public static float calculateAmbientNoise(float frequence, float shippingFactor, float windSpeed) {
        float nT = (float) (17-30*Math.log10(frequence));
        float nS = (float) (40+ 20*(shippingFactor-0.5)+26*Math.log10(frequence)-60*Math.log10(frequence+0.03));
        float nW = (float) (50+7.5*windSpeed+20*Math.log10(frequence)-40*Math.log10(frequence+ 0.4));
        float nTH= (float) (-15+20*Math.log(frequence));
        return nT + nS + nW + nTH;
    }
    
}

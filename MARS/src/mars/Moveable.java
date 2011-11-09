/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars;

import com.jme3.math.Vector3f;

/**
 * Use this interface if you want to move sensors/actuators with servos for example
 * @author Thomas Tosik
 */
public interface Moveable {
    public void updateRotation(Vector3f rotation_axis, float alpha);
    public void updateTranslation(Vector3f translation_axis, Vector3f new_realative_position);
}
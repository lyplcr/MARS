/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.states;

import java.awt.Event;
import java.util.EventListener;

/**
 *
 * @author Thomas Tosik <tosik at iti.uni-luebeck.de>
 */
public interface AppStateListener extends EventListener {

    /**
     *
     * @param e
     */
    public void advertisement(Event e);
}

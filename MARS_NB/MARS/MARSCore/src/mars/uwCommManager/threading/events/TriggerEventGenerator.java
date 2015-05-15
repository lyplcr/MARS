/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.uwCommManager.threading.events;

import com.jme3.math.Vector3f;
import java.util.LinkedList;
import java.util.List;

/**
 * @version 0.1
 * @author Jasper Schwinghammer
 */
public class TriggerEventGenerator {
    
    /**
     * The listeners to the TriggerEvents
     */
    private List<TriggerEventListener> listeners;
    
    /**
     * Basic constructor does nothing special at all
     * @since 0.1
     */
    public TriggerEventGenerator() {
        this.listeners = new LinkedList<TriggerEventListener>();
    }
    
    /**
     * Add a listener, Listeners must implement the TriggerEventListener interface
     * @since 0.1
     * @param listener 
     */
    public void addListener(TriggerEventListener listener) {
        listeners.add(listener);
    }
    
    /**
     * remove a listener 
     * @since 0.1
     * @param listener 
     */
    public void removeListener(TriggerEventListener listener) {
        listeners.remove(listener);
    }
    
    private void fireEvent(ATriggerEvent e) {
        for(TriggerEventListener i : listeners) {
            i.triggerEventHappened(e);
        }
    }
    
    
    public void fireNewTraceHitAUVEvent(Object source, String sourceAUVName, String targetAUVName, List<Vector3f> traces,boolean surfaceFirst) {
        ATriggerEvent e = new TraceHitAUVEvent(source, CommunicationEventConstants.TRACE_HIT_AUV_EVENT, sourceAUVName, targetAUVName, traces,surfaceFirst);
        fireEvent(e);
    }
    
}
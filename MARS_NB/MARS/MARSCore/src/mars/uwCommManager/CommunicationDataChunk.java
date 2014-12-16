/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.uwCommManager;


import java.util.PriorityQueue;

/**
 * This class contains a chunk of a message that was send by a modem.
 * While traveling the message will be altered by noise and other sources
 * TODO: No access to message yet
 * @version 0.1
 * @author Jasper Schwinghammer
 */
public class CommunicationDataChunk {
    
    private String messageDataChunk;
    private float distanceTraveled;
    private PriorityQueue<Float> triggerDistances;
    
    private final float MAX_DISTANCE;
    private boolean dead = false;
    
    /**
     * Create a new CommunicationDataChunk that will live as long as the distance traveled does not exceed the maximum propagation distance of the modem
     * @param messageDataChunk The chunk of data
     * @param triggerDistances The distances of all the paths between our AUVs
     * @param maxDistance the maximum distance of the modem
     */
    public CommunicationDataChunk(String messageDataChunk, PriorityQueue<Float> triggerDistances, float maxDistance) {
        this.MAX_DISTANCE = maxDistance;
        this.messageDataChunk = messageDataChunk;
        this.distanceTraveled = 0f;
        this.triggerDistances = triggerDistances;

       
    }
    
    /**
     * Should be called each tick
     * @param distance the distance since last tick
     */
    public synchronized void addDistance(float distance) {
        distanceTraveled +=distance;
        if(distanceTraveled > MAX_DISTANCE) dead = true;
    }
    
    /**
     * If we find a new path between two AUV's that our already sent message should care about we can add it.
     * Only distances are longer then the already traveled distance will be added.
     * @param triggerDistance the distance to the AUV
     */
    public synchronized void addtriggerDistance(float triggerDistance) {
        if(triggerDistance>=distanceTraveled) {
            triggerDistances.add(triggerDistance);
        }
    }
    
    /**
     * Get the travled distance
     * @return the travled distance
     */
    public synchronized float getDistanceTravled() {
        return distanceTraveled;
    }
    
    /**
     * Check if the message already exceeded its lifetime. Should be used to remove it from the processingQueue.
     * @return if the message already exceeded its lifetime
     */
    public synchronized boolean isDead(){
        return dead;
    }
}

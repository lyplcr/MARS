/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.uwCommManager.helpers;

/**
 * This class holds pure information about a certain data chunk
 * @version 1.0
 * @author Jasper Schwinghammer
 */
public class DataChunkIdentifier {
    
    private int floorBounces, surfaceBounces;
    private float frequence;
    private long startTime;
    private String AUV_Name, messageIdentifier;
    
    public DataChunkIdentifier() {
        floorBounces = 0;
        surfaceBounces = 0;
        frequence = 0;
        startTime = 0;
        AUV_Name = "";
        messageIdentifier = "";
    }
    
    public DataChunkIdentifier(String AUV_Name, long startTime, String messageIdentifier) {
        this.AUV_Name = AUV_Name;
        this.startTime = startTime;
        this.messageIdentifier = messageIdentifier;
        floorBounces = 0;
        surfaceBounces = 0;
        frequence = 0;
    }

    /**
     * @return the floorBounces
     */
    public int getFloorBounces() {
        return floorBounces;
    }

    /**
     * @param floorBounces the floorBounces to set
     */
    public void setFloorBounces(int floorBounces) {
        this.floorBounces = floorBounces;
    }

    /**
     * @return the surfaceBounces
     */
    public int getSurfaceBounces() {
        return surfaceBounces;
    }

    /**
     * @param surfaceBounces the surfaceBounces to set
     */
    public void setSurfaceBounces(int surfaceBounces) {
        this.surfaceBounces = surfaceBounces;
    }

    /**
     * @return the frequence
     */
    public float getFrequence() {
        return frequence;
    }

    /**
     * @param frequence the frequence to set
     */
    public void setFrequence(float frequence) {
        this.frequence = frequence;
    }

    /**
     * @return the startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the AUV_Name
     */
    public String getAUV_Name() {
        return AUV_Name;
    }

    /**
     * @param AUV_Name the AUV_Name to set
     */
    public void setAUV_Name(String AUV_Name) {
        this.AUV_Name = AUV_Name;
    }

    /**
     * @return the messageIdentifier
     */
    public String getMessageIdentifier() {
        return messageIdentifier;
    }

    /**
     * @param messageIdentifier the messageIdentifier to set
     */
    public void setMessageIdentifier(String messageIdentifier) {
        this.messageIdentifier = messageIdentifier;
    }
}

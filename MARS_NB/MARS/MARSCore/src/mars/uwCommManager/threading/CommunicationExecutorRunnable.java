/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.uwCommManager.threading;

import mars.uwCommManager.helpers.CommunicationComputedDataChunk;
import mars.uwCommManager.helpers.CommunicationDataChunk;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import mars.sensors.CommunicationMessage;
import mars.uwCommManager.helpers.DistanceTrigger;
import org.openide.util.Exceptions;

/**
 * The new runnable is to be used with the Executer class from java.util.concurrent
 * There should be one instance of this for each AUV with a modem
 * @version 0.1
 * @author Jasper Schwinghammer
 */
public class CommunicationExecutorRunnable implements Runnable{
    
    /**
     * The bandwidth of the modem this AUV is using
     */
    private final float MODEM_BANDWIDTH;
    /**
     * the ticks per secound
     */
    private final float RESOLUTION;
    /**
     * the bandwidth we have in each of our timeframes
     */
    private final float BANDWIDTH_PER_TICK;
    
    /**
     * chunks that could not yet be sent due to bandwidthlimitation
     */
    private LinkedList<CommunicationDataChunk> waitingChunks;
    
    /**
     * chunks that are processed at the moment
     */
    private LinkedList<CommunicationDataChunk> sentChunks;
    
    
    /**
     * interface to the CommuncationState
     */
    private ConcurrentLinkedQueue<CommunicationMessage> newMessages = null;
    /**
     * interface to the MultipathPropagationModule
     */
    private volatile List<CommunicationComputedDataChunk> computedMessages = null;
    
    /**
     * The distanceTriggers that are added to all CommuncationMessages that are moved to computeMessages
     */
    private volatile List<DistanceTrigger> distanceTriggers = null;
    
    /**
     * Construct a new CommuncationExecutorRunnable for a AUV
     * @since 0.1
     * @param modem_bandwidth the maximum bandwidth the modem has in kilobyte per secound
     * @param resolution the ticks per secound
     */
    public CommunicationExecutorRunnable(float modem_bandwidth, int resolution) {
        MODEM_BANDWIDTH = modem_bandwidth;
        RESOLUTION = resolution;
        BANDWIDTH_PER_TICK = MODEM_BANDWIDTH/RESOLUTION;
        newMessages = new ConcurrentLinkedQueue<CommunicationMessage>();
        computedMessages = new LinkedList<CommunicationComputedDataChunk>();
        
        waitingChunks = new LinkedList();
        sentChunks = new LinkedList();
        distanceTriggers = new LinkedList();
    }
    
    

    /**
     * three steps:
     * transform all new Communcation messages to CommunicationDataChunks
     * take the next pending DataChunk and add it to the sent chunks
     * make a tick with all sent chunks
     * @since 0.1
     */
    @Override
    public void run() {
        computeAllNewMessages();
        if(!waitingChunks.isEmpty()) sentChunks.add(waitingChunks.poll());
        computeSentChunks();
    }
    
    /**
     * Make a step with all sent chunks kill all chunks that have reached maximum range
     * @since 0.1
     * 
     */
    private void computeSentChunks() {
        
        try {
            float distanceSinceLastTick = 10f;
            List<CommunicationDataChunk> deadChunks = new LinkedList();
            
        
            for(CommunicationDataChunk chunk : sentChunks) {
                chunk.addDistance(distanceSinceLastTick);
                while(chunk.hasNextTrigger()) {
                    CommunicationComputedDataChunk cChunk = chunk.evalNextTrigger();
                    synchronized(this) {
                        computedMessages.add(cChunk);
                    }
                }
                if(chunk.isDead()) deadChunks.add(chunk);
            }
            sentChunks.removeAll(deadChunks);
        } catch(Exception e) {
            Exceptions.printStackTrace(e);
        }
        

    }
   
    /**
     * 
     * take all new enqueued CommunicationMessages and converts them into CommuncationDataChunks
     * @since 0.1
     */
    private void computeAllNewMessages() {
        while(newMessages.peek() != null) {
            CommunicationMessage msg = newMessages.poll();
            try {
                byte[] msgByte = msg.getMsg().getBytes("UTF-8");
                //System.out.println("msgByte: "+ msgByte.length);
                int chunkCount = (int) Math.ceil(((double)msgByte.length) / (BANDWIDTH_PER_TICK*1000));
                //System.out.println("Chunk Count: " + chunkCount);
                for(int i = 0; i<chunkCount; i++) {
                    CommunicationDataChunk chunk = null;
                    if(i != chunkCount - 1) {
                        chunk = new CommunicationDataChunk(
                                Arrays.copyOfRange(msgByte, (int) (i*(BANDWIDTH_PER_TICK*1000)), (int)((i+1)*(BANDWIDTH_PER_TICK*1000))-1),
                                new PriorityQueue<DistanceTrigger>() , 100);
                    } else {
                        chunk = new CommunicationDataChunk(
                                Arrays.copyOfRange(msgByte, (int) (i*(BANDWIDTH_PER_TICK*1000)), msgByte.length),
                                new PriorityQueue<DistanceTrigger>(), 100);
                    }
                    chunk.addDistanceTriggers(distanceTriggers);
                    //to emphasize that we will use the list as queue I use the queue methods instead of LinkedList.add
                    waitingChunks.offer(chunk);
                    
                }
            } catch (UnsupportedEncodingException ex) {
                Exceptions.printStackTrace(ex);
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
        }
    }
    
    /**
     * add a message to the queue
     * @since 0.1
     * @param msg 
     */
    public void assignMessage(CommunicationMessage msg) {
        newMessages.add(msg);
    }
    
    /**
     * get all computed messages since last call
     * @since 0.1
     * @return the computed messages
     */
    public synchronized List<CommunicationComputedDataChunk> getComputedMessages() {
        List<CommunicationComputedDataChunk> returnList = new LinkedList(computedMessages);
        computedMessages.clear();
        return returnList;
    }
    
    /**
     * setDistanceTriggers for the next messages that are converted
     * @since 0.1
     * @param triggers 
     */
    public void setDistanceTriggers(List<DistanceTrigger> triggers) {
        this.distanceTriggers = triggers;
    }

    
}
